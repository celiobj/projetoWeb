package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoModel {

    private int codigoNotificacao;
    private int codigoSolicitacao;
    private int codigoUsuario;
    private String descricao;
    private String data;
    private char isAtivo;
    private int codigoLoja;
}
