package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.EnderecoModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/enderecos")
public class EnderecoViewController {

    private final EnderecoControler enderecoController;

    public EnderecoViewController() {
        this.enderecoController = new EnderecoControler();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(defaultValue = "C") char origem,
                         @RequestParam(defaultValue = "0") int codigoPessoa,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        PessoaModel pessoa = new PessoaModel(codigoPessoa, "");
        List<EnderecoModel> enderecos = converterLista(enderecoController.ListarTodosEnderecos(origem, pessoa));
        model.addAttribute("enderecos", enderecos);
        model.addAttribute("totalEnderecos", enderecos.size());
        model.addAttribute("origem", origem);
        model.addAttribute("codigoPessoa", codigoPessoa);
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "endereco/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("endereco", new EnderecoModel());
        return "endereco/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute EnderecoModel endereco, RedirectAttributes redirectAttributes) {
        try {
            int resultado = enderecoController.CadastrarEndereco(endereco);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Endereço cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar endereço.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/enderecos";
    }

    @SuppressWarnings("rawtypes")
    private List<EnderecoModel> converterLista(ArrayList<ArrayList> rows) {
        List<EnderecoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                EnderecoModel e = new EnderecoModel();
                e.setCodigo(Integer.parseInt(row.get(0).toString()));
                e.setEndereco(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                e.setTipo(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
                lista.add(e);
            } catch (Exception ex) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
