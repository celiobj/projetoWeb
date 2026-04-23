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


import com.controlefacilWeb.demo.models.HorarioModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioHorario;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class HorarioController {

    RestApi rest;
    RepositorioHorario rh;

    public int CadastrarHorario(HorarioModel horario, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rh = new RepositorioHorario();
                return rh.CadastrarHorario(horario, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/horarios/?codigoLoja=" + codigoLoja;
                String request = Util.stringObjectToJson(horario, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return 1;
                }

            }
            return 0;
        } catch (IOException | InterruptedException | URISyntaxException ex) {
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<ArrayList> ListarTodosHorarios(char origem, PessoaModel pessoa, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                rh = new RepositorioHorario();
                return rh.ListarTodosHorarios(origem, pessoa, codigoLoja);
            } else {

                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/horarios/listarTodosHorarios?origem=" + origem + "&codPessoa=" + pessoa.getCodigoPessoa() + "&codLoja=" + codigoLoja;
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
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
