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
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioConta;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class ContaController {

    RepositorioConta rc;
    RestApi rest;

    public int Cadastrar(ContaModel conta) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.CadastrarConta(conta);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/contas/";
                String request = Util.stringObjectToJson(conta, "");
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

    public ContaModel Procurar(int codigoConta) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.ProcurarContaPorInstituicao(codigoConta);
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/contas/?codigoConta=";
                ContaModel contaModel = gson.fromJson(rest.Get(url, Integer.toString(codigoConta)).body(), ContaModel.class
                );
                return contaModel;
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ContaModel ProcurarPrimeiraConta() {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.ProcurarContaPorInstituicao(0);
            } else {
                HttpResponse<String> response = null;
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/contas/procurarPrimeiraConta";
                response = rest.Get(url, "");
                ContaModel contaModel;
                contaModel = gson.fromJson(response.body(), ContaModel.class);

                return contaModel;
            }
        } catch (JsonSyntaxException | IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public int Alterar(ContaModel conta, int codigoConta) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.AlterarConta(conta, codigoConta);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/contas/alterar?codigoConta=" + codigoConta;
                String request = Util.stringObjectToJson(conta, "");
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

    public int Remover(int codigoConta) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.RemoverConta(codigoConta);
            } else {
                System.out.println("ultraBarber.controller.ContaController.Remover()");
            }
            return 0;
        } catch (Exception ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public ArrayList<ArrayList> ListarTodasContas(char status) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.ListarTodasContas(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/contas/listarTodasContas";
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
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList PreencherResumoContas() {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.ExibirResumo();
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/contas/preencherResumoContas";
                String request = "";
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

    public ArrayList PreencherComboContas(char status) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.PreencherComboContas(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/contas/preencherComboContas";
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

    public ArrayList ExibirResumo() {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.ExibirResumo();
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/contas/exibirResumo";
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
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public double VerificarSaldo(int numeroConta) {

        try {
            if (!Login.isIsService()) {
                rc = new RepositorioConta();
                return rc.VerificarSaldo(numeroConta);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/contas/";
                String request = "verificarSaldo?numeroConta=" + numeroConta;
                response = rest.Get(url, request);
                if (response.statusCode() == 200) {
                    return Double.parseDouble(response.body());

                }
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
