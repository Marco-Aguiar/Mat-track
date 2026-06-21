# MatTrack

**MatTrack** é uma API REST para acompanhamento de evolução esportiva. A plataforma nasceu focada em Jiu-Jitsu, mas agora foi refatorada para suportar múltiplas modalidades: **Jiu-Jitsu, musculação, funcional e corrida**.

A decisão arquitetural principal desta versão foi introduzir uma camada explícita de modalidade (`SportType`) sem destruir os módulos já existentes. Assim, os endpoints atuais continuam funcionando para o fluxo de Jiu-Jitsu, mas passam a aceitar dados mais genéricos para outros esportes.

---

## Modalidades suportadas

```text
JIU_JITSU
STRENGTH_TRAINING
FUNCTIONAL_TRAINING
RUNNING
```

---

## O que mudou nesta versão

### 1. Usuário agora possui modalidade principal

O cadastro de usuário aceita o campo opcional `primarySport`.

Exemplo:

```json
{
  "name": "Marco Aguiar",
  "email": "marco@test.com",
  "password": "123456",
  "belt": "BLUE",
  "weight": 94,
  "academy": "Buchecha Team",
  "primarySport": "JIU_JITSU"
}
```

Caso `primarySport` não seja informado, o sistema usa `JIU_JITSU` como padrão para manter compatibilidade com o comportamento anterior.

---

### 2. Treinos agora são multi-modalidade

O módulo de treinos agora possui `sportType` e aceita tipos de treino de Jiu-Jitsu, musculação, funcional e corrida.

Endpoint principal:

```http
POST   /api/trainings
GET    /api/trainings?sportType=RUNNING
GET    /api/trainings/{id}
PUT    /api/trainings/{id}
DELETE /api/trainings/{id}
```

Exemplo de treino de Jiu-Jitsu:

```json
{
  "trainingDate": "2026-06-21",
  "sportType": "JIU_JITSU",
  "type": "GI",
  "durationMinutes": 90,
  "rounds": 6,
  "intensity": 4,
  "notes": "Treino de passagem de guarda."
}
```

Exemplo de treino de musculação:

```json
{
  "trainingDate": "2026-06-21",
  "sportType": "STRENGTH_TRAINING",
  "type": "HYPERTROPHY",
  "durationMinutes": 70,
  "intensity": 4,
  "caloriesBurned": 420,
  "notes": "Treino de peito e tríceps."
}
```

Exemplo de corrida:

```json
{
  "trainingDate": "2026-06-21",
  "sportType": "RUNNING",
  "type": "EASY_RUN",
  "durationMinutes": 35,
  "distanceKm": 5.20,
  "intensity": 3,
  "caloriesBurned": 380,
  "notes": "Corrida leve em zona 2."
}
```

Regra importante: o backend valida se o `type` pertence ao `sportType`. Por exemplo, `GI` só é aceito para `JIU_JITSU`, enquanto `EASY_RUN` só é aceito para `RUNNING`.

---

### 3. Técnicas viraram biblioteca de movimentos/exercícios por modalidade

O módulo continua usando o nome técnico `Technique` para manter compatibilidade com o frontend atual, mas conceitualmente ele agora representa uma biblioteca de **técnicas, exercícios, drills ou movimentos**.

Endpoint principal:

```http
POST   /api/techniques
GET    /api/techniques
GET    /api/techniques?sportType=STRENGTH_TRAINING
GET    /api/techniques?sportType=JIU_JITSU&category=GUARD
GET    /api/techniques/{id}
PUT    /api/techniques/{id}
DELETE /api/techniques/{id}
```

Exemplo de técnica de Jiu-Jitsu:

```json
{
  "name": "Armbar da guarda fechada",
  "sportType": "JIU_JITSU",
  "category": "SUBMISSION",
  "description": "Ataque de chave de braço partindo da guarda fechada."
}
```

Exemplo de exercício de musculação:

```json
{
  "name": "Supino reto",
  "sportType": "STRENGTH_TRAINING",
  "category": "PUSH",
  "description": "Exercício base para peitoral, ombros e tríceps."
}
```

Exemplo de drill de corrida:

```json
{
  "name": "Tiro de 400m",
  "sportType": "RUNNING",
  "category": "INTERVAL",
  "description": "Intervalo de alta intensidade para ganho de velocidade."
}
```

