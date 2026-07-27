package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.NotaCompradaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notas-compradas")
public class NotaCompradaViewController {

    private final NotaCompradaController notaCompradaController;

    public NotaCompradaViewController() {
        this.notaCompradaController = new NotaCompradaController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String dataInicial,
                         @RequestParam(required = false) String dataFinal,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        boolean filtroData = dataInicial != null && dataFinal != null
                && !dataInicial.isEmpty() && !dataFinal.isEmpty();
        String inicio = filtroData ? dataInicial : "";
        String fim = filtroData ? dataFinal : "";
        List<NotaCompradaModel> notas = converterLista(
                notaCompradaController.ListarTodasNotas(filtroData, inicio, fim, false, 0, 1));
        model.addAttribute("notas", notas);
        model.addAttribute("totalNotas", notas.size());
        model.addAttribute("dataInicial", inicio);
        model.addAttribute("dataFinal", fim);
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "nota-comprada/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("nota", new NotaCompradaModel());
        return "nota-comprada/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute NotaCompradaModel nota,
                         @RequestParam(defaultValue = "1") int codigoLoja,
                         RedirectAttributes redirectAttributes) {
        try {
            int resultado = notaCompradaController.FecharNota(nota, codigoLoja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Nota de compra registrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao registrar nota de compra.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/notas-compradas";
    }

    @SuppressWarnings("rawtypes")
    private List<NotaCompradaModel> converterLista(ArrayList<ArrayList> rows) {
        List<NotaCompradaModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                NotaCompradaModel n = new NotaCompradaModel();
                n.setCodigoNotaComprada(Integer.parseInt(row.get(0).toString()));
                n.setNumeroNota(row.size() > 1 && row.get(1) != null ? Integer.parseInt(row.get(1).toString()) : 0);
                n.setData(row.size() > 2 && row.get(2) != null ? row.get(2).toString() : "");
                n.setValor(row.size() > 3 && row.get(3) != null ? Double.parseDouble(row.get(3).toString()) : 0.0);
                n.setStatus(row.size() > 4 && row.get(4) != null && !row.get(4).toString().isEmpty()
                        ? row.get(4).toString().charAt(0) : 'A');
                lista.add(n);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
