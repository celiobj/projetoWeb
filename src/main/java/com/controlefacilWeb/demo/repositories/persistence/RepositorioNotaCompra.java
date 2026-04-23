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
import com.controlefacilWeb.demo.models.NotaCompradaModel;
import com.controlefacilWeb.demo.models.ProdutoCompradoModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioNotaCompra implements RepositorioNotaCompraInterface {

    public RepositorioNotaCompra() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int LancarNotaCompra(NotaCompradaModel nota, int codigoLoja) {

        int codigonota = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.executeUpdate("INSERT INTO public.notadecompra "
                        + "( numeronota, codigofornecedor,data,valor,valorcheio, valordesconto,cdformapagamento,codigoloja,status) values("
                        + "" + nota.getNumeroNota() + ","
                        + "" + nota.getCodigoFornecedor() + ","
                        + "" + Util.FormatarDataInsert(nota.getData(),1) + ","
                        + "" + nota.getValor() + ","
                        + "" + nota.getValorCheio() + ","
                        + "" + nota.getValorDesconto() + ","
                        + "" + nota.getCdformaPagamento() + ","
                        + "" + nota.getCodigoLoja() + ","
                        + "'A' )", Statement.RETURN_GENERATED_KEYS);
//                ResultSet rsID = st.getGeneratedKeys();
//                if (rsID.next()) {
//                    codigonota = rsID.getInt("codigonota");
//                }
                if (codigonota == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(codigonota) as codigonota FROM public.notadecompra");
                    if (rs.next()) {
                        codigonota = rs.getInt("codigonota");
                    }
                }

                ArrayList<ProdutoCompradoModel> produtos = nota.getProdutos();
                int qtdServicos = produtos.size();
                for (int i = 0; i < qtdServicos; i++) {
                    st.execute("INSERT INTO public.produtocomprado "
                            + "( codigonotacompra,data,valor,valorcheio, valordesconto, codigoloja, codigoproduto, quantidade) values("
                            + "" + codigonota + ","
                            + "" + Util.FormatarDataInsert(produtos.get(i).getData().substring(0, 10).trim(),1) + ","
                            + "" + produtos.get(i).getValor() + ","
                            + "" + produtos.get(i).getValorCheio() + ","
                            + "" + produtos.get(i).getValorDesconto() + ","
                            + "" + produtos.get(i).getCodigoLoja() + ","
                            + "" + produtos.get(i).getCodigoProduto() + ","
                            + "" + produtos.get(i).getQuantidade() + ")");
                }

                //con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioNotaCompra.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<ArrayList> ListarNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, int codigoloja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (isFiltroFornecedor) {
                    if (isFiltroData) {
                        rs = st.executeQuery("SELECT n.numeronota, f.nome, n.valor, n.valorcheio, n.valordesconto, n.data "
                                + "FROM public.notadecompra n, public.fornecedor f "
                                + "where n.data BETWEEN " + Util.FormatarDataInsert(datainicio.substring(0, 10).trim(),1) + " AND " + Util.FormatarDataInsert(dataFim.substring(0, 10).trim(),1) + " "
                                + "and f.codigofornecedor = n.codigofornecedor "
                                + "and n.codigofornecedor = " + codigoFornecedor + " "
                                + "and n.codigoloja = " + codigoloja + " "
                                + "and n.status = 'A' "
                                + "ORDER BY n.data desc, n.codigonota desc");

                    } else {
                        rs = st.executeQuery("SELECT n.numeronota, f.nome, n.valor, n.valorcheio, n.valordesconto, n.data "
                                + "FROM public.notadecompra n, public.fornecedor f "
                                + "where f.codigofornecedor = n.codigofornecedor "
                                + "and n.codigofornecedor = " + codigoFornecedor + " "
                                + "and n.codigoloja = " + codigoloja + " "
                                + "and n.status = 'A' "
                                + "ORDER BY n.data desc, n.codigonota desc");
                    }
                } else {
                    if (isFiltroData) {
                        rs = st.executeQuery("SELECT n.numeronota, f.nome, n.valor, n.valorcheio, n.valordesconto, n.data "
                                + "FROM public.notadecompra n, public.fornecedor f  "
                                + "where n.data BETWEEN " + Util.FormatarDataInsert(datainicio.substring(0, 10).trim(),1) + " AND " + Util.FormatarDataInsert(dataFim.substring(0, 10).trim(),1) + " "
                                + "and f.codigofornecedor = n.codigofornecedor  "
                                + "and n.codigoloja = " + codigoloja + " "
                                + "and n.status = 'A' "
                                + "ORDER BY n.data desc, n.codigonota desc");
                    } else {
                        rs = st.executeQuery("SELECT n.numeronota, f.nome, n.valor, n.valorcheio, n.valordesconto, n.data "
                                + "FROM public.notadecompra n, public.fornecedor f "
                                + "where f.codigofornecedor = n.codigofornecedor  "
                                + "and n.codigoloja = " + codigoloja + " "
                                + "and n.status = 'A' "
                                + "ORDER BY n.data desc, n.codigonota desc");
                    }
                }

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
    public ArrayList<ArrayList> ListarProdutosNotas(boolean isFiltroData, String datainicio, String dataFim, boolean isFiltroFornecedor, int codigoFornecedor, boolean isFiltroNota, int codigoNota, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (isFiltroNota) {
                    rs = st.executeQuery("SELECT pc.codigoproduto ,p.nome, pc.quantidade, pc.valor, pc.valorcheio, pc.valordesconto "
                            + "FROM public.produto p, public.notadecompra n, public.produtocomprado pc "
                            + "where pc.codigoproduto = p.cdproduto "
                            + "and pc.codigonotacompra = n.codigonota "
                            + "and n.numeronota= " + codigoNota + " "
                            + "and n.codigoloja = " + codigoLoja + " "
                            + "ORDER BY pc.codigoprodutocomprado");
                } else {
                    if (isFiltroData) {
                        if (isFiltroFornecedor) {
                            rs = st.executeQuery("SELECT pc.codigoproduto ,p.nome, pc.quantidade, pc.valor, pc.valorcheio, pc.valordesconto "
                                    + "FROM public.produto p, public.notadecompra n, public.produtocomprado pc "
                                    + "where pc.codigoproduto = p.cdproduto "
                                    + "and pc.codigonotacompra = n.codigonota "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio.substring(0, 10).trim(),1) + " AND " + Util.FormatarDataInsert(dataFim.substring(0, 10).trim(),1) + " "
                                    + "and n.numeronota = " + codigoNota + " "
                                    + "and n.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pc.codigoprodutocomprado");
                        } else {
                            rs = st.executeQuery("SELECT pc.codigoproduto ,p.nome, pc.quantidade, pc.valor, pc.valorcheio, pc.valordesconto "
                                    + "FROM public.produto p, public.notadecompra n, public.produtocomprado pc "
                                    + "where pc.codigoproduto = p.cdproduto "
                                    + "and pc.codigonotacompra = n.codigonota "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio.substring(0, 10).trim(),1) + " AND " + Util.FormatarDataInsert(dataFim.substring(0, 10).trim(),1) + " "
                                    + "and n.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pc.codigoprodutocomprado");
                        }
                    } else {
                        if (isFiltroFornecedor) {
                            rs = st.executeQuery("SELECT pc.codigoproduto ,p.nome, pc.quantidade, pc.valor, pc.valorcheio, pc.valordesconto "
                                    + "FROM public.produto p, public.notadecompra n, public.produtocomprado pc "
                                    + "where pc.codigoproduto = p.cdproduto "
                                    + "and pc.codigonotacompra = n.codigonota "
                                    + "and n.numeronota = " + codigoNota + " "
                                    + "and n.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pc.codigoprodutocomprado");
                        } else {
                            rs = st.executeQuery("SELECT pc.codigoproduto ,p.nome, pc.quantidade, pc.valor, pc.valorcheio, pc.valordesconto "
                                    + "FROM public.produto p, public.notadecompra n, public.produtocomprado pc "
                                    + "where pc.codigoproduto = p.cdproduto "
                                    + "and pc.codigonotacompra = n.codigonota "
                                    + "and n.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pc.codigoprodutocomprado");
                        }
                    }
                }

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
    public int CancelarNota(int numeroNota, int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.notadecompra "
                        + "SET status= 'C' "
                        + "WHERE numeronota = " + numeroNota + " "
                        + "AND codigoloja = " + codigoLoja + "");

                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "codigoproduto "
                        + "FROM public.produtocomprado "
                        + "WHERE codigonotacompra = (SELECT codigonota from public.notadecompra where numeronota= " + numeroNota + ") "
                        + "AND codigoloja = " + codigoLoja + " ");
                rs.next();
                do {
                    st.execute("UPDATE public.produtoloja "
                            + "SET quantidade= quantidade - 1 "
                            + "WHERE codigoproduto = " + rs.getInt("codigoproduto") + " "
                            + "AND codigoloja = " + codigoLoja + " ");
                } while (rs.next());
                st.execute("DELETE from public.produtocomprado "
                        + "WHERE codigonotacompra = (SELECT codigonota from public.notadecompra where numeronota= " + numeroNota + ") ");
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
}
