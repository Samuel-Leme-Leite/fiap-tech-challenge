# Tech Challenge - Sistema de Gestão de Restaurantes

## Sobre o Projeto

Sistema de backend robusto desenvolvido em **Spring Boot** para gerenciamento de usuários de um grupo de restaurantes. Este projeto faz parte da **Fase 1** do Tech Challenge, focando na criação de uma base sólida para futuras expansões.

## Arquitetura

- **Framework:** Spring Boot 3.5.3
- **Linguagem:** Java 21
- **Banco de Dados:** PostgreSQL 16
- **Containerização:** Docker + Docker Compose
- **Arquitetura:** Híbrida (DDD + Clean Architecture)
- **Documentação:** OpenAPI/Swagger

## Como Executar

### Pré-requisitos
- Docker
- Docker Compose
- Java 21 (para desenvolvimento)
- Maven (para build local)

### Executando com Docker
```bash
# Clone o repositório
git clone https://github.com/Samuel-Leme-Leite/fiap-tech-challenge.git
cd tech-challenge-restaurantes

# Build e execução
./mvnw clean package -DskipTests
docker-compose up --build
```

### Acessos
- **API:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Banco de Dados:** localhost:5432
    - Database: `tech_challenge`
    - User: `techuser`
    - Password: `techpass`

## Endpoints da API

### Autenticação
- `POST /auth/login` - Login de usuário
- `POST /auth/token` - Autenticação de sistema (client credentials)

### Gestão de Usuários
- `POST /users` - Cadastrar usuário
- `GET /users/{id}` - Buscar usuário por ID
- `PUT /users/{id}` - Atualizar dados do usuário
- `PATCH /users/{id}/password` - Alterar senha
- `PATCH /users/{id}/activate` - Ativar usuário (soft delete)
- `DELETE /users/{id}` - Desativar usuário (soft delete)

## Testando a API

### Collection Postman
Importe a collection `postman_collection.json` (incluída no projeto) para testar todos os endpoints.

### Exemplo de Uso
```bash
# 1. Cadastrar usuário
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@exemplo.com",
    "userName": "joao.silva",
    "password": "MinhaSenh@123",
    "profile": "CUSTOMER"
  }'

# 2. Fazer login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "joao.silva",
    "password": "MinhaSenh@123"
  }'
```

## Estrutura do Projeto

```
src/main/java/com/techchallenge/
├── domain/                 # Lógica de negócio
│   ├── user/              # Domínio de usuários
│   │   ├── entity/        # Entidades
│   │   ├── repository/    # Repositórios
│   │   ├── service/       # Serviços
│   │   ├── validator/     # Validadores
│   │   └── exception/     # Exceções customizadas
│   ├── auth/              # Domínio de autenticação
|   |   ├── enums/        # Enumerações
|   |   ├── exception/     # Exceções de autenticação
|   |   ├── service/       # Serviços de autenticação
|   |   └── validator/     # Validadores de autenticação
│   └── shared/            # Componentes compartilhados
|       ├── entity/        # Entidades compartilhadas
|       └── enums/         # Enumerações compartilhadas
├── infrastructure/        # Configurações técnicas
│   ├── security/          # Configurações de segurança
│   └── config/            # Configurações de segurança
├── application/           # Camada de apresentação
│   ├── controller/        # Controllers REST
│   ├── dto/              # DTOs (Request/Response)
|   |   ├── response/      # Respostas DTOs
│   |   └── request/       # Requisições DTOs
│   ├── mapper/           # Mapeadores
│   └── exception/        # Exception handlers
└── shared/               # Utilitários globais
```

## Segurança

- **Autenticação JWT** implementada
- **Validação robusta** de senhas
- **Soft delete** para usuários
- **Exception handling** padronizado
- **Validações de entrada** em todas as camadas

## Tecnologias Utilizadas

- **Spring Boot** (Web, Data JPA, Security, Validation)
- **PostgreSQL** - Banco de dados relacional
- **JWT** - Autenticação stateless
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação automática
- **Docker** - Containerização
- **Maven** - Gerenciamento de dependências

## Tipos de Usuário

- **CUSTOMER** - Cliente do restaurante
- **ADMIN** - Administrador do sistema
- **EMPLOYEE** - Funcionário (preparado para futuras fases)

## Funcionalidades Implementadas

### Gestão de Usuários
- Cadastro com validações robustas
- Atualização de dados pessoais
- Troca de senha segura
- Soft delete (desativação)
- Auditoria automática (created_at, updated_at)

### Autenticação
- Login com username/password
- Geração de tokens JWT
- Autenticação de sistemas (client credentials)
- Proteção de endpoints

### Validações
- Senha forte obrigatória
- Email válido
- Username único
- Endereço completo

## Notas de Desenvolvimento

Este projeto foi desenvolvido seguindo:
- **SOLID principles**
- **Clean Architecture**
- **Domain-Driven Design**
- **API-first approach**
- **Containerização completa**
- **Documentação automática**

## Contribuição

Projeto desenvolvido como parte do Tech Challenge da pós-graduação.

---

