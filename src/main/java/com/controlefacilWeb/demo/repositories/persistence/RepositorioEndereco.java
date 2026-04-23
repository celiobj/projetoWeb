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
import com.controlefacilWeb.demo.models.ClienteModel;
import com.controlefacilWeb.demo.models.EnderecoModel;
import com.controlefacilWeb.demo.models.FornecedorModel;
import com.controlefacilWeb.demo.models.FuncionarioModel;
import com.controlefacilWeb.demo.models.LojaModel;
import com.controlefacilWeb.demo.models.PessoaModel;
import com.controlefacilWeb.demo.util.Util;

/**
 *
 * @author celio.junior
 */
public class RepositorioEndereco implements RepositorioEnderecoInterface {

    public RepositorioEndereco() {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int CadastrarEndereco(EnderecoModel endereco) {
        try {
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                st.execute("INSERT INTO public.endereco "
                        + "(cdfuncionario, cdfornecedor, descricao,cdcliente,cdloja,tipo,isativo) values("
                        + "'" + endereco.getCodigoFuncionario() + "',"
                        + "'" + endereco.getCodigoFornecedor() + "',"
                        + "'" + endereco.getEndereco() + "',"
                        + "'" + endereco.getCodigoCliente() + "',"
                        + "'" + endereco.getCodigoLoja() + "',"
                        + "'" + endereco.getTipo() + "',"
                        + "'S')");
                // con.close();
            }
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(RepositorioEndereco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int RemoverEndereco(int codigoTelefone) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @SuppressWarnings("null")
    @Override
    public ArrayList<ArrayList> ListarTodosEnderecos(char origem, PessoaModel pessoa) {
        try {
            ArrayList<ArrayList> linhas = new ArrayList();
            AccessDatabase a = new AccessDatabase();
            try (Connection con = a.conectar()) {
                Statement st = con.createStatement();
                ResultSet rs = null;
                if (pessoa.getClass().equals(ClienteModel.class) || origem == 'C') {
                    // ClienteModel cliente = (ClienteModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdendereco, descricao,tipo, isativo FROM public.endereco WHERE cdcliente = '"
                                    // + cliente.getCodigoCliente() + "' ORDER BY cdendereco");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdendereco");
                }
                if (pessoa.getClass().equals(FuncionarioModel.class) || origem == 'U') {
                    // FuncionarioModel funcionario = (FuncionarioModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdendereco, descricao,tipo, isativo FROM public.endereco WHERE cdfuncionario = '"
                                    // + funcionario.getCodigoFuncionario() + "' ORDER BY cdendereco");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdendereco");
                }
                if (pessoa.getClass().equals(FornecedorModel.class) || origem == 'F') {
                    // FornecedorModel fornecedor = (FornecedorModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdendereco, descricao,tipo, isativo FROM public.endereco WHERE cdfornecedor = '"
                                    // + fornecedor.getCodigoFornecedor() + "' ORDER BY cdendereco");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdendereco");
                }
                if (pessoa.getClass().equals(LojaModel.class) || origem == 'L') {
                    // LojaModel loja = (LojaModel) pessoa;
                    rs = st.executeQuery(
                            "SELECT cdendereco, descricao,tipo, isativo FROM public.endereco WHERE cdloja = '"
                                    // + loja.getCodigoLoja() + "' ORDER BY cdendereco");
                                    + pessoa.getCodigoPessoa() + "' ORDER BY cdendereco");
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
    public ArrayList<ArrayList> PreencherComboEnderecos(char status) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
