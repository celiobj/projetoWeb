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
import com.controlefacilWeb.demo.models.EnderecoModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioEndereco;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class EnderecoControler {

    RepositorioEndereco re;
    RestApi rest;

    public int CadastrarEndereco(EnderecoModel endereco) {

        try {
            if (!Login.isIsService()) {
                re = new RepositorioEndereco();
                return re.CadastrarEndereco(endereco);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/enderecos/";
                String request = Util.stringObjectToJson(endereco, "");
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

    public ArrayList<ArrayList> ListarTodosEnderecos(char origem, PessoaModel pessoa) {

        try {
            if (!Login.isIsService()) {
                re = new RepositorioEndereco();
                return re.ListarTodosEnderecos(origem, pessoa);
            } else {

                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/enderecos/?origem=" + origem + "&codPessoa=" + pessoa.getCodigoPessoa();
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
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
