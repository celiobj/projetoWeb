package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoModel {

    private int codigoServico;
    private String nome;
    private String descricao;
    private double valor;
    private double valorComissao;
    private char isAtivo;
}
