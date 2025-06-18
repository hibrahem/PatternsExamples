# Microservices Patterns Examples

This repository contains practical implementations of various microservices patterns, demonstrating different approaches to solving common challenges in distributed systems. Each pattern is implemented with clear, educational examples that show both the concept and its practical application.

## Available Patterns

### 1. Saga Pattern
Located in `src/saga/`, demonstrates how to manage distributed transactions in microservices architectures.

#### Orchestration Approach (`src/saga/orchestrator/`)
- Centralized coordination of distributed transactions
- Key components:
  - `OrderSaga.java`: Main saga implementation
  - `SagaState.java`: Saga state management
  - `SagaStep.java`: Individual transaction steps
  - `SagaStore.java`: State persistence

#### Choreography Approach (`src/saga/choreography/`)
- Decentralized event-driven transaction management
- Key components:
  - `Main.java`: Entry point
  - `services/`: Individual service implementations

### 2. Bulkhead Pattern
Located in `src/bulkhead/`, demonstrates fault isolation in microservices.

#### Implementation Details
- `BulkheadExample.java`: Shows how to implement thread pool isolation
- Demonstrates:
  - Separate thread pools for different services
  - Resource isolation
  - Failure containment
  - Thread pool monitoring

### 3. Circuit Breaker Pattern
Located in `src/circuitbreaker/`, demonstrates fault tolerance in microservices.

#### Implementation Details
- `CircuitBreakerExample.java`: Shows how to implement circuit breaker pattern
- Demonstrates:
  - Failure detection
  - State management
  - Protection mechanisms
  - Monitoring

### 4. More Patterns Coming Soon
- API Gateway Pattern
- Service Discovery
- Event Sourcing
- CQRS
- And more...

## Getting Started

1. Clone the repository
2. Open the project in your preferred Java IDE
3. Each pattern implementation is self-contained and can be run independently
4. Check the specific pattern's directory for detailed instructions

## Pattern Descriptions

### Saga Pattern
The Saga pattern manages distributed transactions by breaking them into smaller, local transactions. If any step fails, compensating transactions are executed to maintain data consistency.

**Approaches:**
- **Orchestration**: Uses a central coordinator to manage the saga execution
- **Choreography**: Services communicate through events without a central coordinator

### Bulkhead Pattern
The Bulkhead pattern isolates different parts of the system to prevent cascading failures. Named after ship compartments, it ensures that if one part of the system fails, it doesn't bring down the entire system.

**Key Concepts:**
- Resource isolation
- Failure containment
- Thread pool management
- Service degradation

### Circuit Breaker Pattern
The Circuit Breaker pattern helps prevent cascading failures in distributed systems by stopping calls to failing services and allowing them to recover.

**Key Concepts:**
- Failure detection
- State management
- Protection mechanisms
- Monitoring

## Project Structure
```
src/
├── saga/
│   ├── orchestrator/
│   └── choreography/
├── bulkhead/
├── circuitbreaker/
└── common/
└── [future patterns]/
```

## Contributing
Feel free to contribute by:
1. Adding new pattern implementations
2. Improving existing examples
3. Adding more documentation
4. Reporting issues

## About Tech Mentors

This project is part of the Software Architecture course by Tech Mentors. Each pattern implementation is designed to be educational and practical, helping developers understand and implement these patterns in real-world scenarios.

Check out our courses on Udemy: [Hassan Ibrahem's Courses](https://www.udemy.com/user/hassan-ibrahem-2/)

<img src="techmentors.png" alt="Tech Mentors Logo" width="300"/>