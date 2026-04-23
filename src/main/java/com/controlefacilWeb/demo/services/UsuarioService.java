package com.controlefacilWeb.demo.services;

import com.controlefacilWeb.demo.database.AccessDatabase;
import com.controlefacilWeb.demo.models.UsuarioModel;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serviço de acesso a dados para Usuário.
 * Utiliza JDBC direto via AccessDatabase (PostgreSQL/Access).
 */
@Service
public class UsuarioService {

    private static final Logger log = Logger.getLogger(UsuarioService.class.getName());

    private Connection getConnection() {
        try {
            return new AccessDatabase().conectar();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao obter conexão com o banco de dados", e);
        }
    }

    public int cadastrar(UsuarioModel usuario) {
        String sql = "INSERT INTO public.usuario (login, senha, forcarsenha, codigoloja, tipo) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getUser());
            ps.setString(2, usuario.getPass());
            ps.setString(3, String.valueOf(usuario.getForcarSenha()));
            ps.setInt(4, usuario.getCodigoLoja());
            ps.setInt(5, usuario.getTipo());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            // fallback: MAX(cdusuario)
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT MAX(cdusuario) AS cdusuario FROM public.usuario")) {
                if (rs.next()) {
                    return rs.getInt("cdusuario");
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
        }
        return 0;
    }

    public int alterar(UsuarioModel usuario, int codigoUsuario) {
        String sql = "UPDATE public.usuario SET login=?, forcarsenha=?, tipo=? WHERE cdusuario=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getUser());
            ps.setString(2, String.valueOf(usuario.getForcarSenha()));
            ps.setInt(3, usuario.getTipo());
            ps.setInt(4, codigoUsuario);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao alterar usuário", e);
        }
        return 0;
    }

    public int alterarSenha(UsuarioModel usuario) {
        String sql = "UPDATE public.usuario SET senha=?, forcarsenha='N' WHERE cdusuario=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getPass());
            ps.setInt(2, usuario.getCodigo());
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao alterar senha", e);
        }
        return 0;
    }

    public int remover(int codigoUsuario) {
        String sql = "DELETE FROM public.usuario WHERE cdusuario=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codigoUsuario);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao remover usuário", e);
        }
        return 0;
    }

    public UsuarioModel logar(String user, String pass) {
        String sql = "SELECT * FROM public.usuario WHERE login=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioModel u = mapRow(rs);
                    if (u.getForcarSenha() == 'S' || u.getPass().equals(pass)) {
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao logar usuário", e);
        }
        return null;
    }

    public ArrayList<ArrayList<Object>> listarTodos(int codigoLoja) {
        String sql = "SELECT cdusuario, login, tipo FROM public.usuario ORDER BY cdusuario";
        return executarListagem(sql);
    }

    public ArrayList<Object> listarPorTipo(int tipo, int codigoLoja) {
        String sql = tipo == 0
                ? "SELECT cdusuario, login, tipo FROM public.usuario ORDER BY cdusuario"
                : "SELECT cdusuario, login, tipo FROM public.usuario WHERE tipo=? ORDER BY cdusuario";
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(" - ");
        try (Connection con = getConnection()) {
            PreparedStatement ps = tipo == 0
                    ? con.prepareStatement(sql)
                    : con.prepareStatement(sql);
            if (tipo != 0) {
                ps.setInt(1, tipo);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(rs.getInt("cdusuario") + "- " + rs.getString("login"));
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao listar usuários por tipo", e);
            return null;
        }
        return resultado;
    }

    public ArrayList<Object> preencherCombo(int codigoLoja) {
        String sql = "SELECT cdusuario, login FROM public.usuario ORDER BY cdusuario";
        ArrayList<Object> resultado = new ArrayList<>();
        resultado.add(" - ");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.add(rs.getInt("cdusuario") + "- " + rs.getString("login"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro ao preencher combo de usuários", e);
            return null;
        }
        return resultado;
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private UsuarioModel mapRow(ResultSet rs) throws SQLException {
        return new UsuarioModel(
                rs.getInt("cdusuario"),
                rs.getString("login"),
                rs.getString("senha"),
                rs.getInt("tipo"),
                rs.getString("forcarsenha").charAt(0),
                rs.getInt("codigoloja"));
    }

    private ArrayList<ArrayList<Object>> executarListagem(String sql) {
        ArrayList<ArrayList<Object>> linhas = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                ArrayList<Object> linha = new ArrayList<>();
                for (int i = 1; i <= cols; i++) {
                    linha.add(rs.getObject(i));
                }
                linhas.add(linha);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erro na listagem", e);
            return null;
        }
        return linhas;
    }
}
