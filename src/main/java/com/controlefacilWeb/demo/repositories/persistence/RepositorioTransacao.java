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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.RegistroModel;
import com.controlefacilWeb.demo.models.TipoTransacaoModel;
import com.controlefacilWeb.demo.models.TransacaoModel;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioTransacao implements RepositorioTransacaoInterface {

    public RepositorioTransacao() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int RegistrarReceita(TransacaoModel transacao, ContaModel conta, int codigoLoja) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (transacao.getIsEfetivada() == 'S') {
                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo + " + transacao.getValor() + " "
                            + "WHERE numero = " + conta.getNumero() + "");
                }

                st.execute("INSERT INTO public.transacao"
                        + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria,codigofatura, codigoloja, tipo)VALUES ("
                        + "'" + transacao.getUsuario() + "',"
                        + "" + transacao.getValor() + ","
                        + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                        + "'" + transacao.getIsEfetivada() + "',"
                        + "" + transacao.getContaOrigem().getNumero() + ","
                        + "'" + transacao.getObs() + "',"
                        + "" + transacao.getCodigoCategoria() + ","
                        + "" + transacao.getCodigoSubCategoria() + ","
                        + "" + transacao.getCodigofatura() + ","
                        + "" + transacao.getCodigoLoja() + ","
                        + "'" + transacao.getTipoTransacao() + "')");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int Cancelar(TransacaoModel transacao, ContaModel conta, int codigoLoja) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        transacao.setDataHora(data);
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (transacao.getTipoTransacao().equalsIgnoreCase("Receita")) {
                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo - " + transacao.getValor() + " "
                            + "WHERE numero = " + conta.getNumero() + "");
                } else if (transacao.getTipoTransacao().equalsIgnoreCase("Despesa")) {
                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo + " + transacao.getValor() + " "
                            + "WHERE numero = " + conta.getNumero() + "");
                }
                st.execute("UPDATE public.transacao "
                        + "SET isefetivada='C', data=" + Util.FormatarDataInsert(transacao.getDataHora(),1) + " "
                        + "WHERE cdtransacao = " + transacao.getCodigo() + "");

            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int RegistrarTransferecia(ContaModel contaOrigem, ContaModel contaDestino, TransacaoModel transacao, int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (transacao.getIsEfetivada() == 'S') {
                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo - " + transacao.getValor() + " "
                            + "WHERE numero = " + contaOrigem.getNumero() + "");

                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo + " + transacao.getValor() + " "
                            + "WHERE numero = " + contaDestino.getNumero() + "");
                }

                st.execute("INSERT INTO public.transacao"
                        + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria, codigoloja, tipo)VALUES ("
                        + "'" + transacao.getUsuario() + "',"
                        + "" + transacao.getValor() + ","
                        + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                        + "'" + transacao.getIsEfetivada() + "',"
                        + "" + contaOrigem.getNumero() + ","
                        + "'Enviada para a conta " + contaDestino.getNumero() + " - " + transacao.getObs() + "',"
                        + "" + transacao.getCodigoCategoria() + ","
                        + "" + transacao.getCodigoSubCategoria() + ","
                        + "" + transacao.getCodigoLoja() + ","
                        + "'" + transacao.getTipoTransacao() + "')");

                st.execute("INSERT INTO public.transacao"
                        + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria, codigoloja, tipo)VALUES ("
                        + "'" + transacao.getUsuario() + "',"
                        + "" + transacao.getValor() + ","
                        + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                        + "'" + transacao.getIsEfetivada() + "',"
                        + "" + contaDestino.getNumero() + ","
                        + "'Recebida da conta: " + contaOrigem.getNumero() + " - " + transacao.getObs() + "',"
                        + "" + transacao.getCodigoCategoria() + ","
                        + "" + transacao.getCodigoSubCategoria() + ","
                        + "" + transacao.getCodigoLoja() + ","
                        + "'" + transacao.getTipoTransacao() + "')");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<ArrayList> ListarTodasTransacoes(ContaModel conta, int tipo, int qtdDias, char status, int codigoLoja) {

        try {
            ArrayList linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (tipo == 1) {
                    rs = st.executeQuery("SELECT cdtransacao, usuario,valor,data,numeroconta,tipo,isefetivada ,obs "
                            + "FROM public.transacao "
                            // + "WHERE codigoloja = " + codigoLoja + " "
                            + "WHERE numeroconta = " + conta.getNumero() + " "
                            + "AND tipo <> 'Cartao de Crédito' "
                            + "AND isefetivada = '" + status + "' ORDER BY data desc,cdtransacao desc");
                } else {
                    rs = st.executeQuery("SELECT cdtransacao, usuario,valor,data,numeroconta,tipo,isefetivada ,obs "
                            + "FROM public.transacao "
                            // + "WHERE codigoloja = " + codigoLoja + " "
                            + "WHERE numeroconta = " + conta.getNumero() + " "
                            + "AND tipo <> 'Cartao de Crédito' "
                            + "AND isefetivada = '" + status + "' ORDER BY data,cdtransacao desc");
                }

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

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<ArrayList> PreencherComboTipoTransacao(char status) {

        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList contas = new ArrayList<ArrayList>();
            contas.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT tipotransacao FROM public.tipotransacao WHERE isativo = 'S' ORDER BY cdtipotransacao");
                } else {
                    rs = st.executeQuery("SELECT tipotransacao FROM public.tipotransacao ORDER BY cdtipotransacao");
                }
                while (rs.next()) {

                    contas.add(rs.getString("tipotransacao") + "");
                };
            }

            return contas;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public int CadastrarTipoTransacao(TipoTransacaoModel tipo) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.tipotransacao"
                        + "(tipotransacao, isativo)VALUES ("
                        + "'" + tipo.getTipoTransacao() + "',"
                        + "'S')");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> ListarTodosTiposTransacoes(char status) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<ArrayList>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT tipotransacao,isativo FROM public.tipotransacao WHERE isativo = 'S' ORDER BY cdtipotransacao");
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
    public int RegistrarDespesa(TransacaoModel transacao, ContaModel conta, int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (transacao.getIsEfetivada() == 'S') {
                    st.execute("UPDATE public.conta "
                            + "SET saldo= saldo - " + transacao.getValor() + " "
                            + "WHERE numero = " + conta.getNumero() + "");
                }

                st.execute("INSERT INTO public.transacao"
                        + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria,codigofatura, codigoloja, tipo)VALUES ("
                        + "'" + transacao.getUsuario() + "',"
                        + "" + transacao.getValor() + ","
                        + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                        + "'" + transacao.getIsEfetivada() + "',"
                        + "" + transacao.getContaOrigem().getNumero() + ","
                        + "'" + transacao.getObs() + "',"
                        + "" + transacao.getCodigoCategoria() + ","
                        + "" + transacao.getCodigoSubCategoria() + ","
                        + "" + transacao.getCodigofatura() + ","
                        + "" + transacao.getCodigoLoja() + ","
                        + "'" + transacao.getTipoTransacao() + "')");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int EfetivarTransacaoReceita(TransacaoModel transacao, ContaModel conta, int codigoLoja) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        transacao.setDataHora(data);

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();

                st.execute("UPDATE public.conta "
                        + "SET saldo= saldo + " + transacao.getValor() + " "
                        + "WHERE numero = " + conta.getNumero() + "");

                st.execute("UPDATE public.transacao "
                        + "SET isefetivada='S', data='" + transacao.getDataHora() + "' "
                        + "WHERE cdtransacao = " + transacao.getCodigo() + "");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int EfetivarTransacaoDespesa(TransacaoModel transacao, ContaModel conta, int codigoLoja) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        transacao.setDataHora(data);

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();

                st.execute("UPDATE public.conta "
                        + "SET saldo= saldo - " + transacao.getValor() + " "
                        + "WHERE numero = " + conta.getNumero() + "");

                st.execute("UPDATE public.transacao "
                        + "SET isefetivada='S', data=" + Util.FormatarDataInsert(transacao.getDataHora(),1) + " "
                        + "WHERE cdtransacao = " + transacao.getCodigo() + "");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int CriarRegistro(RegistroModel registro, ContaModel conta, ContaModel contaOrigem, ContaModel contaDestino, EnumTipoMovimentacao tipoTransacao) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        registro.setData(data);
        registro.setStatus('A');
        int idRegistro = 0;
        int idTransacao = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("insert into public.registro "
                        + "(data,valor, status) values("
                        + "" + Util.FormatarDataInsert(registro.getData(),1) + ", "
                        + "" + registro.getValor() + ", "
                        + "'" + registro.getStatus() + "')", Statement.RETURN_GENERATED_KEYS);
//                rsID = st.getGeneratedKeys();
//                if (rsID.next()) {
//                    idRegistro = rsID.getInt("codigoregistro");
//                }

                if (idRegistro == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(codigoregistro) as codigoregistro FROM public.registro");
                    if (rs.next()) {
                        idRegistro = rs.getInt("codigoregistro");
                    }
                }
                for (int i = 0; i < registro.getTransacoes().size(); i++) {
                    TransacaoModel transacao = registro.getTransacoes().get(i);
                    switch (tipoTransacao) {
                        case ENTRADA -> {
                            if (transacao.getIsEfetivada() == 'S') {
                                st.execute("UPDATE public.conta "
                                        + "SET saldo= saldo + " + transacao.getValor() + " "
                                        + "WHERE numero = " + conta.getNumero() + "");
                            }
                            st.execute("INSERT INTO public.transacao"
                                    + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria,codigofatura, codigoloja, tipo)VALUES ("
                                    + "'" + transacao.getUsuario() + "',"
                                    + "" + transacao.getValor() + ","
                                    + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                                    + "'" + transacao.getIsEfetivada() + "',"
                                    + "" + transacao.getContaOrigem().getNumero() + ","
                                    + "'" + transacao.getObs() + "',"
                                    + "" + transacao.getCodigoCategoria() + ","
                                    + "" + transacao.getCodigoSubCategoria() + ","
                                    + "" + transacao.getCodigofatura() + ","
                                    + "" + transacao.getCodigoLoja() + ","
                                    + "'" + transacao.getTipoTransacao() + "')", Statement.RETURN_GENERATED_KEYS);
//                            rsID = st.getGeneratedKeys();
//                            if (rsID.next()) {
//                                idTransacao = rsID.getInt("cdtransacao");
//                            }
                            if (idTransacao == 0) {
                                ResultSet rs = st.executeQuery(" SELECT MAX(cdtransacao) as cdtransacao FROM public.transacao");
                                if (rs.next()) {
                                    idTransacao = rs.getInt("cdtransacao");
                                }
                            }
                            st.execute("insert into public.registrotransacao "
                                    + "(codigoregistro, codigotransacao) values("
                                    + "" + idRegistro + ","
                                    + "" + idTransacao + ")");

                        }

                        case SAIDA -> {
                            if (transacao.getIsEfetivada() == 'S') {
                                st.execute("UPDATE public.conta "
                                        + "SET saldo= saldo - " + transacao.getValor() + " "
                                        + "WHERE numero = " + conta.getNumero() + "");
                            }
                            st.execute("INSERT INTO public.transacao"
                                    + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria,codigofatura, codigoloja, tipo)VALUES ("
                                    + "'" + transacao.getUsuario() + "',"
                                    + "" + transacao.getValor() + ","
                                    + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                                    + "'" + transacao.getIsEfetivada() + "',"
                                    + "" + transacao.getContaOrigem().getNumero() + ","
                                    + "'" + transacao.getObs() + "',"
                                    + "" + transacao.getCodigoCategoria() + ","
                                    + "" + transacao.getCodigoSubCategoria() + ","
                                    + "" + transacao.getCodigofatura() + ","
                                    + "" + transacao.getCodigoLoja() + ","
                                    + "'" + transacao.getTipoTransacao() + "')", Statement.RETURN_GENERATED_KEYS);
//                            rsID = st.getGeneratedKeys();
//                            if (rsID.next()) {
//                                idTransacao = rsID.getInt("cdtransacao");
//                            }
                            if (idTransacao == 0) {
                                ResultSet rs = st.executeQuery(" SELECT MAX(cdtransacao) as cdtransacao FROM public.transacao");
                                if (rs.next()) {
                                    idTransacao = rs.getInt("cdtransacao");
                                }
                            }
                            st.execute("insert into public.registrotransacao "
                                    + "(codigoregistro, codigotransacao) values("
                                    + "" + idRegistro + ","
                                    + "" + idTransacao + ")");
                        }
                        case TRANSFERENCIA -> {
                            if (transacao.getIsEfetivada() == 'S') {
                                st.execute("UPDATE public.conta "
                                        + "SET saldo= saldo - " + transacao.getValor() + " "
                                        + "WHERE numero = " + contaOrigem.getNumero() + "");

                                st.execute("UPDATE public.conta "
                                        + "SET saldo= saldo + " + transacao.getValor() + " "
                                        + "WHERE numero = " + contaDestino.getNumero() + "");
                            }
                            st.execute("INSERT INTO public.transacao"
                                    + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria, codigoloja, tipo)VALUES ("
                                    + "'" + transacao.getUsuario() + "',"
                                    + "" + transacao.getValor() + ","
                                    + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                                    + "'" + transacao.getIsEfetivada() + "',"
                                    + "" + contaOrigem.getNumero() + ","
                                    + "'Enviada para a conta " + contaDestino.getNumero() + " - " + transacao.getObs() + "',"
                                    + "" + transacao.getCodigoCategoria() + ","
                                    + "" + transacao.getCodigoSubCategoria() + ","
                                    + "" + transacao.getCodigoLoja() + ","
                                    + "'" + transacao.getTipoTransacao() + "')");

                            st.execute("INSERT INTO public.transacao"
                                    + "(usuario, valor, data,isefetivada, numeroconta, obs,codcategoria,codsubcategoria, codigoloja, tipo)VALUES ("
                                    + "'" + transacao.getUsuario() + "',"
                                    + "" + transacao.getValor() + ","
                                    + "" + Util.FormatarDataInsert(transacao.getDataHora(),1) + ","
                                    + "'" + transacao.getIsEfetivada() + "',"
                                    + "" + contaDestino.getNumero() + ","
                                    + "'Recebida da conta: " + contaOrigem.getNumero() + " - " + transacao.getObs() + "',"
                                    + "" + transacao.getCodigoCategoria() + ","
                                    + "" + transacao.getCodigoSubCategoria() + ","
                                    + "" + transacao.getCodigoLoja() + ","
                                    + "'" + transacao.getTipoTransacao() + "')");
                        }
                        default -> {
                        }
                    }

                }
            }

            return idRegistro;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }

    @Override
    public int CancelarRegistro(int codigoTransacao, int codigoCartao, int codigoFatura, int cancelarTudo, ArrayList<Integer> codigosFatura, String usuario) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        ResultSet rs;
        int codigoRegistro = 0;
        double valor = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();

                rs = st.executeQuery("SELECT codigoregistro FROM public.registrotransacao "
                        + "WHERE codigotransacao = '" + codigoTransacao + "'");
                if (rs.next()) {
                    codigoRegistro = rs.getInt("codigoregistro");
                }
                rs = st.executeQuery("SELECT valor FROM public.registro "
                        + "WHERE codigoregistro = '" + codigoRegistro + "'");
                if (rs.next()) {
                    valor = rs.getDouble("valor");
                }
                st.execute("UPDATE public.registro "
                        + "SET status='C' "
                        + "WHERE codigoregistro = " + codigoRegistro + "");

                st.execute("UPDATE public.transacao set isefetivada = 'C', obs = CONCAT (obs, ' - Cancelado por " + usuario + " em: " + data + "') "
                        + "WHERE cdtransacao in( "
                        + "SELECT t.cdtransacao "
                        + "FROM public.transacao t, public.registrotransacao rt "
                        + "WHERE t.cdtransacao = rt.codigotransacao "
                        + "AND rt.codigoregistro = " + codigoRegistro + ")");

                st.execute("UPDATE public.cartao"
                        + " SET limitedisponivel = limitedisponivel + " + valor + " "
                        + "WHERE codigocartao = " + codigoCartao + "");

                for (int i = 0; i < codigosFatura.size(); i++) {
                    st.execute("UPDATE public.fatura SET "
                            + "valor= (SELECT SUM(valor) from public.transacao where isefetivada = 'N' and codigofatura = " + codigosFatura.get(i) + ") "
                            + "WHERE codigofatura = " + codigosFatura.get(i) + "");
                }

            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioTransacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

}
