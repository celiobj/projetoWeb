/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.FuncionarioModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioFuncionarioInterface {

    int CadastrarFuncionario(FuncionarioModel funcionario, int codigoloja);

    int AlterarFuncionario(FuncionarioModel funcionario, int codigoloja);

    FuncionarioModel ProcurarFuncionario(int codigoFuncionario);

    int RemoverFuncionario(int codigoFuncionario, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosFuncionarios(char status, int codigoloja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboFuncionarios(char status, int codigoloja);

}
