/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.OrdemServicoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioOrdemServicoInterface {

    int LancarOrderdeServico(OrdemServicoModel ordermServico, int codigoloja);

    int CancelarOrderdeServico(int codigoOrdermServico, int codigoloja);

    int TrocarVendedor(int codigoOrdemServico, int codigoVendedor, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarOrdemServico(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroVendedor, int codigoVendedor, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarItensOrdemServico(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarProdutosOrdemServico(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoloja);

    double SomarComissaoItensOrdemServico(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoloja);

    int horarioServicos(boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroServico, int codigoServico, String dataInicio, String dataFim);
}
