package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoModel {

    public ConfiguracaoModel(int codigoConf, int tipo, char isIntegradoNegocioFinanceiro, char isFecharAut,
            char isPagarAut, char verificaPermissao) {
        this.codigoConf = codigoConf;
        this.tipo = tipo;
        this.isIntegradoNegocioFinanceiro = isIntegradoNegocioFinanceiro;
        this.isFecharAut = isFecharAut;
        this.isPagarAut = isPagarAut;
        this.verificaPermissao = verificaPermissao;
    }

    public ConfiguracaoModel(String ScriptCriarBanco) {
        this.scriptCriarBanco = ScriptCriarBanco;
    }

    private int codigoConf;
    private int tipo;
    private char isIntegradoNegocioFinanceiro;
    private char isFecharAut;
    private char isPagarAut;
    private char verificaPermissao;
    private String scriptCriarBanco;
}
