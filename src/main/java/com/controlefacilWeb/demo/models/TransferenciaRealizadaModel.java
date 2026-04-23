package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaRealizadaModel {

    private int codigoTransferenciaRealizada;
    private int codigoTransferencia;
    private int codigoProduto;
    private int quantidade;
}
