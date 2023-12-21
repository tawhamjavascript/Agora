package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.NavPage;
import br.edu.ifpb.agora.model.NavPageBuilder;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.AssuntoRepository;
import br.edu.ifpb.agora.service.AlunoService;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentService;
import br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate.DocumentServiceAnexosProcesso;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import org.springframework.http.HttpHeaders;
import java.security.Principal;
import java.util.List;




@Controller
@RequestMapping("/aluno")

public class AlunoController {

    private HashMap<String, String> pathTo = new HashMap<String, String>();    

    private List<String> getPath(String page) {
    if(page.equals("cadastro")) {
            return Arrays.asList("/css/aluno/main.css", "/css/aluno/cadastro-processo-aluno.css");
        } else if (page.equals("listagem")) {
            return Arrays.asList("/css/aluno/main.css", "/css/aluno/aluno.css");
        }
        return Arrays.asList("/css/aluno/main.css");
    }

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private DocumentServiceAnexosProcesso documentService;

    @GetMapping("/processo/cadastrar")
    public ModelAndView cadastrarProcesso(ModelAndView mav) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");

        mav.setViewName("aluno/tela-aluno-cadastro-processo");
        mav.addObject("processo", new Processo());

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("cadastro"));
        return mav;
    }

    @ModelAttribute("assuntoItens")
    public List<Assunto> getCorrentistas() {
        return assuntoRepository.findAll();
    }

    @ModelAttribute("statusItens")
    public List<StatusEnum> getStatus() {
        return List.of(StatusEnum.values());
    }

    @PostMapping("/processo/cadastrar")
    public ModelAndView salvarProcesso(@Valid Processo processo, BindingResult result, ModelAndView modelAndView, Principal principal) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");
        if (result.hasErrors()) {
            System.out.println(result.getFieldErrors());
            modelAndView.setViewName("aluno/tela-aluno-cadastro-processo");
            modelAndView.addObject("processo", processo);
            modelAndView.addObject("caminho", pathTo);
            modelAndView.addObject("stylePaths", getPath("cadastro"));
            return modelAndView;

        }
        alunoService.cadastraNovoProcesso(principal, processo);
        modelAndView.setViewName("redirect:/aluno/processo/" + processo.getId() + "/upload");
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView consultarProcessos(ModelAndView mav, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, Principal principal) {
        Pageable paging = PageRequest.of(page-1, size);
        Page<Processo> processos = alunoService.consultaProcessos(principal, paging);
        NavPage navPage = NavPageBuilder.newNavPage(processos.getNumber() + 1, processos.getTotalElements(), processos.getTotalPages(), size);
        mav.addObject("navPage", navPage);
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", alunoService.consultaProcessos(principal));

        mav.addObject("caminho", pathTo);
        mav.addObject("stylePaths", getPath("listagem"));
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");

        return mav;

    }

    @GetMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(@RequestParam(name = "filtro", defaultValue = "") String filtro, @RequestParam(name = "order", defaultValue = "") String order, ModelAndView modelAndView, Principal principal) {
        pathTo.put("novoProcesso", "/aluno/processo/cadastrar");        
        pathTo.put("listar", "/aluno/processo");
        pathTo.put("logout", "/auth/logout");
        
        modelAndView.addObject("processos", alunoService.filtrarProcesso(principal, filtro, order));
        modelAndView.setViewName("/aluno/tela-aluno-listagem-processos");

        modelAndView.addObject("caminho", pathTo);
        modelAndView.addObject("stylePaths", getPath("listagem"));
        return modelAndView;
    }

    @GetMapping("/processo/{id}/upload")
    public ModelAndView getFormUpload(@PathVariable("id") Long idProcesso, ModelAndView mav) {
        mav.addObject("idProcesso", idProcesso);
        mav.setViewName("/aluno/tela-aluno-upload-anexos");
        return mav;
    }

    @PostMapping("processo/{id}/upload")
    public ModelAndView upload(@RequestParam("file") List<MultipartFile> arquivo,
            @PathVariable("id") Long id, ModelAndView mav) {
        
        mav.setViewName("redirect:/aluno/processo");

        if (arquivo.size() > 0) {
            System.out.println("Mais de um arquivo");
            documentService.save(id, arquivo);
        }
    
        return mav;
        
    }

    @GetMapping("processo/{id}/anexo/{idDoc}")
    public ResponseEntity<byte[]> getDocumento(@PathVariable("idDoc") Long idDoc) {
        Documento documento = documentService.getDocumento(idDoc);
        System.out.println("chegando aqui");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNome() + "\"")
                .body(documento.getDados());
    }

    @GetMapping("processo/{id}/parecer/documento/{idDoc}")
    public ResponseEntity<byte[]> getDocumentoParecer(@PathVariable("idDoc") Long idDoc) {
        Documento documento = documentService.getDocumento(idDoc);
        System.out.println("chegando aqui");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNome() + "\"")
                .body(documento.getDados());
    }


    
}
