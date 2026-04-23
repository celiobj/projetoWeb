/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import com.controlefacilWeb.demo.models.UsuarioModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioUsuarioInterface {

    int CadastrarUsuario(UsuarioModel usuario, Connection conn);

    int AlterarUsuario(UsuarioModel usuario, int codigoUsuario, Connection conn);

    int AlterarSenha(UsuarioModel usuario, Connection conn);

    int RemoverUsuario(int codigoUsuario, Connection conn);

    UsuarioModel Logar(UsuarioModel usuario, Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosUsuarios(int codigoLoja, Connection conn);
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosUsuariosPorTipo(int tipo, int codigoLoja, Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> preencherComboTodosUsuarios(int codigoLoja, Connection conn);

}
