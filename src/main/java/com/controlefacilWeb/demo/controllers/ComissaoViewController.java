package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.ComissaoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comissoes")
public class ComissaoViewController {

    private final ComissaoController comissaoController;

    public ComissaoViewController() {
        this.comissaoController = new ComissaoController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<ComissaoModel> comissoes = converterLista(comissaoController.listarTodasComissoes());
        model.addAttribute("comissoes", comissoes);
        model.addAttribute("totalComissoes", comissoes.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "comissao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("comissao", new ComissaoModel());
        return "comissao/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ComissaoModel comissao, RedirectAttributes redirectAttributes) {
        try {
            int resultado = comissaoController.CadastrarComissao(comissao);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Comissão cadastrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar comissão.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/comissoes";
    }

    @PostMapping("/{id}/ativar")
    public String alterarStatus(@PathVariable int id,
                                @RequestParam char status,
                                @RequestParam int tipo,
                                RedirectAttributes redirectAttributes) {
        try {
            int resultado = comissaoController.AlterarSatus(id, status, tipo);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Status da comissão atualizado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar status da comissão.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/comissoes";
    }

    @SuppressWarnings("rawtypes")
    private List<ComissaoModel> converterLista(ArrayList<ArrayList> rows) {
        List<ComissaoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                ComissaoModel c = new ComissaoModel();
                c.setCodigoComissao(Integer.parseInt(row.get(0).toString()));
                c.setDescricao(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                c.setValor(row.size() > 2 && row.get(2) != null ? Double.parseDouble(row.get(2).toString()) : 0.0);
                c.setTipo(row.size() > 3 && row.get(3) != null ? Integer.parseInt(row.get(3).toString()) : 0);
                c.setIsAtivo(row.size() > 4 && row.get(4) != null && !row.get(4).toString().isEmpty()
                        ? row.get(4).toString().charAt(0) : 'S');
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
