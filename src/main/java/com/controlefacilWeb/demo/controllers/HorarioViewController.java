package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.HorarioModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/horarios")
public class HorarioViewController {

    private final HorarioController horarioController;

    public HorarioViewController() {
        this.horarioController = new HorarioController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(defaultValue = "0") int codigoLoja,
                         @RequestParam(defaultValue = "0") int codigoPessoa,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        PessoaModel pessoa = new PessoaModel(codigoPessoa, "");
        List<HorarioModel> horarios = converterLista(
                horarioController.ListarTodosHorarios('F', pessoa, codigoLoja));
        model.addAttribute("horarios", horarios);
        model.addAttribute("totalHorarios", horarios.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "horario/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("horario", new HorarioModel());
        return "horario/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute HorarioModel horario,
                         @RequestParam(defaultValue = "1") int codigoLoja,
                         RedirectAttributes redirectAttributes) {
        try {
            int resultado = horarioController.CadastrarHorario(horario, codigoLoja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Horário cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar horário.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/horarios";
    }

    @SuppressWarnings("rawtypes")
    private List<HorarioModel> converterLista(ArrayList<ArrayList> rows) {
        List<HorarioModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                HorarioModel h = new HorarioModel();
                h.setCodigo(Integer.parseInt(row.get(0).toString()));
                h.setInicio(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                h.setFim(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
                h.setDescricao(row.size() > 3 && row.get(3) != null ? row.get(3).toString() : "");
                lista.add(h);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
