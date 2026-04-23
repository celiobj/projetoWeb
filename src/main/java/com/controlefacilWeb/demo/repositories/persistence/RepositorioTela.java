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
import com.controlefacilWeb.demo.models.TelaModel;
import com.controlefacilWeb.demo.models.UsuarioModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioTela implements RepositorioTelaInterface {

    public RepositorioTela() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarTela(TelaModel tela, Connection conn) {

        int codigoTela = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO public.tela "
                    + "(nometela) values("
                    + "'" + tela.getDescricaoTela() + "')", Statement.RETURN_GENERATED_KEYS);
//            ResultSet rsID = st.getGeneratedKeys();
//            if (rsID.next()) {
//                codigoTela = rsID.getInt("codigotela");
//            }
            if (codigoTela == 0) {
                ResultSet rs = st.executeQuery(" SELECT MAX(codigotela) as codigotela FROM public.tela");
                if (rs.next()) {
                    codigoTela = rs.getInt("codigotela");
                }
            }
            //con.close();
            return codigoTela;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }

    }

    @Override
    public int AlterarPermissaoTela(int codigoPermissao, int codigoUsuario, char isPermitido, Connection conn) {
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE public.permissaotela "
                    + "SET ispermitido='" + isPermitido + "' "
                    + "WHERE codigopermissao = " + codigoPermissao + " "
                    + "AND codigousuario = " + codigoUsuario + "");
            //con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> listarTodasTelas(Connection conn) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT t.codigotela,t.nometela "
                    + "FROM public.tela t "
                    + "ORDER BY t.codigotela");
            rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            do {
                linhas.add(Util.proximaLinha(rs, rsmd));
            } while (rs.next());

            //con.close();
            return linhas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public void CriarPermissao(int codigoTela, int codigoUsuario, Connection conn) {

        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.execute("INSERT INTO public.permissaotela "
                    + "(codigotela,codigousuario, ispermitido) values("
                    + "" + codigoTela + ", "
                    + "" + codigoUsuario + ", "
                    + "'N')");

            //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ArrayList> listarTodasPermissoesTelas(int codigoUsuario, Connection conn) {
        ArrayList<ArrayList> linhas = new ArrayList<>();
        try {

            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT p.codigopermissao, u.login, t.nometela, p.ispermitido "
                    + "FROM public.permissaotela p, public.usuario u, public.tela t "
                    + "WHERE p.codigotela = t.codigotela "
                    + "AND p.codigousuario = u.cdusuario "
                    + "AND p.codigousuario = " + codigoUsuario + " "
                    + "ORDER BY codigopermissao");
            rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            do {
                linhas.add(Util.proximaLinha(rs, rsmd));
            } while (rs.next());
            //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }

        return linhas;

    }

    @Override
    public boolean VerificarPermissao(TelaModel tela, UsuarioModel usuario, Connection conn) {

        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT ispermitido FROM public.permissaotela "
                    + "WHERE codigotela = " + tela.getCodigoTela() + " "
                    + "AND codigousuario = " + usuario.getCodigo() + " ");
            if (rs.next()) {
                String isPermitido = rs.getString("ispermitido");
                if (isPermitido.equalsIgnoreCase("S")) {
                    //con.close();
                    return true;
                } else {
                    //con.close();
                    return false;

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    @Override
    public TelaModel BuscarTela(String nomeTela, Connection conn) {

        try {
            TelaModel tela = null;
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * "
                    + "FROM public.tela WHERE "
                    + "nometela = '" + nomeTela + "'");
            if (rs.next()) {
                tela = new TelaModel(
                        rs.getInt("codigotela"),
                        rs.getString("nometela"));
            }
            //con.close();
            //System.out.println(tela.toString());
            return tela;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int BuscarPermissao(int codigoTela, int codigoUsuario, Connection conn) {

        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT codigopermissao FROM public.permissaotela "
                    + "WHERE codigotela = " + codigoTela + " "
                    + "AND codigousuario = " + codigoUsuario + " ");
            if (rs.next()) {
                return rs.getInt("codigopermissao");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
