package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.FormaPagamentoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/formas-pagamento")
public class FormaPagamentoViewController {

    private final FormaPagamentoController formaPagamentoController;

    public FormaPagamentoViewController() {
        this.formaPagamentoController = new FormaPagamentoController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<FormaPagamentoModel> formas = converterLista(formaPagamentoController.ListarTodasFormasPagamento());
        model.addAttribute("formasPagamento", formas);
        model.addAttribute("totalFormas", formas.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "forma-pagamento/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("formaPagamento", new FormaPagamentoModel());
        return "forma-pagamento/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute FormaPagamentoModel formaPagamento, RedirectAttributes redirectAttributes) {
        try {
            int resultado = formaPagamentoController.CadastrarFormaPagamento(formaPagamento);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Forma de pagamento cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar forma de pagamento.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/formas-pagamento";
    }

    @SuppressWarnings("rawtypes")
    private List<FormaPagamentoModel> converterLista(ArrayList<ArrayList> rows) {
        List<FormaPagamentoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                int codigo = Integer.parseInt(row.get(0).toString());
                String descricao = row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "";
                lista.add(new FormaPagamentoModel(codigo, descricao));
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
