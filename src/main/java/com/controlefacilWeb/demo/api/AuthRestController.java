package com.controlefacilWeb.demo.api;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioUsuario;
import com.controlefacilWeb.demo.security.JwtUtil;
import com.controlefacilWeb.demo.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Autenticação", description = "Geração de token JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Value("${app.login.usuario}")
    private String usuarioConfigurado;

    @Value("${app.login.senha}")
    private String senhaConfigurada;

    private final JwtUtil jwtUtil;
    private final RepositorioUsuario repositorioUsuario;

    public AuthRestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.repositorioUsuario = new RepositorioUsuario();
    }

    public record LoginRequest(String usuario, String senha) {}

    public record LoginResponse(String token, String usuario, Integer tipo) {}

    @SecurityRequirements
    @Operation(
            summary = "Login",
            description = "Autentica o usuário e retorna um token JWT. Use o token em todas as demais requisições via header `Authorization: Bearer <token>`.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Token gerado com sucesso",
                        content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        // 1. Credenciais estáticas (application.properties)
        if (usuarioConfigurado.equals(req.usuario()) && senhaConfigurada.equals(req.senha())) {
            String token = jwtUtil.gerar(req.usuario());
            return ResponseEntity.ok(new LoginResponse(token, req.usuario(), null));
        }

        // 2. Credenciais do banco de dados
        try {
            UsuarioModel input = new UsuarioModel();
            input.setUser(req.usuario());
            input.setPass(Util.criptografarSenha(req.senha()));
            UsuarioModel usuario = repositorioUsuario.Logar(input, null);
            if (usuario != null) {
                String token = jwtUtil.gerar(usuario.getUser());
                return ResponseEntity.ok(new LoginResponse(token, usuario.getUser(), usuario.getTipo()));
            }
        } catch (Exception e) {
            // banco indisponível — continua para retornar 401
        }

        return ResponseEntity.status(401).body(Map.of("erro", "Usuário ou senha inválidos."));
    }
}
