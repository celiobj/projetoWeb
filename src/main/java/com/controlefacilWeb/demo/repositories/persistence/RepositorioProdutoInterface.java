/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.ProdutoModel;
import com.controlefacilWeb.demo.models.TransferenciaRealizadaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioProdutoInterface {

    int CadastrarProduto(ProdutoModel produto, @SuppressWarnings("rawtypes") ArrayList<ArrayList> lojas);

    int PegarSaldo(int codigoProduto, int codigoLoja);

    ProdutoModel Procurar(int codigoProduto);

    int AlterararProduto(ProdutoModel produto, int codigoProduto);

    int MovimentarProduto(int codgigoProduto, int quantidade, String nota, @SuppressWarnings("rawtypes") Enum tipo, String descricao);

    int MovimentarProduto(int codgigoProduto, int quantidade, String nota, @SuppressWarnings("rawtypes") Enum tipo, String descricao, int codigoLoja);

    int RealizarTrasnferencia(int codigoLojaOrigem, int codigoLojaDestino, ArrayList<TransferenciaRealizadaModel> itensTrasnferencia);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosProdutos();

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosProdutos(int codigoLoja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasSaidasProduto(int codigoProduto);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasEntradasProduto(int codigoProduto);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboProdutos(char status);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasSaidasProduto(int codigoProduto, int codigoLoja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasEntradasProduto(int codigoProduto, int codigoLoja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasTransferensiasProduto(int codigoProduto);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboProdutos(char status, int codigoLoja);

    int atualizarValorMedioCompraProduto(int codigoProduto);
}
