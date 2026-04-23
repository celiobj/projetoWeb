package com.controlefacilWeb.demo.database;

/**
 * Classe de controle do modo de conexão com o banco de dados.
 *
 * Modos disponíveis:
 *   1 - Access local  (BD/ultraBarber_teste.accdb)
 *   2 - PostgreSQL local  (localhost)
 *   3 - PostgreSQL remoto (postgresql.uhserver.com)
 */
public class Login {

    /**
     * Define o modo de conexão ativo.
     * Altere este valor para trocar o banco em uso em tempo de execução,
     * ou configure-o via conf.properties com a chave {@code conexao}.
     */
    public static int conexao = 1;

    public static boolean isIsService() {
      return false;
    }

    public static String getUrlBase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUrlBase'");
    }
}
