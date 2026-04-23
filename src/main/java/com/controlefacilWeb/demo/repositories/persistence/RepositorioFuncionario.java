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
import com.controlefacilWeb.demo.models.FuncionarioModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioFuncionario implements RepositorioFuncionarioInterface {

    public RepositorioFuncionario() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarFuncionario(FuncionarioModel funcionario, int codigoLoja) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.funcionario "
                        + "(nome,cdusuario, isativo) values("
                        + "'" + funcionario.getNome() + "',"
                        + "" + funcionario.getCodigoUsuario() + ","
                        + "'S')");
                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverFuncionario(int codigoFuncionario, int codigoLoja) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> ListarTodosFuncionarios(char status, int codigoLoja) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT f.cdfuncionario,f.nome, u.login, f.isativo "
                        + "FROM public.funcionario f, public.usuario u "
                        + "where f.cdusuario = u.cdusuario "
                        + "ORDER BY f.cdfuncionario");
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
    public ArrayList<ArrayList> PreencherComboFuncionarios(char status, int codigoLoja) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList funcionarios = new ArrayList();
            funcionarios.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT f.cdfuncionario,f.nome, u.login, f.isativo "
                            + "FROM public.funcionario f, public.usuario u "
                            + "where f.cdusuario = u.cdusuario "
                            + "and f.isativo = 'S' "
                            + "ORDER BY f.cdfuncionario");
                } else {
                    rs = st.executeQuery("SELECT f.cdfuncionario,f.nome, u.login, f.isativo "
                            + "FROM public.funcionario f, public.usuario u "
                            + "where f.cdusuario = u.cdusuario "
                            + "ORDER BY f.cdfuncionario");
                }
                while (rs.next()) {

                    funcionarios.add(rs.getInt("cdfuncionario") + "- " + rs.getString("nome") + "");
                };
                //con.close();
            }

            return funcionarios;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public FuncionarioModel ProcurarFuncionario(int codigoFuncionario) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT * FROM public.funcionario "
                        + "WHERE cdfuncionario = " + codigoFuncionario + "");
                if (rs.next()) {
                    FuncionarioModel fucncionario = new FuncionarioModel(
                            codigoFuncionario,
                            rs.getString("nome"));
                    return fucncionario;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public int AlterarFuncionario(FuncionarioModel funcionario, int codigoloja) {
       
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.funcionario "
                        + "SET nome = '" + funcionario.getNome() + "' "
                        + "WHERE cdfuncionario = " + funcionario.getCodigoFuncionario());
                //con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    

}
