# Diagrammes d'Architecture MyProf

## Architecture Système
```mermaid
graph TB
    subgraph Client
        A[Web App] --> B[PWA]
        A --> C[Mobile App]
    end
    
    subgraph API Gateway
        D[AWS API Gateway]
        E[Load Balancer]
    end
    
    subgraph Services
        F[Auth Service]
        G[Course Service]
        H[User Service]
        I[Payment Service]
        J[Streaming Service]
        K[AI Service]
    end
    
    subgraph Data
        L[(MongoDB)]
        M[(Redis)]
        N[(Elasticsearch)]
    end
    
    subgraph Storage
        O[S3 Media]
        P[EFS Data]
    end
    
    Client --> D
    D --> E
    E --> Services
    Services --> Data
    Services --> Storage
```

## Architecture de Microservices
```mermaid
graph LR
    A[API Gateway] --> B[Auth Service]
    A --> C[Course Service]
    A --> D[User Service]
    A --> E[Payment Service]
    A --> F[Streaming Service]
    A --> G[AI Service]
    
    B --> H[(User DB)]
    C --> I[(Course DB)]
    D --> H
    E --> J[(Payment DB)]
    F --> K[(Media Store)]
    G --> L[(ML Models)]
```

## Flux d'Authentication
```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant A as Auth Service
    participant D as Database
    
    U->>F: Login Request
    F->>A: Auth Request
    A->>D: Validate Credentials
    D-->>A: User Data
    A-->>F: JWT Token
    F-->>U: Login Success
```

## Flux de Paiement
```mermaid
sequenceDiagram
    participant U as User
    participant P as Payment Service
    participant S as Stripe
    participant D as Database
    
    U->>P: Payment Request
    P->>S: Process Payment
    S-->>P: Payment Status
    P->>D: Update Transaction
    P-->>U: Confirmation
```

## Architecture de Données
```mermaid
erDiagram
    USER ||--o{ COURSE : enrolls
    USER {
        string id
        string name
        string email
        string role
    }
    COURSE ||--|{ LESSON : contains
    COURSE {
        string id
        string title
        string description
        float price
    }
    LESSON {
        string id
        string title
        string content
        int duration
    }
    USER ||--o{ PAYMENT : makes
    PAYMENT {
        string id
        float amount
        string status
        date timestamp
    }
```

## Infrastructure Cloud
```mermaid
graph TB
    subgraph AWS
        A[Route 53] --> B[CloudFront]
        B --> C[API Gateway]
        C --> D[EKS Cluster]
        D --> E[EC2 Instances]
        D --> F[RDS]
        D --> G[ElastiCache]
        H[S3] --> B
    end
```

## Pipeline CI/CD
```mermaid
graph LR
    A[Code] --> B[Build]
    B --> C[Test]
    C --> D[Quality Check]
    D --> E[Deploy Dev]
    E --> F[Deploy Staging]
    F --> G[Deploy Prod]
```

## Monitoring System
```mermaid
graph TB
    subgraph Apps
        A[Services]
        B[Databases]
        C[Infrastructure]
    end
    
    subgraph Monitoring
        D[Prometheus]
        E[Grafana]
        F[ELK Stack]
    end
    
    subgraph Alerts
        G[PagerDuty]
        H[Email]
        I[Slack]
    end
    
    Apps --> D
    D --> E
    Apps --> F
    E --> Alerts
    F --> Alerts
```
