package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.*;
import br.edu.ifpb.agora.service.ProfessorService;
import br.edu.ifpb.agora.service.PadraoProjeto.DB4O;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentService;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentServiceAtaReuniao;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentServiceParecerProcesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.edu.ifpb.agora.service.CoordenadorService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.security.Principal;
import java.util.List;




@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath() {
        return Arrays.asList("/css/main.css", "/css/coordenador/listagem.css");
    }


    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private DocumentServiceAtaReuniao documentoServiceAta;

    @Autowired
    private DocumentServiceParecerProcesso documentoServiceParecer;

    private DB4O db4o = DB4O.getInstance();

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

    @GetMapping("/processo")
    public ModelAndView processos(ModelAndView modelAndView, Principal principal) {
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");

        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.listarTodosProcessosDoColegiado(principal));

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath());
        return modelAndView;
    }

    @GetMapping("/processo/consultar")
    public ModelAndView processosConsultar(@RequestParam(name = "filtro") String filtro, ModelAndView modelAndView, Principal principal) {
        pathTo.put("listar", "/coordenador/processo");
        pathTo.put("logout", "/auth/logout");

        modelAndView.setViewName("coordenador/listagem-processos");
        modelAndView.addObject("processos", coordenadorService.filtro(principal, filtro));

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath());
        return modelAndView;
    }

    @GetMapping("/processo/{id}/relator")
    public ModelAndView getProcesso(@PathVariable("id") Long idProcesso, ModelAndView mav) {
        mav.setViewName("coordenador/adicionar-relator");
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
    public ModelAndView getSessoes(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/listagem-sessoes");
        mav.addObject("reunioes", coordenadorService.getReuniaoDoColegiado(principal));
        return mav;
    }
    
    @GetMapping("/sessao/cadastro")
    public ModelAndView getCadastroSessao(Principal principal, ModelAndView mav) {
        mav.setViewName("coordenador/cadastro-sessao");
        mav.addObject("reuniao", coordenadorService.getReuniaoCadastro(principal));
        mav.addObject("processos", coordenadorService.getProcessosEmMemoria(principal));
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

    @GetMapping("reuniao/{id}/ata/documento/{idDoc}")
    public ResponseEntity<byte[]> getDocumentoParecer(@PathVariable("idDoc") Long idDoc) {
        Documento documento = documentoServiceAta.getDocumento(idDoc);
        System.out.println("chegando aqui");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNome() + "\"")
                .body(documento.getDados());
    }
    
}
