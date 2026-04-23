package com.controlefacilWeb.demo.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoModel {

    private int codigoOrdem;
    private int codigoCliente;
    private int codigoFuncionario;
    private int codigoFormaPagamento;
    private String data;
    private double valor;
    private double valorCheio;
    private double valorDesconto;
    private int codigoLoja;
    private ArrayList<ServicosPrestadosModel> servicos;
    private ArrayList<ProdutoVendidoModel> produtos;
}
