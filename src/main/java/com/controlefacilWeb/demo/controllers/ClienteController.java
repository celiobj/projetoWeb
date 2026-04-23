/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import com.google.gson.Gson;

import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.ClienteModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioCliente;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class ClienteController {

    RepositorioCliente rc;
    RestApi rest;

    public int CadastrarCliente(ClienteModel clienteModel) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCliente();
                return rc.CadastrarCliente(clienteModel);
            } else {

                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/clientes/cadastrarCliente";
                String request = Util.stringObjectToJson(clienteModel, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
    
    public int AlterarCliente(ClienteModel clienteModel) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCliente();
                return rc.CadastrarCliente(clienteModel);
            } else {

                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/clientes/alterarCliente";
                String request = Util.stringObjectToJson(clienteModel, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ClienteModel ProcurarCliente(int codigoCliente) {
        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCliente();
                return rc.ProcurarCliente(codigoCliente);
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/clientes/procurarCliente?codigoCliente=";
                ClienteModel clienteModel;
                clienteModel = gson.fromJson(rest.Get(url, Integer.toString(codigoCliente)).body(), ClienteModel.class);

                return clienteModel;
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosClientes() {
        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCliente();
                return rc.ListarTodosClientes();
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/clientes/listarTodosClientes";
                String request = "";
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] clientes = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (clientes != null) {
                        retorno.addAll(Arrays.asList(clientes));
                    }
                    return retorno;
                }
            }
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList PreencherComboClientes(char status) {
        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCliente();
                return rc.PreencherComboClientes(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/clientes/preencherComboClientes";
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

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
