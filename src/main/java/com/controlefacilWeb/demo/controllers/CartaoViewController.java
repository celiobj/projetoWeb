package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.CartaoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cartoes")
public class CartaoViewController {

    private final CartaoController cartaoController;

    public CartaoViewController() {
        this.cartaoController = new CartaoController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<CartaoModel> cartoes = converterLista(cartaoController.ListarTodosCartoes('A'));
        model.addAttribute("cartoes", cartoes);
        model.addAttribute("totalCartoes", cartoes.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "cartao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cartao", new CartaoModel());
        return "cartao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute CartaoModel cartao, RedirectAttributes redirectAttributes) {
        try {
            int resultado = cartaoController.Cadastrar(cartao);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Cartão cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar cartão.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/cartoes";
    }

    @SuppressWarnings("rawtypes")
    private List<CartaoModel> converterLista(ArrayList<ArrayList> rows) {
        List<CartaoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                CartaoModel c = new CartaoModel();
                c.setCodigoCartao(Integer.parseInt(row.get(0).toString()));
                c.setNomeCartao(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                c.setNumeroCartao(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
                c.setIsAtivo(row.size() > 3 && row.get(3) != null && !row.get(3).toString().isEmpty()
                        ? row.get(3).toString().charAt(0) : 'A');
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
