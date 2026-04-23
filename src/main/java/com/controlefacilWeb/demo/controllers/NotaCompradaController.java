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
import com.controlefacilWeb.demo.models.NotaCompradaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioNotaCompra;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class NotaCompradaController {

    RepositorioNotaCompra rn;
    RestApi rest;

    public int FecharNota(NotaCompradaModel nota, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotaCompra();
                return rn.LancarNotaCompra(nota, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/notas/fecharNota?codigoLoja=" + codigoLoja;
                String request = Util.stringObjectToJson(nota, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(NotaCompradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<ArrayList> ListarTodasNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotaCompra();
                return rn.ListarNotas(isFiltroData, datainicio, dataFim, isFiltroFornecedor, codigoFornecedor, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/notas/listarTodasNotas";
                String request = "?isFiltroData=" + isFiltroData + "&datainicio=" + datainicio + "&dataFim=" + dataFim + "&isFiltroFornecedor=" + isFiltroFornecedor + "&codigoFornecedor=" + codigoFornecedor + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(NotaCompradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosProdutosNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, boolean isFiltroNota, int codigoNota, int codigoLoja) {
        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotaCompra();
                return rn.ListarProdutosNotas(isFiltroData, datainicio, dataFim, isFiltroFornecedor, codigoFornecedor, isFiltroNota, codigoNota, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/notas/listarTodosProdutosNotas";
                String request = "?isFiltroData=" + isFiltroData + "&datainicio=" + datainicio + "&dataFim=" + dataFim + "&isFiltroFornecedor=" + isFiltroFornecedor + "&codigoFornecedor=" + codigoFornecedor + "&isFiltroNota=" + isFiltroNota + "&codigoNota=" + codigoNota + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(NotaCompradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int CancelarNota(int codigoNota, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rn = new RepositorioNotaCompra();
                return rn.CancelarNota(codigoNota, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/notas/cancelarNota?codigoNota=" + codigoNota + "&codigoLoja=" + codigoLoja;
                String request = "";
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(NotaCompradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
