# MatTrack API

REST API for multi-sport training tracking. Supports Jiu-Jitsu, Strength Training, Functional Training, and Running.

---

## Stack

- Java 17 · Spring Boot 3.3.5
- PostgreSQL 16 · Flyway · Redis 7
- Spring Security · JWT
- Docker · Maven · Swagger / OpenAPI

---

## Running locally

```bash
docker compose up -d
cd backend
mvn spring-boot:run
```

Swagger available at `http://localhost:8080/swagger-ui/index.html`

---

## Authentication

All endpoints — except `POST /api/auth/register`, `POST /api/auth/login`, and `GET /api/sports/**` — require the header:

```
Authorization: Bearer <token>
```

The token is returned by the register and login endpoints.

---

## Endpoints

### Auth

```
POST  /api/auth/register
POST  /api/auth/login
GET   /api/auth/me
PATCH /api/auth/me
```

**`POST /api/auth/register`**
```json
{
  "name": "Marco Aguiar",
  "email": "marco@email.com",
  "password": "password123",
  "belt": "Purple",
  "weight": 82.5,
  "academy": "Gracie Barra",
  "primarySport": "JIU_JITSU"
}
```
Returns `{ "token": "..." }`. `primarySport` is optional — defaults to `JIU_JITSU`.

**`POST /api/auth/login`**
```json
{ "email": "marco@email.com", "password": "password123" }
```
Returns `{ "token": "..." }`.

**`GET /api/auth/me`** — returns the authenticated user's profile.

**`PATCH /api/auth/me`** — updates the profile. All fields are optional; send only what changed.
```json
{
  "name": "Marco",
  "belt": "Purple",
  "academy": "Gracie Barra SP",
  "weight": 82.5,
  "primarySport": "JIU_JITSU"
}
```
Returns the updated `MeResponse`. Email and password cannot be changed through this endpoint.

---

### Trainings

```
POST   /api/trainings
GET    /api/trainings
GET    /api/trainings/{id}
PUT    /api/trainings/{id}
DELETE /api/trainings/{id}
```

**`GET /api/trainings`** — all query params are optional:

| Param | Type | Description |
|---|---|---|
| `sportType` | enum | Filter by sport |
| `startDate` | `YYYY-MM-DD` | Start date (inclusive) |
| `endDate` | `YYYY-MM-DD` | End date (inclusive) |
| `page` | int | Page number (default `0`) |
| `size` | int | Items per page (default `20`) |

Returns `PageResponse<TrainingResponse>`.

**Payload examples:**

Jiu-Jitsu:
```json
{
  "trainingDate": "2026-06-22",
  "sportType": "JIU_JITSU",
  "type": "GI",
  "durationMinutes": 90,
  "rounds": 6,
  "intensity": 4,
  "notes": "Guard passing focused session."
}
```

Strength Training:
```json
{
  "trainingDate": "2026-06-22",
  "sportType": "STRENGTH_TRAINING",
  "type": "HYPERTROPHY",
  "durationMinutes": 70,
  "intensity": 4,
  "caloriesBurned": 420
}
```

Running:
```json
{
  "trainingDate": "2026-06-22",
  "sportType": "RUNNING",
  "type": "EASY_RUN",
  "durationMinutes": 35,
  "distanceKm": 5.20,
  "intensity": 3,
  "caloriesBurned": 380
}
```

> The backend validates that `type` belongs to `sportType`. `GI` is only valid for `JIU_JITSU`, `EASY_RUN` only for `RUNNING`, etc.

---

### Techniques per training

```
POST   /api/trainings/{trainingId}/techniques
GET    /api/trainings/{trainingId}/techniques
PUT    /api/trainings/{trainingId}/techniques/{techniqueId}
DELETE /api/trainings/{trainingId}/techniques/{techniqueId}
```

Links a technique to a training session with sport-specific metrics.

Strength Training:
```json
{
  "techniqueId": "uuid",
  "sets": 4,
  "reps": 10,
  "loadKg": 80.0,
  "note": "Increase weight next week."
}
```

Running:
```json
{
  "techniqueId": "uuid",
  "sets": 6,
  "distanceKm": 0.40,
  "durationSeconds": 95
}
```

> The backend prevents linking a technique from a different sport than the training session.

---

### Techniques

```
POST   /api/techniques
GET    /api/techniques
GET    /api/techniques/{id}
PUT    /api/techniques/{id}
DELETE /api/techniques/{id}
GET    /api/techniques/{id}/progression
```

#### Ownership

There are two types of techniques:

| Type | `owned` field | Who can edit/delete |
|---|---|---|
| **Global** (seed data) | `false` | Nobody |
| **Custom** | `true` | Creator only |

The database ships with **46 global techniques** ready to use:

| Sport | Count | Examples |
|---|---|---|
| `JIU_JITSU` | 15 | Armbar, Triangle, Kimura, Double Leg, Scissor Sweep |
| `STRENGTH_TRAINING` | 15 | Bench Press, Squat, Deadlift, Pull-up, Stiff |
| `FUNCTIONAL_TRAINING` | 8 | Burpee, Box Jump, Kettlebell Swing, Plank |
| `RUNNING` | 8 | Easy Run, Long Run, Intervals, Tempo Run |

