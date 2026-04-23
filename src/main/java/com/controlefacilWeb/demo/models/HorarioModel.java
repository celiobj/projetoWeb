package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioModel {

    private int codigo;
    private int codigoFuncionario;
    private String inicio;
    private String fim;
    private int codigoLoja;
    private int codigoHorarioInicio;
    private int codigoHorarioFim;
    private String descricao;
}
