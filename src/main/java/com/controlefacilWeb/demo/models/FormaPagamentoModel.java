package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoModel {

    public FormaPagamentoModel(int codigoFormaPagamento, String descricao) {
        this.codigoFormaPagamento = codigoFormaPagamento;
        this.descricao = descricao;
    }

    private int codigoFormaPagamento;
    private String descricao;
    private char codigoLoja;
}