**`GET /api/techniques`** — returns global techniques + the authenticated user's own techniques.

| Param | Type | Description |
|---|---|---|
| `sportType` | enum | Filter by sport |
| `category` | enum | Filter by category |
| `page` | int | Default `0` |
| `size` | int | Default `20` |

Returns `PageResponse<TechniqueResponse>`.

**`TechniqueResponse`:**
```json
{
  "id": "uuid",
  "name": "Armbar",
  "sportType": "JIU_JITSU",
  "category": "SUBMISSION",
  "description": "...",
  "owned": false,
  "createdAt": "2026-01-01T00:00:00",
  "updatedAt": "2026-01-01T00:00:00"
}
```

**`GET /api/techniques/{id}/progression`** — returns the evolution of a technique over time.

| Param | Type | Description |
|---|---|---|
| `startDate` | `YYYY-MM-DD` | Optional |
| `endDate` | `YYYY-MM-DD` | Optional |

Returns a chronologically ordered array (not paginated):
```json
[
  {
    "trainingDate": "2026-01-10",
    "trainingId": "uuid",
    "sets": 4,
    "reps": 8,
    "loadKg": 80.00,
    "distanceKm": null,
    "durationSeconds": null,
    "note": null
  }
]
```

Recommended Y-axis by sport:

| Sport | Primary field | Secondary fields |
|---|---|---|
| Strength Training | `loadKg` | `sets`, `reps` |
| Running | `distanceKm` | `durationSeconds` |
| Functional | `durationSeconds` | `sets`, `reps` |
| Jiu-Jitsu | `note` | — |

---

### Weight History

```
POST   /api/weight-history
GET    /api/weight-history
GET    /api/weight-history/latest
GET    /api/weight-history/{id}
PUT    /api/weight-history/{id}
DELETE /api/weight-history/{id}
```

**`GET /api/weight-history`** — accepts `page` (default `0`) and `size` (default `20`). Returns `PageResponse<WeightHistoryResponse>`.

One entry per date per user. The user's `weight` field is automatically synced to the most recent entry.

---

### Dashboard

All endpoints accept an optional `?sportType=` filter. Responses are cached in Redis.

```
GET /api/dashboard/summary                  (cache 5 min)
GET /api/dashboard/weekly-trainings         (cache 10 min)
GET /api/dashboard/most-trained-techniques  (cache 10 min)
GET /api/dashboard/techniques-by-category   (cache 10 min)
GET /api/dashboard/weight-progress          (cache 5 min)
```

The cache is automatically invalidated when the user creates, updates, or deletes trainings, techniques, or weight entries.

---

### Metadata (no authentication required)

```
GET /api/sports
GET /api/sports/training-types
GET /api/sports/technique-categories
```

Use these to populate frontend dropdowns without hardcoding enums.

---

## Pagination

List endpoints return `PageResponse<T>`:

```json
{
  "content": [...],
  "page": 0,
  "size": 20,
  "totalElements": 87,
  "totalPages": 5,
  "last": false
}
```

---

## Error handling

All errors return `ProblemDetail` (RFC 9457):

```json
{
  "status": 400,
  "title": "Bad Request",
  "detail": "Email already registered"
}
```

| Situation | HTTP |
|---|---|
| Field validation failure | 400 — `Validation Failed` |
| Business rule violation | 400 — `Bad Request` |
| Resource not found | 404 — `Not Found` |
| No permission over resource | 403 — `Forbidden` |
| Missing or invalid token | 401 |

---

## Enum reference

### `SportType`
```
JIU_JITSU · STRENGTH_TRAINING · FUNCTIONAL_TRAINING · RUNNING
```

### `TrainingType` by sport

| JIU_JITSU | STRENGTH_TRAINING | FUNCTIONAL_TRAINING | RUNNING |
|---|---|---|---|
| GI | STRENGTH | FUNCTIONAL | EASY_RUN |
| NO_GI | HYPERTROPHY | HIIT | LONG_RUN |
| OPEN_MAT | POWER | CIRCUIT | INTERVAL_RUN |
| DRILLING | UPPER_BODY | MOBILITY | TEMPO_RUN |
| COMPETITION | LOWER_BODY | CONDITIONING | RECOVERY_RUN |

### `TechniqueCategory`
```
# Jiu-Jitsu
TAKEDOWN · GUARD · SWEEP · PASS · SUBMISSION · ESCAPE · CONTROL · TRANSITION · DEFENSE

# Strength Training
PUSH · PULL · LEGS · CORE · FULL_BODY · STRENGTH · HYPERTROPHY

# Functional Training
MOBILITY · CONDITIONING · BALANCE · PLYOMETRICS · CIRCUIT · HIIT

# Running
EASY_RUN · LONG_RUN · INTERVAL · TEMPO · SPEED_WORK · RUNNING_DRILL
```

---

## Database migrations

| Version | Description |
|---|---|
| V1 | Initial schema (techniques, trainings, training_techniques) |
| V2 | Users table |
| V3 | Fix FK between trainings and users |
| V4 | Weight history table |
| V5 | Multi-sport support (sport_type, sport-specific metrics, indexes) |
| V6 | `created_by` column on techniques (ownership) |
| V7 | Seed data — 46 global techniques |
