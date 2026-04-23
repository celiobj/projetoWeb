package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoModel {

    private int codigoProduto;
    private String nome;
    private String descricao;
    private double valorvenda;
    private double valormMedioCompra;
    private double valorcomissao;
    private Integer quantidade;
    private char isativo;
}
