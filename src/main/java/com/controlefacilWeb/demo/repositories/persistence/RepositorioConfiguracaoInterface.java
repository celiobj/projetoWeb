/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controlefacilWeb.demo.repositories.persistence;

import java.sql.Connection;
import com.controlefacilWeb.demo.models.ConfiguracaoModel;

/**
 *
 * @author celio.junior
 */
public interface RepositorioConfiguracaoInterface {

    int InserirConfiguracao(ConfiguracaoModel configuracao, Connection conn);

    int AlterarConfiguracao(int codigoConfiguracao, ConfiguracaoModel configuracao, Connection conn);

    int CriarBanco(String script, Connection conn);

    ConfiguracaoModel BuscarConfiguracao(int tipo, Connection conn);
}
