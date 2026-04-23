/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.AgendamentoModel;
import com.controlefacilWeb.demo.util.Util;


/**
 *
 * @author celio.junior
 */
public class RepositorioAgendamento {

    public RepositorioAgendamento() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int RealizarAgendamento(AgendamentoModel agendamento) throws Exception {
        String data = Util.FormatarDataInsert(agendamento.getData(), 1);
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.agendamento"
                        + "(codigofuncionario, codigocliente, data, hora,codigoloja,colunadia, colunahora, descricao,isativo) VALUES ("
                        + "'" + agendamento.getCodigoFuncionario() + "',"
                        + "'" + agendamento.getCodigoCliente() + "',"
                        + "" + data + ","
                        + "'" + agendamento.getHora() + "',"
                        + "'" + agendamento.getCodigoLoja() + "',"
                        + "'" + agendamento.getColunaDia() + "',"
                        + "'" + agendamento.getColunaHora() + "',"
                        + "'" + agendamento.getDescricao() + "',"
                        + "'S');");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioAgendamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int CancelarAgendamento(int codigoAgendamento) throws Exception {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("UPDATE public.agendamento "
                        + "SET isativo='N'"
                        + "WHERE codigoagendamento = " + codigoAgendamento + "");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioAgendamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public ArrayList<AgendamentoModel> ListarAgendamentos(int codigoLoja, int codigoVendedor, String dataInicial,
            String dataFinal) throws Exception {
        ArrayList<AgendamentoModel> agendamentos = new ArrayList<>();
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                  String codigoFunc = "";
                if (codigoVendedor == 000) { // Se o código do funcionário for 000, buscar todos os funcionários
                    codigoFunc = "";
                } else {
                    codigoFunc = "and a.codigofuncionario = " + codigoVendedor;
                }
                rs = st.executeQuery(
                        "SELECT a.codigoagendamento, a.codigofuncionario, a.codigocliente, f.nome as nomeFuncionario, c.nome as nomeCliente, a.data,a.hora,a.isativo,a.codigoLoja, a.colunadia, a.colunahora, a.descricao "
                                + "FROM public.agendamento a, public.funcionario f, public.cliente c "
                                + "WHERE a.isativo = 'S' "
                                + "and a.codigoloja = " + codigoLoja + " "
                                +  codigoFunc + " "
                                + "and a.codigofuncionario = f.cdfuncionario "
                                + "and a.codigocliente = c.codigocliente "
                                + "AND data BETWEEN " + Util.FormatarDataInsert(dataInicial, 1) + " AND "
                                + Util.FormatarDataInsert(dataFinal, 1) + "  "
                                + "ORDER BY a.data,a.colunahora");
                rs.next();
                do {
                    
                    String data = Util.FormatarDataShow(rs.getString("data"),1);

                    AgendamentoModel agendamento = new AgendamentoModel(
                            rs.getInt("codigoagendamento"),
                            rs.getInt("codigofuncionario"),
                            rs.getInt("codigocliente"),
                            rs.getString("nomeFuncionario"),
                            rs.getString("nomeCliente"),
                            data,
                            rs.getString("hora"),
                            rs.getString("isativo").charAt(0),
                            rs.getInt("codigoLoja"),
                            rs.getInt("colunadia"),
                            rs.getInt("colunahora"),
                            rs.getString("descricao"));
                    agendamentos.add(agendamento);
                } while (rs.next());
                // con.close();
            }

            return agendamentos;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }

    }

    public AgendamentoModel ProcurarAgendamento(int codigoAgendamento) throws Exception {

        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;
                rs = st.executeQuery(
                        "SELECT a.codigoagendamento, a.codigofuncionario, a.codigocliente, f.nome as nomeFuncionario, c.nome as nomeCliente, a.data,a.hora,a.isativo,a.codigoLoja, a.colunadia, a.colunahora, a.descricao "
                                + "FROM public.agendamento a, public.funcionario f, public.cliente c "
                                + "WHERE a.codigofuncionario = f.cdfuncionario "
                                + "and a.codigocliente = c.codigocliente "
                                + "and codigoagendamento = " + codigoAgendamento + "");
                if (rs.next()) {
                    AgendamentoModel agendamento = new AgendamentoModel(
                            rs.getInt("codigoagendamento"),
                            rs.getInt("codigofuncionario"),
                            rs.getInt("codigocliente"),
                            rs.getString("nomeFuncionario"),
                            rs.getString("nomeCliente"),
                            rs.getString("data"),
                            rs.getString("hora"),
                            rs.getString("isativo").charAt(0),
                            rs.getInt("codigoLoja"),
                            rs.getInt("colunadia"),
                            rs.getInt("colunahora"),
                            rs.getString("descricao"));

                    return agendamento;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
