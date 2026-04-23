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
import com.controlefacilWeb.demo.models.OrdemServicoModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioOrdemServico;
import com.controlefacilWeb.demo.services.RestApi;
import com.controlefacilWeb.demo.util.Util;

import tools.jackson.databind.ObjectMapper;

import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class OrdemServicoController {

    RepositorioOrdemServico ro;
    RestApi rest;

    public int FecharOrdemServico(OrdemServicoModel ordemServico, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.LancarOrderdeServico(ordemServico, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/fecharOrdemServico?codigoLoja=" + codigoLoja;
                String request = Util.stringObjectToJson(ordemServico, "");
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int CancelarOrdemServico(int codOrdemServico, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.CancelarOrderdeServico(codOrdemServico, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/servicos/cancelarOrdemServico?codOrdemServico=" + codOrdemServico + "&codigoLoja=" + codigoLoja;
                String request = "";
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<ArrayList> ListarTodasOrdensDeServico(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroVendedor, int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarOrdemServico(isFiltroData, datainicio, dataFim, isFiltroVendedor, codigoVendedor, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodasOrdensDeServico";
                String request = "?isFiltroData=" + isFiltroData + "&datainicio=" + datainicio + "&dataFim=" + dataFim + "&isFiltroVendedor=" + isFiltroVendedor + "&codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosItensOrdensDeServicoPorOrdem(int codigoOrdem, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarItensOrdemServico(false, null, null, false, 0, true, codigoOrdem, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosItensOrdensDeServicoPorOrdem";
                String request = "?codigoOrdem=" + codigoOrdem + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosProdutosOrdensDeServicoPorOrdem(int codigoOrdem, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarProdutosOrdemServico(false, null, null, false, 0, true, codigoOrdem, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosProdutosOrdensDeServicoPorOrdem";
                String request = "?codigoOrdem=" + codigoOrdem + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosItensOrdensDeServicoPorData(String dataInicio, String dataFim, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarItensOrdemServico(true, dataInicio, dataFim, false, 0, false, 0, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosItensOrdensDeServicoPorData";
                String request = "?dataInicio=" + dataInicio + "&dataFim=" + dataFim + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosItensOrdensDeServicoPorVendedor(int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarItensOrdemServico(false, null, null, true, codigoVendedor, false, 0, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosItensOrdensDeServicoPorVendedor";
                String request = "?codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosItensOrdensDeServicoPorDataEPorVendedor(String dataInicio, String dataFim, int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarItensOrdemServico(true, dataInicio, dataFim, true, codigoVendedor, false, 0, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosItensOrdensDeServicoPorDataEPorVendedor";
                String request = "?dataInicio=" + dataInicio + "&dataFim=" + dataFim + "&codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ArrayList> ListarTodosItensOrdensDeServico(int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.ListarItensOrdemServico(false, null, null, false, 0, false, 0, codigoLoja);
            } else {
                HttpResponse<String> response = null;
                ArrayList<ArrayList> retorno = new ArrayList<>();
                rest = new RestApi();
                String url = "/servicos/listarTodosItensOrdensDeServico";
                String request = "?codigoLoja=" + codigoLoja;
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
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int TrocarVendedor(int codigoOrdem, int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.TrocarVendedor(codigoOrdem, codigoVendedor, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;
                String url = "/servicos/trocarVendedor?codigoOrdem=" + codigoOrdem + "&codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
                String request = "";
                response = rest.Post(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double SomarComissaoTodosItensOrdensDeServico(int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.SomarComissaoItensOrdemServico(false, null, null, false, 0, false, 0, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/";
                String request = "somarComissaoTodosItensOrdensDeServico?codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return Double.parseDouble(response.body());
                }
            }
            return 0;
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double SomarComissaoTodosItensOrdensDeServicoPorData(String dataInicio, String dataFim, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.SomarComissaoItensOrdemServico(true, dataInicio, dataFim, false, 0, false, 0, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/";
                String request = "somarComissaoTodosItensOrdensDeServicoPorData?dataInicio=" + dataInicio + "&dataFim=" + dataFim + "&codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return Double.parseDouble(response.body());
                }
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double SomarComissaoTodosItensOrdensDeServicoPorVendedor(int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.SomarComissaoItensOrdemServico(false, null, null, true, codigoVendedor, false, 0, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/";
                String request = "somarComissaoTodosItensOrdensDeServicoPorVendedor?codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return Double.parseDouble(response.body());
                }
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public double SomarComissaoTodosItensOrdensDeServicoPorDataEPorVendedor(String dataInicio, String dataFim, int codigoVendedor, int codigoLoja) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.SomarComissaoItensOrdemServico(true, dataInicio, dataFim, true, codigoVendedor, false, 0, codigoLoja);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/";
                String request = "somarComissaoTodosItensOrdensDeServicoPorDataEPorVendedor?dataInicio=" + dataInicio + "&dataFim=" + dataFim + "&codigoVendedor=" + codigoVendedor + "&codigoLoja=" + codigoLoja;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return Double.parseDouble(response.body());
                }
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
       
    }

    public int HorarioServicos(boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroCliente, int codigoCliente, String dataInicio, String dataFim) {

        try {
            if (!Login.isIsService()) {
                ro = new RepositorioOrdemServico();
                return ro.horarioServicos(isFiltroVendedor, codigoVendedor, isFiltroCliente, codigoCliente, dataInicio, dataFim);
            } else {
                rest = new RestApi();
                HttpResponse<String> response = null;

                String url = "/servicos/";
                String request = "horarioServicos?isFiltroVendedor=" + isFiltroVendedor + "&codigoVendedor=" + codigoVendedor + "&isFiltroCliente=" + isFiltroCliente + "&codigoCliente=" + codigoCliente + "&dataInicio=" + dataInicio + "&dataFim=" + dataFim;
                response = rest.Get(url, request);
                if (response.statusCode() == 201) {
                    return Integer.parseInt(response.body());
                }
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
