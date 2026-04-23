/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.controlefacilWeb.demo.util;

/**
 *
 * @author celio.junior
 */
public enum EnumTipoMovimentacao {
    ENTRADA(1),
    SAIDA(2),
    TRANSFERENCIA(3);

    private final int valor;

    private EnumTipoMovimentacao(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
