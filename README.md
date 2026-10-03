# Enterprise Pricing & Billing Engine (Spring Boot / gRPC / Kafka)

This repository contains the foundational architecture for a high-performance **Pricing and Billing Platform** built with Java and Spring Boot. I engineered this sandbox project to rigorously test and implement distributed systems concepts, specifically focusing on data correctness, concurrency, and high-throughput messaging.

## Key Architectural Implementations

### 1. High-Performance gRPC Pricing Platform
- Built the foundation for a gRPC and REST-enabled pricing service to handle complex routing logic securely.
- **N+1 Persistence Pitfall Prevented:** Implemented JPA `@EntityGraph` in the `PricingPlanRepository` to ensure complex entity relationships (Plans and Rules) are fetched in a single, highly-optimized SQL `JOIN`, eliminating N+1 latency spikes on critical hot paths.

### 2. Message-Driven Billing Architecture (Kafka)
- Integrated Spring Kafka to consume asynchronous `sms-billing-events`.
- **Manual Offset Management:** Disabled Kafka's auto-commit feature. Consumer offsets are acknowledged manually *only* after a successful database transaction to guarantee at-least-once processing without data loss.
- **Concurrency & Optimistic Locking:** Implemented JPA `@Version` on the `CustomerBalance` entity. If two microservice instances attempt to deduct a customer's balance simultaneously, the database intercepts the race condition, throws an `ObjectOptimisticLockingFailureException`, and triggers safe retry logic.

### 3. Database Schema Evolution (Liquibase)
- Strict separation of schema control from Hibernate (`ddl-auto: validate`).
- All MySQL database modifications are heavily version-controlled utilizing **Liquibase** YAML changesets, ensuring append-only and soft-delete data patterns are strictly enforced without manual in-place mutations.

## Tech Stack
* Java 17
* Spring Boot 3 / Spring Data JPA
* gRPC (Protocol Buffers)
* Apache Kafka
* Liquibase
* MySQL / Docker
