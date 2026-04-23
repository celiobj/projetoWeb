/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.util.ArrayList;
import com.controlefacilWeb.demo.models.AgendamentoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioAgendamentoInterface {

    int RealizarAgendamento(AgendamentoModel agendamento);

    AgendamentoModel ProcurarAgendamento(int codigoAgendamento);

    int CancelarAgendamento(int codigoAgendamento);

    ArrayList<AgendamentoModel> ListarAgendamentos(int codigoloja, int codigoVendedor, String dataInicial,
            String dataFinal);
}
