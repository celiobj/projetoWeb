package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicosPrestadosModel {

    private int codigoservicoprestado;
    private String data;
    private int codigoservico;
    private int codigoOrdemServico;
    private double valor;
    private double valorCheio;
    private double valorDesconto;
    private double valorcomissao;
    private int codigoLoja;
    private int quantidade;
}
