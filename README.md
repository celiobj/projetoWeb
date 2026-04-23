# 📦 ControleFácil Web - Sistema de Gestão de Produtos

Um sistema web moderno e intuitivo para gerenciar produtos e controlar estoque, desenvolvido com Spring Boot 4.0 e Thymeleaf.

## 🚀 Características Principais

- ✅ **Gestão de Produtos**: Criar, editar, visualizar e deletar produtos
- ✅ **Controle de Estoque**: Monitorar quantidade disponível em tempo real
- ✅ **Busca Avançada**: Filtrar produtos por nome, preço e disponibilidade
- ✅ **Interface Responsiva**: Design moderno com Bootstrap 5
- ✅ **Validação de Dados**: Validação em frontend e backend
- ✅ **Banco de Dados**: Suporte para Access, H2 e outros bancos

---

## 📋 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/controlefacilWeb/demo/
│   │   ├── models/
│   │   │   └── Produto.java              # Entidade JPA
│   │   ├── repositories/
│   │   │   └── ProdutoRepository.java    # Operações CRUD
│   │   ├── services/
│   │   │   └── ProdutoService.java       # Lógica de negócio
│   │   ├── controllers/
│   │   │   ├── ProdutoController.java    # Endpoints de produtos
│   │   │   └── IndexController.java      # Página inicial
│   │   ├── DemoApplication.java          # Classe main
│   │   └── ServletInitializer.java       # Inicializador WAR
│   └── resources/
│       ├── application.properties         # Configurações
│       └── templates/
│           ├── index.html                # Página inicial
│           └── produtos/
│               ├── lista.html            # Listagem de produtos
│               ├── formulario.html       # Formulário criar/editar
│               └── detalhe.html          # Detalhes do produto
└── pom.xml                                # Dependências Maven
```

---

## 📦 Dependências Principais

```xml
<!-- Spring Boot Web MVC e Thymeleaf -->
- spring-boot-starter-webmvc
- spring-boot-starter-thymeleaf

<!-- Persistência -->
- spring-boot-starter-data-jpa
- ucanaccess (para banco Access)
- h2 (banco em memória/arquivo)

<!-- Validação -->
- spring-boot-starter-validation

<!-- Utilidades -->
- projectlombok (reduz boilerplate)

<!-- Testing -->
- spring-boot-starter-test
```

---

## 🔧 Configuração do Banco de Dados

### Opção 1: H2 (Recomendado para Desenvolvimento)

O projeto vem configurado com **H2** por padrão. Nenhuma configuração adicional é necessária.

```properties
spring.datasource.url=jdbc:h2:mem:controledb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
```

**Vantagens:**
- ✅ Nenhuma instalação necessária
- ✅ Tables criadas automaticamente
- ✅ Dados em memória (reiniciado ao parar app)

### Opção 2: Microsoft Access

Para usar banco Access (.accdb ou .mdb):

1. **Editar `application.properties`:**

```properties
# Descomente as linhas do UcanAccess:
spring.datasource.url=jdbc:ucanaccess://C:/dados/produtos.accdb
spring.datasource.driverClassName=net.ucanaccess.jdbc.UcanaccessDriver
spring.datasource.username=
spring.datasource.password=

# Mude o dialect do Hibernate:
spring.jpa.database-platform=org.hibernate.dialect.HSQLDialect
```

2. **Criar arquivo Access:**
   - Abra Microsoft Access
   - Crie banco novo com tabela `produtos` (ou deixe o Hibernate criar)
   - Colunas necessárias:
     - `id` (PRIMARYKEY, autonúmero)
     - `nome` (Texto, 255 chars)
     - `descricao` (Memo)
     - `preco` (Número decimal)
     - `quantidade` (Inteiro)
     - `data_criacao` (Timestamp)
     - `data_atualizacao` (Timestamp)

---

## 🏃 Como Executar

### Pré-requisitos

- ☕ Java 21+
- 📦 Maven 3.6+

### Passos

1. **Clone e entre na pasta do projeto:**
```bash
cd c:\repo\projetoWeb
```

2. **Limpe dependências antigas:**
```bash
mvn clean
```

3. **Instale dependências:**
```bash
mvn install
```

4. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

5. **Acesse no navegador:**
```
http://localhost:8080
```

---

## 📚 Endpoints Disponíveis

### Página Principal
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/` | Página inicial |

