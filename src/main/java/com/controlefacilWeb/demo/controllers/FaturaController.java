/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.FaturaModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioFatura;
import com.controlefacilWeb.demo.database.Login;

/**
 *
 * @author celio.junior
 */
public class FaturaController {

    public FaturaModel BuscarFaturaAberta(CartaoModel cartao, int dia, int mesInicio, int mesFim, int mesVencimento, int ano, int anoFatura, int anoVencimento) {
        RepositorioFatura rf = new RepositorioFatura();
        FaturaModel fatura = null;
        String data = "";
        int mesData = cartao.getDiaFechamento() >= dia ? mesFim : mesInicio;
        if (Login.conexao == 1) {
            data = String.format("%02d", dia) + "-" + String.format("%02d", mesData) + "-" + anoFatura;
        } else {
            data = anoFatura + "-" + String.format("%02d", mesData) + "-" + String.format("%02d", dia);
        }
        try {
            if (!Login.isIsService()) {
                fatura = rf.BuscarFaturaAberta(cartao, data);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.BuscarFaturaAberta()");
            }

        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (fatura != null) {
            return fatura;
        } else {
            Date dataFatura = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataFatura);
            FaturaModel novaFatura = new FaturaModel();
            novaFatura.setCodigoCartao(cartao.getCodigoCartao());
            novaFatura.setIsFechada('N');
            novaFatura.setIsPaga('N');
            novaFatura.setIsVigente('S');
            novaFatura.setValorFatura(0);
            String dataInicio;
            String dataFim;
            String dataVencimento;
            int diaAbertura = cartao.getDiaFechamento() + 1;
            int diaFechamento = cartao.getDiaFechamento();
            int diaVencimento = cartao.getDiaVencimento();
            if (Login.conexao == 1) {
                dataInicio = String.format("%02d", diaAbertura) + "-" + String.format("%02d", mesInicio) + "-" + ano;
                dataFim = String.format("%02d", diaFechamento) + "-" + String.format("%02d", mesFim) + "-" + anoFatura;
                dataVencimento = String.format("%02d", diaVencimento) + "-" + String.format("%02d", mesVencimento) + "-" + anoVencimento;
            } else {
                dataInicio = ano + "-" + String.format("%02d", mesInicio) + "-" + String.format("%02d", diaAbertura);
                dataFim = anoFatura + "-" + String.format("%02d", mesFim) + "-" + String.format("%02d", diaFechamento);
                dataVencimento = anoVencimento + "-" + String.format("%02d", mesVencimento) + "-" + String.format("%02d", diaVencimento);
            }
            novaFatura.setDataInicio(dataInicio);
            novaFatura.setDataFim(dataFim);
            novaFatura.setDataVencimento(dataVencimento);
            int cod = CriarFatura(novaFatura);
            if (cod != 0) {
                novaFatura.setCodigoFatura(cod);
                return novaFatura;
            } else {
                return null;
            }
        }
    }

    public int CriarFatura(FaturaModel fatura) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                return rf.CriarFatura(fatura);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.CriarFatura()");
            }
            return 0;
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void AtualizarSaldo(int codigoFatura, char status) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                rf.AtualizarSaldo(codigoFatura, status);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.AtualizarSaldo()");
            }
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList ListarTodasFaturas(int codigoFatura) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                return rf.Listartodasfaturas(codigoFatura);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.ListarTodasFaturas()");
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<FaturaModel> ListarTodasFaturasFechadasNaoPagas(int codigoFatura) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                return rf.ListartodasfaturasFechadasNaoPagas(codigoFatura);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.ListarTodasFaturasFechadasNaoPagas()");
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList ListarTodasTransacoesFatura(int codigoFatura, char isefetivada) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                return rf.ListartodasTransacoesDafatura(codigoFatura, isefetivada);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.ListarTodasTransacoesFatura()");
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void FecharFatura(int codigoFatura) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                rf.FecharFatura(codigoFatura);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.FecharFatura()");
            }
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FecharFaturasVencidas() {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                rf.FecharFaturasVencidas();
            } else {
                System.out.println("ultraBarber.controller.FaturaController.FecharFaturasVencidas()");
            }
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PagarFatura(FaturaModel fatura, ContaModel conta, int codigoCategoria, int codigoSubCategoria, boolean lancarDebito) {

        try {
            if (!Login.isIsService()) {
                RepositorioFatura rf = new RepositorioFatura();
                rf.PagarFatura(fatura, conta, codigoCategoria, codigoSubCategoria, lancarDebito);
            } else {
                System.out.println("ultraBarber.controller.FaturaController.PagarFatura()");
            }
        } catch (Exception ex) {
            Logger.getLogger(FaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
