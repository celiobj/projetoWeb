-- ============================================================
-- Script de criação do banco de dados - PostgreSQL
-- Sistema: ControleFácil Web
-- Gerado a partir da análise dos repositórios Java
-- ============================================================

-- Criação do schema (opcional, usa public por padrão)
-- CREATE SCHEMA IF NOT EXISTS public;

-- ============================================================
-- Tabelas independentes (sem FK)
-- ============================================================

CREATE TABLE IF NOT EXISTS public.loja (
    codigoloja  SERIAL       PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    isativo     CHAR(1)      NOT NULL DEFAULT 'S'
);

CREATE TABLE IF NOT EXISTS public.instituicao (
    cdinstituicao SERIAL       PRIMARY KEY,
    numero        VARCHAR(20)  NOT NULL UNIQUE,
    nome          VARCHAR(100) NOT NULL,
    tipo          VARCHAR(20),
    isativo       CHAR(1)      NOT NULL DEFAULT 'S'
);

CREATE TABLE IF NOT EXISTS public.formapagamento (
    cdformapagamento SERIAL       PRIMARY KEY,
    descricao        VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.categoria (
    cdcategoria   SERIAL       PRIMARY KEY,
    nomecategoria VARCHAR(100) NOT NULL,
    isativo       CHAR(1)      NOT NULL DEFAULT 'S'
);

CREATE TABLE IF NOT EXISTS public.subcategoria (
    cdsubcategoria   SERIAL       PRIMARY KEY,
    nomesubcategoria VARCHAR(100) NOT NULL,
    isativo          CHAR(1)      NOT NULL DEFAULT 'S',
    cdcategoria      INT          NOT NULL,
    CONSTRAINT fk_subcategoria_categoria FOREIGN KEY (cdcategoria) REFERENCES public.categoria (cdcategoria)
);

CREATE TABLE IF NOT EXISTS public.servico (
    cdservico      INT          PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    descricao      VARCHAR(255),
    valor          NUMERIC(10,2) NOT NULL DEFAULT 0,
    valorcomissao  NUMERIC(10,2) NOT NULL DEFAULT 0,
    isativo        CHAR(1)      NOT NULL DEFAULT 'S'
);

CREATE TABLE IF NOT EXISTS public.produto (
    cdproduto         INT           PRIMARY KEY,
    nome              VARCHAR(100)  NOT NULL,
    descricao         VARCHAR(255),
    quantidade        INT           NOT NULL DEFAULT 0,
    valorvenda        NUMERIC(10,2) NOT NULL DEFAULT 0,
    valorcomissao     VARCHAR(20),
    valormediocompra  NUMERIC(10,2) NOT NULL DEFAULT 0,
    isativo           CHAR(1)       NOT NULL DEFAULT 'S'
);

CREATE TABLE IF NOT EXISTS public.cliente (
    codigocliente SERIAL       PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    documento     VARCHAR(20),
    sexo          CHAR(1),
    numerosus     VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS public.fornecedor (
    codigofornecedor SERIAL       PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL,
    cnpj             VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS public.tela (
    codigotela SERIAL       PRIMARY KEY,
    nometela   VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.configuracao (
    codigoconfiguracao INT     PRIMARY KEY,
    tipo               INT     NOT NULL,
    isintegnegfin      CHAR(1) NOT NULL DEFAULT 'N',
    isfecharaut        CHAR(1) NOT NULL DEFAULT 'N',
    ispagaraut         CHAR(1) NOT NULL DEFAULT 'N',
    verificaPermissao  CHAR(1) NOT NULL DEFAULT 'N'
);

-- ============================================================
-- Tabelas com dependência de 1º nível
-- ============================================================

CREATE TABLE IF NOT EXISTS public.usuario (
    cdusuario   SERIAL       PRIMARY KEY,
    login       VARCHAR(100) NOT NULL UNIQUE,
    senha       VARCHAR(255) NOT NULL,
    forcarsenha CHAR(1)      NOT NULL DEFAULT 'N',
    codigoloja  INT,
    tipo        INT          NOT NULL DEFAULT 0,
    CONSTRAINT fk_usuario_loja FOREIGN KEY (codigoloja) REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.funcionario (
    cdfuncionario SERIAL       PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    cdusuario     INT          NOT NULL,
    isativo       CHAR(1)      NOT NULL DEFAULT 'S',
    CONSTRAINT fk_funcionario_usuario FOREIGN KEY (cdusuario) REFERENCES public.usuario (cdusuario)
);

CREATE TABLE IF NOT EXISTS public.conta (
    cdconta          SERIAL        PRIMARY KEY,
    numero           VARCHAR(20)   NOT NULL UNIQUE,
    nome             VARCHAR(100)  NOT NULL,
    tipo             VARCHAR(20),
    numeroinstituicao VARCHAR(20),
    saldo            NUMERIC(15,2) NOT NULL DEFAULT 0,
    datacriacao      DATE,
    isativo          CHAR(1)       NOT NULL DEFAULT 'S',
    CONSTRAINT fk_conta_instituicao FOREIGN KEY (numeroinstituicao) REFERENCES public.instituicao (numero)
);

CREATE TABLE IF NOT EXISTS public.cartao (
    codigocartao      SERIAL        PRIMARY KEY,
    nomecartao        VARCHAR(100)  NOT NULL,
    numerocartao      VARCHAR(20),
    codigoinstituicao INT           NOT NULL,
    limitedisponivel  NUMERIC(15,2) NOT NULL DEFAULT 0,
    limiteestipulado  NUMERIC(15,2) NOT NULL DEFAULT 0,
    diafechamento     INT,
    diavencimento     INT,
    isativo           CHAR(1)       NOT NULL DEFAULT 'S',
    CONSTRAINT fk_cartao_instituicao FOREIGN KEY (codigoinstituicao) REFERENCES public.instituicao (cdinstituicao)
);

CREATE TABLE IF NOT EXISTS public.comissao (
    codigocomissao SERIAL        PRIMARY KEY,
    descricao      VARCHAR(255),
    valor          NUMERIC(10,2) NOT NULL DEFAULT 0,
    tipo           INT           NOT NULL DEFAULT 0,
    codigoloja     INT           NOT NULL,
    isativo        CHAR(1)       NOT NULL DEFAULT 'S',
    CONSTRAINT fk_comissao_loja FOREIGN KEY (codigoloja) REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.produtoloja (
    codigoproduto INT NOT NULL,
    codigoloja    INT NOT NULL,
    quantidade    INT NOT NULL DEFAULT 0,
    PRIMARY KEY (codigoproduto, codigoloja),
    CONSTRAINT fk_produtoloja_produto FOREIGN KEY (codigoproduto) REFERENCES public.produto (cdproduto),
    CONSTRAINT fk_produtoloja_loja    FOREIGN KEY (codigoloja)    REFERENCES public.loja (codigoloja)
);

-- ============================================================
-- Tabelas com dependência de 2º nível
-- ============================================================

CREATE TABLE IF NOT EXISTS public.horario (
    cdhorario         SERIAL       PRIMARY KEY,
    inicio            VARCHAR(10)  NOT NULL,
    fim               VARCHAR(10)  NOT NULL,
    cdfuncionario     INT          NOT NULL,
    cdloja            INT          NOT NULL,
    cdHorarioInicio   INT          NOT NULL DEFAULT 0,
    cdHorarioFim      INT          NOT NULL DEFAULT 0,
    descricao         VARCHAR(100),
    isativo           CHAR(1)      NOT NULL DEFAULT 'S',
    CONSTRAINT fk_horario_funcionario FOREIGN KEY (cdfuncionario) REFERENCES public.funcionario (cdfuncionario),
    CONSTRAINT fk_horario_loja        FOREIGN KEY (cdloja)        REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.agendamento (
    codigoagendamento SERIAL       PRIMARY KEY,
    codigofuncionario INT          NOT NULL,
    codigocliente     INT          NOT NULL,
    data              DATE         NOT NULL,
    hora              VARCHAR(10),
    codigoloja        INT          NOT NULL,
    colunadia         INT          NOT NULL DEFAULT 0,
    colunahora        INT          NOT NULL DEFAULT 0,
    descricao         VARCHAR(255),
    isativo           CHAR(1)      NOT NULL DEFAULT 'S',
    CONSTRAINT fk_agendamento_funcionario FOREIGN KEY (codigofuncionario) REFERENCES public.funcionario (cdfuncionario),
    CONSTRAINT fk_agendamento_cliente     FOREIGN KEY (codigocliente)     REFERENCES public.cliente (codigocliente),
    CONSTRAINT fk_agendamento_loja        FOREIGN KEY (codigoloja)        REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.fatura (
    codigofatura  SERIAL        PRIMARY KEY,
    mesfatura     VARCHAR(7)    NOT NULL,
    valor         NUMERIC(15,2) NOT NULL DEFAULT 0,
    isfechada     CHAR(1)       NOT NULL DEFAULT 'N',
    ispaga        CHAR(1)       NOT NULL DEFAULT 'N',
    isvigente     CHAR(1)       NOT NULL DEFAULT 'S',
    datapagamento DATE,
    codigocartao  INT           NOT NULL,
    datainicio    DATE          NOT NULL,
    datafim       DATE          NOT NULL,
    datavencimento DATE         NOT NULL,
    CONSTRAINT fk_fatura_cartao FOREIGN KEY (codigocartao) REFERENCES public.cartao (codigocartao)
);

CREATE TABLE IF NOT EXISTS public.permissaotela (
    codigopermissao SERIAL  PRIMARY KEY,
    codigotela      INT     NOT NULL,
    codigousuario   INT     NOT NULL,
    ispermitido     CHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT fk_permissao_tela    FOREIGN KEY (codigotela)    REFERENCES public.tela (codigotela),
    CONSTRAINT fk_permissao_usuario FOREIGN KEY (codigousuario) REFERENCES public.usuario (cdusuario)
);

CREATE TABLE IF NOT EXISTS public.solicitacao (
    codigosolicitacao SERIAL  PRIMARY KEY,
    codigousuarioadm  INT     NOT NULL,
    codigousuariosolic INT    NOT NULL,
    codigotela        INT     NOT NULL,
    isativo           CHAR(1) NOT NULL DEFAULT 'S',
    data              DATE    NOT NULL,
    codigoloja        INT     NOT NULL,
    status            CHAR(1) NOT NULL DEFAULT 'A',
    CONSTRAINT fk_solicitacao_usu_adm  FOREIGN KEY (codigousuarioadm)   REFERENCES public.usuario (cdusuario),
    CONSTRAINT fk_solicitacao_usu_solic FOREIGN KEY (codigousuariosolic) REFERENCES public.usuario (cdusuario),
    CONSTRAINT fk_solicitacao_tela      FOREIGN KEY (codigotela)         REFERENCES public.tela (codigotela),
    CONSTRAINT fk_solicitacao_loja      FOREIGN KEY (codigoloja)         REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.notadecompra (
    codigonota        SERIAL        PRIMARY KEY,
    numeronota        VARCHAR(20)   NOT NULL,
    codigofornecedor  INT           NOT NULL,
    data              DATE          NOT NULL,
    valor             NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcheio        NUMERIC(15,2) NOT NULL DEFAULT 0,
    valordesconto     NUMERIC(15,2) NOT NULL DEFAULT 0,
    cdformapagamento  INT           NOT NULL,
    codigoloja        INT           NOT NULL,
    status            CHAR(1)       NOT NULL DEFAULT 'A',
    CONSTRAINT fk_nota_fornecedor    FOREIGN KEY (codigofornecedor) REFERENCES public.fornecedor (codigofornecedor),
    CONSTRAINT fk_nota_formapagamento FOREIGN KEY (cdformapagamento) REFERENCES public.formapagamento (cdformapagamento),
    CONSTRAINT fk_nota_loja          FOREIGN KEY (codigoloja)       REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.ordemservico (
    codigoordem      SERIAL        PRIMARY KEY,
    cdcliente        INT           NOT NULL,
    cdfuncionario    INT           NOT NULL,
    codigoloja       INT           NOT NULL,
    data             DATE          NOT NULL,
    valor            NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcheio       NUMERIC(15,2) NOT NULL DEFAULT 0,
    valordesconto    NUMERIC(15,2) NOT NULL DEFAULT 0,
    status           CHAR(1)       NOT NULL DEFAULT 'A',
    cdformapagamento INT           NOT NULL,
    CONSTRAINT fk_os_cliente         FOREIGN KEY (cdcliente)        REFERENCES public.cliente (codigocliente),
    CONSTRAINT fk_os_funcionario     FOREIGN KEY (cdfuncionario)    REFERENCES public.funcionario (cdfuncionario),
    CONSTRAINT fk_os_loja            FOREIGN KEY (codigoloja)       REFERENCES public.loja (codigoloja),
    CONSTRAINT fk_os_formapagamento  FOREIGN KEY (cdformapagamento) REFERENCES public.formapagamento (cdformapagamento)
);

CREATE TABLE IF NOT EXISTS public.transacao (
    cdtransacao       SERIAL        PRIMARY KEY,
    usuario           VARCHAR(100)  NOT NULL,
    valor             NUMERIC(15,2) NOT NULL DEFAULT 0,
    data              TIMESTAMP     NOT NULL DEFAULT NOW(),
    isefetivada       CHAR(1)       NOT NULL DEFAULT 'S',
    numeroconta       VARCHAR(20)   NOT NULL,
    obs               VARCHAR(255),
    codcategoria      INT,
    codsubcategoria   INT,
    codigofatura      INT,
    codigoloja        INT,
    tipo              VARCHAR(30),
    CONSTRAINT fk_transacao_conta         FOREIGN KEY (numeroconta)    REFERENCES public.conta (numero),
    CONSTRAINT fk_transacao_categoria     FOREIGN KEY (codcategoria)   REFERENCES public.categoria (cdcategoria),
    CONSTRAINT fk_transacao_subcategoria  FOREIGN KEY (codsubcategoria) REFERENCES public.subcategoria (cdsubcategoria),
    CONSTRAINT fk_transacao_fatura        FOREIGN KEY (codigofatura)   REFERENCES public.fatura (codigofatura),
    CONSTRAINT fk_transacao_loja          FOREIGN KEY (codigoloja)     REFERENCES public.loja (codigoloja)
);

-- ============================================================
-- Tabelas com dependência de 3º nível
-- ============================================================

CREATE TABLE IF NOT EXISTS public.produtocomprado (
    codigoprodutocomprado SERIAL        PRIMARY KEY,
    codigonotacompra      INT           NOT NULL,
    data                  DATE          NOT NULL,
    valor                 NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcheio            NUMERIC(15,2) NOT NULL DEFAULT 0,
    valordesconto         NUMERIC(15,2) NOT NULL DEFAULT 0,
    codigoloja            INT           NOT NULL,
    codigoproduto         INT           NOT NULL,
    quantidade            INT           NOT NULL DEFAULT 1,
    CONSTRAINT fk_produtocomprado_nota    FOREIGN KEY (codigonotacompra) REFERENCES public.notadecompra (codigonota),
    CONSTRAINT fk_produtocomprado_produto FOREIGN KEY (codigoproduto)    REFERENCES public.produto (cdproduto),
    CONSTRAINT fk_produtocomprado_loja    FOREIGN KEY (codigoloja)       REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.servicosprestados (
    codigoservicoprestado SERIAL        PRIMARY KEY,
    codigoordemservico    INT           NOT NULL,
    codigoloja            INT           NOT NULL,
    data                  DATE          NOT NULL,
    valor                 NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcheio            NUMERIC(15,2) NOT NULL DEFAULT 0,
    valordesconto         NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcomissao         NUMERIC(15,2) NOT NULL DEFAULT 0,
    codigoservico         INT           NOT NULL,
    CONSTRAINT fk_sp_ordemservico FOREIGN KEY (codigoordemservico) REFERENCES public.ordemservico (codigoordem),
    CONSTRAINT fk_sp_servico      FOREIGN KEY (codigoservico)      REFERENCES public.servico (cdservico),
    CONSTRAINT fk_sp_loja         FOREIGN KEY (codigoloja)         REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.produtovendido (
    codigoprodutovendido SERIAL        PRIMARY KEY,
    codigoordemservico   INT           NOT NULL,
    data                 DATE          NOT NULL,
    valor                NUMERIC(15,2) NOT NULL DEFAULT 0,
    valorcheio           NUMERIC(15,2) NOT NULL DEFAULT 0,
    valordesconto        NUMERIC(15,2) NOT NULL DEFAULT 0,
    codigoproduto        INT           NOT NULL,
    valorcomissao        NUMERIC(15,2) NOT NULL DEFAULT 0,
    codigoloja           INT           NOT NULL,
    quantidade           INT           NOT NULL DEFAULT 1,
    CONSTRAINT fk_pv_ordemservico FOREIGN KEY (codigoordemservico) REFERENCES public.ordemservico (codigoordem),
    CONSTRAINT fk_pv_produto      FOREIGN KEY (codigoproduto)      REFERENCES public.produto (cdproduto),
    CONSTRAINT fk_pv_loja         FOREIGN KEY (codigoloja)         REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.notificacoes (
    codigonotificacao  SERIAL       PRIMARY KEY,
    codigosolicitacao  INT          NOT NULL,
    codigousuario      INT          NOT NULL,
    descricao          VARCHAR(255),
    isativo            CHAR(1)      NOT NULL DEFAULT 'S',
    codigoloja         INT          NOT NULL,
    data               DATE         NOT NULL,
    CONSTRAINT fk_notif_solicitacao FOREIGN KEY (codigosolicitacao) REFERENCES public.solicitacao (codigosolicitacao),
    CONSTRAINT fk_notif_usuario     FOREIGN KEY (codigousuario)     REFERENCES public.usuario (cdusuario),
    CONSTRAINT fk_notif_loja        FOREIGN KEY (codigoloja)        REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.endereco (
    cdendereco      SERIAL       PRIMARY KEY,
    cdfuncionario   INT,
    cdfornecedor    INT,
    descricao       VARCHAR(255) NOT NULL,
    cdcliente       INT,
    cdloja          INT,
    tipo            VARCHAR(20),
    isativo         CHAR(1)      NOT NULL DEFAULT 'S',
    CONSTRAINT fk_endereco_funcionario FOREIGN KEY (cdfuncionario) REFERENCES public.funcionario (cdfuncionario),
    CONSTRAINT fk_endereco_fornecedor  FOREIGN KEY (cdfornecedor)  REFERENCES public.fornecedor (codigofornecedor),
    CONSTRAINT fk_endereco_cliente     FOREIGN KEY (cdcliente)     REFERENCES public.cliente (codigocliente),
    CONSTRAINT fk_endereco_loja        FOREIGN KEY (cdloja)        REFERENCES public.loja (codigoloja)
);

CREATE TABLE IF NOT EXISTS public.telefone (
    cdtelefone    SERIAL      PRIMARY KEY,
    cdfuncionario INT,
    cdcliente     INT,
    cdfornecedor  INT,
    cdloja        INT,
    numero        VARCHAR(20) NOT NULL,
    tipo          VARCHAR(20),
    isativo       CHAR(1)     NOT NULL DEFAULT 'S',
    CONSTRAINT fk_telefone_funcionario FOREIGN KEY (cdfuncionario) REFERENCES public.funcionario (cdfuncionario),
    CONSTRAINT fk_telefone_cliente     FOREIGN KEY (cdcliente)     REFERENCES public.cliente (codigocliente),
    CONSTRAINT fk_telefone_fornecedor  FOREIGN KEY (cdfornecedor)  REFERENCES public.fornecedor (codigofornecedor),
    CONSTRAINT fk_telefone_loja        FOREIGN KEY (cdloja)        REFERENCES public.loja (codigoloja)
);

-- ============================================================
-- Índices de performance
-- ============================================================

CREATE INDEX IF NOT EXISTS idx_agendamento_data       ON public.agendamento (data);
CREATE INDEX IF NOT EXISTS idx_agendamento_loja       ON public.agendamento (codigoloja);
CREATE INDEX IF NOT EXISTS idx_ordemservico_data      ON public.ordemservico (data);
CREATE INDEX IF NOT EXISTS idx_ordemservico_loja      ON public.ordemservico (codigoloja);
CREATE INDEX IF NOT EXISTS idx_transacao_numeroconta  ON public.transacao (numeroconta);
CREATE INDEX IF NOT EXISTS idx_transacao_data         ON public.transacao (data);
CREATE INDEX IF NOT EXISTS idx_notadecompra_data      ON public.notadecompra (data);
CREATE INDEX IF NOT EXISTS idx_fatura_cartao          ON public.fatura (codigocartao);
CREATE INDEX IF NOT EXISTS idx_notificacoes_usuario   ON public.notificacoes (codigousuario);
CREATE INDEX IF NOT EXISTS idx_permissaotela_usuario  ON public.permissaotela (codigousuario);
