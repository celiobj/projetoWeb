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
import com.controlefacilWeb.demo.models.FornecedorModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioFornecedor;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class FornecedorController {

    RepositorioFornecedor rf;
    RestApi rest;

    public int CadastrarFornecedor(FornecedorModel fornecedor) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFornecedor();
                return rf.CadastrarFornecedor(fornecedor);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/fornecedores/";
                String request = Util.stringObjectToJson(fornecedor, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int AlterarFornecedor(FornecedorModel fornecedor) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFornecedor();
                return rf.CadastrarFornecedor(fornecedor);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/fornecedores/alterarFornecedor";
                String request = Util.stringObjectToJson(fornecedor, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    
    public ArrayList<ArrayList> ListarTodosFornecedores() {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFornecedor();
                return rf.ListarTodosFornecedor();
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/fornecedores/listarTodosFornecedores";
                String request = "";
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
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherFornecedores(char status) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFornecedor();
                return rf.PreencherComboFornecedor(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/fornecedores/preencherFornecedores";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] fornecedores = objectMapper.readValue(response.body(), String[].class
                    );
                    if (fornecedores != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(fornecedores));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
