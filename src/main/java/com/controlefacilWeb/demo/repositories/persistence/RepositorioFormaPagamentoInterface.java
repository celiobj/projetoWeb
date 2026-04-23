/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.FormaPagamentoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioFormaPagamentoInterface {

    int CadastrarFormaPagamento(FormaPagamentoModel formaPagamento);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasFormasPagamento();
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboFormaPagamento(char status);

}
