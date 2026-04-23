/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioNotificacoes;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class NotificacaoController {

    RepositorioNotificacoes rn;
    RestApi rest;

    public ArrayList ListarTodasNotificacoesPorUsuario(int codigoUsuario, String status, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotificacoes();
                return rn.Procurar(codigoUsuario, status, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/notificacoes/listarTodasNotificacoesPorUsuario";
                String request = "?codigoUsuario=" + codigoUsuario + "&status=" + status + "&codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] notificacoes = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (notificacoes != null) {
                        retorno.addAll(Arrays.asList(notificacoes));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(NotificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int MarcarNotificacaoLida(int codigoNotificacao) {

        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotificacoes();
                return rn.MarcarNotificacaoLida(codigoNotificacao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/notificacoes/marcarNotificacaoLida?codigoNotificacao=" + codigoNotificacao;
                String request = "";
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(NotificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
