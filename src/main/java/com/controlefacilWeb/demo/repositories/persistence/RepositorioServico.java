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
import com.controlefacilWeb.demo.models.ServicoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioServico implements RepositorioServicoInterface {

    public RepositorioServico() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarServico(ServicoModel servico) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.servico "
                        + "( cdservico,nome, descricao,valor,valorcomissao,isativo) values("
                        + "" + servico.getCodigoServico() + ","
                        + "'" + servico.getNome() + "',"
                        + "'" + servico.getDescricao() + "',"
                        + "'" + servico.getValor() + "',"
                        + "'" + servico.getValorComissao() + "',"
                        + "'S')");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> ListarTodosServicos() {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT cdservico,nome, descricao,valor,valorcomissao,isativo FROM public.servico ORDER BY cdservico");
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    linhas.add(Util.proximaLinha(rs, rsmd));
                } while (rs.next());
                // con.close();
            }

            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ServicoModel Procurar(int codigoServico) {
        try {

            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "cdservico,nome, descricao,valor,valorcomissao,isativo "
                        + "FROM public.servico "
                        + "WHERE cdservico = '" + codigoServico + "'");
                rs.next();
                ServicoModel servico = new ServicoModel(codigoServico, rs.getString("nome"), rs.getString("descricao"),
                        rs.getDouble("valor"), rs.getDouble("valorcomissao"), '0');

                // con.close();
                return servico;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ArrayList<ArrayList> PreencherComboServicos(char status) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList servicos = new ArrayList();
            servicos.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery(
                            "SELECT cdservico,nome, descricao,valor,isativo FROM public.servico ORDER BY cdservico");
                } else {
                    rs = st.executeQuery(
                            "SELECT cdservico,nome, descricao,valor,isativo FROM public.servico ORDER BY cdservico");
                }
                while (rs.next()) {

                    servicos.add(rs.getInt("cdservico") + "- " + rs.getString("nome") + "");
                }
                ;
                // con.close();
            }

            return servicos;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int AlterarServico(ServicoModel servico) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.servico "
                        + "SET descricao='" + servico.getDescricao() + "', "
                        + "nome='" + servico.getNome() + "', "
                        + "valor='" + servico.getValor() + "', "
                        + "valorcomissao='" + servico.getValorComissao() + "',  "
                        + "isativo='" + servico.getIsAtivo() + "' "
                        + "WHERE cdservico = " + servico.getCodigoServico() + "");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
