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
import com.controlefacilWeb.demo.models.ComissaoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioComissao implements RepositorioComissaoInterface {

    public RepositorioComissao() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarComissao(ComissaoModel comissao) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (comissao.getTipo() == 1) {
                    st.executeUpdate("UPDATE public.comissao "
                            + "SET isativo='N' "
                            + "WHERE tipo = 1");
                }
                st.execute("INSERT INTO public.comissao "
                        + "(descricao, valor,tipo,codigoloja, isativo) values("
                        + "'" + comissao.getDescricao() + "', "
                        + "'" + comissao.getValor() + "', "
                        + "'" + comissao.getTipo() + "', "
                        + "" + comissao.getCodigoLoja() + ", "
                        + "'" + comissao.getIsAtivo() + "')");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioComissao.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<ArrayList> listarTodasComissoes(int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT codigocomissao,descricao, valor,tipo, isativo "
                        + "FROM public.comissao "
                        + "WHERE codigoloja = " + codigoLoja + " "
                        + "ORDER BY codigocomissao");
                rs.next();
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

    @Override
    public int AlterarStatus(int codigoComissao, char isAtivo, int tipo) {

        Connection con;
        try {
            AccessDatabase a = new AccessDatabase();
            con = a.conectar();
            Statement st = con.createStatement();
            if (tipo == 1) {
                st.executeUpdate("UPDATE public.comissao "
                        + "SET isativo='N' "
                        + "WHERE tipo = 1");
            }
            st.executeUpdate("UPDATE public.comissao "
                    + "SET isativo='" + isAtivo + "' "
                    + "WHERE codigocomissao = " + codigoComissao + "");
            //con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public ComissaoModel ProcurarComissaoPercentual(int codigoLoja) {

        try {

            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "codigocomissao,descricao, valor,tipo,isativo,codigoloja "
                        + "FROM public.comissao "
                        + "WHERE tipo = '1' "
                        + "AND codigoloja = " + codigoLoja + " "
                        + "AND isativo = 'S'");
                rs.next();
                ComissaoModel comissao;
                comissao = new ComissaoModel(
                        rs.getInt("codigocomissao"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getInt("tipo"),
                        rs.getString("isativo").charAt(0),
                        rs.getInt("codigoloja"));
                //con.close();
                return comissao;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

}
