package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;

import com.controlefacilWeb.demo.models.HorarioModel;
import com.controlefacilWeb.demo.models.PessoaModel;

public interface RepositorioHorarioInterface {

    int CadastrarHorario(HorarioModel horario, int codigoLoja);

    int RemoverHorario(int codigoHorario, int codigoLoja);

    ArrayList<ArrayList> PreencherComboHorario(int codigoFuncionario, int codigoLoja, String descricao, String data);

    ArrayList<ArrayList> Procurar(int codigoHorario, int codigoLoja);

    ArrayList<ArrayList> ListarTodosHorarios(char origem, PessoaModel pessoa, int CodLoja);
}
