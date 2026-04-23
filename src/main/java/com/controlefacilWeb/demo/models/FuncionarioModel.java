package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class FuncionarioModel extends PessoaModel {

    public FuncionarioModel(int codigoPessoa, String nome) {
        super(codigoPessoa, nome);
    }

    private int codigoFuncionario;
    private int codigoUsuario;
}
