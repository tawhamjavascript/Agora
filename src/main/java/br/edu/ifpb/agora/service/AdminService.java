package br.edu.ifpb.agora.service;

import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.*;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.AdicionarProfessorHandler;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.AlterarCoordenadorParaProfessorHandler;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.AlterarProfessorParaCoordenadorHandler;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.AtualizarCoordenadorHandler;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.BaseHandler;
import br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility.CopiandoDadosSalvandoSenha;
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
        BaseHandler firstHandler = null;
        professor.setAdmin(false);
        PasswordEncoder hash = new BCryptPasswordEncoder();
        if (professor.isCoordenador()) {
            
            Professor atualCoordenador = professorRepository.findByCoordenadorTrueAndCursoId(professor.getCurso().getId());
            if (atualCoordenador != null && professor.getId() != atualCoordenador.getId()) {
                firstHandler = new AtualizarCoordenadorHandler(professorRepository, authorityRepository, userRepository, atualCoordenador, professor);
                
            } 
        }
        if (professor.getId() == null) {
            BaseHandler adicionarProfessorHandler = new AdicionarProfessorHandler(professorRepository, authorityRepository, userRepository, null, professor);
            if (firstHandler == null) {
                firstHandler = adicionarProfessorHandler;
            }
            else {
                firstHandler.setNextHandler(adicionarProfessorHandler);
            }

        }
        
        else {
            System.out.println("Professor existe no banco");
            Professor professorBD = professorRepository.findByMatricula(professor.getMatricula());
            
            System.out.println(" " + professorBD.isCoordenador() + " " + professor.isCoordenador());
            
            if(!professorBD.isCoordenador() && professor.isCoordenador()) {
                System.out.println("Professor no BD não é coordenador");
                BaseHandler alterarProfessorParaCoordenador = new AlterarProfessorParaCoordenadorHandler(professorRepository, authorityRepository, userRepository, professorBD, professor);

                if (firstHandler == null) {
                    firstHandler = alterarProfessorParaCoordenador;
              
                }
                else {
                    if (firstHandler.getNextHandler() == null) {
                        firstHandler.setNextHandler(alterarProfessorParaCoordenador);
                    }
                    else {
                        BaseHandler lastHandler = firstHandler;
                        while(lastHandler.getNextHandler() != null) {
                            lastHandler = lastHandler.getNextHandler();
                        }
                        lastHandler.setNextHandler(alterarProfessorParaCoordenador); 
                    }
                }
            }
            else if (professorBD.isCoordenador() && !professor.isCoordenador()) {
                BaseHandler alterarCoordenadorParaProfessor = new AlterarCoordenadorParaProfessorHandler(professorRepository, authorityRepository, userRepository, professorBD, professor);
                if (firstHandler == null) {
                    firstHandler = alterarCoordenadorParaProfessor;
                }
                else {
                    if (firstHandler.getNextHandler() == null) {
                        firstHandler.setNextHandler(alterarCoordenadorParaProfessor);
                    }
                    else {
                        BaseHandler lastHandler = firstHandler;
                        while(lastHandler.getNextHandler() != null) {
                            lastHandler = lastHandler.getNextHandler();
                        } 
                        lastHandler.setNextHandler(alterarCoordenadorParaProfessor);
                    }
                }  
            }
            BaseHandler copiarDadosSalvandoSenha = new CopiandoDadosSalvandoSenha(professorRepository, authorityRepository, userRepository, professorBD, professor);
            if (firstHandler == null) {
                firstHandler = copiarDadosSalvandoSenha;
            }
            else {
                if (firstHandler.getNextHandler() == null) {
                    firstHandler.setNextHandler(copiarDadosSalvandoSenha);
                }
                else {
                    BaseHandler lastHandler = firstHandler;
                    while(lastHandler.getNextHandler() != null) {
                        lastHandler = lastHandler.getNextHandler();
                    } 
                    lastHandler.setNextHandler(copiarDadosSalvandoSenha);
                }
            }
        }
        do {
            System.out.println("entrando no do while");
            firstHandler.handle();
            firstHandler = firstHandler.getNextHandler();
        }
        while(firstHandler != null);
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
