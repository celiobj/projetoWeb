/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import com.controlefacilWeb.demo.models.LojaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioLojaInterface {

    int CadastrarLoja(LojaModel loja, @SuppressWarnings("rawtypes") ArrayList<ArrayList> produtos, Connection conn);

    LojaModel BuscarLoja(int codigoLoja, Connection conn);

    int RemoverLoja(int codigoLoja, Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasLojas(Connection conn);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboLoja(char status, Connection conn);

}
