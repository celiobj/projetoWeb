/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioCartao implements RepositorioCartaoInterface {

    public RepositorioCartao() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int Cadastrar(CartaoModel cartao) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.cartao"
                        + "(nomecartao,numerocartao, codigoinstituicao, limitedisponivel, limiteestipulado, diafechamento, diavencimento, isativo) VALUES ("
                        + "'" + cartao.getNomeCartao() + "',"
                        + "'" + cartao.getNumeroCartao() + "',"
                        + "" + cartao.getCodigoInstituicao() + ","
                        + "" + cartao.getLimiteDisponivel() + ","
                        + "" + cartao.getLimiteEstipulado() + ","
                        + "" + cartao.getDiaFechamento() + ","
                        + "" + cartao.getDiaVencimento() + ","
                        + "'S');");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCartao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int Editar(CartaoModel cartaoNovo, int codigoCartao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int Excluir(int codigoCartao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList<ArrayList> listarTodosCartoes(char status) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT codigocartao, nomecartao,numerocartao, codigoinstituicao, limitedisponivel,limiteestipulado,diafechamento,diavencimento,isativo FROM public.cartao WHERE isativo = '" + status + "' ORDER BY codigocartao");
//                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    linhas.add(Util.proximaLinha(rs, rsmd));
                } while (rs.next());
                //con.close();
            }
            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ArrayList<ArrayList> PreencherComboCartoes(char status) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList contas = new ArrayList<ArrayList>();
            contas.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT codigocartao, nomecartao, codigoinstituicao FROM public.cartao WHERE isativo = '" + status + "' ORDER BY codigocartao");

                while (rs.next()) {

                    contas.add(rs.getInt("codigocartao") + "- " + rs.getString("nomecartao") + " - " + rs.getString("codigoinstituicao") + "");
                };
                //con.close();
            }

            return contas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public CartaoModel Procurar(int codigoCartao) {
        try {
            CartaoModel cartao = null;
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * "
                        + "FROM public.cartao WHERE "
                        + "codigocartao = " + codigoCartao);
                if (rs.next()) {
                    cartao = new CartaoModel();
                    cartao.setCodigoCartao(codigoCartao);
                    cartao.setNomeCartao(rs.getString("nomecartao"));
                    cartao.setNumeroCartao(rs.getString("numerocartao"));
                    cartao.setCodigoInstituicao(rs.getInt("codigoinstituicao"));
                    cartao.setLimiteDisponivel(rs.getDouble("limitedisponivel"));
                    cartao.setLimiteEstipulado(rs.getDouble("limiteestipulado"));
                    cartao.setDiaFechamento(rs.getInt("diafechamento"));
                    cartao.setDiaVencimento(rs.getInt("diavencimento"));
                }
                //con.close();
                return cartao;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public void AlterarSaldo(CartaoModel cartao, double valor, EnumTipoMovimentacao tipoTransacao) {

        try {
            AccessDatabase a = new AccessDatabase();
            if (tipoTransacao == EnumTipoMovimentacao.ENTRADA) {
                try (Connection con = a.conectar()) {
                    Statement st = con.createStatement();
                    st.execute("UPDATE public.cartao"
                            + " SET limitedisponivel = limitedisponivel + " + valor + " "
                            + "WHERE codigocartao = " + cartao.getCodigoCartao() + "");
                }
            } else {
                if (tipoTransacao == EnumTipoMovimentacao.SAIDA) {
                    try (Connection con = a.conectar()) {
                        Statement st = con.createStatement();
                        st.execute("UPDATE public.cartao"
                                + " SET limitedisponivel = limitedisponivel - " + valor + " "
                                + "WHERE codigocartao = " + cartao.getCodigoCartao() + "");
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCartao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public double VerificarSaldo(int codigoCartao) {
        double saldo = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = a.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT limitedisponivel FROM public.cartao "
                    + "WHERE codigocartao = '" + codigoCartao + "'");
            if (rs.next()) {
                saldo = rs.getDouble("limitedisponivel");
            } //con.close();
            return saldo;

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCartao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
