package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoModel {

    private int codigoSolicitacao;
    private int codigoUsuarioAdm;
    private int codigoUsuarioSolic;
    private int codigoTela;
    private String data;
    private char status;
    private char isAtivo;
    private int codigoLoja;
    private String descricao;
}
