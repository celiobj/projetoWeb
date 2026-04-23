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
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioCartao;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class CartaoController {

    RepositorioCartao rc;
    RestApi rest;

    public int Cadastrar(CartaoModel cartao) {
        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                return rc.Cadastrar(cartao);
            } else {

                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/cartoes/cadastrar";
                String request = Util.stringObjectToJson(cartao, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public void AtualizarSaldo(CartaoModel cartao, double saldo, EnumTipoMovimentacao tipoTransacao) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                rc.AlterarSaldo(cartao, saldo, tipoTransacao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/cartoes/atualizarSaldo?saldo=" + saldo + "&tipoTransacao=" + tipoTransacao;
                String request = Util.stringObjectToJson(cartao, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                }
            }

        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ArrayList> ListarTodosCartoes(char status) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                return rc.listarTodosCartoes(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/cartoes/listarTodosCartoes";
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
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherComboCartoes(char status) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                return rc.PreencherComboCartoes(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/cartoes/preencherComboCartoes";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] objetos = objectMapper.readValue(response.body(), String[].class
                    );
                    if (objetos != null) {
                        retorno.addAll(Arrays.asList(objetos));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public CartaoModel Procurar(int codigoCartao) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                return rc.Procurar(codigoCartao);
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/cartoes/?codigoCartao=";
                CartaoModel cartaoModel = gson.fromJson(rest.Get(url, Integer.toString(codigoCartao)).body(), CartaoModel.class
                );
                return cartaoModel;
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public double VerificarSaldo(int codigoCartao) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioCartao();
                return rc.VerificarSaldo(codigoCartao);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/cartoes/";
                String request = "verificarSaldo?codigoCartao=" + codigoCartao;
                response = rest.Get(url, request);
                if (response.statusCode() == 200) {
                    return Double.parseDouble(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(CartaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
