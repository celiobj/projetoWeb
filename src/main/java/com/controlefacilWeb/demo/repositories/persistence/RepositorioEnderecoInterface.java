/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.EnderecoModel;
import com.controlefacilWeb.demo.models.PessoaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioEnderecoInterface {

    int CadastrarEndereco(EnderecoModel endereco);

    int RemoverEndereco(int codigoEndereco);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosEnderecos(char status, PessoaModel pessoa);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboEnderecos(char status);

}
