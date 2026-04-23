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
import com.controlefacilWeb.demo.models.LojaModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioLoja implements RepositorioLojaInterface {

    public RepositorioLoja() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarLoja(LojaModel loja, ArrayList<ArrayList> produtos, Connection conn) {
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            st.execute("INSERT INTO public.loja "
                    + "(nome, isativo) values("
                    + "'" + loja.getNome() + "', 'S')", Statement.RETURN_GENERATED_KEYS);

            if (id == 0) {
                ResultSet rs = st.executeQuery(" SELECT MAX(codigoloja) as codigoloja FROM public.loja");
                if (rs.next()) {
                    id = rs.getInt("codigoloja");
                }
            }
            if (!produtos.get(0).isEmpty()) {

                for (int i = 0; i < produtos.size(); i++) {
                    int codigoproduto = Integer.parseInt(produtos.get(i).get(0).toString());
                    st.execute("INSERT INTO public.produtoloja "
                            + "( codigoproduto, codigoloja,quantidade) values("
                            + "" + codigoproduto + ","
                            + "" + id + ", "
                            + " 0 )"
                    );
                }
            }

            return id;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverLoja(int codigoLoja, Connection conn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> ListarTodasLojas(Connection conn) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT l.codigoloja,l.nome "
                    + "FROM public.loja l "
                    + "ORDER BY l.codigoloja");
            rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            do {
                linhas.add(Util.proximaLinha(rs, rsmd));
            } while (rs.next());

            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<ArrayList> PreencherComboLoja(char status, Connection conn) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList lojas = new ArrayList();
            lojas.add(" - ");
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs;
            if (status == 'S') {
                rs = st.executeQuery("SELECT l.codigoloja,l.nome  "
                        + "FROM public.loja l  "
                        + "ORDER BY l.codigoloja");
            } else {
                rs = st.executeQuery("SELECT l.codigoloja,l.nome  "
                        + "FROM public.loja l  "
                        + "ORDER BY l.codigoloja");
            }
            while (rs.next()) {

                lojas.add(rs.getInt("codigoloja") + "- " + rs.getString("nome") + "");
            };

            return lojas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public LojaModel BuscarLoja(int codigoLoja, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * FROM public.loja "
                        + "WHERE codigoloja = '" + codigoLoja + "'");
                if (rs.next()) {
                    LojaModel loja = new LojaModel(rs.getInt("codigoloja"), rs.getString("nome"));
                    loja.setCodigoLoja(rs.getInt("codigoloja"));

                    return loja;
                }
            }
        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
        return null;
    }
}
