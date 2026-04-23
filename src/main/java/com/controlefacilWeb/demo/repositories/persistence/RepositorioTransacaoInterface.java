/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.RegistroModel;
import com.controlefacilWeb.demo.models.TipoTransacaoModel;
import com.controlefacilWeb.demo.models.TransacaoModel;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;

/**
 *
 * @author celio.junior
 */
public interface RepositorioTransacaoInterface {

    int RegistrarReceita(TransacaoModel transacao, ContaModel conta, int codigoLoja);

    int RegistrarDespesa(TransacaoModel transacao, ContaModel conta, int codigoLoja);

    int EfetivarTransacaoDespesa(TransacaoModel transacao, ContaModel conta, int codigoLoja);

    int EfetivarTransacaoReceita(TransacaoModel transacao, ContaModel conta, int codigoLoja);

    int Cancelar(TransacaoModel transacao, ContaModel conta, int codigoLoja);

    int RegistrarTransferecia(ContaModel contaOrigem, ContaModel contaDestino, TransacaoModel transacao, int codigoLoja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodasTransacoes(ContaModel conta, int tipo, int qtdDias, char status, int codigoLoja);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> PreencherComboTipoTransacao(char status);

    int CadastrarTipoTransacao(TipoTransacaoModel tipo);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListarTodosTiposTransacoes(char status);

    int CriarRegistro(RegistroModel registro, ContaModel conta, ContaModel contaOrigem, ContaModel contaDestino, EnumTipoMovimentacao tipoTransacao);

    int CancelarRegistro(int codigoTransacao, int codigoCartao, int codigoFatura, int cancelarTudo,ArrayList<Integer> codigosFatura, String usuario);

}
