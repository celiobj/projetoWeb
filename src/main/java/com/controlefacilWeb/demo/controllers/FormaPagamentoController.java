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
import com.controlefacilWeb.demo.models.FormaPagamentoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioFormaPagamento;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class FormaPagamentoController {

    RepositorioFormaPagamento rfp;
    RestApi rest;

    public int CadastrarFormaPagamento(FormaPagamentoModel formaPagamento) {

        try {
            if (!Login.isIsService()) {
                rfp = new RepositorioFormaPagamento();
                return rfp.CadastrarFormaPagamento(formaPagamento);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/formasPagamento/";
                String request = Util.stringObjectToJson(formaPagamento, "");
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

    public ArrayList<ArrayList> ListarTodasFormasPagamento() {

        try {
            if (!Login.isIsService()) {
                rfp = new RepositorioFormaPagamento();
                return rfp.ListarTodasFormasPagamento();
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/formasPagamento/listarTodasFormasPagamento";
                String request = "";
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] formasPagamento = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (formasPagamento != null) {
                        retorno.addAll(Arrays.asList(formasPagamento));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherComboFormaPagamento(char status) {

        try {
            if (!Login.isIsService()) {
                rfp = new RepositorioFormaPagamento();
                return rfp.PreencherComboFormaPagamento(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/formasPagamento/preencherComboFormaPagamento";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] lista = objectMapper.readValue(response.body(), String[].class
                    );
                    if (lista != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(lista));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
