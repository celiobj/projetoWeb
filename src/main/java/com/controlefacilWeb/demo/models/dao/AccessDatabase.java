/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.models.dao;

/**
 *
 * @author celio.junior
 */
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;
import ultraBarber.view.desktop.swing.Login;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ultraBarber.util.Manipulador.getProp;

public class AccessDatabase implements IDatabase {

    private String url = null;
    private String user = null;
    private String password = null;
    private Connection conn = null;

    @Override
    public Connection conectar(String cliente) {

        switch (Login.conexao) {
            case 1 -> {
                try {
                    //String filename = "https://drive.google.com/file/d/1Vb6_tIy93ee_wNKxUu1lIAAj44cy5hWZ/view?usp=drive_link";

                    // String filename = "G:/Meu Drive/Sistema/Barber/BD/ultraBarber.accdb";
                    String filename = "BD/ultraBarber_teste.accdb";
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    File arquivo = new File(filename);
                    if (!arquivo.exists()) {
                        System.err.println("Arquivo não existe");
                    }
                    String database = "jdbc:ucanaccess://" + filename.trim();
                    conn = DriverManager.getConnection(database);
                    return conn;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Conexão não estabelecida: " + e.getMessage(), "Mensagem do programa", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AccessDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 2 -> {
                setUrl("jdbc:postgresql://localhost/ultrabarber");
                setUser("postgres");
                setPassword("slipclown");
            }
            case 3 -> {
                setUrl("jdbc:postgresql://postgresql.uhserver.com/ultrabarber");
                setUser("celiobj");
                setPassword("B@rber2023");
            }
            default -> {
            }
        }
        try {

            setConn(DriverManager.getConnection(getUrl(), getUser(), getPassword()));
            // System.out.println("Connected to the PostgreSQL server successfully.");
            Login.setCon(getConn());
            return getConn();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexão não  estabelecida: " + e.getMessage(), "Mensagem do Programa",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            System.exit(0);

        }
        return null;

    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Connection conectar() {
        switch (1) {
            case 1 -> {
                try {
                    Properties prop = getProp();
                    String cliente = prop.getProperty("prop.client");
                    String filename = "C:/Sistemas/BD/Bragasi/" + cliente + ".accdb";
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    File arquivo = new File(filename);
                    if (!arquivo.exists()) {
                        System.err.println("Arquivo nao existe");
                    }
                    String database = "jdbc:ucanaccess://" + filename.trim();
                    conn = DriverManager.getConnection(database);
                    return conn;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Conexão não estabelecida: " + e.getMessage(),
                            "Mensagem do programa", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e.getMessage());
                    System.exit(0);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AccessDatabase.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AccessDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 2 -> {
                setUrl("jdbc:postgresql://localhost/bragasi");
                setUser("postgres");
                setPassword("slipclown");
            }
            case 3 -> {
                setUrl("jdbc:postgresql://postgresql.uhserver.com/ultrabarber");
                setUser("celiobj");
                setPassword("B@rber2023");
            }
            default -> {
            }
        }
        try {

            setConn(DriverManager.getConnection(getUrl(), getUser(), getPassword()));
            // System.out.println("Connected to the PostgreSQL server successfully.");
            Login.setCon(getConn());
            return getConn();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexão não  estabelecida: " + e.getMessage(), "Mensagem do Programa",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            System.exit(0);

        }
        return null;
    }
}
/**
 * Para teste
 *
 * @param args
 */
