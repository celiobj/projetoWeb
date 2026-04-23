package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoModel {

    private int codigo;
    private int numero;
    private String nome;
    private int tipo;
    private boolean isAtivo;
}
