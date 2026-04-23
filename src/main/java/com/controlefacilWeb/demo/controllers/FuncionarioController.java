/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.FuncionarioModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioFuncionario;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class FuncionarioController {

    RepositorioFuncionario rf;
    RestApi rest;

    public int CadastrarFuncionario(FuncionarioModel funcionario, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFuncionario();
                return rf.CadastrarFuncionario(funcionario, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/funcionarios/cadastrarFuncionario?codigoLoja=" + codigoLoja + "&nome=" + funcionario.getNome().trim() + "&codigoUsuario=" + funcionario.getCodigoUsuario();
                String request = Util.stringObjectToJson(funcionario, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public FuncionarioModel ProcurarFuncionario(int codigoFuncionario) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFuncionario();
                return rf.ProcurarFuncionario(codigoFuncionario);
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/funcionarios/procurarFuncionario?codigoFuncionario=";
                FuncionarioModel funcionarioModel = gson.fromJson(rest.Get(url, Integer.toString(codigoFuncionario)).body(), FuncionarioModel.class
                );
                return funcionarioModel;
            }
        } catch (JsonIOException | JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList<ArrayList> ListarTodosFuncionarios(char status, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFuncionario();
                return rf.ListarTodosFuncionarios(status, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/funcionarios/listarTodosFuncionarios";
                String request = "?codigoLoja=" + codigoLoja + "&status=" + status;
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
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherComboFuncionarios(char status, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rf = new RepositorioFuncionario();
                return rf.PreencherComboFuncionarios(status, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/funcionarios/preencherComboFuncionarios";
                String request = "?status=" + status + "&codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] funcionarios = objectMapper.readValue(response.body(), String[].class
                    );
                    if (funcionarios != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(funcionarios));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
