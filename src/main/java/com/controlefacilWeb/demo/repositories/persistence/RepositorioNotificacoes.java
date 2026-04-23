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
import com.controlefacilWeb.demo.models.NotificacaoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioNotificacoes implements RepositorioNotificacoesInterface {

    public RepositorioNotificacoes() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int InserirNotificacao(NotificacaoModel notificacao, int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.notificacoes "
                        + "(codigosolicitacao,codigousuario, descricao,isativo,codigoloja,data) values("
                        + "" + notificacao.getCodigoSolicitacao() + ", "
                        + "" + notificacao.getCodigoUsuario() + ", "
                        + "'" + notificacao.getDescricao() + "', "
                        + "'" + notificacao.getIsAtivo() + "', "
                        + "" + notificacao.getCodigoLoja() + ", "
                        + "" + Util.FormatarDataInsert(notificacao.getData(),1) + ")");
                //con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int RemoverNotificacao(int codigoNotificacao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> Procurar(int codigoUsuario, String status, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT n.codigonotificacao,u.login, n.descricao, n.data "
                        + "FROM public.notificacoes n, public.usuario u, public.solicitacao s "
                        + "WHERE u.cdusuario = s.codigousuariosolic "
                        + "AND s.codigousuarioadm = n.codigousuario "
                        + "AND s.codigosolicitacao = n.codigosolicitacao "
                        + "AND n.isativo = '" + status + "' "
                        + "AND n.codigousuario = " + codigoUsuario + " "
                        + "ORDER BY n.codigonotificacao desc,n.data desc");
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
    public int MarcarNotificacaoLida(int codigoNotificacao) {

        Connection con;
        try {
            AccessDatabase a = new AccessDatabase();
            con = a.conectar();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE public.notificacoes "
                    + "SET isativo='N' "
                    + "WHERE codigonotificacao = " + codigoNotificacao + "");
            //con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioNotificacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
