package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Controller REST para operações de Usuário.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * POST /usuarios — Cadastra um novo usuário.
     * Retorna o código gerado (HTTP 201) ou 400 em caso de falha.
     */
    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody UsuarioModel usuario) {
        int codigo = usuarioService.cadastrar(usuario);
        if (codigo != 0) {
            return ResponseEntity.status(201).body(codigo);
        }
        return ResponseEntity.badRequest().body(0);
    }

    /**
     * POST /usuarios/alterar?codigoUsuario={id} — Altera dados de um usuário.
     */
    @PostMapping("/alterar")
    public ResponseEntity<Integer> alterar(@RequestBody UsuarioModel usuario,
                                           @RequestParam int codigoUsuario) {
        return ResponseEntity.ok(usuarioService.alterar(usuario, codigoUsuario));
    }

    /**
     * PATCH /usuarios/alterarSenhaUsuario — Altera a senha de um usuário.
     */
    @PatchMapping("/alterarSenhaUsuario")
    public ResponseEntity<Integer> alterarSenha(@RequestBody UsuarioModel usuario) {
        return ResponseEntity.ok(usuarioService.alterarSenha(usuario));
    }

    /**
     * DELETE /usuarios/{codigoUsuario} — Remove um usuário.
     */
    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Integer> remover(@PathVariable int codigoUsuario) {
        return ResponseEntity.ok(usuarioService.remover(codigoUsuario));
    }

    /**
     * GET /usuarios/logar?user={user}&pass={pass} — Autentica um usuário.
     * Retorna os dados do usuário ou 404 se as credenciais forem inválidas.
     */
    @GetMapping("/logar")
    public ResponseEntity<UsuarioModel> logar(@RequestParam String user,
                                              @RequestParam String pass) {
        UsuarioModel resultado = usuarioService.logar(user, pass);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * GET /usuarios/listarTodosUsuarios?codigoLoja={id} — Lista todos os usuários de uma loja.
     */
    @GetMapping("/listarTodosUsuarios")
    public ResponseEntity<ArrayList<ArrayList<Object>>> listarTodosUsuarios(@RequestParam int codigoLoja) {
        ArrayList<ArrayList<Object>> resultado = usuarioService.listarTodos(codigoLoja);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }

    /**
     * GET /usuarios/listarTodosUsuariosPorTipo?tipo={tipo}&codigoLoja={id}
     * — Lista usuários filtrados por tipo.
     */
    @GetMapping("/listarTodosUsuariosPorTipo")
    public ResponseEntity<ArrayList<Object>> listarTodosUsuariosPorTipo(@RequestParam int tipo,
                                                           @RequestParam int codigoLoja) {
        ArrayList<Object> resultado = usuarioService.listarPorTipo(tipo, codigoLoja);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }

    /**
     * GET /usuarios/preencherComboTodosUsuarios?codigoLoja={id}
     * — Retorna lista de nomes para preenchimento de combo.
     */
    @GetMapping("/preencherComboTodosUsuarios")
    public ResponseEntity<ArrayList<Object>> preencherCombo(@RequestParam int codigoLoja) {
        ArrayList<Object> resultado = usuarioService.preencherCombo(codigoLoja);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
        //TODO Auto-generated constructor stub
    }
}
