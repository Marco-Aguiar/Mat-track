# MatTrack

**MatTrack** é uma plataforma full stack para gestão de treinos de Jiu-Jitsu, criada para ajudar atletas a registrarem sua evolução, acompanharem frequência de treinos, monitorarem técnicas praticadas e futuramente receberem análises inteligentes sobre desempenho.

O objetivo do projeto é construir uma aplicação com padrão profissional, utilizando boas práticas de arquitetura, autenticação, segurança, banco de dados relacional, API REST e futuramente frontend moderno com Angular.

---

## Sobre o projeto

O MatTrack nasceu como uma plataforma para praticantes de Jiu-Jitsu que desejam acompanhar sua evolução de forma estruturada.

A ideia é permitir que o atleta registre:

* Treinos realizados
* Tipo de treino
* Duração
* Intensidade
* Quantidade de rounds
* Técnicas treinadas
* Anotações técnicas
* Histórico de evolução

Com esses dados, a plataforma poderá futuramente gerar dashboards, estatísticas e recomendações com inteligência artificial.

---

## Status atual

Atualmente o projeto possui o backend funcional com:

* Autenticação com JWT
* Cadastro de usuários
* Login
* Rota protegida para recuperar dados do usuário autenticado
* CRUD de treinos
* CRUD de técnicas
* Associação entre treinos e técnicas
* Controle de acesso por usuário autenticado
* Banco de dados PostgreSQL
* Migrations com Flyway
* Segurança com Spring Security
* Documentação via Swagger/OpenAPI

---

## Stack utilizada

### Backend

* Java 17+
* Spring Boot 3.3.5
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* PostgreSQL
* Flyway
* Redis
* Docker
* Maven
* Swagger / OpenAPI

### Futuro Frontend

* Angular
* TypeScript
* Angular Router
* Angular Forms
* Angular Material ou Tailwind CSS
* Chart.js ou biblioteca similar para dashboards

---

## Funcionalidades implementadas

### Autenticação

O sistema possui autenticação baseada em JWT.

Endpoints implementados:

```http
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
```

#### Cadastro

Permite criar um novo usuário com:

* Nome
* E-mail
* Senha
* Faixa
* Peso
* Academia

Exemplo:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Marco Aguiar",
    "email": "marco@test.com",
    "password": "123456",
    "belt": "BLUE",
    "weight": 94,
    "academy": "Buchecha Team"
  }'
```

Resposta esperada:

```json
{
  "token": "jwt-token"
}
```

#### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "marco@test.com",
    "password": "123456"
  }'
```

#### Dados do usuário autenticado

```bash
curl http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## Módulo de treinos

O módulo de treinos permite que cada usuário registre seus próprios treinos.

Endpoints implementados:

```http
POST   /api/trainings
GET    /api/trainings
GET    /api/trainings/{id}
PUT    /api/trainings/{id}
DELETE /api/trainings/{id}
```

Cada treino possui:

* Data do treino
* Tipo do treino
* Duração em minutos
* Quantidade de rounds
* Intensidade de 1 a 5
* Anotações

Tipos de treino disponíveis:

```text
GI
NO_GI
OPEN_MAT
DRILLING
COMPETITION
```

Exemplo de criação de treino:

```bash
curl -X POST http://localhost:8080/api/trainings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "trainingDate": "2026-06-19",
    "type": "GI",
    "durationMinutes": 90,
    "rounds": 6,
    "intensity": 4,
    "notes": "Treino de passagem de guarda e defesa de triângulo."
  }'
```

---

## Módulo de técnicas

O módulo de técnicas permite cadastrar técnicas de Jiu-Jitsu que podem ser posteriormente vinculadas aos treinos.

Endpoints implementados:

```http
POST   /api/techniques
GET    /api/techniques
GET    /api/techniques/{id}
PUT    /api/techniques/{id}
DELETE /api/techniques/{id}
```

Cada técnica possui:

* Nome
* Categoria
* Descrição

Categorias disponíveis:

```text
TAKEDOWN
GUARD
SWEEP
PASS
SUBMISSION
ESCAPE
CONTROL
TRANSITION
DEFENSE
```

Exemplo de criação de técnica:

```bash
curl -X POST http://localhost:8080/api/techniques \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "name": "Defesa de triângulo",
    "category": "DEFENSE",
    "description": "Técnica para criar postura, proteger o braço preso e escapar antes da finalização encaixar."
  }'
