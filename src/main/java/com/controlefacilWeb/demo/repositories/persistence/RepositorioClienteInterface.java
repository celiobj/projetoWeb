/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.ClienteModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioClienteInterface {

    int CadastrarCliente(ClienteModel cliente);

    int AlterarCliente(ClienteModel cliente);
        
    ClienteModel ProcurarCliente(int codigoCliente);

     ClienteModel ProcurarClientePorNome(String nomeCliente);

    int RemoverCliente(int codigoCliente);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosClientes();

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboClientes(char status);

}
