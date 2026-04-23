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
import com.controlefacilWeb.demo.models.InstituicaoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioInstituicao implements RepositorioInstituicaoInterface {

    public RepositorioInstituicao() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarInstituicao(InstituicaoModel instituicao) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("insert into public.instituicao(numero, nome,tipo, isativo) values("
                        + "'" + instituicao.getNumero() + "',"
                        + "'" + instituicao.getNome() + "','" + instituicao.getTipo() + "','S')");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioInstituicao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> ListarTodasInstituicoes(char status) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<ArrayList>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT cdinstituicao,numero,nome, tipo, isativo FROM public.instituicao WHERE isativo = 'S' ORDER BY cdinstituicao");
                } else {
                    rs = st.executeQuery("SELECT cdinstituicao,numero,nome, tipo, isativo FROM public.instituicao ORDER BY cdinstituicao");
                }
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
    public int AlterarInstituicao(InstituicaoModel instituicao, int codigoInstituicao) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.instituicao SET "
                        + "numero='" + instituicao.getNumero() + "', "
                        + "tipo=" + instituicao.getTipo() + " "
                        + "WHERE cdinstituicao = " + codigoInstituicao + "");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioInstituicao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverInstituicao(int codigoInstituicao) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("DELETE from public.instituicao WHERE cdinstituicao = " + codigoInstituicao + "");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioInstituicao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList PreencherComboInstituicoes(char status) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList instituicoes = new ArrayList();
            instituicoes.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT cdinstituicao,numero,nome, tipo, isativo FROM public.instituicao WHERE isativo = 'S' ORDER BY cdinstituicao");
                } else {
                    rs = st.executeQuery("SELECT cdinstituicao,numero,nome, tipo, isativo FROM public.instituicao ORDER BY cdinstituicao");
                }
                while (rs.next()) {
                    instituicoes.add(rs.getInt("numero") + "- " + rs.getString("nome") + "");
                };
                //con.close();
            }

            return instituicoes;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

}
