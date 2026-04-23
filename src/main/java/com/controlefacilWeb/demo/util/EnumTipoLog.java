/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.controlefacilWeb.demo.util;

/**
 *
 * @author celio.junior
 */
public enum EnumTipoLog {
    INFO(1),
    ERROR(2);

    private final int valor;

    private EnumTipoLog(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
