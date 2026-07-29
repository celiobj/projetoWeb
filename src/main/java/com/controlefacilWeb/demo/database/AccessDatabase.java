package com.controlefacilWeb.demo.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gerenciador de conexões com o banco de dados.
 *
 * Suporta três modos de conexão, controlados por {@link Login#conexao}:
 * <ul>
 * <li>1 - Microsoft Access local (UCanAccess JDBC)</li>
 * <li>2 - PostgreSQL local (localhost)</li>
 * <li>3 - PostgreSQL remoto (postgresql.uhserver.com)</li>
 * </ul>
 *
 * Uso nos repositórios:
 * 
 * <pre>
 * AccessDatabase db = new AccessDatabase();
 * Connection con = db.conectar();
 * </pre>
 */
public class AccessDatabase {

    // ---------------------------------------------------------------
    // Arquivo de configurações lido uma vez por instância
    // ---------------------------------------------------------------
    private static final String CONF_FILE = "conf.properties";

    private final Properties conf;

    public AccessDatabase() {
        conf = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CONF_FILE)) {
            // try (InputStream is = new java.io.FileInputStream(new java.io.File("data/" +
            // CONF_FILE))) {
            if (is != null) {
                conf.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar " + CONF_FILE, e);
        }
    }

    // ---------------------------------------------------------------
    // conectar(String cliente) – modo determinado por Login.conexao
    // ---------------------------------------------------------------

    /**
     * Abre uma conexão de acordo com o modo definido em {@link Login#conexao}.
     *
     * @param cliente nome do cliente (usado apenas no modo 1 para selecionar o
     *                arquivo)
     * @return {@link Connection} aberta e pronta para uso
     * @throws Exception se o driver não for encontrado ou a conexão falhar
     */
    public Connection conectar(String cliente) throws Exception {
        switch (Login.conexao) {

            // --------------------------------------------------------
            // Caso 1: Access local
            // --------------------------------------------------------
            case 1: {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                String filename = "BD/ultraBarber_teste.accdb";
                String url = "jdbc:ucanaccess://" + filename.trim();
                return DriverManager.getConnection(url);
            }

            // --------------------------------------------------------
            // Caso 2: PostgreSQL local (localhost)
            // --------------------------------------------------------
            case 2: {
                Class.forName("org.postgresql.Driver");
                String host = conf.getProperty("pg.local.host", "localhost");
                String port = conf.getProperty("pg.local.port", "5432");
                String db = conf.getProperty("pg.local.database", "bragasi");
                String user = conf.getProperty("pg.local.user", "postgres");
                String pass = conf.getProperty("pg.local.password", "");
                String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
                return DriverManager.getConnection(url, user, pass);
            }

            // --------------------------------------------------------
            // Caso 3: PostgreSQL remoto
            // --------------------------------------------------------
            case 3: {
                Class.forName("org.postgresql.Driver");
                String host = conf.getProperty("pg.remoto.host", "postgresql.uhserver.com");
                String port = conf.getProperty("pg.remoto.port", "5432");
                String db = conf.getProperty("pg.remoto.database", "ultrabarber");
                String user = conf.getProperty("pg.remoto.user", "");
                String pass = conf.getProperty("pg.remoto.password", "");
                String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
                return DriverManager.getConnection(url, user, pass);
            }

            default:
                throw new IllegalStateException("Modo de conexão inválido: " + Login.conexao);
        }
    }

    // ---------------------------------------------------------------
    // conectar() – Access com cliente definido em conf.properties
    // ---------------------------------------------------------------

    /**
     * Abre uma conexão com o arquivo Access configurado em {@code conf.properties}.
     *
     * Se a chave {@code db.path} estiver definida, usa o caminho absoluto
     * diretamente.
     * Caso contrário, constrói o caminho como
     * {@code C:/Sistemas/BD/Bragasi/{cliente}.accdb}
     * usando a chave {@code cliente}.
     *
     * @return {@link Connection} aberta e pronta para uso
     * @throws Exception se o driver não for encontrado ou a conexão falhar
     */
    public Connection conectar() {
        /*
         * String dbPath = conf.getProperty("db.path");
         * String filename;
         * if (dbPath != null && !dbPath.isBlank()) {
         * filename = dbPath.trim();
         * } else {
         * String cliente = conf.getProperty("cliente");
         * if (cliente == null || cliente.isBlank()) {
         * throw new IllegalStateException(
         * "Defina 'db.path' ou 'cliente' em " + CONF_FILE);
         * }
         * filename = "data/" + cliente.trim() + ".accdb";
         * }
         * 
         * try {
         * Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
         * } catch (ClassNotFoundException | ExceptionInInitializerError |
         * NoClassDefFoundError e) {
         * e.printStackTrace();
         * return null;
         * }
         * String url = "jdbc:ucanaccess://" + filename;
         * try {
         * return DriverManager.getConnection(url);
         * } catch (SQLException e) {
         * e.printStackTrace();
         * } catch (Throwable e) {
         * e.printStackTrace();
         * }
         * return null;
         */
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String host = conf.getProperty("pg.local.host", "127.0.0.1");
        String port = conf.getProperty("pg.local.port", "5432");
        String db = conf.getProperty("pg.local.database", "bragasi");
        String user = conf.getProperty("pg.local.user", "postgres");
        String pass = conf.getProperty("pg.local.password", "slipclown");
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
        try {
            System.out.println("Host: " + host);
            System.out.println("Port: " + port);
            System.out.println("Database: " + db);
            System.out.println("User: " + user);
            System.out.println("URL: " + url);
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
