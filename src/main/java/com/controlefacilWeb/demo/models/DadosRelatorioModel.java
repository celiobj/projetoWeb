package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosRelatorioModel {

    private int codigoordem;
    private String nomeVendedor;
    private String nomeCliente;
    private String valor;
    private String valorCheio;
    private String valorDesconto;
    private String data;
    private int codigoservico;
    private String nomeServico;
    private String valorServico;
    private String valorCheioServico;
    private String valorDescontoServico;
    private String valorComissaoServico;
}
