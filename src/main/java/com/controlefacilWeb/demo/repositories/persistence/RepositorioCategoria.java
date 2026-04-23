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
import com.controlefacilWeb.demo.models.CategoriaModel;
import com.controlefacilWeb.demo.models.SubCategoriaModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioCategoria implements RepositorioCategoriaInterface {

    public RepositorioCategoria() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarCategoria(CategoriaModel categoria) {
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.categoria"
                        + "(nomecategoria, isativo) VALUES ("
                        + "'" + categoria.getNomeCategoria() + "',"
                        + "'S');", Statement.RETURN_GENERATED_KEYS);
//                ResultSet rsID = st.getGeneratedKeys();
//                if (rsID.next()) {
//                    id = rsID.getInt("cdcategoria");
//                }
                if (id == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(cdcategoria) as cdcategoria FROM public.categoria");
                    if (rs.next()) {
                        id = rs.getInt("cdcategoria");
                    }
                }
                //con.close();
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int CadastrarSubCategoria(SubCategoriaModel subCategoria
    ) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.subcategoria"
                        + "(nomesubcategoria, isativo, cdcategoria) VALUES ("
                        + "'" + subCategoria.getNomeCategoria() + "',"
                        + "'S',"
                        + "" + subCategoria.getCodigoCategoriaPai() + ");");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList<ArrayList> listarTodasCategorias(char status
    ) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT nomecategoria, isativo FROM public.categoria WHERE isativo = 'S' ORDER BY cdcategoria");
                } else {
                    rs = st.executeQuery("SELECT nomecategoria , isativo FROM public.categoria ORDER BY cdcategoria");
                }
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    linhas.add(Util.proximaLinha(rs, rsmd));
                } while (rs.next());
            }

            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ArrayList<ArrayList> listarTodasSubCategorias(int codigoCategoriaPai, char status
    ) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    if (codigoCategoriaPai == 0) {
                        rs = st.executeQuery("SELECT nomesubcategoria, cdcategoria, isativo FROM public.subcategoria WHERE isativo = 'S' ORDER BY cdsubcategoria");
                    } else {
                        rs = st.executeQuery("SELECT nomesubcategoria, cdcategoria, isativo FROM public.subcategoria WHERE cdcategoria = " + codigoCategoriaPai + " and isativo = 'S' ORDER BY cdsubcategoria");
                    }
                } else {
                    if (codigoCategoriaPai == 0) {
                        rs = st.executeQuery("SELECT nomesubcategoria, cdcategoria, isativo FROM public.subcategoria ORDER BY cdsubcategoria");
                    } else {
                        rs = st.executeQuery("SELECT nomesubcategoria, cdcategoria, isativo FROM public.subcategoria WHERE cdcategoria = " + codigoCategoriaPai + " ORDER BY cdsubcategoria");

                    }
                }
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    linhas.add(Util.proximaLinha(rs, rsmd));
                } while (rs.next());

            }

            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ArrayList<ArrayList> PreencherComboCategoria(char status
    ) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList contas = new ArrayList<ArrayList>();
            contas.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT cdcategoria, nomecategoria, isativo FROM public.categoria WHERE isativo = 'S' ORDER BY cdcategoria");
                } else {
                    rs = st.executeQuery("SELECT cdcategoria, nomecategoria , isativo FROM public.categoria ORDER BY cdcategoria");
                }
                while (rs.next()) {

                    contas.add(rs.getInt("cdcategoria") + "- " + rs.getString("nomecategoria") + "");
                };
            }

            return contas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ArrayList PreencherComboSubCategoria(int codigoCategoriaPai, char status
    ) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList contas = new ArrayList();
            contas.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT nomesubcategoria, cdsubcategoria, isativo FROM public.subcategoria WHERE cdcategoria = " + codigoCategoriaPai + " and isativo = 'S' ORDER BY cdsubcategoria");
                } else {
                    rs = st.executeQuery("SELECT nomesubcategoria, cdsubcategoria, isativo FROM public.subcategoria WHERE cdcategoria = " + codigoCategoriaPai + " ORDER BY cdsubcategoria");
                }
                while (rs.next()) {

                    contas.add(rs.getInt("cdsubcategoria") + "- " + rs.getString("nomesubcategoria") + "");
                };
            }

            return contas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public String buscarCategoria(int codigoCategoria
    ) {
        try {
            String retorno;
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select nomecategoria from public.categoria WHERE cdcategoria = " + codigoCategoria + "");
                rs.next();
                retorno = rs.getString("nomecategoria");
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscarSubCategoria(int codigoSubCategoria
    ) {
        try {
            String retorno;
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select nomesubcategoria from public.subcategoria WHERE cdsubcategoria = " + codigoSubCategoria + "");
                rs.next();
                retorno = rs.getString("nomesubcategoria");
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
