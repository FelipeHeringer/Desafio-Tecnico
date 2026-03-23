# 🏠 Gestão de Casas

Sistema web para cadastro e gerenciamento de imóveis por proprietários, desenvolvido com **Java 21**, **JSF + PrimeFaces**, **Hibernate**, **CDI (Weld)** e banco de dados **PostgreSQL**, rodando em servidor embarcado **Tomcat 7**.

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

| Ferramenta | Versão mínima | Download |
|---|---|---|
| **Java JDK** | 21 | [https://adoptium.net](https://adoptium.net) |
| **Apache Maven** | 3.8+ | [https://maven.apache.org](https://maven.apache.org) |
| **PostgreSQL** | 13+ | [https://www.postgresql.org/download](https://www.postgresql.org/download) |
| **Git** | qualquer | [https://git-scm.com](https://git-scm.com) |

> 💡 Para verificar se já os tem instalados, execute no terminal:
> ```bash
> java -version
> mvn -version
> psql --version
> git --version
> ```

---

## 🗄️ Configuração do Banco de Dados

### 1. Criar o banco de dados no PostgreSQL

Acesse o PostgreSQL pelo terminal (ou pgAdmin) e execute:

```sql
CREATE DATABASE desafio;
```

> O projeto está configurado com as seguintes credenciais padrão. **Se as suas forem diferentes, siga o passo abaixo para alterá-las.**

| Parâmetro | Valor padrão |
|---|---|
| Host | `localhost` |
| Porta | `5432` |
| Banco | `desafio` |
| Usuário | `postgres` |
| Senha | `PoiLkjmnb25@` |

### 2. (Opcional) Alterar as credenciais do banco

Caso seu PostgreSQL use usuário ou senha diferentes, edite os dois arquivos abaixo antes de continuar:

**`desafio/pom.xml`** — bloco do plugin Flyway:
```xml
<configuration>
    <url>jdbc:postgresql://localhost:5432/desafio</url>
    <user>SEU_USUARIO</user>
    <password>SUA_SENHA</password>
    ...
</configuration>
```

**`desafio/src/main/resources/hibernate.cfg.xml`**:
```xml
<property name="hibernate.connection.username">SEU_USUARIO</property>
<property name="hibernate.connection.password">SUA_SENHA</property>
```

---

## 🚀 Passo a Passo para Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/FelipeHeringer/Desafio-Tecnico.git
```

> Substitua a URL pelo endereço real do repositório.

---

### 2. Abrir a pasta no editor de código

Abra a pasta raiz do projeto clonado no seu editor preferido (VS Code, IntelliJ, etc.).

A estrutura esperada é:
```
nome-do-repo/
└── desafio/
    ├── pom.xml
    ├── .mvn/
    └── src/
```

---

### 3. Entrar na pasta do projeto pelo terminal

```bash
cd desafio
```

> ⚠️ Todos os comandos Maven a seguir devem ser executados **dentro da pasta `desafio`**, onde está o `pom.xml`.

---

### 4. Rodar as migrations do banco de dados

Este comando cria todas as tabelas e popula os dados iniciais (estados e cidades brasileiras) via Flyway:

```bash
mvn flyway:migrate
```

O que será executado:
- `V1` — Criação das tabelas (`casas`, `propietarios`, `enderecos`, `cep`, `estados`, `cidades`)
- `V2` — Adição das chaves estrangeiras
- `V3` — Adição da coluna `uf` na tabela `estados`
- `V4` — Inserção dos 27 estados brasileiros
- `V5` — Inserção das cidades brasileiras

✅ Saída esperada ao final:
```
[INFO] Successfully applied 5 migrations to schema "public"
[INFO] BUILD SUCCESS
```

---

### 5. Compilar e subir o servidor Tomcat

```bash
mvn clean install tomcat7:run
```

Este comando irá:
1. Limpar builds anteriores (`clean`)
2. Compilar e empacotar o projeto (`install`)
3. Subir o servidor Tomcat 7 embarcado na porta **8080** (`tomcat7:run`)

✅ Saída esperada ao final:
```
[INFO] Starting Tomcat Server
...
INFO: Starting ProtocolHandler ["http-bio-8080"]
```

---

### 6. Acessar a aplicação

Com o servidor no ar, abra o navegador e acesse:

```
http://localhost:8080
```

A página inicial será o formulário de **cadastro de proprietário**.

---

## 🗺️ Fluxo da Aplicação

```
Register.xhtml       →   Cadastro do proprietário (nome, CPF, email, telefone, endereço)
Login.xhtml          →   Login com nome + CPF
GestaoCasas.xhtml    →   Lista de imóveis do proprietário logado
CadastroCasa.xhtml   →   Cadastro de novo imóvel
EdicaoCasa.xhtml     →   Edição de imóvel existente
```

---

## 🛠️ Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Frontend | JSF 2.2 + PrimeFaces 6.0 |
| Injeção de dependência | CDI via Weld Servlet 2.4 |
| ORM | Hibernate 6.5 + Jakarta Persistence 3.1 |
| Banco de dados | PostgreSQL 42.7 |
| Migrations | Flyway 10 |
| Servidor | Tomcat 7 (plugin Maven embarcado) |
| Build | Apache Maven |

---

## ⚠️ Problemas Comuns

**Erro de conexão com o banco:**
Verifique se o PostgreSQL está rodando e se as credenciais em `pom.xml` e `hibernate.cfg.xml` estão corretas.

**Porta 8080 em uso:**
Encerre o processo que está usando a porta ou altere a porta no `pom.xml`:
```xml
<configuration>
    <port>8081</port>
    ...
</configuration>
```

**Erro nas migrations (`already applied`):**
Se precisar recriar o banco do zero:
```bash
mvn flyway:clean
mvn flyway:migrate
```
> ⚠️ `flyway:clean` apaga todos os dados. Use apenas em ambiente de desenvolvimento.

**Erro de compilação Java:**
Confirme que a variável de ambiente `JAVA_HOME` aponta para o JDK 21.
```bash
echo $JAVA_HOME   # Linux/macOS
echo %JAVA_HOME%  # Windows
```