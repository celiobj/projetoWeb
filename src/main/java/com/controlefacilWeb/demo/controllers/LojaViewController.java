package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.LojaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lojas")
public class LojaViewController {

    private final LojaController lojaController;

    public LojaViewController() {
        this.lojaController = new LojaController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<LojaModel> lojas = converterLista(lojaController.ListarTodasLojas());
        model.addAttribute("lojas", lojas);
        model.addAttribute("totalLojas", lojas.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "loja/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("loja", new LojaModel());
        return "loja/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LojaModel loja, RedirectAttributes redirectAttributes) {
        try {
            int resultado = lojaController.CadastrarLoja(loja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Loja cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar loja.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/lojas";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        LojaModel loja = lojaController.ProcurarLoja(id);
        if (loja == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Loja não encontrada.");
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lojas";
        }
        model.addAttribute("loja", loja);
        return "loja/formulario";
    }

    @SuppressWarnings("rawtypes")
    private List<LojaModel> converterLista(ArrayList<ArrayList> rows) {
        List<LojaModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                LojaModel l = new LojaModel();
                l.setCodigoLoja(Integer.parseInt(row.get(0).toString()));
                l.setNome(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                lista.add(l);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
