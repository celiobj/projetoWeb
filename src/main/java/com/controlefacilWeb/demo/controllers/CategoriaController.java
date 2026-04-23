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
import com.controlefacilWeb.demo.models.CategoriaModel;
import com.controlefacilWeb.demo.models.SubCategoriaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioCategoria;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class CategoriaController {

    RestApi rest;

    public int CadastrarCategoria(CategoriaModel categoria) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.CadastrarCategoria(categoria);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/categorias/cadastrarCategoria";
                String request = Util.stringObjectToJson(categoria, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int CadastrarSubCategoria(SubCategoriaModel subCategoria) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.CadastrarSubCategoria(subCategoria);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/categorias/cadastrarSubCategoria";
                String request = Util.stringObjectToJson(subCategoria, "");
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

    public ArrayList<ArrayList> ListarTodasCategorias(char status) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.listarTodasCategorias(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/categorias/listarTodasCategorias";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    ArrayList[] categorias = objectMapper.readValue(response.body(), ArrayList[].class
                    );
                    if (categorias != null) {
                        retorno.addAll(Arrays.asList(categorias));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList<ArrayList> ListarTodasSubCategorias(int codigoCategoria, char status) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.listarTodasSubCategorias(codigoCategoria, status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/categorias/listarTodasSubCategorias";
                String request = "?codigoCategoria=" + codigoCategoria + "&status=" + status;
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

    public ArrayList preencherComboCategoria(char status) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.PreencherComboCategoria(status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/categorias/preencherComboCategoria";
                String request = "?status=" + status;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;
                } else {
                    String[] categorias = objectMapper.readValue(response.body(), String[].class
                    );
                    if (categorias != null) {
//                        retorno.addAll(Arrays.asList(lojas));
                        retorno.addAll(Arrays.asList(categorias));
                    }
                    return retorno;
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList preencherComboSubCategoria(int codigoCategoria, char status) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.PreencherComboSubCategoria(codigoCategoria, status);
            } else {
                HttpResponse<String> response = null;
                ArrayList<String> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/categorias/preencherComboSubCategoria";
                String request = "?status=" + status + "&codigoCategoria=" + codigoCategoria;
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
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String buscarNomeCategoria(int codigo) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.buscarCategoria(codigo);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/categorias/";
                String request = "buscarNomeCategoria?codigo=" + codigo;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return String.valueOf(response.body());
                }
            }
            return null;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String buscarNomeSubCategoria(int codigo) {

        try {
            if (!Login.isIsService()) {
                RepositorioCategoria rc = new RepositorioCategoria();
                return rc.buscarSubCategoria(codigo);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/categorias/";
                String request = "buscarNomeSubCategoria?codigo=" + codigo;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return String.valueOf(response.body());
                }
            }
            return null;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
