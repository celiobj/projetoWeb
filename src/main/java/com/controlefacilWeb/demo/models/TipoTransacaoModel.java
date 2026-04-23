package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoTransacaoModel {

    private int cdTipoTransacao;
    private String tipoTransacao;
    private boolean isAtivo;
}
