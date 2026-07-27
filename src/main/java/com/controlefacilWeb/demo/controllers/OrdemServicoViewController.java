package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.OrdemServicoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ordens-servico")
public class OrdemServicoViewController {

    private final OrdemServicoController ordemServicoController;

    public OrdemServicoViewController() {
        this.ordemServicoController = new OrdemServicoController();
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
        List<OrdemServicoModel> ordens = converterLista(
                ordemServicoController.ListarTodasOrdensDeServico(filtroData, inicio, fim, false, 0, 1));
        model.addAttribute("ordens", ordens);
        model.addAttribute("totalOrdens", ordens.size());
        model.addAttribute("dataInicial", inicio);
        model.addAttribute("dataFinal", fim);
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "ordem-servico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ordemServico", new OrdemServicoModel());
        return "ordem-servico/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute OrdemServicoModel ordemServico,
                         @RequestParam(defaultValue = "1") int codigoLoja,
                         RedirectAttributes redirectAttributes) {
        try {
            int resultado = ordemServicoController.FecharOrdemServico(ordemServico, codigoLoja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Ordem de serviço registrada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao registrar ordem de serviço.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/ordens-servico";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable int id,
                           @RequestParam(defaultValue = "1") int codigoLoja,
                           RedirectAttributes redirectAttributes) {
        try {
            int resultado = ordemServicoController.CancelarOrdemServico(id, codigoLoja);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Ordem de serviço cancelada com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cancelar ordem de serviço.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/ordens-servico";
    }

    @SuppressWarnings("rawtypes")
    private List<OrdemServicoModel> converterLista(ArrayList<ArrayList> rows) {
        List<OrdemServicoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                OrdemServicoModel o = new OrdemServicoModel();
                o.setCodigoOrdem(Integer.parseInt(row.get(0).toString()));
                o.setData(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                o.setValor(row.size() > 2 && row.get(2) != null ? Double.parseDouble(row.get(2).toString()) : 0.0);
                o.setCodigoCliente(row.size() > 3 && row.get(3) != null ? Integer.parseInt(row.get(3).toString()) : 0);
                lista.add(o);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
