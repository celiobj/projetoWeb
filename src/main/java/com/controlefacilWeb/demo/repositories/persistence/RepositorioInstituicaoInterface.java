/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.InstituicaoModel;


/**
 *
 * @author celio.junior
 */
public interface RepositorioInstituicaoInterface {
    
    int CadastrarInstituicao(InstituicaoModel instituicao);
    
    int AlterarInstituicao(InstituicaoModel instituicao, int codigoIntituicao);
    
    int RemoverInstituicao(int codigoInstituicao);
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasInstituicoes(char status);
    
    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboInstituicoes(char status);
    
   
}
