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
import com.controlefacilWeb.demo.models.SolicitacaoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioSolicitacoes implements RepositorioSolicitacoesInterface {

    public RepositorioSolicitacoes() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int InserirSolicitacao(SolicitacaoModel solicitacao, int codigoloja) {
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.executeUpdate("INSERT INTO public.solicitacao "
                        + "(codigousuarioadm,codigousuariosolic, codigotela,isativo,data,codigoloja,status) values("
                        + "" + solicitacao.getCodigoUsuarioAdm() + ", "
                        + "" + solicitacao.getCodigoUsuarioSolic() + ", "
                        + "" + solicitacao.getCodigoTela() + ", "
                        + "'" + solicitacao.getIsAtivo() + "', "
                        + "" + Util.FormatarDataInsert(solicitacao.getData(),1) + ", "
                        + "" + solicitacao.getCodigoLoja() + ", "
                        // + "'" + solicitacao.getStatus() + "')", Statement.RETURN_GENERATED_KEYS);
                        + "'" + solicitacao.getStatus() + "')");
//                ResultSet rsID = st.getGeneratedKeys();
//                if (rsID.next()) {
//                    id = rsID.getInt("codigosolicitacao");
//                }
                if (id == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(codigosolicitacao) as codigosolicitacao FROM public.solicitacao");
                    if (rs.next()) {
                        id = rs.getInt("codigosolicitacao");
                    }
                }
                //con.close();
            }

            return id;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverSolicitacao(int codigoSolicitacao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ArrayList> Procurar(int codigoUsuario, String status, int codigoloja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT s.codigosolicitacao, u.login, s.data, n.descricao, s.isativo "
                        + "FROM public.solicitacao s, public.usuario u, public.notificacoes n "
                        + "WHERE s.codigousuarioadm = u.cdusuario "
                        + "AND n.codigosolicitacao = s.codigosolicitacao "
                        + "AND s.isativo = '" + status + "' "
                        + "AND s.codigousuariosolic = " + codigoUsuario + " "
                        + "ORDER BY s.codigosolicitacao"
                );
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

}