```

Também é possível filtrar técnicas por categoria:

```bash
curl "http://localhost:8080/api/techniques?category=PASS" \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## Associação entre treinos e técnicas

O sistema permite vincular técnicas específicas a um treino.

Essa relação não foi implementada como um simples `ManyToMany`, mas sim como uma entidade intermediária, porque existe um campo adicional de anotação técnica.

Estrutura conceitual:

```text
Training
   ↓
TrainingTechnique
   ↓
Technique
```

Isso permite registrar observações específicas de uma técnica dentro de um treino.

Exemplo:

```text
Treino: 19/06/2026
Técnica: Defesa de triângulo
Anotação: Preciso melhorar postura e controle do braço preso.
```

Endpoints implementados:

```http
POST   /api/trainings/{trainingId}/techniques
GET    /api/trainings/{trainingId}/techniques
PUT    /api/trainings/{trainingId}/techniques/{techniqueId}
DELETE /api/trainings/{trainingId}/techniques/{techniqueId}
```

Exemplo de vínculo entre treino e técnica:

```bash
curl -X POST http://localhost:8080/api/trainings/ID_DO_TREINO/techniques \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "techniqueId": "ID_DA_TECNICA",
    "note": "Treinei essa técnica durante os drills. Preciso melhorar a postura e o timing."
  }'
```

---

## Segurança

O projeto utiliza Spring Security com autenticação baseada em JWT.

As rotas públicas são:

```http
POST /api/auth/register
POST /api/auth/login
GET  /swagger-ui/**
GET  /v3/api-docs/**
```

As demais rotas exigem token JWT no header:

```http
Authorization: Bearer SEU_TOKEN
```

Além disso, os treinos são vinculados ao usuário autenticado. O frontend não envia `userId` para criar ou consultar treinos. O backend identifica o usuário pelo JWT.

Isso evita que um usuário acesse, altere ou exclua treinos de outro usuário.

---

## Banco de dados

O projeto utiliza PostgreSQL com versionamento de banco via Flyway.

Tabelas principais:

```text
users
trainings
techniques
training_techniques
```

### users

Armazena os usuários da aplicação.

### trainings

Armazena os treinos registrados pelos usuários.

### techniques

Armazena as técnicas de Jiu-Jitsu cadastradas.

### training_techniques

Tabela intermediária que vincula treinos e técnicas, permitindo também registrar uma anotação específica para cada técnica dentro de cada treino.

---

## Como rodar o projeto

### Pré-requisitos

* Java 17+
* Maven
* Docker
* Docker Compose

### Subir banco e Redis

Na raiz do projeto:

```bash
docker compose up -d
```

### Rodar o backend

Entre na pasta do backend:

```bash
cd backend
```

Execute:

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## Swagger

Após subir a aplicação, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Organização atual do backend

Estrutura principal:

```text
src/main/java/com/mattrack
├── auth
│   ├── dto
│   ├── AuthController
│   └── AuthService
├── security
│   ├── CustomUserDetailsService
│   ├── JwtAuthenticationFilter
│   ├── JwtService
│   └── SecurityConfig
├── user
│   ├── Role
│   ├── User
│   └── UserRepository
├── training
│   ├── dto
│   ├── Training
│   ├── TrainingController
│   ├── TrainingRepository
│   ├── TrainingService
│   ├── TrainingType
│   └── TrainingNotFoundException
├── technique
│   ├── dto
│   ├── Technique
│   ├── TechniqueCategory
│   ├── TechniqueController
│   ├── TechniqueRepository
│   ├── TechniqueService
│   └── TechniqueNotFoundException
└── trainingtechnique
    ├── dto
    ├── TrainingTechnique
    ├── TrainingTechniqueController
    ├── TrainingTechniqueId
    ├── TrainingTechniqueRepository
    ├── TrainingTechniqueService
    └── TrainingTechniqueNotFoundException
```

