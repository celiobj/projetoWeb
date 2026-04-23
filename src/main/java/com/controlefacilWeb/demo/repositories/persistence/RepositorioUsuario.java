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
import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioUsuario implements RepositorioUsuarioInterface {

    public RepositorioUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarUsuario(UsuarioModel usuario, Connection conn) {
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.execute("insert into public.usuario"
                    + "(login, senha,forcarsenha,codigoloja,tipo) values("
                    + "'" + usuario.getUser() + "',"
                    + "'" + usuario.getPass() + "',"
                    + "'" + usuario.getForcarSenha() + "',"
                    + "" + usuario.getCodigoLoja() + ","
                    + "'" + usuario.getTipo() + "')", Statement.RETURN_GENERATED_KEYS);
            // ResultSet rsID = st.getGeneratedKeys();
            // if (rsID.next()) {
            // id = rsID.getInt("cdusuario");
            // }
            if (id == 0) {
                ResultSet rs = st.executeQuery(" SELECT MAX(cdusuario) as cdusuario FROM public.usuario");
                if (rs.next()) {
                    id = rs.getInt("cdusuario");
                }
            }
            // con.close();

            return id;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public UsuarioModel Logar(UsuarioModel usuario, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * FROM public.usuario "
                    + "WHERE login = '" + usuario.getUser() + "'");
            if (rs.next()) {
                UsuarioModel usu = new UsuarioModel(
                        rs.getInt("cdusuario"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getInt("tipo"),
                        rs.getString("forcarsenha").charAt(0),
                        rs.getInt("codigoloja"));
                if (usu.getForcarSenha() == 'S') {
                    return usu;
                } else {

                    if (usu.getPass().equals(usuario.getPass())) {
                        return usu;
                    } else {
                        return null;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList<ArrayList> ListarTodosUsuarios(int codigoLoja, Connection conn) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT cdusuario,login,tipo,forcarsenha "
                    + "FROM public.usuario "
                    // + "WHERE codigoloja = " + codigoLoja + " "
                    + "ORDER BY cdusuario");
            rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            do {
                linhas.add(Util.proximaLinha(rs, rsmd));
            } while (rs.next());
            // con.close();
            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int AlterarUsuario(UsuarioModel usuario, int codigoUsuario, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.execute("UPDATE public.usuario "
                    + "SET login='" + usuario.getUser() + "',"
                    + "forcarsenha='" + usuario.getForcarSenha() + "',"
                    + " tipo=" + usuario.getTipo() + " "
                    + "WHERE cdusuario = " + codigoUsuario + "");
            // con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int AlterarSenha(UsuarioModel usuario, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            st.execute("UPDATE public.usuario "
                    + "SET senha='" + usuario.getPass() + "', "
                    + "forcarsenha='N' "
                    + "WHERE cdusuario = " + usuario.getCodigo() + "");
            // con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverUsuario(int codigoUsuario, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            st.execute("DELETE from public.usuario WHERE cdusuario = " + codigoUsuario + "");
            // con.close();

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ArrayList<ArrayList> preencherComboTodosUsuarios(int codigoLoja, Connection conn) {

        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList usuarios = new ArrayList();
            usuarios.add(" - ");
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs;

            rs = st.executeQuery("SELECT cdusuario,login,tipo "
                    + "FROM public.usuario "
                    // + "WHERE codigoloja = " + codigoLoja + " "
                    + "ORDER BY cdusuario");
            while (rs.next()) {

                usuarios.add(rs.getInt("cdusuario") + "- " + rs.getString("login") + "");
            }
            ;

            return usuarios;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ArrayList<ArrayList> ListarTodosUsuariosPorTipo(int tipo, int codigoLoja, Connection conn) {

        String sql = "SELECT cdusuario,login,tipo FROM public.usuario ";
        // + "WHERE codigoloja = " + codigoLoja + " ";
        switch (tipo) {
            case 0 ->
                sql = sql + " ORDER BY cdusuario";
            case 1 ->
                sql = sql + "WHERE tipo = " + tipo + " ORDER BY cdusuario";

            default ->
                throw new AssertionError();
        }

        try {
            ArrayList usuarios = new ArrayList();
            usuarios.add(" - ");
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                usuarios.add(rs.getInt("cdusuario") + "- " + rs.getString("login") + "");
            }
            ;
            // con.close();

            return usuarios;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

}
