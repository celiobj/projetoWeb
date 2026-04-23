/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.SolicitacaoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioSolicitacoesInterface {

    int InserirSolicitacao(SolicitacaoModel solicitacao, int codigoloja);

    int RemoverSolicitacao(int codigoSolicitacao);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> Procurar(int codigoUsuario, String status, int codigoloja);

}
