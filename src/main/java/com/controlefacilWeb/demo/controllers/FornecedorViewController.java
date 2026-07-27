package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.FornecedorModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorViewController {

    private final FornecedorController fornecedorController;

    public FornecedorViewController() {
        this.fornecedorController = new FornecedorController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<FornecedorModel> fornecedores = converterLista(fornecedorController.ListarTodosFornecedores());
        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("totalFornecedores", fornecedores.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "fornecedor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new FornecedorModel());
        return "fornecedor/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute FornecedorModel fornecedor, RedirectAttributes redirectAttributes) {
        try {
            int resultado = fornecedorController.CadastrarFornecedor(fornecedor);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Fornecedor cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar fornecedor.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/fornecedores";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable int id, @ModelAttribute FornecedorModel fornecedor,
                          RedirectAttributes redirectAttributes) {
        try {
            fornecedor.setCodigoFornecedor(id);
            int resultado = fornecedorController.AlterarFornecedor(fornecedor);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Fornecedor atualizado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar fornecedor.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/fornecedores";
    }

    @SuppressWarnings("rawtypes")
    private List<FornecedorModel> converterLista(ArrayList<ArrayList> rows) {
        List<FornecedorModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                FornecedorModel f = new FornecedorModel();
                f.setCodigoFornecedor(Integer.parseInt(row.get(0).toString()));
                f.setNome(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                f.setCnpj(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
                lista.add(f);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
