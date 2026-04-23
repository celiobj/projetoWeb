/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.NotificacaoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioNotificacoesInterface {

    int InserirNotificacao(NotificacaoModel notificacao, int codigoloja);

    int RemoverNotificacao(int codigoNotificacao);

    int MarcarNotificacaoLida(int codigoNotificacao);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> Procurar(int codigoUsuario, String status, int codigoloja);

}