### Produtos
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/produtos` | Lista todos os produtos |
| GET | `/produtos/novo` | Formulário novo produto |
| POST | `/produtos` | Criar novo produto |
| GET | `/produtos/{id}` | Ver detalhes do produto |
| GET | `/produtos/{id}/editar` | Formulário editar produto |
| POST | `/produtos/{id}` | Atualizar produto |
| POST | `/produtos/{id}/deletar` | Deletar produto |
| GET | `/produtos/buscar?termo=...` | Buscar por nome |
| GET | `/produtos/comestoque` | Listar com estoque |

---

## 🎨 Estrutura de Camadas

```
┌─────────────────────────────┐
│        Thymeleaf View        │  Templates HTML
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│      Controller              │  Processa requisições HTTP
│    (ProdutoController)       │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│      Service                 │  Lógica de negócio
│     (ProdutoService)         │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│      Repository              │  Acesso ao BD
│   (ProdutoRepository)        │
└──────────────┬──────────────┘
               │
┌──────────────▼──────────────┐
│       Database               │  H2, Access, etc
│      (Spring Data JPA)       │
└─────────────────────────────┘
```

---

## 📝 Entidade Produto

```java
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nome;           // Nome do produto
    
    @NotBlank
    private String descricao;      // Descrição detalhada
    
    @NotNull
    @DecimalMin("0.0")
    private Double preco;          // Preço em R$
    
    @NotNull
    @Min(0)
    private Integer quantidade;    // Qtd em estoque
    
    private Long dataCriacao;      // Criado em
    private Long dataAtualizacao;  // Atualizado em
}
```

---

## 🔍 Validações

O projeto implementa validações em **dois níveis**:

### Backend (Annotations - @Valid)
```java
@NotBlank(message = "Nome é obrigatório")
@NotNull(message = "Preço é obrigatório")
@DecimalMin(value = "0.0", inclusive = false)
@Min(value = 0)
```

### Frontend (HTML5)
```html
<input type="text" required />
<input type="number" min="0" step="0.01" />
<textarea required></textarea>
```

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|------------|--------|----------|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.5 | Framework Web |
| Spring Data JPA | - | ORM e Persistência |
| Hibernate | - | Mapeamento objeto-relacional |
| Thymeleaf | - | Template Engine |
| Bootstrap | 5.3.0 | CSS Framework |
| Maven | - | Gerenciador de dependências |
| Lombok | - | Reduzir boilerplate |
| H2 Database | - | Banco de dados |
| UcanAccess | 5.1.1 | Driver Access |

---

## 📖 Exemplos de Uso

### Criar um Produto

```java
Produto produto = new Produto();
produto.setNome("Notebook");
produto.setDescricao("Notebook Dell Inspiron 15");
produto.setPreco(3500.00);
produto.setQuantidade(10);

produtoService.criar(produto);
```

### Buscar Produtos

```java
// Todos
List<Produto> todos = produtoService.obterTodos();

// Por nome
List<Produto> notebooks = produtoService.buscarPorNome("notebook");

// Com estoque
List<Produto> comEstoque = produtoService.obterComEstoque();

// Por preço
List<Produto> baratos = produtoService.buscarPorPrecoPorIntervalo(
    100.0, 5000.0
);
```

### Atualizar Estoque

```java
// Reduzir quantidade após venda
produtoService.reduzirEstoque(1L, 2);
```

---

## 🐛 Debug e Logs

Para ativar logs de SQL (em `application.properties`):

```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🚀 Deploy

### Build WAR para Produção

```bash
mvn clean package -DskipTests
```

Arquivo gerado: `target/demo-0.0.1-SNAPSHOT.war`

Deploy em Tomcat:
1. Copie o `.war` para `$TOMCAT_HOME/webapps/`
2. Reinicie Tomcat
3. Acesse: `http://localhost:8080/demo`

---

## 📌 Próximas Melhorias

- [ ] Autenticação de usuários
- [ ] Dashboard com gráficos
- [ ] Relatórios em PDF
- [ ] Integração com APIs externas
- [ ] Paginação em listagens
- [ ] Filtros avançados
- [ ] Exportar para Excel
- [ ] Backup automático

---

## 📝 Licença

Projeto desenvolvido para fins educacionais.

---

## 👨‍💻 Suporte

Documentação Spring Boot: https://spring.io/projects/spring-boot
Documentação Thymeleaf: https://www.thymeleaf.org/
Documentação Bootstrap: https://getbootstrap.com/

---

**Última atualização:** Abril 2024
**Versão:** 1.0.0
