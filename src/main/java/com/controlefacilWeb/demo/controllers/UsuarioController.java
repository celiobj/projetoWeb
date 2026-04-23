package com.controlefacilWeb.demo.controllers;

import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioUsuario;
import com.controlefacilWeb.demo.util.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RepositorioUsuario repositorioUsuario;

    public UsuarioController() {
        this.repositorioUsuario = new RepositorioUsuario();
    }

    /** POST /api/usuarios — Cadastra um novo usuário. */
    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody UsuarioModel usuario) {
        usuario.setPass(Util.gerarHash(usuario.getPass()));
        int codigo = repositorioUsuario.CadastrarUsuario(usuario, null);
        if (codigo != 0) {
            return ResponseEntity.status(201).body(codigo);
        }
        return ResponseEntity.badRequest().body(0);
    }

    /** POST /api/usuarios/alterar?codigoUsuario={id} — Altera dados de um usuário. */
    @PostMapping("/alterar")
    public ResponseEntity<Integer> alterar(@RequestBody UsuarioModel usuario,
                                           @RequestParam int codigoUsuario) {
        int resultado = repositorioUsuario.AlterarUsuario(usuario, codigoUsuario, null);
        return ResponseEntity.ok(resultado);
    }

    /** PATCH /api/usuarios/alterarSenhaUsuario — Altera a senha de um usuário. */
    @PatchMapping("/alterarSenhaUsuario")
    public ResponseEntity<Integer> alterarSenha(@RequestBody UsuarioModel usuario) {
        usuario.setPass(Util.gerarHash(usuario.getPass()));
        int resultado = repositorioUsuario.AlterarSenha(usuario, null);
        return ResponseEntity.ok(resultado);
    }

    /** DELETE /api/usuarios/{codigoUsuario} — Remove um usuário. */
    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Integer> remover(@PathVariable int codigoUsuario) {
        int resultado = repositorioUsuario.RemoverUsuario(codigoUsuario, null);
        return ResponseEntity.ok(resultado);
    }

    /** GET /api/usuarios/logar?user={user}&pass={pass} — Autentica um usuário. */
    @GetMapping("/logar")
    public ResponseEntity<UsuarioModel> logar(@RequestParam String user,
                                              @RequestParam String pass) {
        UsuarioModel req = new UsuarioModel();
        req.setUser(user);
        req.setPass(pass);
        UsuarioModel resultado = repositorioUsuario.Logar(req, null);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    /** GET /api/usuarios/listarTodosUsuarios?codigoLoja={id} — Lista todos os usuários. */
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarTodosUsuarios")
    public ResponseEntity<ArrayList<ArrayList>> listarTodosUsuarios(@RequestParam int codigoLoja) {
        ArrayList<ArrayList> resultado = repositorioUsuario.ListarTodosUsuarios(codigoLoja, null);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }

    /** GET /api/usuarios/listarTodosUsuariosPorTipo?tipo={tipo}&codigoLoja={id} */
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarTodosUsuariosPorTipo")
    public ResponseEntity<ArrayList<ArrayList>> listarTodosUsuariosPorTipo(@RequestParam int tipo,
                                                                            @RequestParam int codigoLoja) {
        ArrayList<ArrayList> resultado = repositorioUsuario.ListarTodosUsuariosPorTipo(tipo, codigoLoja, null);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }

    /** GET /api/usuarios/preencherComboTodosUsuarios?codigoLoja={id} */
    @SuppressWarnings("rawtypes")
    @GetMapping("/preencherComboTodosUsuarios")
    public ResponseEntity<ArrayList<ArrayList>> preencherCombo(@RequestParam int codigoLoja) {
        ArrayList<ArrayList> resultado = repositorioUsuario.preencherComboTodosUsuarios(codigoLoja, null);
        return ResponseEntity.ok(resultado != null ? resultado : new ArrayList<>());
    }
}
