package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final RepositorioUsuario usuarioService;

    public UsuarioViewController() {
        this.usuarioService = new RepositorioUsuario();
    }

    @GetMapping
    public String listar(Model model,
                         @org.springframework.web.bind.annotation.RequestParam(required = false) String mensagem,
                         @org.springframework.web.bind.annotation.RequestParam(required = false) String tipo) {
        List<ArrayList> rows = usuarioService.ListarTodosUsuarios(1, null);
        List<UsuarioModel> usuarios = converterLista(rows);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        if (mensagem != null) {
            model.addAttribute("mensagem", mensagem);
            model.addAttribute("tipo", tipo != null ? tipo : "sucesso");
        }
        return "usuarios/lista";
    }

    @SuppressWarnings("rawtypes")
    private List<UsuarioModel> converterLista(List<ArrayList> rows) {
        List<UsuarioModel> lista = new ArrayList<>();
        if (rows == null) return lista;
        for (ArrayList row : rows) {
            try {
                int codigo = Integer.parseInt(row.get(0).toString());
                String login = row.get(1).toString();
                int tipo = Integer.parseInt(row.get(2).toString());
                char forcarSenha = row.size() > 3 && row.get(3) != null
                        ? row.get(3).toString().isEmpty() ? 'N' : row.get(3).toString().charAt(0)
                        : 'N';
                lista.add(new UsuarioModel(codigo, login, "", tipo, forcarSenha, 0));
            } catch (Exception e) {
                // ignora linha inválida
            }
        }
        return lista;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "usuarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute UsuarioModel usuario,
                         RedirectAttributes redirectAttributes) {
        try {
            int codigo = usuarioService.CadastrarUsuario(usuario, null);
            if (codigo != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao cadastrar usuário.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            int resultado = usuarioService.RemoverUsuario(id, null);
            if (resultado != 0) {
                redirectAttributes.addFlashAttribute("mensagem", "Usuário removido com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "sucesso");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Erro ao remover usuário.");
                redirectAttributes.addFlashAttribute("tipo", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/usuarios";
    }
}
