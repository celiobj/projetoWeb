/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.ConfiguracaoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioConfiguracao;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class ConfiguracaoController {

    RepositorioConfiguracao rc;
    Connection con;
    RestApi rest;

    public int CriarBanco(String script) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConfiguracao();
                return rc.CriarBanco(script, con);
            } else {
                System.out.println("ultraBarber.controller.ConfiguracaoController.CriarBanco()");
            }
            return 0;
        } catch (Exception ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int InserirConfiguracao(ConfiguracaoModel conf) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConfiguracao();
                return rc.InserirConfiguracao(conf, con);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/configuracoes/";
                String request = Util.stringObjectToJson(conf, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ConfiguracaoModel BuscarConfiguracao(int tipo) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConfiguracao();
                return rc.BuscarConfiguracao(tipo, con);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                Gson gson = new Gson();
                String url = "/configuracoes/?tipo=";
                response = rest.Get(url, Integer.toString(tipo));
                ConfiguracaoModel configuracaoModel = gson.fromJson(response.body(), ConfiguracaoModel.class
                );
                return configuracaoModel;
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public int AlterarConfiguracao(int codigoConfiguracao, ConfiguracaoModel conf) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConfiguracao();
                return rc.AlterarConfiguracao(codigoConfiguracao, conf, con);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/configuracoes/alterarConfiguracao?codigoConfiguracao=" + codigoConfiguracao;
                String request = Util.stringObjectToJson(conf, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
