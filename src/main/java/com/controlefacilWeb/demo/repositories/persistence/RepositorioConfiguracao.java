/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.ConfiguracaoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioConfiguracao implements RepositorioConfiguracaoInterface {

    public RepositorioConfiguracao() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int AlterarConfiguracao(int codigoConfiguracao, ConfiguracaoModel configuracao, Connection conn) {

        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE public.configuracao "
                    + "SET isintegnegfin='" + configuracao.getIsIntegradoNegocioFinanceiro() + "', "
                    + "isfecharaut='" + configuracao.getIsFecharAut() + "', "
                    + "verificaPermissao='" + configuracao.getVerificaPermissao() + "', "
                    + "ispagaraut='" + configuracao.getIsPagarAut() + "' ");
            // con.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public ConfiguracaoModel BuscarConfiguracao(int tipo, Connection conn) {

        try {

            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT * "
                    + "FROM public.configuracao WHERE "
                    + "tipo = " + tipo + "");
            rs.next();
            ConfiguracaoModel configuracaoModel = new ConfiguracaoModel(
                    rs.getInt("codigoconfiguracao"),
                    rs.getInt("tipo"),
                    rs.getString("isintegnegfin").charAt(0),
                    rs.getString("isfecharaut").charAt(0),
                    rs.getString("ispagaraut").charAt(0),
                    rs.getString("verificaPermissao").charAt(0));

            // con.close();
            // System.out.println(tela.toString());
            return configuracaoModel;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @Override
    public int InserirConfiguracao(ConfiguracaoModel configuracao, Connection conn) {

        int codigoConfiguracao = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO public.configuracao "
                    + "(codigoconfiguracao, tipo, isintegnegfin,isfecharaut,ispagaraut, verificaPermissao) values("
                    + "" + configuracao.getCodigoConf() + ", "
                    + "" + configuracao.getTipo() + ", "
                    + "'" + configuracao.getIsIntegradoNegocioFinanceiro() + "', "
                    + "'" + configuracao.getIsFecharAut() + "', "
                     + "'" + configuracao.getIsFecharAut() + "', "
                    + "'" + configuracao.getVerificaPermissao() + "')", Statement.RETURN_GENERATED_KEYS);
            // ResultSet rsID = st.getGeneratedKeys();
            // if (rsID.next()) {
            // codigoConfiguracao = rsID.getInt("codigoconfiguracao");
            // }
            if (codigoConfiguracao == 0) {
                ResultSet rs = st
                        .executeQuery(" SELECT MAX(codigoconfiguracao) as codigoconfiguracao FROM public.configuracao");
                if (rs.next()) {
                    codigoConfiguracao = rs.getInt("codigoconfiguracao");
                }
            }
            // con.close();
            return codigoConfiguracao;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTela.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }

    }

    @Override
    public int CriarBanco(String script, Connection conn) {
        ConfiguracaoModel configuracao = null;
        try {
            configuracao = new ConfiguracaoModel(
                    Util.lerArquivoTexto(script));
        } catch (IOException ex) {
            Logger.getLogger(RepositorioConfiguracao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            AccessDatabase a = new AccessDatabase();
            Connection con = conn != null ? !conn.isClosed() ? conn : conn : a.conectar();

            Statement st = con.createStatement();
            st.execute(configuracao.getScriptCriarBanco());
            return 1;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;

        }

    }

}
