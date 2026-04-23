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
    private String cpf;

    public ClienteModel(int codigoCliente, String nome, int codigoPessoa, String cpf) {
        setCodigoPessoa(codigoPessoa);
        setNome(nome);
        this.codigoCliente = codigoCliente;
        this.cpf = cpf;
    }
}
