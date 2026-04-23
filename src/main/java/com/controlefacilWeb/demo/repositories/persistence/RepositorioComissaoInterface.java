/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.ComissaoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioComissaoInterface {

    int CadastrarComissao(ComissaoModel comissao);

    int AlterarStatus(int codigoComissao, char isAtivo, int tipo);

    ComissaoModel ProcurarComissaoPercentual( int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodasComissoes( int codigoloja);
}
