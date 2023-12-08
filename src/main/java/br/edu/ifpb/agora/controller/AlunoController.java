package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Assunto;
import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.StatusEnum;
import br.edu.ifpb.agora.model.Usuario;
import br.edu.ifpb.agora.repository.AssuntoRepository;
import br.edu.ifpb.agora.service.AlunoService;
import br.edu.ifpb.agora.service.DocumentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.HttpHeaders;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/aluno")

public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/processo/cadastrar")
    public ModelAndView cadastrarProcesso(ModelAndView mav) {
        mav.setViewName("aluno/tela-aluno-cadastro-processo");
        mav.addObject("processo", new Processo());
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
        if (result.hasErrors()) {
            System.out.println(result.getFieldErrors());
            modelAndView.setViewName("aluno/tela-aluno-cadastro-processo");
            modelAndView.addObject("processo", processo);
            return modelAndView;

        }
        alunoService.cadastraNovoProcesso(principal, processo);
        modelAndView.setViewName("redirect:/aluno/processo/" + processo.getId() + "/upload");
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView consultarProcessos(ModelAndView mav, Principal principal) {
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", alunoService.consultaProcessos(principal));
        return mav;

    }

    @GetMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(@RequestParam(name = "filtro", defaultValue = "") String filtro, @RequestParam(name = "order", defaultValue = "") String order, ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("processos", alunoService.filtrarProcesso(principal, filtro, order));
        modelAndView.setViewName("/aluno/tela-aluno-listagem-processos");
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

    @GetMapping("processo/{id}/documentos/{idDoc}")
    public ResponseEntity<byte[]> getDocumento(@PathVariable("idDoc") Long idDoc) {
        Documento documento = documentService.getDocumento(idDoc);
        System.out.println("chegando aqui");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNome() + "\"")
                .body(documento.getDados());
    }
    
}
