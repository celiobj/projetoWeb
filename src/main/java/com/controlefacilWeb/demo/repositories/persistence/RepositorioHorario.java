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
import com.controlefacilWeb.demo.models.AgendamentoModel;
import com.controlefacilWeb.demo.models.ClienteModel;
import com.controlefacilWeb.demo.models.FornecedorModel;
import com.controlefacilWeb.demo.models.FuncionarioModel;
import com.controlefacilWeb.demo.models.HorarioModel;
import com.controlefacilWeb.demo.models.LojaModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.util.Util;

public class RepositorioHorario implements RepositorioHorarioInterface {

    @Override
    public int CadastrarHorario(HorarioModel horario, int codigoLoja) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.horario "
                        + "(inicio,fim,cdfuncionario, cdloja, cdHorarioInicio, cdHorarioFim, descricao, isativo) values("
                        + "'" + horario.getInicio() + "',"
                        + "'" + horario.getFim() + "',"
                        + "'" + horario.getCodigoFuncionario() + "',"
                        + "'" + horario.getCodigoLoja() + "',"
                        + "'" + horario.getCodigoHorarioInicio() + "',"
                        + "'" + horario.getCodigoHorarioFim() + "',"
                        + "'" + horario.getDescricao() + "',"
                        + "'S')");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverHorario(int codigoHorario, int codigoLoja) {
        // Implementação do método para remover horário
        return 0; // Retornar o status da remoção
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ArrayList<ArrayList> PreencherComboHorario(int codigoFuncionario, int codigoLoja, String descricao,
            String data) {
        try {

            AccessDatabase a = new AccessDatabase();
            ArrayList horarios = new ArrayList<ArrayList>();
            ArrayList disponiveis = new ArrayList<ArrayList>();
            int inicio = 0, fim = 0;
            // horarios.add(" - ");
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery(
                        "SELECT cdHorarioInicio, cdHorarioFim FROM public.horario WHERE cdfuncionario = "
                                + codigoFuncionario + " and cdloja = " + codigoLoja + " and descricao = '" + descricao
                                + "' ORDER BY cdhorario");

                while (rs.next()) {

                    inicio = rs.getInt("cdHorarioInicio");
                    fim = rs.getInt("cdHorarioFim");
                }
                if (inicio == 0 && fim == 0) {
                    inicio = 0; // Hora inicial padrão
                    fim = 28; // Hora final padrão
                }
                for (int i = inicio; i <= fim; i++) {
                    ArrayList horario = new ArrayList();
                    horario.add(Util.pegarHoraColuna(i));
                    horarios.add(horario);
                    disponiveis.add(horario);

                }
                RepositorioAgendamento ra = new RepositorioAgendamento();
                ArrayList<AgendamentoModel> agendamentos = null;
                try {
                    agendamentos = ra.ListarAgendamentos(codigoLoja, codigoFuncionario, data,
                            data);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (int i = 0; i < horarios.size(); i++) {
                    int colunaHora = Util.pegarColunaHora(horarios.get(i).toString().substring(1, 6));
                    if (agendamentos == null || agendamentos.isEmpty()) {
                        continue; // Se não houver agendamentos, não remover nada
                    }
                    for (AgendamentoModel agendamento : agendamentos) {
                        if (agendamento.getColunaHora() == colunaHora) {
                            try {
                                disponiveis.set(i, "Reservado: " + agendamento.getHora());
                            } catch (Exception e) {
                                System.out.println("Erro ao remover horário: " + e.getMessage());
                            }

                        }
                    }
                }

                // con.close();
            }

            return disponiveis;

        } catch (SQLException t) {
            System.out.println(t.getMessage());
            return null;

        }
    }

    @Override
    public ArrayList<ArrayList> Procurar(int codigoHorario, int codigoLoja) {
        // Implementação do método para procurar um horário específico
        return new ArrayList<>(); // Retornar os detalhes do horário procurado
    }

    @SuppressWarnings("null")
    @Override
    public ArrayList<ArrayList> ListarTodosHorarios(char origem, PessoaModel pessoa, int CodLoja) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (pessoa.getClass().equals(ClienteModel.class) || origem == 'C') {
                    // ClienteModel cliente = (ClienteModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdtelefone, numero, descricao, isativo FROM public.telefone WHERE cdcliente = '"
                                    // + cliente.getCodigoCliente() + "' ORDER BY cdtelefone");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdtelefone");
                }
                if (pessoa.getClass().equals(FuncionarioModel.class) || origem == 'U') {
                    // FuncionarioModel funcionario = (FuncionarioModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdhorario, descricao, inicio, fim FROM public.horario WHERE cdfuncionario = '"
                                    // + fornecedor.getCodigoFornecedor() + "' ORDER BY cdtelefone");
                                    + pessoa.getCodigoPessoa() + "' AND cdloja = '" + CodLoja + "' ORDER BY cdhorario");
                }
                if (pessoa.getClass().equals(FornecedorModel.class) || origem == 'F') {
                    // FornecedorModel fornecedor = (FornecedorModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdhorario, descricao, inicio, fim FROM public.horario WHERE cdfuncionario = '"
                                    // + fornecedor.getCodigoFornecedor() + "' ORDER BY cdtelefone");
                                    + pessoa.getCodigoPessoa() + "' AND cdloja = '" + CodLoja + "' ORDER BY cdhorario");
                }
                if (pessoa.getClass().equals(LojaModel.class) || origem == 'L') {
                    // LojaModel loja = (LojaModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdtelefone, numero, tipo, isativo FROM public.telefone WHERE cdloja = '"
                                    // + loja.getCodigoLoja() + "' ORDER BY cdtelefone");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdtelefone");
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

}