---

## Próximos passos

### 1. Melhorar tratamento de erros

Criar um `GlobalExceptionHandler` para padronizar respostas de erro.

Exemplo esperado:

```json
{
  "timestamp": "2026-06-19T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Technique already exists",
  "path": "/api/techniques"
}
```

---

### 2. Criar dashboard backend

Criar endpoints para métricas:

```http
GET /api/dashboard/summary
GET /api/dashboard/trainings-per-week
GET /api/dashboard/techniques-by-category
GET /api/dashboard/most-trained-techniques
```

Métricas desejadas:

* Total de treinos
* Total de horas treinadas
* Treinos no mês
* Média de intensidade
* Técnicas mais treinadas
* Distribuição por categoria
* Frequência semanal

---

### 3. Criar frontend Angular

O frontend deve conter inicialmente:

* Tela de login
* Tela de cadastro
* Layout autenticado
* Dashboard
* Listagem de treinos
* Cadastro de treino
* Edição de treino
* Listagem de técnicas
* Cadastro de técnica
* Tela de detalhes do treino
* Associação de técnicas ao treino

---

### 4. Criar módulo de evolução de peso

Implementar histórico de peso do atleta.

Possíveis endpoints:

```http
POST   /api/weight-history
GET    /api/weight-history
DELETE /api/weight-history/{id}
```

Esse módulo permitirá gerar gráfico de evolução de peso ao longo do tempo.

---

### 5. Criar módulo de competições

Implementar registro de campeonatos e lutas.

Possíveis dados:

* Nome do campeonato
* Organização
* Data
* Local
* Categoria
* Modalidade
* Resultado
* Método de vitória ou derrota
* Pontuação
* Observações

---

### 6. Adicionar inteligência artificial

Futuramente o MatTrack poderá usar IA para analisar os treinos do atleta.

Exemplo de entrada:

```text
Hoje treinei passagem de guarda, tomei muito triângulo e cansei rápido nos rounds finais.
```

Possível resposta:

```text
Você demonstrou dificuldade em defesa de triângulo e manutenção de ritmo.
Sugestão: incluir drills de postura dentro da guarda fechada, defesa de triângulo e treinos intervalados para melhorar condicionamento.
```

Possíveis funcionalidades com IA:

* Análise de anotações dos treinos
* Sugestão de técnicas para revisar
* Identificação de padrões de dificuldade
* Plano semanal de treino
* Preparação para campeonato
* Resumo mensal de evolução

---

## Visão futura do produto

A longo prazo, o MatTrack pode evoluir para uma plataforma SaaS para atletas e academias de Jiu-Jitsu.

Possibilidades:

* Gestão de alunos por academia
* Perfil de professor
* Acompanhamento de evolução dos alunos
* Ranking interno
* Planejamento de campeonatos
* Relatórios de frequência
* App mobile
* Integração com wearables
* IA para análise de performance
* Multi-tenant para várias academias

---

## Objetivo técnico do projeto

Além de ser uma aplicação útil para praticantes de Jiu-Jitsu, o MatTrack também tem como objetivo demonstrar domínio em desenvolvimento full stack moderno.

O projeto envolve conceitos importantes como:

* API REST
* Autenticação JWT
* Spring Security
* JPA/Hibernate
* Relacionamentos entre entidades
* Entidade intermediária em relacionamento N:N
* Validações com Bean Validation
* Migrations com Flyway
* PostgreSQL
* Docker
* Organização em camadas
* Separação entre DTOs, entidades, services e controllers
* Boas práticas de segurança
* Preparação para frontend Angular
* Preparação para dashboards e IA

---

## Autor

Projeto desenvolvido por Marco Aguiar.
