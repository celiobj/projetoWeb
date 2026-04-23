package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaModel {

    private int codigo;
    private int numero;
    private String nome;
    private int tipo;
    private InstituicaoModel instituicao;
    private double saldo;
    private String dataCriacao;
    private boolean isAtivo;
}
