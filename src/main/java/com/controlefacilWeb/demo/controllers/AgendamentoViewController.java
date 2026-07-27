package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.AgendamentoModel;
import com.controlefacilWeb.demo.models.ClienteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoViewController {

    private final AgendamentoController agendamentoController;
    private final ClienteController clienteController;

    public AgendamentoViewController() {
        this.agendamentoController = new AgendamentoController();
        this.clienteController = new ClienteController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String dataInicial,
                         @RequestParam(required = false) String dataFinal,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        String inicio = dataInicial != null ? dataInicial : "";
        String fim = dataFinal != null ? dataFinal : "";
        List<AgendamentoModel> agendamentos = new ArrayList<>();
        try {
            ArrayList<AgendamentoModel> resultado = agendamentoController.ListarTodosAgendamentos(1, 0, inicio, fim);
            if (resultado != null) agendamentos.addAll(resultado);
        } catch (Exception e) {
            // retorna lista vazia em caso de erro
        }
        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("totalAgendamentos", agendamentos.size());
        model.addAttribute("dataInicial", inicio);
        model.addAttribute("dataFinal", fim);
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "agendamento/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("agendamento", new AgendamentoModel());
        List<ClienteModel> clientes = new ArrayList<>();
        try {
            clientes = converterListaClientes(clienteController.ListarTodosClientes());
        } catch (Throwable t) {
            // mantém lista vazia em caso de falha (inclui Error de driver/memória)
        }
        model.addAttribute("clientes", clientes);
        return "agendamento/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute AgendamentoModel agendamento, RedirectAttributes redirectAttributes) {
        try {
            int resultado = agendamentoController.RealizarAgendamento(agendamento, agendamento.getCodigoLoja());
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Agendamento realizado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao realizar agendamento.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/agendamentos";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            int resultado = agendamentoController.CancelarAgendamento(id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Agendamento cancelado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cancelar agendamento.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/agendamentos";
    }

    @SuppressWarnings("rawtypes")
    private List<ClienteModel> converterListaClientes(ArrayList<ArrayList> rows) {
        List<ClienteModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                int codigo = Integer.parseInt(row.get(0).toString());
                String nome = row.get(1).toString();
                ClienteModel c = new ClienteModel();
                c.setCodigoCliente(codigo);
                c.setNome(nome);
                lista.add(c);
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
