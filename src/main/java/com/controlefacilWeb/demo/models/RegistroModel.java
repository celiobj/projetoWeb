package com.controlefacilWeb.demo.models;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroModel {

    private int codigoRegistro;
    private String data;
    private char status;
    private double valor;
    private ArrayList<TransacaoModel> transacoes;
}
