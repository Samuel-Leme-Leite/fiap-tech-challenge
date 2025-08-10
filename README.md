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
- **Segurança:** JWT Authentication
- **Padrões:** SOLID, Clean Code, API-First


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
cd fiap-tech-challenge

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

## Autenticação

O sistema implementa **dois tipos de autenticação JWT**:

### 1. Autenticação de Cliente (Client Credentials)
Para sistemas e aplicações que precisam acessar a API:

```bash
curl -X POST http://localhost:8080/auth/token \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "tech-challenge-client",
    "clientSecret": "tech-challenge-secret",
    "grantType": "client_credentials"
  }'
```

**Resposta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

### 2. Autenticação de Usuário (User Login)
Para usuários finais do sistema:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao.silva",
    "password": "Senha@123"
  }'
```

**Resposta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@exemplo.com",
    "userName": "joao.silva",
    "profile": "CUSTOMER"
  }
}
```

### Usando o Token de Autenticação

Após obter o token, inclua-o no header `Authorization` de todas as requisições protegidas:

```bash
curl -X GET http://localhost:8080/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```



## Endpoints da API

### Autenticação
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/auth/login` | Login de usuário | ❌ Não |
| `POST` | `/auth/token` | Autenticação de sistema (client credentials) | ❌ Não |

###  Gestão de Usuários
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/users` | Cadastrar usuário | ✅ Bearer Token |
| `GET` | `/users/{id}` | Buscar usuário por ID | ✅ Bearer Token |
| `PUT` | `/users/{id}` | Atualizar dados do usuário | ✅ Bearer Token |
| `PATCH` | `/users/{id}/password` | Alterar senha | ✅ Bearer Token |
| `PATCH` | `/users/{id}/activate` | Ativar usuário | ✅ Bearer Token |
| `DELETE` | `/users/{id}` | Desativar usuário (soft delete) | ✅ Bearer Token |

## Testando a API

### Collection Postman
Importe a collection `Fiap Tech Challenge.postman_collection.json` (incluída no projeto) para testar todos os endpoints com exemplos pré-configurados.

### Fluxo Completo de Teste

#### 1. Obter Token de Autenticação
```bash
# Autenticação de sistema
curl -X POST http://localhost:8080/auth/token \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "tech-challenge-client",
    "clientSecret": "tech-challenge-secret",
    "grantType": "client_credentials"
  }'
```

#### 2. Cadastrar Usuário
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@exemplo.com",
    "userName": "joao.silva",
    "password": "Senha@123",
    "address": {
      "street": "Rua das Flores",
      "addressNumber": "123",
      "complement": "Apto 45",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567",
      "country": "Brasil",
      "neighborhood": "Centro"
    },
    "profile": "CUSTOMER"
  }'
```

#### 3. Fazer Login do Usuário
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao.silva",
    "password": "Senha@123"
  }'
```

#### 4. Buscar Usuário
```bash
curl -X GET http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### 5. Atualizar Usuário
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "firstName": "João Carlos",
    "lastName": "Silva Santos",
    "email": "joao.carlos@exemplo.com",
    "userName": "joao.silva",
    "address": {
      "street": "Rua das Palmeiras",
      "addressNumber": "456",
      "complement": "Casa",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567",
      "country": "Brasil",
      "neighborhood": "Jardins"
    },
    "profile": "CUSTOMER"
  }'
```

#### 6. Alterar Senha
```bash
curl -X PATCH http://localhost:8080/users/1/password \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "oldPassword": "Senha@123",
    "newPassword": "NovaSenha@456"
  }'
```

## Estrutura do Projeto (DDD)

```
src/main/java/com/techchallenge/
├── domain/                   # Camada de Domínio (Lógica de Negócio)
│   ├── user/                 # Contexto Delimitado: Usuários
│   │   ├── entity/           # Entidades do domínio
│   │   │   └── User.java     # Agregado raiz
│   │   ├── repository/       # Contratos de repositório
│   │   ├── service/          # Serviços de domínio
│   │   ├── validator/        # Validadores de negócio
│   │   └── exception/        # Exceções específicas do domínio
│   ├── auth/                 # Contexto Delimitado: Autenticação
│   │   ├── enums/           # Enumerações (TokenType)
│   │   ├── exception/       # Exceções de autenticação
│   │   ├── service/         # Serviços de autenticação
│   │   └── validator/       # Validadores de autenticação
│   └── shared/              # Elementos compartilhados
│       ├── entity/          # Entidades base (BaseEntity, Address)
│       └── enums/           # Enumerações compartilhadas (UserRole)
├── infrastructure/          # Camada de Infraestrutura
│   ├── security/            # Implementações de segurança JWT
│   └── config/              # Configurações do Spring Security
├── application/             # Camada de Aplicação (Interface)
│   ├── controller/          # Controllers REST
│   ├── dto/                 # Objetos de Transferência de Dados
│   │   ├── request/         # DTOs de entrada
│   │   └── response/        # DTOs de saída
│   ├── mapper/              # Mapeadores entre DTOs e Entidades
│   └── exception/           # Tratamento global de exceções
└── FiapTechChallengeApplication.java  # Classe principal
```

### Padrões DDD Implementados

- **Agregados:** User como agregado raiz
- **Entidades:** User, Address como entidades com identidade
- **Objetos de Valor:** Address (quando usado como componente)
- **Repositórios:** UserRepository para persistência
- **Serviços de Domínio:** UserService, AuthService
- **Eventos de Domínio:** Preparado para implementação futura
- **Contextos Delimitados:** User, Auth, Shared

## Segurança

- **Autenticação JWT** com dois fluxos (Client Credentials e User Login)
- **Validação robusta** de senhas (maiúscula, minúscula, número, caractere especial)
- **Soft delete** para usuários
- **Exception handling** padronizado
- **Validações de entrada** em todas as camadas

## Tecnologias Utilizadas

### Core
- **Spring Boot 3.5.3** (Web, Data JPA, Security, Validation)
- **Java 21** com recursos modernos
- **PostgreSQL 16** - Banco de dados relacional

### Segurança
- **Spring Security** - Framework de segurança
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **BCrypt** - Hash de senhas

### Desenvolvimento
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação automática
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização

### Qualidade
- **Bean Validation** - Validações declarativas
- **Global Exception Handler** - Tratamento centralizado de erros
- **Audit Fields** - Rastreamento automático de criação/atualização

## Tipos de Usuário

| Perfil | Descrição | Permissões |
|--------|-----------|------------|
| **CUSTOMER** | Cliente do restaurante | Acesso básico ao sistema |
| **ADMIN** | Administrador do sistema | Acesso completo |
| **EMPLOYEE** | Funcionário | Preparado para futuras fases |

### Gestão de Usuários
- Cadastro com validações robustas
- Atualização de dados pessoais
- Troca de senha segura
- Soft delete (desativação/ativação)
- Auditoria automática (created_at, updated_at)
- Validação de unicidade de username e email

### Autenticação
- Login com username/password
- Geração de tokens JWT
- Autenticação de sistemas (client credentials)
- Proteção de endpoints
- Refresh token preparado para implementação

### Validações
- Senha forte obrigatória (8+ chars, maiúscula, minúscula, número, especial)
- Email válido e único
- Username único e formato válido
- Endereço completo obrigatório
- Validação de perfis de usuário

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
> 💡 **Dica:** Use a collection do Postman incluída no projeto para testar todos os endpoints de forma rápida e eficiente!

