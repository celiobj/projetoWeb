package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCompradoModel {

    private int codigoProdutoComprado;
    private String data;
    private int codigoProduto;
    private int codigoNota;
    private double valor;
    private double valorCheio;
    private double valorDesconto;
    private int codigoLoja;
    private int quantidade;
}
