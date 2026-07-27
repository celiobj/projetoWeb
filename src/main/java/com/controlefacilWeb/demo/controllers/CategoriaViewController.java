package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.CategoriaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaViewController {

    private final CategoriaController categoriaController;

    public CategoriaViewController() {
        this.categoriaController = new CategoriaController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<CategoriaModel> categorias = converterLista(categoriaController.ListarTodasCategorias('A'));
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "categoria/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new CategoriaModel());
        return "categoria/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute CategoriaModel categoria, RedirectAttributes redirectAttributes) {
        try {
            int resultado = categoriaController.CadastrarCategoria(categoria);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Categoria cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar categoria.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/categorias";
    }

    @SuppressWarnings("rawtypes")
    private List<CategoriaModel> converterLista(ArrayList<ArrayList> rows) {
        List<CategoriaModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                CategoriaModel c = new CategoriaModel();
                c.setCodigo(Integer.parseInt(row.get(0).toString()));
                c.setNomeCategoria(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                c.setAtivo(row.size() > 2 && row.get(2) != null && row.get(2).toString().equalsIgnoreCase("S"));
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
