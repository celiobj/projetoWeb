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
import com.controlefacilWeb.demo.models.FornecedorModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioFornecedor implements RepositorioFornecedorInterface {

    public RepositorioFornecedor() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarFornecedor(FornecedorModel fornecedor) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.fornecedor "
                        + "(nome, cnpj) values("
                        + "'" + fornecedor.getNome() + "', "
                        + "'" + fornecedor.getCnpj() + "')");
                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFornecedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int RemoverFornecedor(int codigoFornecedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> ListarTodosFornecedor() {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT f.codigofornecedor,f.nome,f.cnpj "
                        + "FROM public.fornecedor f "
                        + "ORDER BY f.codigofornecedor");
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

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<ArrayList> PreencherComboFornecedor(char status) {

        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList fornecedores = new ArrayList();
            fornecedores.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT f.codigofornecedor, f.nome,f.cnpj  "
                            + "FROM public.fornecedor f  "
                            + "ORDER BY f.codigofornecedor");
                } else {
                    rs = st.executeQuery("SELECT f.codigofornecedor, f.nome,f.cnpj  "
                            + "FROM public.fornecedor f  "
                            + "ORDER BY f.codigofornecedor");
                }
                while (rs.next()) {

                    fornecedores.add(rs.getInt("codigofornecedor") + "- " + rs.getString("nome")+ "- " + rs.getString("cnpj") + "");
                }
                ;
                // con.close();
            }

            return fornecedores;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int AlterarFornecedor(FornecedorModel fornecedor) {
    
        int codigoFornecedor = fornecedor.getCodigoFornecedor() != 0 ? fornecedor.getCodigoFornecedor() : fornecedor.getCodigoPessoa();
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.fornecedor "
                        + "SET nome = '" + fornecedor.getNome() + "', "
                        + "cnpj = '" + fornecedor.getCnpj() + "' "
                        + "WHERE codigofornecedor = " + codigoFornecedor);
                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFornecedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
