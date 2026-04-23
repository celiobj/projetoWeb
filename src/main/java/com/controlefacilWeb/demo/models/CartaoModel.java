package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoModel {

    private int codigoCartao;
    private String nomeCartao;
    private int codigoInstituicao;
    private double limiteDisponivel;
    private double limiteEstipulado;
    private int diaFechamento;
    private int diaVencimento;
    private char isAtivo;
    private String numeroCartao;
   
    
    
}
