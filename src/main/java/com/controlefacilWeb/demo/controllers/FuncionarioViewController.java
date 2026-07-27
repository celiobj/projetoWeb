package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.FuncionarioModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioViewController {

    private final FuncionarioController funcionarioController;

    public FuncionarioViewController() {
        this.funcionarioController = new FuncionarioController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<FuncionarioModel> funcionarios = converterLista(funcionarioController.ListarTodosFuncionarios('A', 1));
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("totalFuncionarios", funcionarios.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "funcionario/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", new FuncionarioModel());
        return "funcionario/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute FuncionarioModel funcionario,
                         @RequestParam(defaultValue = "1") int codigoLoja,
                         RedirectAttributes redirectAttributes) {
        try {
            int resultado = funcionarioController.CadastrarFuncionario(funcionario, codigoLoja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar funcionário.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/funcionarios";
    }

    @SuppressWarnings("rawtypes")
    private List<FuncionarioModel> converterLista(ArrayList<ArrayList> rows) {
        List<FuncionarioModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                FuncionarioModel f = new FuncionarioModel();
                f.setCodigoFuncionario(Integer.parseInt(row.get(0).toString()));
                f.setNome(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                f.setCodigoUsuario(row.size() > 2 && row.get(2) != null ? Integer.parseInt(row.get(2).toString()) : 0);
                lista.add(f);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
