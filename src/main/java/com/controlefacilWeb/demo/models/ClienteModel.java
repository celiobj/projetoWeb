package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClienteModel extends PessoaModel {

    private int codigoCliente;
    private String documento;
    private String sexo;
    private String numerosus;

    public ClienteModel(int codigoCliente, String nome, String documento, String sexo, String numerosus) {
        setNome(nome);
        this.codigoCliente = codigoCliente;
        this.documento = documento;
        this.sexo = sexo;
        this.numerosus = numerosus;
    }
}
