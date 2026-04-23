package com.controlefacilWeb.demo.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaCompradaModel {

    private int codigoNotaComprada;
    private int numeroNota;
    private int codigoFornecedor;
    private int cdformaPagamento;
    private int codigoLoja;
    private String data;
    private double valor;
    private double valorCheio;
    private double valorDesconto;
    private char status;
    private ArrayList<ProdutoCompradoModel> produtos;
}
