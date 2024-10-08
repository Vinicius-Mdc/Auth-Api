# Auth API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Flyway](https://img.shields.io/badge/flyway-red?style=for-the-badge&logo=flyway)

Este projeto é uma API desenvolvida usando Java, Spring Boot, Spring Security, PostgresSQL, JWT e Flyway.

## Índice

- [Instalação](#instalação)
- [Inicialização](#inicialização)
- [Endpoints](#endpoints)
- [Autenticação](#autenticação)
- [Banco de dados](#banco-de-dados)

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/Vinicius-Mdc/AuthApi.git
```

2. Instale as dependências usando o Maven.

3. Instale o [Docker](https://www.docker.com/)
   
4. Inicialize o banco de dados:

```bash
docker-compose up -d
```

## Inicialização

1. Inicialize a aplicação com Maven usando o profile 'developer'
2. A API estará disponível em: http://localhost:8080/api
3. O Swagger estárá disponível em: http://localhost:8080/api/swagger-ui/index.html


## Endpoints
A API possui os seguintes endpoints:

```markdown
GET /usuario/get/{id} - Obtém o usuário referente ao id informado (requer acesso MANAGER).

POST /usuario/criar - Cria um novo usuário (requer acesso ADMIN).

POST /auth/login - Realiza o login na aplicação.

POST /auth/refresh-token - Gera um novo access token a partir de um refresh token.
```

## Autenticação
A API usa o Spring Security para autenticação e autorização, onde o fluxo de autorização é dinâmico e gerenciado a partir de registros no banco de dados.

## Banco de dados
O projeto usa o [PostgresSQL](https://www.postgresql.org/) como banco de dados, com as migrações(migrations) sendo gerenciadas pelo Flyway.
