package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import br.edu.ifpb.agora.service.PadraoProjeto.DB4O;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentService;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentServiceAtaReuniao;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentServiceParecerProcesso;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.security.Principal;
import java.util.List;
import java.util.Map;





@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath(String page) {
        if(page.equals("listagem")) {
            return Arrays.asList("/css/main.css", "/css/coordenador/listagem.css");
        }
        else if(page.equals("cadastro")) {
            return Arrays.asList("/css/main.css", "/css/coordenador/cadastro.css");
        }
        else if(page.equals("adicionar")) {
            return Arrays.asList("/css/coordenador/adicionar.css");
        }
        else if(page.equals("adicionar-processo-relator")) {
            return Arrays.asList("/css/main.css", "/css/coordenador/adicionar.css");
        }

        return Arrays.asList("/css/coordenador/home.css");
    }


    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private DocumentServiceAtaReuniao documentoServiceAta;

    @Autowired
    private DocumentServiceParecerProcesso documentoServiceParecer;

    private DB4O db4o = DB4O.getInstance();

    @Autowired
    private ReuniaoRepository reuniaoRepository;


    @ModelAttribute("statusItens")
    public List<StatusEnum> getStatus() {
        return List.of(StatusEnum.values());
    }

    @ModelAttribute("relatorItens")
    public List<Professor> getRelator(Principal user) {
        return coordenadorService.listarTodosProfessoresDoColegiado(user);

    }

    @ModelAttribute("alunoItens")
    public List<Aluno> getAluno(Principal principal) {
        
        return coordenadorService.listarTodosAlunosProcesso(principal);

    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("coordenador/home");
        modelAndView.addObject("stylePaths", getPath(""));
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView processos(ModelAndView modelAndView, HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, Principal principal) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Pageable paging = PageRequest.of(page -1, size);
        Page<Processo> processos = coordenadorService.listarTodosProcessosDoColegiado(usuario.getId(), paging);
        NavPage navPage = NavPageBuilder.newNavPage(processos.getNumber() + 1, processos.getTotalElements(), processos.getTotalPages(), size);
        modelAndView.addObject("navPage", navPage);
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");
        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
        modelAndView.addObject("processos", processos);
        modelAndView.addObject("page", "processo");
        return modelAndView;
    }

    @GetMapping("/processo/consultar")
    public ModelAndView processosConsultar(@RequestParam(name = "filtro") String filtro, ModelAndView modelAndView, Principal principal) {
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");

        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.filtro(principal, filtro));

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
        modelAndView.addObject("page", "processo");
        return modelAndView;
    }

    @GetMapping("/processo/{id}/relator")
    public ModelAndView getProcesso(@PathVariable("id") Long idProcesso, ModelAndView mav) {
        mav.setViewName("coordenador/adicionar-relator");
        mav.addObject("stylePaths", getPath("adicionar-processo-relator"));
        mav.addObject("processo", coordenadorService.getProcesso(idProcesso));
        return mav;
    }

    @PostMapping("/processo/relator")
    public ModelAndView setRelator(Processo processo, ModelAndView mav) {
        coordenadorService.salvarProcesso(processo);
        mav.setViewName("redirect:/coordenador/processo");
        return mav;
    }

    @GetMapping("/sessao")
    public ModelAndView getSessoes(Principal principal, ModelAndView mav, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page -1, size);
        Page<Reuniao> reunioes = coordenadorService.getReuniaoDoColegiado(principal, paging);
        NavPage navPage = NavPageBuilder.newNavPage(reunioes.getNumber(), reunioes.getTotalElements(), reunioes.getTotalPages(), size);
        mav.addObject("navPage", navPage);
        pathTo.put("cadastrar", "/coordenador/sessao/cadastro");
        pathTo.put("listar", "/coordenador/sessao");
        pathTo.put("home", "/coordenador/home");

        mav.setViewName("coordenador/listagem-sessoes");
        mav.addObject("reunioes", reunioes);

        mav.addObject("stylePaths", getPath("listagem"));
        mav.addObject("caminho", pathTo);
        mav.addObject("page", "sessao");
        return mav;
    }
    
    @GetMapping("/sessao/cadastro")
    public ModelAndView getCadastroSessao(Principal principal, ModelAndView mav) {
        pathTo.put("cadastrar", "/coordenador/sessao/cadastro");
        pathTo.put("listar", "/coordenador/sessao");
        pathTo.put("home", "/coordenador/home");

        mav.setViewName("coordenador/cadastro-sessao");
        mav.addObject("reuniao", coordenadorService.getReuniaoCadastro(principal));
        mav.addObject("processos", coordenadorService.getProcessosEmMemoria(principal));

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        mav.addObject("page", "sessao");
        return mav;
    }

    @PostMapping("/sessao/cadastro")
    public ModelAndView CadastroReuniao(Reuniao reuniao, ModelAndView mav, Principal principal) {
        coordenadorService.salvarReuniao(principal, reuniao);
        mav.setViewName("redirect:/coordenador/sessao");
        return mav;
           
    }
    

    @GetMapping("/sessao/add/processo")
    public ModelAndView getCadastroProcesso(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/adicionar-processo");
        mav.addObject("stylePaths", getPath("adicionar"));
        mav.addObject("processos", coordenadorService.getProcessosDoCursoEComDecisaoRelator(principal));
        return mav;

    }

    @PostMapping("/sessao/add/processo")
    public ModelAndView salvarProcessoEmMemoria(Long idProcesso, Principal principal, ModelAndView mav) {
        coordenadorService.salvarProcessoEmMemoria(principal, idProcesso);
        mav.setViewName("redirect:/coordenador/sessao/add/processo");
        return mav;
        
    }

    @PostMapping("/sessao/salvarMemoria")
    @ResponseBody
    public ResponseEntity<String> salvarReuniaoMemoria(Principal principal, @RequestBody Reuniao reuniao, HttpEntity<String> httpEntity) {
        System.out.println("Data: " + reuniao.getDataReuniao() + " Horario:" + reuniao.getHorario());
        coordenadorService.salvarReuniaoMemoria(principal, reuniao);
        return new ResponseEntity("{\"mensagem\": \"Reunião salva com sucesso\"}", HttpStatus.OK);

    }

    @GetMapping("sessao/{id}/ata/documento/{idDoc}")
    public ResponseEntity<byte[]> getDocumentoParecer(@PathVariable("idDoc") Long idDoc) {
        Documento documento = documentoServiceAta.getDocumento(idDoc);
        System.out.println("chegando aqui");

        
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNome() + "\"")
                .body(documento.getDados());
    }

    @GetMapping("sessao/{id}/conducao") 
    public ModelAndView getConducaoSessao(@PathVariable("id") Long idReuniao, Principal principal, ModelAndView mav) {


        if (coordenadorService.existeReuniaoEmAndamento(idReuniao, principal)) {
            mav.setViewName("redirect:/coordenador/sessao");
            return mav;
        }
        mav.setViewName("coordenador/conducao-sessao");
        Reuniao reuniao = coordenadorService.getReuniao(idReuniao);
        mav.addObject("reuniao", reuniao);
        mav.addObject("votos", coordenadorService.getVotos(reuniao));

        pathTo.put("cadastrar", "/coordenador/sessao/cadastro");
        pathTo.put("listar", "/coordenador/sessao");
        pathTo.put("home", "/coordenador/home");

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        mav.addObject("page", "sessao");

        return mav;
    }

    @PostMapping("sessao/{id}/professor/{idProfessor}/votar")
    public ModelAndView votar(@PathVariable("id") Long idReuniao, @PathVariable("idProfessor") Long idProfessor, ModelAndView mav, String voto) {
        coordenadorService.salvarVoto(idReuniao, idProfessor, voto);
        mav.setViewName("redirect:/coordenador/sessao/" + idReuniao + "/conducao");
        return mav;
    }

    @GetMapping("sessao/{id}/salvar")
    public ModelAndView salvarSessao(@PathVariable("id") Long idReuniao, ModelAndView mav) {
        boolean result = coordenadorService.finalizarReuniao(idReuniao);
        if (result) {
            mav.setViewName("redirect:/coordenador/sessao");
        } else {
            mav.setViewName("redirect:/coordenador/sessao/" + idReuniao + "/conducao");
        }
        return mav;
    }

    @GetMapping("sessao/{id}/processo/salvar")
    public ModelAndView salvarProcesso(@PathVariable("id") Long idReuniao, ModelAndView mav) {
        coordenadorService.finalizarVotacao(idReuniao);
        mav.setViewName("redirect:/coordenador/sessao/" + idReuniao + "/conducao");
        return mav;
    }

    @PostMapping("sessao/{id}/ata")
    public ModelAndView postMethodName(@RequestParam("file") List<MultipartFile> arquivo,
            @PathVariable("id") Long id, ModelAndView mav) {
    
        documentoServiceAta.save(id, arquivo);
        mav.setViewName("redirect:/coordenador/sessao/" + id + "/conducao");
        return mav;
  
    }

}
