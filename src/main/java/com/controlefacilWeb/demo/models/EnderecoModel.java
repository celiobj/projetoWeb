package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {

    private int codigo;
    private int codigoCliente;
    private int codigoFuncionario;
    private int codigoFornecedor;
    private String endereco;
    private String tipo;
    private int codigoLoja;
}
