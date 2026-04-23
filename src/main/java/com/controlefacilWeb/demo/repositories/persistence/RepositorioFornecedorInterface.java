/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.FornecedorModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioFornecedorInterface {

    int CadastrarFornecedor(FornecedorModel fornecedor);

    int AlterarFornecedor(FornecedorModel fornecedor);

    int RemoverFornecedor(int codigoFornecedor);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosFornecedor();

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboFornecedor(char status);
}
