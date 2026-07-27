package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.NotificacaoModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notificacoes")
public class NotificacaoViewController {

    private final NotificacaoController notificacaoController;

    public NotificacaoViewController() {
        this.notificacaoController = new NotificacaoController();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(defaultValue = "1") int codigoUsuario,
                         @RequestParam(defaultValue = "P") String status,
                         @RequestParam(defaultValue = "1") int codigoLoja,
                         @RequestParam(required = false) String mensagem,
                         @RequestParam(required = false) String tipo) {
        List<NotificacaoModel> notificacoes = converterLista(
                notificacaoController.ListarTodasNotificacoesPorUsuario(codigoUsuario, status, codigoLoja));
        model.addAttribute("notificacoes", notificacoes);
        model.addAttribute("totalNotificacoes", notificacoes.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "notificacao/lista";
    }

    @PostMapping("/{id}/marcarLida")
    public String marcarLida(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            int resultado = notificacaoController.MarcarNotificacaoLida(id);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Notificação marcada como lida!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao marcar notificação.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/notificacoes";
    }

    @SuppressWarnings("rawtypes")
    private List<NotificacaoModel> converterLista(ArrayList rows) {
        List<NotificacaoModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (Object item : rows) {
            try {
                if (item instanceof ArrayList) {
                    ArrayList row = (ArrayList) item;
                    NotificacaoModel n = new NotificacaoModel();
                    n.setCodigoNotificacao(Integer.parseInt(row.get(0).toString()));
                    n.setDescricao(row.size() > 1 && row.get(1) != null ? row.get(1).toString() : "");
                    n.setIsAtivo(row.size() > 2 && row.get(2) != null && !row.get(2).toString().isEmpty()
                            ? row.get(2).toString().charAt(0) : 'S');
                    lista.add(n);
                }
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }
}
