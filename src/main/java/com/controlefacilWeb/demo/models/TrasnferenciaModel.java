package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrasnferenciaModel {

    private int codigoTransferencia;
    private int codigoLojaOrigem;
    private int codigoLojaDestino;
    private String data;
    private char status;
}
