package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {

    private int codigo;
    private String user;
    private String pass;
    private Integer tipo;
    private char forcarSenha;
    private int codigoLoja;
}
