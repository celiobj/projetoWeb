/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.util;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.controlefacilWeb.demo.controllers.CategoriaController;
import com.controlefacilWeb.demo.controllers.ConfiguracaoController;
import com.controlefacilWeb.demo.controllers.ContaController;
import com.controlefacilWeb.demo.controllers.FormaPagamentoController;
import com.controlefacilWeb.demo.controllers.InstituicaoController;
import com.controlefacilWeb.demo.controllers.UsuarioController;
import com.controlefacilWeb.demo.database.Login;
import com.controlefacilWeb.demo.models.CategoriaModel;
import com.controlefacilWeb.demo.models.ConfiguracaoModel;
import com.controlefacilWeb.demo.models.ContaModel;
import com.controlefacilWeb.demo.models.FormaPagamentoModel;
import com.controlefacilWeb.demo.models.InstituicaoModel;
import com.controlefacilWeb.demo.models.SolicitacaoModel;
import com.controlefacilWeb.demo.models.SubCategoriaModel;
import com.controlefacilWeb.demo.models.TelaModel;
import com.controlefacilWeb.demo.models.UsuarioModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 *
 * @author celio.junior
 */
public class Util {

    public static ArrayList<String> proximaLinha(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        ArrayList<String> LinhaAtual = new ArrayList<>();
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
        try {
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                switch (rsmd.getColumnType(i)) {

                    case Types.NVARCHAR ->
                        LinhaAtual.add(rs.getString(i));
                    case Types.DATE ->
                        LinhaAtual.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getDate(i)));
                    case Types.INTEGER ->
                        LinhaAtual.add(Integer.toString(rs.getInt(i)));
                    case Types.BIGINT ->
                        LinhaAtual.add(Integer.toString(rs.getInt(i)));
                    case Types.NUMERIC ->
                        LinhaAtual.add(rs.getBigDecimal(i).toString());
                    case Types.VARCHAR ->
                        LinhaAtual.add(rs.getString(i));
                    case Types.CHAR ->
                        LinhaAtual.add(rs.getString(i));
                    case Types.DOUBLE ->
                        LinhaAtual.add(numberFormatter.format(rs.getDouble(i)));
                    case Types.DECIMAL ->
                        LinhaAtual.add(numberFormatter.format(rs.getDouble(i)));
                    case Types.TIMESTAMP ->
                        LinhaAtual.add(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp(i)));

                }
            }
        } catch (SQLException e) {
        }
        return LinhaAtual;
    }

    public static void LimparTabela(JTable tabela) {
        DefaultTableModel dtmTransacoes = (DefaultTableModel) tabela.getModel();
        int qtdRows = dtmTransacoes.getRowCount();
        for (int i = 0; i < qtdRows; i++) {
            dtmTransacoes.removeRow(0);
        }
    }

    public static void RodarConfInicial() {
        new Thread() {

            // @Override
            public void run() {
                UsuarioController uc = new UsuarioController();
                // ArrayList<ArrayList> usuarios = uc.listarTodosUsuarios(1);
                // if (usuarios.get(0).isEmpty()) {

                //     UsuarioModel usuario = new UsuarioModel(
                //             0,
                //             "admin",
                //             criptografarSenha("admin"),
                //             1,
                //             'S',
                //             0);
                //     uc.Cadastrar(usuario);

                // }

                ConfiguracaoController cc = new ConfiguracaoController();
                if (cc.BuscarConfiguracao(1) == null) {

                    ConfiguracaoModel conf = new ConfiguracaoModel(1, 1, 'N', 'N', 'N', 'N');
                    cc.InserirConfiguracao(conf);

                }

                FormaPagamentoController fpc = new FormaPagamentoController();
                ArrayList<ArrayList> formasPagamento = fpc.ListarTodasFormasPagamento();
                if (formasPagamento.get(0).isEmpty()) {

                    FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel(
                            0,
                            "Débito");
                    fpc.CadastrarFormaPagamento(formaPagamentoModel);

                    // formaPagamentoModel = new FormaPagamentoModel(
                    // 0,
                    // "Cartão de Crédito");
                    // fpc.CadastrarFormaPagamento(formaPagamentoModel);
                }

                CategoriaController catc = new CategoriaController();
                ArrayList<ArrayList> categorias = catc.ListarTodasCategorias('S');
                if (categorias.get(0).isEmpty()) {

                    CategoriaModel categoria = new CategoriaModel();
                    categoria.setNomeCategoria("Comercial");
                    categoria.setAtivo(true);
                    int categoriaPai = catc.CadastrarCategoria(categoria);
                    if (categoriaPai != 0) {
                        SubCategoriaModel subcategoria = new SubCategoriaModel();
                        subcategoria.setCodigoCategoriaPai(categoriaPai);
                        subcategoria.setNomeCategoria("Compra/Venda");
                        subcategoria.setAtivo(true);
                        catc.CadastrarSubCategoria(subcategoria);
                    }

                }

                InstituicaoController ic = new InstituicaoController();
                ArrayList<ArrayList> instituicoes = ic.ListarTodasInstituicoes('S');
                if (instituicoes.get(0).isEmpty()) {

                    InstituicaoModel instituicao = new InstituicaoModel();
                    instituicao.setAtivo(true);
                    instituicao.setNome("Principal");
                    instituicao.setNumero(001);
                    instituicao.setTipo(1);
                    ic.Cadastrar(instituicao);

                    ContaController conc = new ContaController();
                    ArrayList<ArrayList> contas = conc.ListarTodasContas('S');
                    if (contas.get(0).isEmpty()) {

                        ContaModel conta = new ContaModel();
                        conta.setNome("Comercial");
                        conta.setSaldo(0);
                        conta.setAtivo(true);
                        conta.setNumero(001);
                        conta.setInstituicao(instituicao);
                        conta.setTipo(1);
                        conc.Cadastrar(conta);
                    }
                } else {
                    ContaController conc = new ContaController();
                    ArrayList<ArrayList> contas = conc.ListarTodasContas('S');
                    if (contas.get(0).isEmpty()) {

                        InstituicaoModel instituicao = new InstituicaoModel();
                        instituicao.setAtivo(true);
                        instituicao.setNome("Principal");
                        instituicao.setNumero(001);
                        instituicao.setTipo(1);

                        ContaModel conta = new ContaModel();
                        conta.setNome("Comercial");
                        conta.setSaldo(0);
                        conta.setAtivo(true);
                        conta.setNumero(001);
                        conta.setInstituicao(instituicao);
                        conta.setTipo(1);
                        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
                        String dataString = formatoData.format(new Date());
                        conta.setDataCriacao(dataString);
                        conc.Cadastrar(conta);
                    }
                }
            }
        }.start();
    }

    public static String textoParaDouble(String texto) {
        if (texto.contains("R$")) {
            texto = texto.substring(3);
        }
        if (texto.contains(",")) {
            String retornoSemPonto = texto.replace(".", "");
            String retornoCerto = retornoSemPonto.replaceAll(",", ".");
            return retornoCerto;
        } else {
            return texto;
        }

    }

    public static String criptografarSenha(String senha) {
        MessageDigest algorithm = null;

        try {
            algorithm = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        byte messageDigest[] = null;

        try {
            messageDigest = algorithm.digest(senha.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Login.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }

    public static String GerarPattern() {
        String pattern = "";
        switch (Login.conexao) {
            case 1 ->
                pattern = "MM/dd/yyyy HH:mm:ss";
            case 2 ->
                pattern = "dd-MM-yyyy HH:mm:ss";
            case 3 ->
                pattern = "MM-dd-yyyy HH:mm:ss";
            default -> {
            }
        }
        return pattern;
    }

    public static String FormatarDataInsert(String data) {
        String retorno = "";
        if (Login.conexao == 1) {
            if (data.length() < 18) {
                if (data.contains("-")) {
                    String dia = data.substring(0, 2);
                    String mes = data.substring(3, 5);
                    String ano = data.substring(6, 10);
                    retorno = "#" + mes + "/" + dia + "/" + ano + "#";
                } else {
                    retorno = "#" + data.replaceAll("-", "/") + "#";
                }
            } else {
                if (data.contains("-")) {
                    String dia = data.substring(0, 2);
                    String mes = data.substring(3, 5);
                    String ano = data.substring(6, 10);
                    String hora = data.substring(11, 19);
                    retorno = "#" + mes + "/" + dia + "/" + ano + " " + hora + "#";
                } else {
                    retorno = "#" + data.replaceAll("-", "/") + "#";
                }
            }
        } else {
            retorno = "'" + data + "'";
        }

        return retorno;
    }

    public static String FormatarDataInsertDigitado(String data) {
        String retorno = "";
        if (Login.conexao == 1) {

        } else {
            retorno = "'" + data + "'";
        }
        return retorno;
    }

    public static int RetornarMes(int mesCalendar) {
        int retorno = 0;
        switch (mesCalendar) {
            case 0 ->
                retorno = 1;
            case 1 ->
                retorno = 2;
            case 2 ->
                retorno = 3;
            case 3 ->
                retorno = 4;
            case 4 ->
                retorno = 5;
            case 5 ->
                retorno = 6;
            case 6 ->
                retorno = 7;
            case 7 ->
                retorno = 8;
            case 8 ->
                retorno = 9;
            case 9 ->
                retorno = 10;
            case 10 ->
                retorno = 11;
            case 11 ->
                retorno = 12;
            default ->
                throw new AssertionError();
        }
        return retorno;
    }

    public static void enviarEmail(String destinatario, String assunto, String corpo) {

        // Properties props = new Properties();
        // props.put("mail.transport.protocol", "smtp");
        // props.put("mail.smtp.host", "smtp.titan.email");
        // props.put("mail.smtp.socketFactory.port", "465");
        // props.put("mail.smtp.socketFactory.fallback", "false");
        // props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.smtp.port", "465");
        // Session session = Session.getDefaultInstance(props,
        // new javax.mail.Authenticator() {
        // protected PasswordAuthentication getPasswordAuthentication() {
        // return new PasswordAuthentication("celiobj@bragancasystems.com.br",
        // "C@rla270681");
        // }
        // });
        //
        // /**
        // * Ativa Debug para sessão
        // */
        // session.setDebug(true);
        //
        // try {
        //
        // // Remetente
        // Message message = new MimeMessage(session);
        // message.setFrom(new InternetAddress("celiobj@bragancasystems.com.br"));
        //
        // // Destinatário(s)
        // Address[] toUser = InternetAddress
        // .parse(destinatario);// aqui pode colocar mais de um separados por vírgula
        // // Assunto
        // message.setRecipients(Message.RecipientType.TO, toUser);
        // message.setSubject(assunto);
        // message.setText(corpo);
        // /**
        // * Método para enviar a mensagem criada
        // */
        // new Thread() {
        //
        // @Override
        // public void run() {
        // try {
        // Transport.send(message);
        // } catch (MessagingException ex) {
        // Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        // }
        // }
        // }.start();
        //
        // } catch (MessagingException e) {
        // throw new RuntimeException(e);
        // }
    }

    public static boolean VerificarPermissao(String textoTela, UsuarioModel usuario) {
        return false;
        // boolean retorno = false;
        // ConfiguracaoController cc = new ConfiguracaoController();
        // ConfiguracaoModel configuracao = cc.BuscarConfiguracao(1);
        // if (configuracao.getVerificaPermissao() == 'N') {
        // return true;
        // }
        // TelaController tc = new TelaController();
        // TelaModel tela = tc.BuscarTelaPorNome(textoTela);
        // if (usuario.getUser().equalsIgnoreCase("ROOT") || usuario.getTipo() == 1) {
        // return true;
        // } else {
        // if (tela == null) {
        // tela = new TelaModel(0, textoTela);
        // int codigoTela = tc.CadastrarTela(tela, 0);
        // tela.setCodigoTela(codigoTela);
        // }

        // retorno = tc.VerificarPermissao(tela, usuario);
        // if (retorno) {
        // return retorno;
        // } else {
        // JOptionPane.showMessageDialog(null,
        // "Usuário " + usuario.getUser() + " sem acesso ao recurso: " +
        // tela.getDescricaoTela());
        // int resposta = JOptionPane.showConfirmDialog(null, "Deseja abrir
        // solicitacãode acesso ao adm?",
        // "Abrir solicitacão", 0);
        // if (resposta == 0) {
        // SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        // String data = formatoData.format(new Date());

        // SolicitacaoModel solicitacao = new SolicitacaoModel();
        // solicitacao.setCodigoTela(tela.getCodigoTela());
        // solicitacao.setCodigoUsuarioSolic(usuario.getCodigo());
        // solicitacao.setIsAtivo('S');
        // solicitacao.setStatus('A');
        // solicitacao.setData(data.substring(0, 10).trim());
        // String descricao = "Acesso à tela: -" + solicitacao.getCodigoTela() + "- " +
        // tela.getDescricaoTela()
        // + " para o usuario: -" + solicitacao.getCodigoUsuarioSolic() + "- " +
        // usuario.getUser();
        // EscolhaAdm ea = new EscolhaAdm(solicitacao, descricao);
        // }
        // return retorno;
        // }
        // }
    }

    public static String gerarHash(String texto) {
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        byte messageDigest[] = null;
        try {
            messageDigest = algorithm.digest(texto.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Util.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();

        return senhahex;
    }

    public static String lerArquivoTexto(String path) throws IOException {
        return Files.readString(Paths.get(path), StandardCharsets.UTF_8);
    }

    public static String stringObjectToJson(Object objeto, String valor) {
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement el;
        if (objeto != null) {
            el = parser.parse(gson.toJson(objeto));
        } else {
            el = parser.parse(valor);
        }

        return gson.toJson(el);

    }

    public static void Logar(EnumTipoLog tipoLog, String log) {
        SimpleDateFormat formatoData = new SimpleDateFormat(Util.GerarPattern());
        String data = formatoData.format(new Date());
        BufferedWriter br = null;
        if (EnumTipoLog.ERROR == tipoLog) {
            try {
                br = new BufferedWriter(new FileWriter("error" + data + ".txt"));
                br.write(log);
                // br.newLine();
                // br.newLine();
                br.close();

            } catch (IOException ex) {
                Logger.getLogger(Util.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();

                } catch (IOException ex) {
                    Logger.getLogger(Util.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                br = new BufferedWriter(new FileWriter("log" + data + ".txt"));
                br.write(log);
                // br.newLine();
                // br.newLine();
                br.close();

            } catch (IOException ex) {
                Logger.getLogger(Util.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();

                } catch (IOException ex) {
                    Logger.getLogger(Util.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static int pegarColunaDia(String data) {
        // Espera data no formato "yyyy-MM-dd"
        String formatoEsperado = converterParaFormatoIso(data);
        try {
            LocalDate localDate = LocalDate.parse(formatoEsperado, DateTimeFormatter.ISO_LOCAL_DATE);
            DayOfWeek diaSemana = localDate.getDayOfWeek();
            // Retorna o nome do dia da semana em português
            int colunaDia = traduzirDiaSemana(diaSemana);
            return colunaDia;
        } catch (DateTimeParseException e) {
            // Se a data não estiver no formato esperado, retorna 0
            return 0;
        }
    }

    private static int traduzirDiaSemana(DayOfWeek dia) {
        switch (dia) {
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            case SATURDAY:
                return 7;
            case SUNDAY:
                return 1;
            default:
                return 0;
        }
    }

    public static int pegarColunaHora(String hora) {
        // Espera hora no formato "HH:mm"
        try {
            String[] partes = hora.split(":");
            if (partes.length == 2) {
                int horaInt = Integer.parseInt(partes[0]);
                int minutoInt = Integer.parseInt(partes[1]);
                if (minutoInt == 30) {
                    return (horaInt - 7) * 2 + 1; // Ajusta para o índice de coluna
                } else if (minutoInt == 0) {
                    return (horaInt - 7) * 2; // Ajusta para o índice de coluna
                } else {
                    return 0; // Minutos inválidos
                }
            } else {
                return 0; // Formato inválido
            }
        } catch (NumberFormatException e) {
            return 0; // Se a conversão falhar, retorna 0
        }
    }

    public static String pegarHoraColuna(int coluna) {
        // Espera hora no formato "HH:mm"
        switch (coluna) {
            case 0:
                return "07:00";
            case 1:
                return "07:30";
            case 2:
                return "08:00";
            case 3:
                return "08:30";
            case 4:
                return "09:00";
            case 5:
                return "09:30";
            case 6:
                return "10:00";
            case 7:
                return "10:30";
            case 8:
                return "11:00";
            case 9:
                return "11:30";
            case 10:
                return "12:00";
            case 11:
                return "12:30";
            case 12:
                return "13:00";
            case 13:
                return "13:30";
            case 14:
                return "14:00";
            case 15:
                return "14:30";
            case 16:
                return "15:00";
            case 17:
                return "15:30";
            case 18:
                return "16:00";
            case 19:
                return "16:30";
            case 20:
                return "17:00";
            case 21:
                return "17:30";
            case 22:
                return "18:00";
            case 23:
                return "18:30";
            case 24:
                return "19:00";
            case 25:
                return "19:30";
            case 26:
                return "20:00";
            case 27:
                return "20:30";
            case 28:
                return "21:00";
            default:
                return "00:00"; // Retorna 00:00 para colunas inválidas
        }
    }

    /**
     * Converte uma data em string para o formato yyyy-MM-dd. Aceita formatos
     * comuns como dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd, yyyy/MM/dd. Retorna null
     * se não conseguir converter.
     */
    public static String converterParaFormatoIso(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] formatos = {
                "dd/MM/yyyy",
                "MM-dd-yyyy",
                "yyyy-MM-dd",
                "yyyy/MM/dd"
        };
        for (String formato : formatos) {
            try {
                DateTimeFormatter entrada = DateTimeFormatter.ofPattern(formato);
                LocalDate localDate = LocalDate.parse(data, entrada);
                return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                // tenta próximo formato
            }
        }
        return null;
    }

    public static String FormatarDataShow(String data, int conexao) {
        String retorno = "";

        String dia = data.substring(8, 10);
        String mes = data.substring(5, 7);
        String ano = data.substring(0, 4);
        retorno = dia + "/" + mes + "/" + ano;

        return retorno;
    }

    public static String FormatarDataInsert(String data, int conexao) {
        String retorno = "";
        if (conexao == 1) {
            if (data.length() < 18) {
                if (data.contains("-")) {
                    String dia = data.substring(0, 2);
                    String mes = data.substring(3, 5);
                    String ano = data.substring(6, 10);
                    retorno = "#" + mes + "/" + dia + "/" + ano + "#";

                } else {
                    // retorno = "#" + data.replaceAll("-", "/").substring(0, 10).trim() + "#";
                    retorno = "#" + data.replaceAll("-", "/").trim() + "#";
                }
            } else {
                if (data.contains("-")) {
                    String dia = data.substring(0, 2);
                    String mes = data.substring(3, 5);
                    String ano = data.substring(6, 10);
                    String hora = data.substring(11, 19);
                    // retorno = "#" + mes + "/" + dia + "/" + ano + " " + hora + "#";
                    retorno = "#" + mes + "/" + dia + "/" + ano + "#";
                } else {
                    retorno = "#" + data.replaceAll("-", "/").substring(0, 10).trim() + "#";
                }
            }
        } else {
            retorno = "'" + data + "'";
        }

        return retorno;
    }

    public static void atualizarBaseDados() {
        Properties prop;
        try {
            prop = getProp();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Erro ao ler barberoficial.properties", ex);
            return;
        }

        String cliente = prop.getProperty("prop.client");
        String produto = prop.getProperty("prop.produto");
        String ftpHost = "ftp.bragancasystems.com.br";
        String ftpUser = "atualizacao@bragancasystems.com.br";
        String ftpPass = "66@slip66";
        String ftpDir = "/" + produto + "/";
        String localUpdateDir = "data/" + produto;
        String localZipPath = localUpdateDir + "/" + cliente + ".zip";
        String localAccdbPath = localUpdateDir + "/" + cliente + ".accdb";

        new Thread(() -> {
            try {
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get(localUpdateDir));
                java.io.File accdbFile = new java.io.File(localAccdbPath);
                java.io.File zipFile = new java.io.File(localZipPath);
                if (accdbFile.exists()) {
                    return;
                }

                org.apache.commons.net.ftp.FTPClient ftp = new org.apache.commons.net.ftp.FTPClient();
                ftp.connect(ftpHost);
                if (!ftp.login(ftpUser, ftpPass)) {
                    Logger.getLogger(Util.class.getName()).log(Level.WARNING, "Falha no login do FTP");
                    ftp.disconnect();
                    return;
                }
                ftp.enterLocalPassiveMode();
                ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                ftp.changeWorkingDirectory(ftpDir);

                org.apache.commons.net.ftp.FTPFile[] files = ftp.listFiles();
                String zipFileName = null;
                for (org.apache.commons.net.ftp.FTPFile file : files) {
                    if (file.getName().equalsIgnoreCase(cliente + ".zip")) {
                        zipFileName = file.getName();
                        break;
                    }
                }
                if (zipFileName == null) {
                    Logger.getLogger(Util.class.getName()).log(Level.WARNING, "Arquivo de atualização não encontrado no FTP.");
                    ftp.logout();
                    ftp.disconnect();
                    return;
                }

                Logger.getLogger(Util.class.getName()).log(Level.INFO, "Baixando atualização do FTP...");
                try (java.io.OutputStream output = new java.io.FileOutputStream(localZipPath)) {
                    boolean success = ftp.retrieveFile(zipFileName, output);
                    if (!success) {
                        Logger.getLogger(Util.class.getName()).log(Level.WARNING, "Falha ao baixar o arquivo do FTP.");
                        ftp.logout();
                        ftp.disconnect();
                        return;
                    }
                }
                ftp.logout();
                ftp.disconnect();

                if (zipFile.exists()) {
                    Logger.getLogger(Util.class.getName()).log(Level.INFO, "Descompactando atualização...");
                    String canonicalDest = new java.io.File(localUpdateDir).getCanonicalPath() + java.io.File.separator;
                    try (java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(
                            new java.io.FileInputStream(localZipPath))) {
                        java.util.zip.ZipEntry entry;
                        while ((entry = zis.getNextEntry()) != null) {
                            java.io.File outFile = new java.io.File(localUpdateDir, entry.getName());
                            if (!outFile.getCanonicalPath().startsWith(canonicalDest)) {
                                throw new IOException("Caminho inválido no ZIP: " + entry.getName());
                            }
                            if (entry.isDirectory()) {
                                outFile.mkdirs();
                            } else {
                                if (outFile.getParentFile() != null) outFile.getParentFile().mkdirs();
                                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(outFile)) {
                                    byte[] buffer = new byte[8192];
                                    int len;
                                    while ((len = zis.read(buffer)) > 0) {
                                        fos.write(buffer, 0, len);
                                    }
                                }
                            }
                        }
                    }
                    zipFile.delete();
                    Logger.getLogger(Util.class.getName()).log(Level.INFO, "Atualização concluída.");
                }
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "Erro na atualização da base de dados", ex);
            }
        }).start();
    }

    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        InputStream file = Util.class.getClassLoader().getResourceAsStream(
                "barberoficial.properties");
        props.load(file);
        return props;

    }

}
