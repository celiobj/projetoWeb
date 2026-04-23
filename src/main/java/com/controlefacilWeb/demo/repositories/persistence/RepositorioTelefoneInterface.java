/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.models.TelefoneModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioTelefoneInterface {

    int CadastrarTelefone(TelefoneModel telefone);

    int RemoverTelefone(int codigoTelefone);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosTelefones(char status, PessoaModel pessoa);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboTelefone(char status);

}
