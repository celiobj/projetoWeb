/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.ContaModel;


/**
 *
 * @author celio.junior
 */
public interface RepositorioContaInterface {
    
    int CadastrarConta(ContaModel conta);    
    
    int AlterarConta(ContaModel conta, int codigoConta);
    
    int retornarPrimeiraConta();
        
    public ContaModel ProcurarContaPorInstituicao(int codigoInstituicao);
    
    int RemoverConta(int codigoConta);
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasContas(char status);
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ExibirResumo();
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboContas(char status);
    
    double VerificarSaldo(int numeroConta);
    
}
