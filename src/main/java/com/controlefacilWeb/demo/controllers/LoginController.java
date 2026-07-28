package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioUsuario;
import jakarta.servlet.http.HttpSession;
import com.controlefacilWeb.demo.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
public class LoginController {

    @Value("${app.login.usuario}")
    private String usuarioConfigurado;

    @Value("${app.login.senha}")
    private String senhaConfigurada;

    private final RepositorioUsuario repositorioUsuario;

    public LoginController() {
        this.repositorioUsuario = new RepositorioUsuario();
    }

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

        // 0. Verifica se o arquivo de banco de dados existe na pasta data/
        try (InputStream confIs = new java.io.FileInputStream(new java.io.File("data/conf.properties"))) {
            if (confIs != null) {
                Properties conf = new Properties();
                conf.load(confIs);
                String dbPath = conf.getProperty("db.path", "").trim();
                if (!dbPath.isEmpty() && !new java.io.File(dbPath).exists()) {
                    Util.atualizarBaseDados();
                    model.addAttribute("info", "Base de dados não encontrada. Download iniciado em segundo plano — aguarde alguns instantes e tente novamente.");
                    return "login";
                }
            }
        } catch (IOException e) {
            // conf.properties indisponível — continua normalmente
        }

        // 1. Credenciais estáticas (application.properties)
        if (usuarioConfigurado.equals(usuario) && senhaConfigurada.equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("tipoUsuario", null);
            return "redirect:/";
        }

        // 2. Credenciais do banco de dados
        try {
            UsuarioModel req = new UsuarioModel();
            req.setUser(usuario);
            req.setPass(Util.criptografarSenha(senha));
            UsuarioModel usuarioDb = repositorioUsuario.Logar(req, null);
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
