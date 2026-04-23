package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComissaoModel {

    private int codigoComissao;
    private String descricao;
    private double valor;
    private int tipo;
    private char isAtivo;
    private int codigoLoja;
}
