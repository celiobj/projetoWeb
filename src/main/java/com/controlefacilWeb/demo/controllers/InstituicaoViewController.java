package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.InstituicaoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoViewController {

    private final InstituicaoController instituicaoController;

    public InstituicaoViewController() {
        this.instituicaoController = new InstituicaoController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<InstituicaoModel> instituicoes = converterLista(instituicaoController.ListarTodasInstituicoes('A'));
        model.addAttribute("instituicoes", instituicoes);
        model.addAttribute("totalInstituicoes", instituicoes.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "instituicao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("instituicao", new InstituicaoModel());
        return "instituicao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute InstituicaoModel instituicao, RedirectAttributes redirectAttributes) {
        try {
            int resultado = instituicaoController.Cadastrar(instituicao);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Instituição cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar instituição.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/instituicoes";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable int id, @ModelAttribute InstituicaoModel instituicao,
                          RedirectAttributes redirectAttributes) {
        try {
            int resultado = instituicaoController.Alterar(instituicao, id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Instituição atualizada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar instituição.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/instituicoes";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            int resultado = instituicaoController.Remover(id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Instituição removida com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao remover instituição.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/instituicoes";
    }

    @SuppressWarnings("rawtypes")
    private List<InstituicaoModel> converterLista(ArrayList<ArrayList> rows) {
        List<InstituicaoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                InstituicaoModel i = new InstituicaoModel();
                i.setCodigo(Integer.parseInt(row.get(0).toString()));
                i.setNome(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                i.setTipo(row.size() > 2 && row.get(2) != null ? Integer.parseInt(row.get(2).toString()) : 0);
                i.setAtivo(row.size() > 3 && row.get(3) != null && row.get(3).toString().equalsIgnoreCase("S"));
                lista.add(i);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
