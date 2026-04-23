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
import com.controlefacilWeb.demo.models.ProdutoModel;
import com.controlefacilWeb.demo.models.TransferenciaRealizadaModel;
import com.controlefacilWeb.demo.util.EnumTipoMovimentacao;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioProduto implements RepositorioProdutoInterface {

    public RepositorioProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarProduto(ProdutoModel produto, ArrayList<ArrayList> lojas) {
        // int id = 0;
        // int codigoProduto = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.produto "
                        + "( cdproduto,nome, descricao,quantidade,valorvenda,valorcomissao, valormediocompra,isativo) values("
                        + "" + produto.getCodigoProduto() + ","
                        + "'" + produto.getNome() + "',"
                        + "'" + produto.getDescricao() + "',"
                        + "" + produto.getQuantidade() + ","
                        + "" + produto.getValorvenda() + ","
                        + "'" + produto.getValorcomissao() + "',"
                        + "" + produto.getValormMedioCompra() + ","
                        + "'S')", Statement.RETURN_GENERATED_KEYS);
                // ResultSet rsID = st.getGeneratedKeys();
                // if (rsID.next()) {
                // id = rsID.getInt("cdproduto");
                // }
                // if (id == 0) {
                // ResultSet rs = st.executeQuery(" SELECT codigo FROM public.produto");
                // if (rs.next()) {
                // codigoProduto = rs.getInt("cdproduto");
                // }
                // }
                for (int i = 0; i < lojas.size(); i++) {
                    int codigoLoja = Integer.parseInt(lojas.get(i).get(0).toString());
                    st.execute("INSERT INTO public.produtoloja "
                            + "( codigoproduto, codigoloja,quantidade) values("
                            + "" + produto.getCodigoProduto() + ","
                            + "" + codigoLoja + ", "
                            + " 0 )");
                }
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int MovimentarProduto(int codgigoProduto, int quantidade, String nota, Enum tipo, String descricao) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (tipo == EnumTipoMovimentacao.ENTRADA) {
                    st.execute("UPDATE public.produto "
                            + "SET quantidade= quantidade +" + quantidade + " "
                            + "WHERE cdproduto = " + codgigoProduto + "");
                } else {
                    st.execute("UPDATE public.produto "
                            + "SET quantidade= quantidade -" + quantidade + " "
                            + "WHERE cdproduto = " + codgigoProduto + "");
                }

                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int AlterararProduto(ProdutoModel produto, int codigoProduto) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (codigoProduto == 0) {
                    codigoProduto = produto.getCodigoProduto();
                }
                if (produto.getValormMedioCompra() == 0) {
                    st.execute("UPDATE public.produto "
                            + "SET nome = '" + produto.getNome() + "', "
                            + "descricao = '" + produto.getDescricao() + "', "
                            + "valorvenda = " + produto.getValorvenda() + ", "
                            + "valorcomissao = '" + produto.getValorcomissao() + "', "
                            + "isativo = '" + produto.getIsativo() + "' "
                            + "WHERE cdproduto = " + codigoProduto);
                } else {
                    st.execute("UPDATE public.produto "
                            + "SET nome = '" + produto.getNome() + "', "
                            + "descricao = '" + produto.getDescricao() + "', "
                            + "quantidade = " + produto.getQuantidade() + ", "
                            + "valorvenda = " + produto.getValorvenda() + ", "
                            + "valorcomissao = '" + produto.getValorcomissao() + "', "
                            + "valormediocompra = " + produto.getValormMedioCompra() + ", "
                            + "isativo = '" + produto.getIsativo() + "' "
                            + "WHERE cdproduto = " + codigoProduto);
                    // con.close();
                }
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<ArrayList> ListarTodosProdutos() {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT cdproduto, nome, descricao, quantidade, valormediocompra , valorvenda, valorcomissao, isativo FROM public.produto ORDER BY cdproduto");
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
    public ArrayList<ArrayList> ListarTodasEntradasProduto(int codigoProduto) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT  p.codigoproduto, n.numeronota , p.data, p.valor, p.valorcheio, p.valordesconto "
                                + "FROM public.produtocomprado p, public.notadecompra n "
                                + "WHERE n.codigonota = p.codigonotacompra "
                                + "AND codigoproduto = " + codigoProduto + " "
                                + "ORDER BY codigoprodutocomprado desc");
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
    public ArrayList<ArrayList> ListarTodasSaidasProduto(int codigoProduto) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT codigoproduto, codigoordemservico, data, valor, valorcheio, valordesconto "
                        + "FROM public.produtovendido "
                        + "WHERE codigoproduto = " + codigoProduto + " "
                        + "ORDER BY codigoprodutovendido desc");

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
    public ArrayList<ArrayList> PreencherComboProdutos(char status) {

        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList produtos = new ArrayList();
            produtos.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (status == 'S') {
                    rs = st.executeQuery("SELECT cdproduto,nome FROM public.produto ORDER BY cdproduto");
                } else {
                    rs = st.executeQuery("SELECT cdproduto,nome FROM public.produto ORDER BY cdproduto");
                }
                while (rs.next()) {
                    produtos.add(rs.getInt("cdproduto") + "- " + rs.getString("nome") + "");
                }
                ;
                // con.close();
            }

            return produtos;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @Override
    public ProdutoModel Procurar(int codigoProduto) {

        try {

            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "cdproduto,nome,valorvenda,valorcomissao "
                        + "FROM public.produto "
                        + "WHERE cdproduto = '" + codigoProduto + "'");
                rs.next();
                ProdutoModel produtoModel = new ProdutoModel(
                        codigoProduto,
                        rs.getString("nome"),
                        "",
                        rs.getDouble("valorvenda"),
                        0,
                        rs.getDouble("valorcomissao"),
                        0,
                        'S');

                // con.close();
                return produtoModel;
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    @Override
    public int MovimentarProduto(int codgigoProduto, int quantidade, String nota, Enum tipo, String descricao,
            int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                if (tipo == EnumTipoMovimentacao.ENTRADA) {
                    st.execute("UPDATE public.produtoloja "
                            + "SET quantidade= quantidade +" + quantidade + " "
                            + "WHERE codigoproduto = " + codgigoProduto + " "
                            + "AND codigoloja = " + codigoLoja + " ");
                } else {
                    st.execute("UPDATE public.produtoloja "
                            + "SET quantidade= quantidade -" + quantidade + " "
                            + "WHERE codigoproduto = " + codgigoProduto + " "
                            + "AND codigoloja = " + codigoLoja + " ");
                }

                // con.close();
            }

            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public ArrayList<ArrayList> ListarTodosProdutos(int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT p.cdproduto, p.nome, p.descricao, pl.quantidade,l.nome, p.valormediocompra , p.valorvenda, p.valorcomissao, p.isativo "
                                + "FROM public.produto p, public.produtoloja pl, public.loja l "
                                + "WHERE p.cdproduto = pl.codigoproduto "
                                + "AND pl.codigoloja = l.codigoloja "
                                + "AND l.codigoloja = " + codigoLoja + " "
                                + "ORDER BY cdproduto");
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
    public ArrayList<ArrayList> ListarTodasSaidasProduto(int codigoProduto, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT codigoproduto, codigoordemservico,quantidade, data, valor, valorcheio, valordesconto "
                                + "FROM public.produtovendido "
                                + "WHERE codigoproduto = " + codigoProduto + " "
                                + "AND codigoloja = " + codigoLoja + " "
                                + "ORDER BY codigoprodutovendido desc");

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
    public ArrayList<ArrayList> ListarTodasEntradasProduto(int codigoProduto, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT  p.codigoproduto, n.numeronota, p.quantidade , p.data, p.valor, p.valorcheio, p.valordesconto "
                                + "FROM public.produtocomprado p, public.notadecompra n "
                                + "WHERE n.codigonota = p.codigonotacompra "
                                + "AND p.codigoproduto = " + codigoProduto + " "
                                + "AND n.codigoloja = " + codigoLoja + " "
                                + "ORDER BY codigoprodutocomprado desc");
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
    public ArrayList<ArrayList> PreencherComboProdutos(char status, int codigoLoja) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int PegarSaldo(int codigoProduto, int codigoLoja) {
        int qtd = 0;
        try {

            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT SUM(quantidade) as qtd "
                        + "FROM public.produtoloja "
                        + "WHERE codigoloja = " + codigoLoja + " "
                        + "AND codigoproduto = " + codigoProduto);
                while (rs.next()) {
                    qtd = rs.getInt("qtd");
                }
                ;
                // con.close();
            }
            return qtd;
        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;

        }

    }

    @Override
    public int RealizarTrasnferencia(int codigoLojaOrigem, int codigoLojaDestino,
            ArrayList<TransferenciaRealizadaModel> itensTrasnferencia) {

        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date()).substring(0, 10).trim();
        int id = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.transferencia "
                        + "( codigolojaorigem, codigolojadestino,data,isativo) values("
                        + "'" + codigoLojaOrigem + "',"
                        + "'" + codigoLojaDestino + "',"
                        + "" + Util.FormatarDataInsert(data, 1) + ","
                        + "'S')", Statement.RETURN_GENERATED_KEYS);
                // ResultSet rsID = st.getGeneratedKeys();
                // if (rsID.next()) {
                // id = rsID.getInt("cdproduto");
                // }
                if (id == 0) {
                    ResultSet rs = st.executeQuery(
                            " SELECT MAX(codigotransferencia) as codigotransferencia FROM public.transferencia");
                    if (rs.next()) {
                        id = rs.getInt("codigotransferencia");
                    }
                }
                for (int i = 0; i < itensTrasnferencia.size(); i++) {
                    int codigoProduto = itensTrasnferencia.get(i).getCodigoProduto();
                    int quantidade = itensTrasnferencia.get(i).getQuantidade();
                    st.execute("INSERT INTO public.transferenciarealizada "
                            + "( codigotransferencia, codigoproduto,quantidade) values("
                            + "" + id + ", "
                            + "" + codigoProduto + ", "
                            + "" + quantidade + " )");
                    MovimentarProduto(
                            codigoProduto,
                            quantidade,
                            data,
                            EnumTipoMovimentacao.SAIDA,
                            "Transferencia enviada: " + id,
                            codigoLojaOrigem);
                    MovimentarProduto(
                            codigoProduto,
                            quantidade,
                            data,
                            EnumTipoMovimentacao.ENTRADA,
                            "Transferencia recebida: " + id,
                            codigoLojaDestino);
                }
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public ArrayList<ArrayList> ListarTodasTransferensiasProduto(int codigoProduto) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT DISTINCT(t.codigotransferencia), l.nome, l2.nome, tr.quantidade, t.data "
                        + "FROM public.transferencia t, public.loja l, public.loja l2 , public.produto p, public.transferenciarealizada tr "
                        + "WHERE tr.codigoproduto = " + codigoProduto + " "
                        + "AND tr.codigotransferencia = t.codigotransferencia "
                        + "AND t.codigolojaorigem = l.codigoloja "
                        + "AND t.codigolojadestino = l2.codigoloja "
                        + "AND t.isativo = 'S'  "
                        + "ORDER BY t.codigotransferencia desc");

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
    public int atualizarValorMedioCompraProduto(int codigoProduto) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery("SELECT AVG(valor) as valor FROM public.produtocomprado WHERE codigoproduto = "
                        + codigoProduto + " ");
                if (rs.next()) {
                    double valorMedioCompra = rs.getDouble("valor");
                    st.executeUpdate("UPDATE public.produto "
                            + "SET valormediocompra = " + valorMedioCompra + " "
                            + "WHERE cdproduto = " + codigoProduto);
                }
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioProduto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
