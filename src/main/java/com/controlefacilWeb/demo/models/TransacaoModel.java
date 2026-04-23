package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoModel {

    private int codigo;
    private String tipoTransacao;
    private String usuario;
    private double valor;
    private String dataHora;
    private char isEfetivada;
    private ContaModel contaOrigem;
    private String obs;
    private int codigoCategoria;
    private int codigoSubCategoria;
    private int codigofatura;
    private char autoEfetiva;
    private String dataEfetivacao;
    private int codigoLoja;
}
