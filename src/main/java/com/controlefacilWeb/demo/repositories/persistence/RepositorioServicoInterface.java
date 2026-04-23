/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.ServicoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioServicoInterface {

    int CadastrarServico(ServicoModel servico);

    int AlterarServico(ServicoModel servico);

    ServicoModel Procurar(int codigoServico);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosServicos();

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboServicos(char status);

}
