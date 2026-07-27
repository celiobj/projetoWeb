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


import com.controlefacilWeb.demo.models.AgendamentoModel;
import com.controlefacilWeb.demo.models.ClienteModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioAgendamento;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class AgendamentoController {

    RepositorioAgendamento ra;
    RestApi rest;

    public int RealizarAgendamento(AgendamentoModel agendamento, int codigoLoja) {
        try {
            if (!Login.isIsService()) {
                ra = new RepositorioAgendamento();
                try {
                    return ra.RealizarAgendamento(agendamento);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/agendamentos/realizarAgendamento";
                String request = Util.stringObjectToJson(agendamento, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    EnderecoControler ec = new EnderecoControler();
                    ClienteModel cliente = new ClienteModel(agendamento.getCodigoCliente(), "", "", "", "");
                    char origem = 'C';
                    PessoaModel pessoaEndereco = cliente;
                    ArrayList<ArrayList> itensTabela = ec.ListarTodosEnderecos(origem, pessoaEndereco);
                    if (itensTabela.size() >= 1) {
                        for (ArrayList iterator : itensTabela) {
                            if (iterator.get(2).toString().equalsIgnoreCase("email")) {
                                Util.enviarEmail(iterator.get(1).toString(), "Agendamento", "Serviço agendado");
                            }
                        }
                    }
                    return 1;
                }
            }
            return 0;
        } catch (URISyntaxException | IOException | InterruptedException | IndexOutOfBoundsException ex) {
            Logger.getLogger(AgendamentoController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return 0;

    }

    public ArrayList<AgendamentoModel> ListarTodosAgendamentos(int codigoLoja, int codigoFuncionario, String dataInicial, String dataFinal) {

        try {
            if (!Login.isIsService()) {
                ra = new RepositorioAgendamento();
                try {
                    return ra.ListarAgendamentos(codigoLoja, codigoFuncionario, dataInicial, dataFinal);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                HttpResponse<String> response = null;
                ArrayList<AgendamentoModel> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/agendamentos";
                String request = "?codigoLoja=" + codigoLoja + "&codigoFuncionario=" + codigoFuncionario + "&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;
                response = rest.Get(url, request);
                ObjectMapper objectMapper = new ObjectMapper();
                if (response.body().equalsIgnoreCase("")) {
                    return null;

                } else {
                    AgendamentoModel[] agendamentos = objectMapper.readValue(response.body(), AgendamentoModel[].class
                    );
                    if (agendamentos != null) {
                        retorno.addAll(Arrays.asList(agendamentos));
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

    public int CancelarAgendamento(int codigoAgendamento) {

        try {
            if (!Login.isIsService()) {
                ra = new RepositorioAgendamento();
                try {
                    return ra.CancelarAgendamento(codigoAgendamento);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/agendamentos/cancelarAgendamento?codigoAgendamento=" + codigoAgendamento;
                response = rest.Post(url, "");
                if (response.statusCode() == 201) {
                    return 1;
                }
            }

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public AgendamentoModel ProcurarAgendamento(int codigoAgendamento) {

        try {
            if (!Login.isIsService()) {
                ra = new RepositorioAgendamento();
                try {
                    return ra.ProcurarAgendamento(codigoAgendamento);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                rest = new RestApi();
                Gson gson = new Gson();
                String url = "/agendamentos/consultarAgendamento?codigoAgendamento=";
                AgendamentoModel agendamentoModel = gson.fromJson(rest.Get(url, Integer.toString(codigoAgendamento)).body(), AgendamentoModel.class
                );
                return agendamentoModel;
            }
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Logger.getLogger(AgendamentoController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
