package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoModel {

    
    private Integer codigoProduto;
    private String nome;
    private String descricao;
    private Double valorvenda;
    private Double valormMedioCompra;
    private Double valorcomissao;
    private Integer quantidade;
    private char isativo;

    public ProdutoModel(int codigoProduto, String nome, String descricao, double valorvenda, double valormMedioCompra, double valorcomissao, Integer quantidade, char isativo) {
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.valorvenda = valorvenda;
        this.valormMedioCompra = valormMedioCompra;
        this.valorcomissao = valorcomissao;
        this.quantidade = quantidade;
        this.isativo = isativo;
    }

    public ProdutoModel(int codigoProduto, String nome, String descricao, double valorvenda, double valorcomissao, char isAtivo) {
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.valorvenda = valorvenda;
        this.valorcomissao = valorcomissao;
        this.isativo = isAtivo;
    }
}
