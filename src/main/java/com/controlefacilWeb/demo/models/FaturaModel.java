package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaturaModel {

    private int codigoFatura;
    private String mesFatura;
    private double valorFatura;
    private char isFechada;
    private char isPaga;
    private char isVigente;
    private String dataPagamento;
    private int codigoCartao;
    private String dataInicio;
    private String dataFim;
    private String dataVencimento;
}
