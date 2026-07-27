package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.ConfiguracaoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracaoViewController {

    private final ConfiguracaoController configuracaoController;

    public ConfiguracaoViewController() {
        this.configuracaoController = new ConfiguracaoController();
    }

    @GetMapping
    public String exibir(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        ConfiguracaoModel configuracao = configuracaoController.BuscarConfiguracao(1);
        if (configuracao == null) {
            configuracao = new ConfiguracaoModel();
        }
        model.addAttribute("configuracao", configuracao);
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "configuracao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ConfiguracaoModel configuracao, RedirectAttributes redirectAttributes) {
        try {
            int resultado = configuracaoController.InserirConfiguracao(configuracao);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Configuração salva com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar configuração.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/configuracoes";
    }
}
