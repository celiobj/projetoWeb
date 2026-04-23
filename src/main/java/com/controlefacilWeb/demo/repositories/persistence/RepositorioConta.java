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
import javax.swing.JTable;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioConta implements RepositorioContaInterface {

    public RepositorioConta() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarConta(ContaModel conta) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.conta"
                        + "(numero, nome,tipo,numeroinstituicao,saldo,datacriacao, isativo) VALUES ("
                        + "'" + conta.getNumero() + "',"
                        + "'" + conta.getNome() + "',"
                        + "'" + conta.getTipo() + "',"
                        + "'" + conta.getInstituicao().getNumero() + "',"
                        + "'" + conta.getSaldo() + "',"
                        + "" + Util.FormatarDataInsert(conta.getDataCriacao(), 1) + ","
                        + "'S');");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioConta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int AlterarConta(ContaModel conta, int codigoConta) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.conta SET "
                        + "nome='" + conta.getNome() + "', "
                        + "tipo=" + conta.getTipo() + ", "
                        + "saldo=" + conta.getSaldo() + ", "
                        + "isativo=" + conta.isAtivo() + ", "
                        + "numero=" + conta.getNumero() + ", "
                        + "numeroinstituicao=" + conta.getInstituicao().getNumero() + " "
                        + "WHERE cdconta = " + codigoConta + "");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioConta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ContaModel ProcurarContaPorInstituicao(int codigoInstituicao) {
        try {
            ContaModel conta;
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * from public.conta "
                        + "WHERE isativo = 'S' LIMIT 1 ");
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    conta = new ContaModel(
                            rs.getInt("cdconta"),
                            rs.getInt("numero"),
                            "",
                            0,
                            null,
                            0,
                            null,
                            true);
                } while (rs.next());
            }
            return conta;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int RemoverConta(int codigoConta) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("DELETE from public.conta WHERE cdconta = " + codigoConta + "");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioConta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList<ArrayList> ListarTodasContas(char status) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery(
                            "SELECT numero,nome, tipo, numeroinstituicao, isativo FROM public.conta WHERE isativo = 'S' ORDER BY cdconta");
                } else {
                    rs = st.executeQuery(
                            "SELECT numero,nome, tipo,numeroinstituicao, isativo FROM public.conta ORDER BY cdconta");
                }
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
    public ArrayList<ArrayList> ExibirResumo() {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<ArrayList>();
            AccessDatabase a = new AccessDatabase();
            JTable tabela;
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery(
                        "SELECT numero,nome, tipo, numeroinstituicao, saldo FROM public.conta WHERE isativo = 'S' ORDER BY cdconta");
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
    public ArrayList<ArrayList> PreencherComboContas(char status) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList contas = new ArrayList();
            contas.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery(
                            "SELECT numero,nome, tipo, isativo FROM public.conta WHERE isativo = 'S' ORDER BY cdconta");
                } else {
                    rs = st.executeQuery("SELECT numero,nome, tipo, isativo FROM public.conta ORDER BY cdconta");
                }
                while (rs.next()) {

                    contas.add(rs.getInt("numero") + "- " + rs.getString("nome") + "");
                }
                ;
                // con.close();
            }

            return contas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public double VerificarSaldo(int numeroConta) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = a.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT saldo FROM public.conta "
                    + "WHERE numero = '" + numeroConta + "'");
            rs.next();
            double saldo = rs.getDouble("saldo");
            // con.close();
            return saldo;

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioConta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int retornarPrimeiraConta() {
        try {
            ContaModel conta;
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * from public.conta "
                        + "WHERE isativo = 'S' LIMIT 1 ");
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    conta = new ContaModel(
                            rs.getInt("cdconta"),
                            rs.getInt("numero"),
                            "",
                            0,
                            null,
                            0,
                            null,
                            true);
                } while (rs.next());
            }
            return conta.getNumero();

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;

        }

    }

}
