# Inventory Management Backend

Backend REST API for managing products and inventory with Redis caching, PostgreSQL persistence, validation, exception handling, and Dockerized deployment.

---

## Features

### Product Management

* Create product
* Get product by ID
* Update product
* DTO validation
* Exception handling

### Inventory Management

* Create inventory for product
* Get inventory by product
* Update inventory quantities
* Validation for inventory rules

### Redis Caching

* Manual caching using `RedisTemplate`
* Cache-aside strategy
* Manual cache invalidation
* TTL based expiration
* JSON serialization

### Infrastructure

* PostgreSQL database
* Dockerized deployment
* Centralized logging
* Global exception handling

---

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA

### Database

* PostgreSQL

### Cache

* Redis

### DevOps

* Docker
* Docker Compose

### Utilities

* Lombok
* Jakarta Validation

---

## Architecture

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Redis Cache
   ↓ (MISS)
PostgreSQL
   ↓
Redis Populate
   ↓
Response
```
## Screenshots

### Application Running

Example:

```text
docker compose ps
```

Image:

```text
/docs/screenshots/docker-running.png
```

---

### Redis Cache Logs

Example logs:

```text
CACHE MISS → products:inventory:1
CACHE SET → products:inventory:1

CACHE HIT → products:inventory:1

CACHE EVICT → products:inventory:1
```

Image:

```text
/docs/screenshots/cache-logs.png
```

---

### Redis Stored Data

Example:

```text
KEYS *

GET products:inventory:1
```

Image:

```text
/docs/screenshots/redis-cache.png
```

---

### API Testing

Include:

* GET inventory
* PATCH inventory
* Response body

Image:

```text
/docs/screenshots/postman-api.png
```

---

### Benchmark Results

Example:

| Scenario  | Avg Time |
| --------- | -------- |
| DB Read   | XX ms    |
| Redis HIT | XX ms    |

Image:

```text
/docs/screenshots/benchmark.png
```


---

# API Endpoints

## Product APIs

### Create Product

```http
POST /products
```

Request:

```json
{
  "sku": "ABC_101",
  "name": "Keyboard",
  "price": 1499.99
}
```

---

### Get Product

```http
GET /products/{pid}
```

Flow:

```text
Redis
↓ HIT
Return

OR

Redis
↓ MISS
Database
↓
Cache
↓
Return
```

---

### Update Product

```http
PATCH /products/{pid}
```

Flow:

```text
Update DB
↓
Delete Redis Cache
↓
Next GET rebuilds cache
```

---

## Inventory APIs

### Create Inventory

```http
POST /products/{pid}/inventory
```

Request:

```json
{
  "availableQuantity": 100,
  "reservedQuantity": 20
}
```

Validation:

```text
reservedQuantity ≤ availableQuantity
```

---

### Get Inventory

```http
GET /products/{pid}/inventory
```

Flow:

```text
Check Redis
↓ HIT
Return

OR

MISS
↓
Database
↓
Store Redis
↓
Return
```

Cache key:

```text
products:inventory:{pid}
```

---

### Update Inventory

```http
PATCH /products/{pid}/inventory
```

Flow:

```text
Update Database
↓
Delete Cache
↓
Next GET refreshes cache
```

---

# Redis Design

## Cache Strategy

Cache Aside Pattern

```text
GET
↓
Check Cache
↓
MISS
↓
DB
↓
SET Cache
↓
Return
```

---

## Cache Invalidation

```text
PATCH
↓
Update DB
↓
Delete Cache
```

---

## Serialization

Keys:

```text
StringRedisSerializer
```

Values:

```text
GenericJacksonJsonRedisSerializer
```

Example:

```json
{
  "availableQuantity": 100,
  "reservedQuantity": 20
}
```

---

## TTL

Inventory cache uses expiration to automatically remove stale entries.

---

# Logging

Application logs include:

```text
CACHE HIT
CACHE MISS
CACHE SET
CACHE EVICT
```

Logs can be viewed using:

```bash
docker compose logs -f app
```

---

# Run Locally

Clone repository:

```bash
git clone <https://github.com/Jitbaul13-maker/Inventory_managent_system/>
```

Start containers:

```bash
docker compose up --build
```

Application:

```text
http://localhost:8080
```

---

# Future Improvements

* Redis Hash based inventory updates
* Benchmark cache performance
* Authentication & Authorization
* Monitoring and metrics
* CI/CD pipeline

---

# Learning Outcomes

Through this project:

* Built REST APIs using Spring Boot
* Designed DTO driven architecture
* Implemented Redis cache-aside pattern
* Configured JSON serialization
* Implemented cache invalidation
* Containerized application using Docker
* Improved observability through logging
