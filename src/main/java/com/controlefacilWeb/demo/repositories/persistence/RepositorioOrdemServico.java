/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.OrdemServicoModel;
import com.controlefacilWeb.demo.models.ProdutoVendidoModel;
import com.controlefacilWeb.demo.models.ServicosPrestadosModel;
import com.controlefacilWeb.demo.repositories.persistence.RepositorioOrdemServicoInterface;
import com.controlefacilWeb.demo.util.Util;



/**
 *
 * @author celio.junior
 */
public class RepositorioOrdemServico implements RepositorioOrdemServicoInterface {

    public RepositorioOrdemServico() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int LancarOrderdeServico(OrdemServicoModel ordermServico, int codigoloja) {
        int codigoOrdem = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.executeUpdate("INSERT INTO public.ordemservico "
                        + "( cdcliente, cdfuncionario,codigoloja,data,valor,valorcheio, valordesconto,status,cdformapagamento) values("
                        + "" + ordermServico.getCodigoCliente() + ","
                        + "" + ordermServico.getCodigoFuncionario() + ","
                        + "" + ordermServico.getCodigoLoja() + ","
                        //  + "" + Util.FormatarDataInsert(ordermServico.getData().substring(0, 10).trim(), 1) + ","
                        + "" + Util.FormatarDataInsert(ordermServico.getData().trim(), 1) + ","
                        + "" + ordermServico.getValor() + ","
                        + "" + ordermServico.getValorCheio() + ","
                        + "" + ordermServico.getValorDesconto() + ","
                        + "'A',"
                        + "" + ordermServico.getCodigoFormaPagamento() + ")", Statement.RETURN_GENERATED_KEYS);
                // ResultSet rsID = st.getGeneratedKeys();
                // if (rsID.next()) {
                // codigoOrdem = rsID.getInt("codigoordem");
                // }

                if (codigoOrdem == 0) {
                    ResultSet rs = st.executeQuery(" SELECT MAX(codigoordem) as codigoordem FROM public.ordemservico");
                    if (rs.next()) {
                        codigoOrdem = rs.getInt("codigoordem");
                    }
                }

                ArrayList<ServicosPrestadosModel> servicos = ordermServico.getServicos();
                int qtdServicos = servicos.size();
                for (int i = 0; i < qtdServicos; i++) {
                    st.execute("INSERT INTO public.servicosprestados "
                            + "( codigoordemservico, codigoloja,data,valor,valorcheio, valordesconto, valorcomissao, codigoservico) values("
                            + "" + codigoOrdem + ","
                            + "" + servicos.get(i).getCodigoLoja() + ","
                            + "" + Util.FormatarDataInsert(servicos.get(i).getData().substring(0, 10).trim(), 1) + ","
                            + "" + servicos.get(i).getValor() + ","
                            + "" + servicos.get(i).getValorCheio() + ","
                            + "" + servicos.get(i).getValorDesconto() + ","
                            + "" + servicos.get(i).getValorcomissao() + ","
                            + "" + servicos.get(i).getCodigoservico() + ")");
                }

                ArrayList<ProdutoVendidoModel> produtos = ordermServico.getProdutos();
                int qtdProdutos = produtos.size();
                for (int i = 0; i < qtdProdutos; i++) {
                    st.execute("INSERT INTO public.produtovendido "
                            + "( codigoordemservico,data,valor,valorcheio, valordesconto,codigoproduto, valorcomissao, codigoloja,quantidade) values("
                            + "" + codigoOrdem + ","
                            + "" + Util.FormatarDataInsert(produtos.get(i).getData().substring(0, 10).trim(), 1) + ","
                            + "" + produtos.get(i).getValor() + ","
                            + "" + produtos.get(i).getValorCheio() + ","
                            + "" + produtos.get(i).getValorDesconto() + ","
                            + "" + produtos.get(i).getCodigoProduto() + ","
                            + "" + produtos.get(i).getValorcomissao() + ","
                            + "" + produtos.get(i).getCodigoLoja() + ","
                            + "" + produtos.get(i).getQuantidade() + ")");
                }
                // con.close();
            }
            return codigoOrdem;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public ArrayList<ArrayList> ListarOrdemServico(boolean isFiltroData, String datainicio, String dataFim,
            boolean isFiltroVendedor, int codigoVendedor, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (isFiltroVendedor) {
                    if (isFiltroData) {
                        rs = st.executeQuery(
                                "SELECT o.codigoordem,f.nome, c.nome, o.valor, o.valorcheio, o.valordesconto, o.data "
                                + "FROM public.funcionario f, public.ordemservico o, public.cliente c "
                                + "where o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                + Util.FormatarDataInsert(dataFim, 1) + " "
                                + "and f.cdfuncionario = o.cdfuncionario "
                                + "and c.codigocliente = o.cdcliente "
                                + "and o.codigoloja = " + codigoLoja + " "
                                + "and o.cdfuncionario = " + codigoVendedor + " "
                                + "and o.status = 'A' "
                                + "ORDER BY o.data desc, codigoordem desc");

                    } else {
                        rs = st.executeQuery(
                                "SELECT o.codigoordem,f.nome, c.nome, o.valor, o.valorcheio, o.valordesconto, o.data "
                                + "FROM public.funcionario f, public.ordemservico o, public.cliente c "
                                + "where f.cdfuncionario = o.cdfuncionario "
                                + "and c.codigocliente = o.cdcliente "
                                + "and o.codigoloja = " + codigoLoja + " "
                                + "and o.cdfuncionario = " + codigoVendedor + " "
                                + "and o.status = 'A' "
                                + "ORDER BY o.data desc, codigoordem desc");
                    }
                } else {
                    if (isFiltroData) {
                        rs = st.executeQuery(
                                "SELECT o.codigoordem,f.nome, c.nome, o.valor, o.valorcheio, o.valordesconto, o.data "
                                + "FROM public.funcionario f, public.ordemservico o , public.cliente c "
                                + "where o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                + Util.FormatarDataInsert(dataFim, 1) + " "
                                + "and c.codigocliente = o.cdcliente "
                                + "and f.cdfuncionario = o.cdfuncionario "
                                + "and o.codigoloja = " + codigoLoja + " "
                                + "and o.status = 'A' "
                                + "ORDER BY o.data desc, codigoordem desc");
                    } else {
                        rs = st.executeQuery(
                                "SELECT o.codigoordem as codigoordem ,f.nome, c.nome, o.valor, o.valorcheio, o.valordesconto, o.data "
                                + "FROM public.funcionario f, public.ordemservico o , public.cliente c "
                                + "where f.cdfuncionario = o.cdfuncionario "
                                + "and c.codigocliente = o.cdcliente "
                                + "and o.codigoloja = " + codigoLoja + " "
                                + "and o.status = 'A' "
                                + "ORDER BY o.data desc, codigoordem desc");
                    }
                }

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
    public ArrayList<ArrayList> ListarProdutosOrdemServico(boolean isFiltroData, String datainicio, String dataFim,
            boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (isFiltroOrdem) {
                    rs = st.executeQuery(
                            "SELECT pv.codigoproduto ,p.nome, pv.valor, pv.valorcheio, pv.valordesconto, p.valorcomissao "
                            + "FROM public.produto p, public.ordemservico o, public.produtovendido pv "
                            + "where pv.codigoproduto = p.cdproduto "
                            + "and pv.codigoordemservico = o.codigoordem "
                            + "and o.codigoloja = " + codigoLoja + " "
                            + "and pv.codigoordemservico = " + codigoOrdem + " "
                            + "ORDER BY pv.codigoproduto");
                } else {
                    if (isFiltroData) {
                        if (isFiltroVendedor) {
                            rs = st.executeQuery(
                                    "SELECT pv.codigoproduto ,p.nome, pv.valor, pv.valorcheio, pv.valordesconto, p.valorcomissao "
                                    + "FROM public.produto p, public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoproduto = p.cdproduto "
                                    + "and pv.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pv.codigoproduto");
                        } else {
                            rs = st.executeQuery(
                                    "SELECT pv.codigoproduto ,p.nome, pv.valor, pv.valorcheio, pv.valordesconto, p.valorcomissao "
                                    + "FROM public.produto p, public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoproduto = p.cdproduto "
                                    + "and pv.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pv.codigoproduto");
                        }
                    } else {
                        if (isFiltroVendedor) {
                            rs = st.executeQuery(
                                    "SELECT pv.codigoproduto ,p.nome, pv.valor, pv.valorcheio, pv.valordesconto, p.valorcomissao "
                                    + "FROM public.produto p, public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoproduto = p.cdproduto "
                                    + "and pv.codigoordemservico = o.codigoordem "
                                    + "and o.cdfuncionario = " + codigoVendedor + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pv.codigoproduto");
                        } else {
                            rs = st.executeQuery(
                                    "SELECT pv.codigoproduto ,p.nome, pv.valor, pv.valorcheio, pv.valordesconto, p.valorcomissao "
                                    + "FROM public.produto p, public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoproduto = p.cdproduto "
                                    + "and pv.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY pv.codigoproduto");
                        }
                    }
                }

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
    public ArrayList<ArrayList> ListarItensOrdemServico(boolean isFiltroData, String datainicio, String dataFim,
            boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoLoja) {

        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (isFiltroOrdem) {
                    rs = st.executeQuery(
                            "SELECT sp.codigoservico,s.nome, sp.valor, sp.valorcheio, sp.valordesconto, s.valorcomissao "
                            + "FROM public.servico s, public.ordemservico o, public.servicosprestados sp "
                            + "where sp.codigoservico = s.cdservico "
                            + "and sp.codigoordemservico = o.codigoordem "
                            + "and o.codigoloja = " + codigoLoja + " "
                            + "and sp.codigoordemservico = " + codigoOrdem + " "
                            + "ORDER BY SP.codigoservico");
                } else {
                    if (isFiltroData) {
                        if (isFiltroVendedor) {
                            rs = st.executeQuery(
                                    "SELECT sp.codigoservico,s.nome, sp.valor, sp.valorcheio, sp.valordesconto, s.valorcomissao "
                                    + "FROM public.servico s, public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoservico = s.cdservico "
                                    + "and sp.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " "
                                    + "ORDER BY SP.codigoservico");
                        } else {
                            rs = st.executeQuery(
                                    "SELECT sp.codigoservico,s.nome, sp.valor, sp.valorcheio, sp.valordesconto, s.valorcomissao "
                                    + "FROM public.servico s, public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoservico = s.cdservico "
                                    + "and sp.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY SP.codigoservico");
                        }
                    } else {
                        if (isFiltroVendedor) {
                            rs = st.executeQuery(
                                    "SELECT sp.codigoservico,s.nome, sp.valor, sp.valorcheio, sp.valordesconto, s.valorcomissao "
                                    + "FROM public.servico s, public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoservico = s.cdservico "
                                    + "and sp.codigoordemservico = o.codigoordem "
                                    + "and o.cdfuncionario = " + codigoVendedor + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY SP.codigoservico");
                        } else {
                            rs = st.executeQuery(
                                    "SELECT sp.codigoservico,s.nome, sp.valor, sp.valorcheio, sp.valordesconto, s.valorcomissao "
                                    + "FROM public.servico s, public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoservico = s.cdservico "
                                    + "and sp.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "ORDER BY SP.codigoservico");
                        }
                    }
                }

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
    public int TrocarVendedor(int codigoOrdemServico, int codigoVendedor, int codigoLoja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.ordemservico "
                        + "SET cdfuncionario=" + codigoVendedor + " "
                        + "WHERE codigoordem = " + codigoOrdemServico + "");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public double SomarComissaoItensOrdemServico(boolean isFiltroData, String datainicio, String dataFim,
            boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroOrdem, int codigoOrdem, int codigoLoja) {
        double valorTotal = 0;
        double valorTotalServicos = 0;
        double valorTotalProdutos = 0;
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rsServicos = null;
                if (isFiltroOrdem) {
                    rsServicos = st.executeQuery("SELECT SUM(sp.valorcomissao) as valorcomissao "
                            + "FROM public.ordemservico o, public.servicosprestados sp "
                            + "where sp.codigoordemservico = o.codigoordem "
                            + "and o.codigoloja = " + codigoLoja + " "
                            + "and sp.codigoordemservico = " + codigoOrdem + " ");
                } else {
                    if (isFiltroData) {
                        if (isFiltroVendedor) {
                            rsServicos = st.executeQuery("SELECT SUM(sp.valorcomissao) as valorcomissao "
                                    + "FROM public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " ");
                        } else {
                            rsServicos = st.executeQuery("SELECT SUM(sp.valorcomissao) as valorcomissao "
                                    + "FROM  public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " ");
                        }
                    } else {
                        if (isFiltroVendedor) {
                            rsServicos = st.executeQuery("SELECT SUM(sp.valorcomissao) as valorcomissao "
                                    + "FROM  public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " ");
                        } else {
                            rsServicos = st.executeQuery("SELECT SUM(sp.valorcomissao) as valorcomissao "
                                    + "FROM public.ordemservico o, public.servicosprestados sp "
                                    + "where sp.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " ");
                        }
                    }
                }

                rsServicos.next();
                ResultSetMetaData rsmd = rsServicos.getMetaData();
                do {
                    valorTotalServicos = rsServicos.getDouble("valorcomissao");
                } while (rsServicos.next());
                // con.close();

                ResultSet rsProdutos = null;
                if (isFiltroOrdem) {
                    rsProdutos = st.executeQuery("SELECT SUM(pv.valorcomissao) as valorcomissao "
                            + "FROM public.ordemservico o, public.produtovendido pv "
                            + "where pv.codigoordemservico = o.codigoordem "
                            + "and o.codigoloja = " + codigoLoja + " "
                            + "and sp.codigoordemservico = " + codigoOrdem + " ");
                } else {
                    if (isFiltroData) {
                        if (isFiltroVendedor) {
                            rsProdutos = st.executeQuery("SELECT SUM(pv.valorcomissao) as valorcomissao "
                                    + "FROM public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoordemservico = o.codigoordem "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " ");
                        } else {
                            rsProdutos = st.executeQuery("SELECT SUM(pv.valorcomissao) as valorcomissao "
                                    + "FROM  public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.data BETWEEN " + Util.FormatarDataInsert(datainicio, 1) + " AND "
                                    + Util.FormatarDataInsert(dataFim, 1) + " ");
                        }
                    } else {
                        if (isFiltroVendedor) {
                            rsProdutos = st.executeQuery("SELECT SUM(pv.valorcomissao) as valorcomissao "
                                    + "FROM  public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " "
                                    + "and o.cdfuncionario = " + codigoVendedor + " ");
                        } else {
                            rsProdutos = st.executeQuery("SELECT SUM(pv.valorcomissao) as valorcomissao "
                                    + "FROM public.ordemservico o, public.produtovendido pv "
                                    + "where pv.codigoordemservico = o.codigoordem "
                                    + "and o.codigoloja = " + codigoLoja + " ");
                        }
                    }

                }
                rsProdutos.next();
                rsmd = rsProdutos.getMetaData();
                do {
                    valorTotalProdutos = rsProdutos.getDouble("valorcomissao");
                } while (rsProdutos.next());

            }
            valorTotal = valorTotalProdutos + valorTotalServicos;
            return valorTotal;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;

        }

    }

    @Override
    public int CancelarOrderdeServico(int codigoOrdermServico, int codigoloja) {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.ordemservico "
                        + "SET status= 'C' "
                        + "WHERE codigoordem = " + codigoOrdermServico + "");

                ResultSet rs;
                rs = st.executeQuery("SELECT "
                        + "codigoproduto "
                        + "FROM public.produtovendido "
                        + "WHERE codigoordemservico = " + codigoOrdermServico + "");
                if (rs.next()) {
                    do {
                        st.execute("UPDATE public.produtoloja "
                                + "SET quantidade= quantidade + 1 "
                                + "WHERE codigoproduto = " + rs.getInt("codigoproduto") + "");
                    } while (rs.next());
                    st.execute("DELETE from public.produtovendido "
                            + "WHERE codigoordemservico = " + codigoOrdermServico + "");
                }
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioOrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public int horarioServicos(boolean isFiltroVendedor, int codigoVendedor, boolean isFiltroCliente, int codigoCliente,
            String dataInicio, String dataFim) {

        int retorno = 0;
        try {
            ArrayList<ArrayList> linhas = new ArrayList<>();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                if (isFiltroVendedor) {
                    if (isFiltroCliente) {
                        rs = st.executeQuery("SELECT COUNT(*) as quantidade "
                                + "FROM ordemservico "
                                + "WHERE data BETWEEN " + dataInicio + " AND " + dataFim + " "
                                + "AND cdfuncionario = " + codigoVendedor + " "
                                + "AND cdcliente = " + codigoCliente + "");

                    } else {
                        rs = st.executeQuery("SELECT COUNT(*) as quantidade "
                                + "FROM ordemservico "
                                + "WHERE data BETWEEN " + dataInicio + " AND " + dataFim + " "
                                + "AND cdfuncionario = " + codigoVendedor + " ");
                    }
                } else {
                    if (isFiltroCliente) {
                        rs = st.executeQuery("SELECT COUNT(*) as quantidade "
                                + "FROM ordemservico "
                                + "WHERE data BETWEEN " + dataInicio + " AND " + dataFim + " "
                                + " AND cdcliente = " + codigoCliente + "");
                    } else {
                        rs = st.executeQuery("SELECT COUNT(*) as quantidade "
                                + "FROM ordemservico "
                                + "WHERE data BETWEEN " + dataInicio + " AND " + dataFim + "");
                    }
                }
                rs.next();
                retorno = rs.getInt("quantidade");
            }

            return retorno;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return 0;

        }

    }

}
