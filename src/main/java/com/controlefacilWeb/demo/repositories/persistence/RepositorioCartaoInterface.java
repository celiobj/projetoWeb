/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;

/**
 *
 * @author celio.junior
 */
public interface RepositorioCartaoInterface {

    int Cadastrar(CartaoModel cartao);
    
    void AlterarSaldo(CartaoModel cartao, double valor, EnumTipoMovimentacao tipoTransacao);

    int Editar(CartaoModel cartaoNovo, int codigoCartao);

    CartaoModel Procurar(int codigoCartao);

    int Excluir(int codigoCartao);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodosCartoes(char status);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboCartoes(char status);
    
    double VerificarSaldo(int codigoCartao);

}
