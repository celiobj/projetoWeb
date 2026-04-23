/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.CategoriaModel;
import com.controlefacilWeb.demo.models.SubCategoriaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioCategoriaInterface {

    String buscarCategoria(int codigoCategoria);

    String buscarSubCategoria(int codigoSubCategoria);

    int CadastrarCategoria(CategoriaModel categoria);

    int CadastrarSubCategoria(SubCategoriaModel subCategoria);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodasCategorias(char status);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> listarTodasSubCategorias(int codigoCategoriaPai, char status);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboCategoria(char status);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboSubCategoria(int codigoCategoriaPai, char status);

}
