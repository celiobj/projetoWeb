/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.NotaCompradaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioNotaCompraInterface {

    int LancarNotaCompra(NotaCompradaModel nota, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarProdutosNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, boolean isFiltroNota, int codigoNota, int codigoloja);

    int CancelarNota(int codigoNota, int codigoLoja);
}
