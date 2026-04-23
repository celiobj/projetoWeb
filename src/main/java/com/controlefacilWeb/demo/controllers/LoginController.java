package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.controlefacilWeb.demo.util.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller de autenticação — login e logout.
 * Valida credenciais primeiro contra application.properties e,
 * em seguida, contra o banco de dados via UsuarioService.
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    @Value("${app.login.usuario}")
    private String usuarioConfigurado;

    @Value("${app.login.senha}")
    private String senhaConfigurada;

    private final UsuarioService usuarioService;

    /** GET /login — exibe o formulário de login */
    @GetMapping("/login")
    public String exibirLogin(HttpSession session) {
        if (session.getAttribute("usuarioLogado") != null) {
            return "redirect:/";
        }
        return "login";
    }

    /** POST /login — valida as credenciais */
    @PostMapping("/login")
    public String processarLogin(@RequestParam String usuario,
                                 @RequestParam String senha,
                                 HttpSession session,
                                 Model model) {

        // 1. Credenciais estáticas (application.properties)
        if (usuarioConfigurado.equals(usuario) && senhaConfigurada.equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("tipoUsuario", null);
            return "redirect:/";
        }

        // 2. Credenciais do banco de dados
        try {
            UsuarioModel usuarioDb = usuarioService.logar(usuario, Util.criptografarSenha(senha));
            if (usuarioDb != null) {
                session.setAttribute("usuarioLogado", usuarioDb.getUser());
                session.setAttribute("tipoUsuario", usuarioDb.getTipo());
                session.setAttribute("codigoUsuario", usuarioDb.getCodigo());
                return "redirect:/";
            }
        } catch (Exception e) {
            // banco indisponível — continua para mensagem de erro
        }

        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }

    /** GET /logout — encerra a sessão */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
