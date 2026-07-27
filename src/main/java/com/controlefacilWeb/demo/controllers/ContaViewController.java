package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.ContaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaViewController {

    private final ContaController contaController;

    public ContaViewController() {
        this.contaController = new ContaController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<ContaModel> contas = converterLista(contaController.ListarTodasContas('A'));
        model.addAttribute("contas", contas);
        model.addAttribute("totalContas", contas.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "conta/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("conta", new ContaModel());
        return "conta/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ContaModel conta, RedirectAttributes redirectAttributes) {
        try {
            int resultado = contaController.Cadastrar(conta);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Conta cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar conta.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/contas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        ContaModel conta = contaController.Procurar(id);
        if (conta == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Conta não encontrada.");
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/contas";
        }
        model.addAttribute("conta", conta);
        return "conta/formulario";
    }

    @PostMapping("/{id}/alterar")
    public String alterar(@PathVariable int id, @ModelAttribute ContaModel conta,
                          RedirectAttributes redirectAttributes) {
        try {
            int resultado = contaController.Alterar(conta, id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Conta atualizada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar conta.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/contas";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            int resultado = contaController.Remover(id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Conta removida com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao remover conta.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/contas";
    }

    @SuppressWarnings("rawtypes")
    private List<ContaModel> converterLista(ArrayList<ArrayList> rows) {
        List<ContaModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                ContaModel c = new ContaModel();
                c.setCodigo(Integer.parseInt(row.get(0).toString()));
                c.setNome(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                c.setSaldo(row.size() > 2 && row.get(2) != null ? Double.parseDouble(row.get(2).toString()) : 0.0);
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
