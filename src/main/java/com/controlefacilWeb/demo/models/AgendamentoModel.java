package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoModel {

    private int codigoAgendamento;
    private int codigoFuncionario;
    private int codigoCliente;
    private String nomeFuncionario;
    private String nomeCliente;
    private String data;
    private String hora;
    private char isAtivo;
    private int codigoLoja;
    private int colunaDia;
    private int colunaHora;
    private String descricao;
}
