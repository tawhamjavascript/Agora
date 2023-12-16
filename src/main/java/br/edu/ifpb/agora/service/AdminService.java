package br.edu.ifpb.agora.service;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;

@Service
public class AdminService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired ColegiadoRepository colegiadoRepository;

    @Autowired AuthorityRepository authorityRepository;

    @Autowired UserRepository userRepository;

    @Transactional
    public void registerTeacher(Professor professor) {
        professor.setAdmin(false);
        PasswordEncoder hash = new BCryptPasswordEncoder();
        if (professor.isCoordenador()) {
            Professor atualCoordenador = professorRepository.findByCoordenadorTrueAndCursoId(professor.getCurso().getId());
            System.out.println(atualCoordenador);
            if (atualCoordenador != null && professor.getId() != atualCoordenador.getId()) {
                atualCoordenador.setCoordenador(false);
                List<Authority> authorities = atualCoordenador.getUser().getAuthorities();
                for(int i = 0; i < authorities.size(); i++) {
                    System.out.println("Entrando no for que verifica se tem o role_coordenador ");

                    if(authorities.get(i).getAuthority().equals("ROLE_COORDENADOR")) {
                        System.out.println("Entrando no if que verifica se tem o role_coordenador");
                        
                        authorityRepository.delete(authorities.remove(i));
                        userRepository.save(atualCoordenador.getUser());
                    }

                }
            }
            
        }
        if (professor.getId() == null) {
            System.out.println("o id é nulo");
            String passwordEncrypt = hash.encode((CharSequence)professor.getSenha());
            User user = new User(professor.getMatricula(), passwordEncrypt);
            user.setEnabled(true);

            List<Authority> authorities = new ArrayList<Authority>(2);
            authorities.add(new Authority(user, "ROLE_PROFESSOR"));
            if (professor.isCoordenador()) {
                authorities.add(new Authority(user, "ROLE_COORDENADOR"));
            }
            user.setAuthorities(authorities);
            professor.setUser(user);
            professorRepository.save(professor);

        }
        
        else {
            System.out.println("Professor existe no banco");
            Professor professorBD = professorRepository.findByMatricula(professor.getMatricula());
            
            System.out.println(" " + professorBD.isCoordenador() + " " + professor.isCoordenador());
            
            if(!professorBD.isCoordenador() && professor.isCoordenador()) {
                System.out.println("Professor no BD não é coordenador");
                User user = professorBD.getUser();
                user.getAuthorities().add(new Authority(user, "ROLE_COORDENADOR"));
            }
            else if (professorBD.isCoordenador() && !professor.isCoordenador()) {
                System.out.println("Professor no BD é coordenador");
                User user = professorBD.getUser();
                List<Authority> authorities = user.getAuthorities();
                for(int i = 0; i < authorities.size(); i++) {
                    if(authorities.get(i).getAuthority().equals("ROLE_COORDENADOR")) {
                        System.out.println("retirando flag de coordenador");
                        authorityRepository.delete(authorities.remove(i));
                        userRepository.save(user);
                    }
                    
                }
                user.getAuthorities().forEach((authority) -> System.out.println(authority));
                
            }
            
            BeanUtils.copyProperties(professor, professorBD, "senha", "processos", "matricula", "user", "colegiado", "votos");
            boolean matchPassword = hash.matches(professor.getSenha(), professorBD.getSenha());
            if (!matchPassword) {
                String passwordEncrypt = hash.encode(professor.getSenha());
                professorBD.setSenha(passwordEncrypt);
            }
            professorRepository.save(professorBD);
        }
    }

    @Transactional
    public void removeTeacher(Long id) {
        Professor professor = professorRepository.findById(id).get();
        professor.getUser().setEnabled(false);
        professorRepository.delete(professor);
    }

    @Transactional
    public void updateTeacher(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> allTeachers() {

        return professorRepository.findAll();
    }

    public List<Professor> allTeachersOfCourse(Long colegiadoId) {
        Colegiado colegiado = getColegiado(colegiadoId);
        List<Professor> result = new ArrayList<Professor>();

        professorRepository.findAllByCursoId(colegiado.getCurso().getId()).forEach(professor -> {
            if (!colegiado.getMembros().contains(professor) && !professor.isCoordenador()) {
                result.add(professor);
            }
        });

        return result;
    }

    public Professor getTeacher(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerStudent(Aluno aluno) {
        aluno.setAdmin(false);
        PasswordEncoder hash = new BCryptPasswordEncoder();
        if (aluno.getId() == null) {
            String passwordEncrypt = hash.encode((CharSequence)aluno.getSenha());
            User user = new User(aluno.getMatricula(), passwordEncrypt);
            user.setEnabled(true);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_ALUNO")));
            aluno.setUser(user);
            alunoRepository.save(aluno);

        }

        else {
            Aluno alunoBD = alunoRepository.findByMatricula(aluno.getMatricula());
            BeanUtils.copyProperties(aluno, alunoBD, "senha", "processos", "matricula", "user");
            boolean matchPassword = hash.matches(aluno.getSenha(), alunoBD.getSenha());
            if (!matchPassword) {
                String passwordEncrypt = hash.encode(aluno.getSenha());
                alunoBD.setSenha(passwordEncrypt);
            }
            alunoRepository.save(alunoBD);
        }
    }
    @Transactional
    public void removeStudent(Long id) {
        Aluno aluno = alunoRepository.findById(id).get();
        aluno.getUser().setEnabled(false);
        alunoRepository.delete(aluno);
    }
    @Transactional
    public void updateStudent(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> allStudent() {
        return alunoRepository.findAll();
    }

    public Aluno getStudent(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    @Transactional
    public void removeCourse(Long id) {
        cursoRepository.delete( cursoRepository.findById(id).get() );

    }

    @Transactional
    public void updateCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> allCourses() {

        return cursoRepository.findAll();
    }

    public Curso getCourse(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }


    @Transactional
    public void registerSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    @Transactional
    public void removeSubject(Long id) {

        assuntoRepository.delete(assuntoRepository.findById(id).get());
    }

    @Transactional
    public void updateSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    public List<Assunto> allSubjects() {
        return this.assuntoRepository.findAll();
    }

    public Assunto getSubject(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }

    public List<Colegiado> allColegiados() {
        return colegiadoRepository.findAll();
    }

    public Colegiado getColegiado(Long id) {
        return colegiadoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void salvarColegiado(Colegiado colegiado) {
        if (colegiado.getId() == null) {
            colegiadoRepository.save(colegiado);

        }
        else {
            Colegiado colegiadoBD = colegiadoRepository.findById(colegiado.getId()).get();
            BeanUtils.copyProperties(colegiado, colegiadoBD, "reunioes", "curso");
        
        }
    }

    @Transactional
    public void excluir(Long id) {
        colegiadoRepository.deleteById(id);
    }

    @Transactional
    public void adicionarMembro(Long ColegiadoId, Long professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().add(professor);
        professor.setColegiado(colegiado);
        salvarColegiado(colegiado);
    }

    @Transactional
    public void deletarMembro(Long ColegiadoId, Long professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().remove(professor);
        professor.setColegiado(null);
        professorRepository.save(professor);
        colegiadoRepository.save(colegiado);
    }
}
