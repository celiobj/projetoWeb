/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import com.controlefacilWeb.demo.models.TelaModel;
import com.controlefacilWeb.demo.models.UsuarioModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioTelaInterface {

    int CadastrarTela(TelaModel tela, Connection conn);

    void CriarPermissao(int codigoTela, int codigoUsuario, Connection conn);

    int AlterarPermissaoTela(int codigoTela, int codigoUsuario, char isPermitido, Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodasTelas(Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodasPermissoesTelas(int codigoUsuario, Connection conn);

    boolean VerificarPermissao(TelaModel tela, UsuarioModel usuario, Connection conn);

    int BuscarPermissao(int codigoTela, int codigoUsuario, Connection conn);

    TelaModel BuscarTela(String nomeTela, Connection conn);
}
