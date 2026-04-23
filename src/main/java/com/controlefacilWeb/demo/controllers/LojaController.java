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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.LojaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioLoja;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class LojaController {

    RepositorioLoja rl;
    Connection con;
    RestApi rest;

    public int CadastrarLoja(LojaModel loja) {

        try {
            if (!Login.isIsService()) {
                rl = new RepositorioLoja();
                ProdutoController pc = new ProdutoController(null);
                ArrayList<ArrayList> produtos = pc.listarTodosProdutos();
                return rl.CadastrarLoja(loja, produtos, con);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/lojas/";
                String request = Util.stringObjectToJson(loja, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public LojaModel ProcurarLoja(int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rl = new RepositorioLoja();
                return rl.BuscarLoja(codigoLoja, con);
            } else {
                HttpResponse<String> response = null;
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/lojas/?codigoLoja=";
                response = rest.Get(url, Integer.toString(codigoLoja));
                LojaModel lojaModel;
                lojaModel = gson.fromJson(response.body(), LojaModel.class);

                return lojaModel;
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList<ArrayList> ListarTodasLojas() {

        try {
            if (!Login.isIsService()) {
                rl = new RepositorioLoja();
                return rl.ListarTodasLojas(con);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/lojas/listarTodasLojas";
                String request = "";
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] lojas = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (lojas != null) {
                        retorno.addAll(Arrays.asList(lojas));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherComboLojas(char status) {

        try {
            if (!Login.isIsService()) {
                rl = new RepositorioLoja();
                return rl.PreencherComboLoja(status, con);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/lojas/preencherComboLojas";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] lojas = objectMapper.readValue(response.body(), String[].class
                    );
                    if (lojas != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(lojas));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
