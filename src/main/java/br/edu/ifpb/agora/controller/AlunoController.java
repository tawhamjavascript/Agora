package br.edu.ifpb.agora.controller;


import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aluno")

public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @RequestMapping("/home")
    public String home() {
        return "aluno/home";

    }

    @GetMapping("/processos")
    public Model allProcessos(Model model) {
        model.addAttribute("processos", alunoService.consultaProcessos());
        return model;
    }

    @GetMapping("/processos/{assunto}")
    public Model processosPorAssunto(@PathVariable(value = "assunto") String assunto, Model model) {
        model.addAttribute("processos", alunoService.consultaProcessosPorAssunto(assunto));
        return model;
    }

    @GetMapping("/processos/{assunto}/ordenados")
    public Model processosPorAssuntoOrdenados(@PathVariable(value = "assunto") String assunto, Model model) {
        model.addAttribute("processos", alunoService.consultarProcessosPorAssuntoOrdenados(assunto));
        return model;
    }


    @GetMapping("processos/{status}")
    public Model processosPorStatus(@PathVariable(value = "status") String status, Model model) {
        model.addAttribute("processos", alunoService.consultaProcessosPorStatus(status));
        return model;
    }

    @GetMapping("/processos/{status}")
    public Model processosPorStatusOrdenados(@PathVariable(value = "status") String status, Model model) {
        model.addAttribute("processos", alunoService.consultarProcessosPorStatusOrdenados(status));
        return model;
    }

    @GetMapping("/processos/form")
    public String formProcesso() {
        return "aluno/formProcesso";
    }

    @PostMapping("/processos/form")
    public ModelAndView cadastraProcesso(Processo processo, ModelAndView modelAndView, RedirectAttributes attr) {
        alunoService.cadastraNovoProcesso(processo);
        attr.addFlashAttribute("mensagem", "Processo cadastrado com sucesso!");
        modelAndView.setViewName("redirect:/aluno/processos");
        return modelAndView;
    }
}
