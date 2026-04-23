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
import com.controlefacilWeb.demo.models.CartaoModel;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.FaturaModel;
import com.controlefacilWeb.demo.util.Util;


/**
 *
 * @author celio.junior
 */
public class RepositorioFatura implements RepositorioFaturaInterface {

    public RepositorioFatura() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int AbrirFatura(FaturaModel fatura) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int AlterarFatura(FaturaModel fatura, int codigoFatura) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void FecharFatura(int codigoFatura) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.fatura SET "
                        + "isfechada = 'S' "
                        + "WHERE codigofatura = " + codigoFatura + "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFatura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public FaturaModel BuscarFaturaAberta(CartaoModel cartao, String data) {
        try {
            String dataFormatada = Util.FormatarDataInsert(data,1);
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT codigofatura,mesfatura,valor,isfechada,ispaga,isvigente,datapagamento,codigocartao,datainicio,datafim "
                        + "FROM public.fatura WHERE "
                        + "codigocartao = " + cartao.getCodigoCartao() + " AND "
                        + "isfechada = 'N' AND "
                        + "" + dataFormatada + " BETWEEN datainicio AND datafim AND "
                        + "isvigente = 'S'");
                rs.next();
                FaturaModel fatura = new FaturaModel();
                fatura.setCodigoFatura(rs.getInt("codigofatura"));
                fatura.setMesFatura(rs.getString("mesfatura"));

                //con.close();
                return fatura;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int CriarFatura(FaturaModel fatura) {
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.executeUpdate("INSERT INTO public.fatura "
                        + "(mesfatura, valor,isfechada,ispaga,codigocartao, datainicio, datafim,datavencimento, isvigente) values("
                        + "'" + fatura.getMesFatura() + "',"
                        + "'" + fatura.getValorFatura() + "',"
                        + "'" + fatura.getIsFechada() + "',"
                        + "'" + fatura.getIsPaga() + "',"
                        + "'" + fatura.getCodigoCartao() + "',"
                        + "" + Util.FormatarDataInsert(fatura.getDataInicio(),1) + ","
                        + "" + Util.FormatarDataInsert(fatura.getDataFim(),1) + ","
                        + "" + Util.FormatarDataInsert(fatura.getDataVencimento(),1) + ","
                        + "'" + fatura.getIsVigente() + "')", Statement.RETURN_GENERATED_KEYS);
//                ResultSet rsID = st.getGeneratedKeys();
//                if (rsID.next()) {
//                    id = rsID.getInt("codigofatura");
//                }
                if (id == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(codigofatura) as codigofatura FROM public.fatura");
                    if (rs.next()) {
                        id = rs.getInt("codigofatura");
                    }
                }
            }
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFatura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int PagarFatura(FaturaModel fatura, ContaModel conta, int codigoCategoria, int codigoSubCategoria, boolean lancarDebito) {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public void AtualizarSaldo(int codigoFatura, char status) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.fatura SET "
                        + "valor= (SELECT SUM(valor) from public.transacao where isefetivada = '" + status + "' and codigofatura = " + codigoFatura + ") "
                        + "WHERE codigofatura = " + codigoFatura + "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFatura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ArrayList> Listartodasfaturas(int codigoCartao) {

        try {
            String filtro = "";
            if (codigoCartao != 0) {
                filtro = "AND f.codigocartao = " + codigoCartao + " ";
            }
            ArrayList<ArrayList> linhas = new ArrayList<ArrayList>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "f.codigofatura,"
                        + "f.valor, "
                        + "c.numerocartao, "
                        + "f.datainicio, "
                        + "f.datafim, "
                        + "f.datavencimento, "
                        + "f.isfechada, "
                        + "f.ispaga "
                        + "FROM public.fatura f, public.cartao c "
                        + "WHERE c.codigocartao = f.codigocartao "
                        + "AND isvigente = 'S' "
                        + "AND isfechada = 'N' "
                        + filtro
                        + "ORDER BY datainicio");
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
    public ArrayList<ArrayList> ListartodasTransacoesDafatura(int codigoFatura, char isefetivada) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT t.cdtransacao,t.valor, t.data, c.nomecategoria, sc.nomesubcategoria, t.obs "
                        + "FROM public.transacao t, public.categoria c, public.subcategoria sc "
                        + "WHERE c.cdcategoria = t.codcategoria "
                        + "and sc.cdsubcategoria = t.codsubcategoria "
                        + "and t.codigofatura = " + codigoFatura + " "
                        + "and isefetivada = '" + isefetivada + "' "
                        + "ORDER BY t.cdtransacao desc");
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
    public void FecharFaturasVencidas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public ArrayList<FaturaModel> ListartodasfaturasFechadasNaoPagas(int codigoCartao) {
        try {
            String filtro = "";
            if (codigoCartao != 0) {
                filtro = "AND codigocartao = " + codigoCartao + " ";
            }
            ArrayList<FaturaModel> faturas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "codigofatura,"
                        + "valor, "
                        + "codigocartao, "
                        + "datainicio, "
                        + "datafim, "
                        + "datavencimento "
                        + "FROM public.fatura "
                        + "WHERE ispaga = 'N' "
                        + "AND isfechada = 'S' "
                        + filtro
                        + "ORDER BY datainicio");
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                do {
                    FaturaModel fatura = new FaturaModel();
                    fatura.setCodigoFatura(rs.getInt("codigofatura"));
                    fatura.setValorFatura(rs.getDouble("valor"));
                    fatura.setCodigoCartao(rs.getInt("codigocartao"));
                    fatura.setDataVencimento(rs.getString("datavencimento"));
                    faturas.add(fatura);
                } while (rs.next());
            }
            return faturas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

}
