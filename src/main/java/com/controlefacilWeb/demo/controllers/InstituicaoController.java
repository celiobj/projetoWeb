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
import com.controlefacilWeb.demo.models.InstituicaoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioInstituicao;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class InstituicaoController {

    RepositorioInstituicao ri;
    RestApi rest;

    public int Cadastrar(InstituicaoModel instituicao) {

        try {
            if (!Login.isIsService()) {
                ri = new RepositorioInstituicao();
                return ri.CadastrarInstituicao(instituicao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/instituicoes/";
                String request = Util.stringObjectToJson(instituicao, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int Alterar(InstituicaoModel instituicao, int codigoInstituicao) {

        try {
            if (!Login.isIsService()) {
                ri = new RepositorioInstituicao();
                return ri.AlterarInstituicao(instituicao, codigoInstituicao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/instituicoes/alterar?codigoInstituicao=" + codigoInstituicao;
                String request = Util.stringObjectToJson(instituicao, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int Remover(int codigoInstituicao) {

        try {
            if (!Login.isIsService()) {
                ri = new RepositorioInstituicao();
                return ri.RemoverInstituicao(codigoInstituicao);
            } else {
                System.out.println("ultraBarber.controller.InstituicaoController.Remover()");
            }
            return 0;
        } catch (Exception ex) {
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ArrayList<ArrayList> ListarTodasInstituicoes(char status) {

        try {
            if (!Login.isIsService()) {
                ri = new RepositorioInstituicao();
                return ri.ListarTodasInstituicoes(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/instituicoes/listarTodasInstituicoes";
                String request = "?status=" + status;
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
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherComboInstituicoes(char status) {

        try {
            if (!Login.isIsService()) {
                ri = new RepositorioInstituicao();
                return ri.PreencherComboInstituicoes(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/instituicoes/preencherComboInstituicoes";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] instituicoes = objectMapper.readValue(response.body(), String[].class
                    );
                    if (instituicoes != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(instituicoes));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
