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
import com.controlefacilWeb.demo.models.FormaPagamentoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioFormaPagamento implements RepositorioFormaPagamentoInterface {

    public RepositorioFormaPagamento() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarFormaPagamento(FormaPagamentoModel formaPagamento) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.formapagamento "
                        + "(descricao) values("
                        + "'" + formaPagamento.getDescricao() + "')");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFormaPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> ListarTodasFormasPagamento() {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT cdformapagamento, descricao FROM public.formapagamento ORDER BY cdformapagamento");
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
    public ArrayList<ArrayList> PreencherComboFormaPagamento(char status) {

        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList formaPagamentos = new ArrayList();
            formaPagamentos.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT cdformapagamento, descricao FROM public.formapagamento ORDER BY cdformapagamento");
                } else {
                    rs = st.executeQuery("SELECT cdformapagamento, descricao FROM public.formapagamento ORDER BY cdformapagamento");
                }
                while (rs.next()) {

                    formaPagamentos.add(rs.getInt("cdformapagamento") + "- " + rs.getString("descricao") + "");
                };
                //con.close();
            }

            return formaPagamentos;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

}