---

### 4. Associação entre treino e técnica agora aceita métricas específicas

A relação entre treino e técnica/exercício agora aceita campos úteis para modalidades diferentes:

```http
POST /api/trainings/{trainingId}/techniques
GET  /api/trainings/{trainingId}/techniques
PUT  /api/trainings/{trainingId}/techniques/{techniqueId}
DELETE /api/trainings/{trainingId}/techniques/{techniqueId}
```

Exemplo para musculação:

```json
{
  "techniqueId": "UUID_DO_SUPINO",
  "sets": 4,
  "reps": 10,
  "loadKg": 70,
  "note": "Boa execução, aumentar carga na próxima semana."
}
```

Exemplo para corrida:

```json
{
  "techniqueId": "UUID_DO_TIRO_400M",
  "sets": 6,
  "distanceKm": 0.40,
  "durationSeconds": 95,
  "note": "Recuperação de 90 segundos entre tiros."
}
```

O backend impede que uma técnica/exercício de uma modalidade seja vinculada a um treino de outra modalidade.

---

## Catálogo para o frontend

Foram adicionados endpoints auxiliares para facilitar a construção de telas dinâmicas no frontend:

```http
GET /api/sports
GET /api/sports/training-types
GET /api/sports/technique-categories
```

Eles permitem carregar modalidades, tipos de treino por modalidade e categorias disponíveis sem hardcode no frontend.

---

## Tipos de treino disponíveis

### Jiu-Jitsu

```text
GI
NO_GI
OPEN_MAT
DRILLING
COMPETITION
```

### Musculação

```text
STRENGTH
HYPERTROPHY
POWER
UPPER_BODY
LOWER_BODY
```

### Funcional

```text
FUNCTIONAL
HIIT
CIRCUIT
MOBILITY
CONDITIONING
```

### Corrida

```text
EASY_RUN
LONG_RUN
INTERVAL_RUN
TEMPO_RUN
RECOVERY_RUN
```

---

## Categorias disponíveis

A enumeração `TechniqueCategory` foi expandida para cobrir Jiu-Jitsu, musculação, funcional e corrida.

Exemplos:

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
PUSH
PULL
LEGS
CORE
FULL_BODY
STRENGTH
HYPERTROPHY
MOBILITY
CONDITIONING
BALANCE
PLYOMETRICS
CIRCUIT
HIIT
EASY_RUN
LONG_RUN
INTERVAL
TEMPO
SPEED_WORK
RUNNING_DRILL
```

---

## Dashboard multi-modalidade

Os dashboards agora aceitam filtro opcional por modalidade:

```http
GET /api/dashboard/summary?sportType=RUNNING
GET /api/dashboard/weekly-trainings?sportType=STRENGTH_TRAINING
GET /api/dashboard/most-trained-techniques?sportType=JIU_JITSU
GET /api/dashboard/techniques-by-category?sportType=FUNCTIONAL_TRAINING
GET /api/dashboard/weight-progress
```

O resumo agora também retorna:

* `totalDistanceKm`
* `totalCaloriesBurned`

---

## Migrations Flyway

Foi adicionada a migration:

```text
V5__generalize_platform_to_multi_sport.sql
```

Ela adiciona:

* `users.primary_sport`
* `techniques.sport_type`
* `techniques.updated_at`
* `trainings.sport_type`
* `trainings.distance_km`
* `trainings.calories_burned`
* Métricas na associação `training_techniques`: `sets`, `reps`, `load_kg`, `distance_km`, `duration_seconds`
* Índices para consultas por modalidade
* Índice único para evitar técnica/exercício duplicado dentro da mesma modalidade

---

## Stack

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

---

## Como rodar

```bash
docker compose up -d
cd backend
mvn spring-boot:run
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Próximos passos recomendados

1. Renomear gradualmente o domínio público de `techniques` para `movements` ou `exercises`, mantendo aliases para compatibilidade.
2. Criar presets de exercícios por modalidade.
3. Criar dashboards específicos: evolução de carga na musculação, pace na corrida e volume semanal por modalidade.
4. Criar planos de treino multi-modalidade.
5. Evoluir o frontend para onboarding com seleção de modalidade principal.
