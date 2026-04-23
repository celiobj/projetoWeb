/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.FaturaModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioFaturaInterface {

    int CriarFatura(FaturaModel fatura);

    int AbrirFatura(FaturaModel fatura);

    int AlterarFatura(FaturaModel fatura, int codigoFatura);

    void AtualizarSaldo(int codigoFatura, char status);

    void FecharFatura(int codigoFatura);

    void FecharFaturasVencidas();

    int PagarFatura(FaturaModel fatura, ContaModel conta, int codigoCategoria, int codigoSubCategoria, boolean lancarDebito);

    FaturaModel BuscarFaturaAberta(CartaoModel cartao, String data);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> Listartodasfaturas(int codigoCartao);
    
    ArrayList<FaturaModel> ListartodasfaturasFechadasNaoPagas(int codigoCartao);

    @SuppressWarnings("rawtypes")
    ArrayList<ArrayList> ListartodasTransacoesDafatura(int codigoFatura, char isefetivada);

}
