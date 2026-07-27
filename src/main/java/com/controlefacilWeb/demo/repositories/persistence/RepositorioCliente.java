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
import com.controlefacilWeb.demo.models.ClienteModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioCliente implements RepositorioClienteInterface {

    public RepositorioCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarCliente(ClienteModel cliente) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.cliente "
                        + "(nome, documento, sexo, numerosus) values("
                        + "'" + cliente.getNome() + "', "
                        + "'" + cliente.getDocumento() + "', "
                        + "'" + cliente.getSexo() + "', "
                        + "'" + cliente.getNumerosus() + "')");
                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverCliente(int codigoCliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> ListarTodosClientes() {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            Connection con = a.conectar();
            if (con == null) return null;
            try (con) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT c.codigocliente, c.nome, c.documento, c.sexo, c.numerosus "
                        + "FROM public.cliente c "
                        + "ORDER BY c.codigocliente");
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    linhas.add(Util.proximaLinha(rs, rsmd));
                } while (rs.next());
            }

            return linhas;

        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<ArrayList> PreencherComboClientes(char status) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList clientes = new ArrayList();
            clientes.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT c.codigocliente, c.nome, c.documento "
                            + "FROM public.cliente c  "
                            + "ORDER BY c.codigocliente");
                } else {
                    rs = st.executeQuery("SELECT c.codigocliente, c.nome, c.documento "
                            + "FROM public.cliente c  "
                            + "ORDER BY c.codigocliente");
                }
                while (rs.next()) {

                    clientes.add(
                            rs.getInt("codigocliente") + "- " + rs.getString("nome") + "- " + rs.getString("documento") + "");
                }
                ;
                // con.close();
            }

            return clientes;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ClienteModel ProcurarCliente(int codigoCliente) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * FROM public.cliente "
                        + "WHERE codigocliente = " + codigoCliente + "");
                if (rs.next()) {
                    ClienteModel cliente = new ClienteModel(
                            codigoCliente,
                            rs.getString("nome"),
                            rs.getString("documento"),
                            rs.getString("sexo"),
                            rs.getString("numerosus"));
                    return cliente;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ClienteModel ProcurarClientePorNome(String nomeCliente) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * FROM public.cliente "
                        + "WHERE nome = '" + nomeCliente + "'");
                if (rs.next()) {
                    ClienteModel cliente = new ClienteModel(
                            rs.getInt("codigocliente"),
                            rs.getString("nome"),
                            rs.getString("documento"),
                            rs.getString("sexo"),
                            rs.getString("numerosus"));
                    return cliente;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int AlterarCliente(ClienteModel cliente) {
    
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.cliente "
                        + "SET nome = '" + cliente.getNome() + "', "
                        + "documento = '" + cliente.getDocumento() + "', "
                        + "sexo = '" + cliente.getSexo() + "', "
                        + "numerosus = '" + cliente.getNumerosus() + "' "
                        + "WHERE codigocliente = " + cliente.getCodigoCliente());
                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
