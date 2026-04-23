/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.ComissaoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioComissao;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class ComissaoController {

    RepositorioComissao rc;
    RestApi rest;

    public int CadastrarComissao(ComissaoModel comissao) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioComissao();
                return rc.CadastrarComissao(comissao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/comissoes/";
                String request = Util.stringObjectToJson(comissao, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int AlterarSatus(int codigoComissao, char isAtivo, int tipo) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioComissao();
                return rc.AlterarStatus(codigoComissao, isAtivo, tipo);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/comissoes/alterarSatus?codigoComissao=" + codigoComissao + "&isAtivo=" + isAtivo + "&tipo =" + tipo;
                String request = "";
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ArrayList<ArrayList> listarTodasComissoes() {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioComissao();
                return rc.listarTodasComissoes(1);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/comissoes/listarTodasComissoes";
                String request = "?codigoLoja=1" ;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] objetos = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (objetos != null) {
                        retorno.addAll(Arrays.asList(objetos));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ComissaoModel ProcurarComissaoPercentual(int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioComissao();
                return rc.ProcurarComissaoPercentual(codigoLoja);
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/comissoes/procurarComissaoPercentual?codigoLoja=";
                ComissaoModel comissaoModel = gson.fromJson(rest.Get(url, Integer.toString(codigoLoja)).body(), ComissaoModel.class
                );
                return comissaoModel;

            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
