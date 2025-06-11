# Banking Application

A modern banking application built with Spring Boot microservices and Angular frontend.

## Architecture

The application consists of the following microservices:

- **User Service** (Port 8081): Handles user registration, authentication, and profile management
- **Account Service** (Port 8082): Manages bank accounts and balances
- **Transaction Service** (Port 8083): Handles money transfers and transaction history
- **Notification Service** (Port 8084): Manages user notifications
- **API Gateway** (Port 8080): Routes requests to appropriate microservices
- **Eureka Server** (Port 8761): Service discovery and registration
- **Frontend** (Port 80): Angular-based user interface

## Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- Maven 3.8 or higher
- Docker and Docker Compose
- PostgreSQL 14 or higher (if running without Docker)

## Local Development Setup

### Backend Services

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd banking-app
   ```

2. Build all services:
   ```bash
   mvn clean install
   ```

3. Start the services in order:
   ```bash
   # Start Eureka Server
   cd eureka-server
   mvn spring-boot:run

   # Start API Gateway
   cd ../api-gateway
   mvn spring-boot:run

   # Start other services
   cd ../user-service
   mvn spring-boot:run

   cd ../account-service
   mvn spring-boot:run

   cd ../transaction-service
   mvn spring-boot:run

   cd ../notification-service
   mvn spring-boot:run
   ```

### Frontend Development

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

The frontend will be available at `http://localhost:4200`

## Docker Setup

1. Build and start all services using Docker Compose:
   ```bash
   docker-compose up --build
   ```

2. Access the application:
   - Frontend: http://localhost
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

## API Documentation

Swagger documentation is available for each service:

- User Service: http://localhost:8081/swagger-ui.html
- Account Service: http://localhost:8082/swagger-ui.html
- Transaction Service: http://localhost:8083/swagger-ui.html
- Notification Service: http://localhost:8084/swagger-ui.html

## Features

- User registration and authentication
- Account management
- Money transfers
- Transaction history
- Real-time notifications
- Responsive UI with Material Design
- Role-based access control
- JWT-based security

## Security

The application uses JWT (JSON Web Tokens) for authentication. The JWT secret is configured in each service's `application.yml` file. In production, make sure to:

1. Change the JWT secret
2. Use HTTPS
3. Implement proper password hashing
4. Configure CORS properly
5. Set up proper database credentials

## Database

The application uses PostgreSQL. When running with Docker, the database is automatically configured. For local development:

1. Create a database named `banking`
2. Update the database configuration in each service's `application.yml`
3. Run the Flyway migrations:
   ```bash
   mvn flyway:migrate
   ```

## Testing

Run tests for all services:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Production Deployment

### Prerequisites for Production

1. Production-grade infrastructure:
   - Kubernetes cluster or cloud platform (AWS, GCP, Azure)
   - Load balancer
   - SSL certificates
   - Production database instance
   - Monitoring and logging solutions

2. Security requirements:
   - Strong JWT secret
   - SSL/TLS certificates
   - Firewall rules
   - Network security groups
   - Database encryption

### Deployment Steps

1. **Environment Configuration**

   Create production-specific configuration files:
   ```bash
   # Create production profiles for each service
   cp user-service/src/main/resources/application.yml user-service/src/main/resources/application-prod.yml
   cp account-service/src/main/resources/application.yml account-service/src/main/resources/application-prod.yml
   cp transaction-service/src/main/resources/application.yml transaction-service/src/main/resources/application-prod.yml
   cp notification-service/src/main/resources/application.yml notification-service/src/main/resources/application-prod.yml
   ```

   Update the production configurations with:
   - Production database credentials
   - Strong JWT secrets
   - Production Eureka server URL
   - Production logging levels
   - CORS settings for production domain

2. **Database Setup**

   ```bash
   # Create production database
   createdb banking_prod

   # Run Flyway migrations
   mvn flyway:migrate -Dspring.profiles.active=prod
   ```

3. **Building Production Images**

   ```bash
   # Build all services
   mvn clean package -Pprod

   # Build Docker images
   docker build -t bankapp/user-service:prod ./user-service
   docker build -t bankapp/account-service:prod ./account-service
   docker build -t bankapp/transaction-service:prod ./transaction-service
   docker build -t bankapp/notification-service:prod ./notification-service
   docker build -t bankapp/api-gateway:prod ./api-gateway
   docker build -t bankapp/eureka-server:prod ./eureka-server
   docker build -t bankapp/frontend:prod ./frontend
   ```

4. **Kubernetes Deployment**

   Create Kubernetes manifests:
   ```yaml
   # Example deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: user-service
   spec:
     replicas: 3
     selector:
       matchLabels:
         app: user-service
     template:
       metadata:
         labels:
           app: user-service
       spec:
         containers:
         - name: user-service
           image: bankapp/user-service:prod
           env:
           - name: SPRING_PROFILES_ACTIVE
             value: "prod"
           - name: SPRING_DATASOURCE_URL
             valueFrom:
               secretKeyRef:
                 name: db-credentials
                 key: url
   ```

   Apply the configurations:
   ```bash
   kubectl apply -f k8s/
   ```

5. **Load Balancer Configuration**

   Configure your load balancer to:
   - Route traffic to the API Gateway
   - Handle SSL termination
   - Implement health checks
   - Set up proper timeouts

6. **Monitoring Setup**

   Configure monitoring tools:
   ```bash
   # Install Prometheus
   helm install prometheus prometheus-community/kube-prometheus-stack

   # Install Grafana
   helm install grafana grafana/grafana
   ```

   Set up alerts for:
   - Service availability
   - Error rates
   - Response times
   - Resource usage

7. **Logging Setup**

   Configure centralized logging:
   ```bash
   # Install ELK stack
   helm install elasticsearch elastic/elasticsearch
   helm install kibana elastic/kibana
   helm install filebeat elastic/filebeat
   ```

8. **Backup Configuration**

   Set up automated backups:
   ```bash
   # Database backup cronjob
   kubectl create -f k8s/backup-cronjob.yaml
   ```

### Production Checklist

- [ ] All services are running with production profiles
- [ ] Database is properly configured and secured
- [ ] SSL certificates are installed and valid
- [ ] JWT secrets are strong and unique
- [ ] Monitoring and logging are configured
- [ ] Backups are scheduled and tested
- [ ] Load balancer is configured
- [ ] Firewall rules are set
- [ ] CORS settings are production-ready
- [ ] Error handling is production-grade
- [ ] Performance testing is completed
- [ ] Security audit is performed

### Scaling

To scale the application:

1. **Horizontal Scaling**
   ```bash
   # Scale services
   kubectl scale deployment user-service --replicas=5
   kubectl scale deployment account-service --replicas=5
   kubectl scale deployment transaction-service --replicas=5
   ```

2. **Database Scaling**
   - Set up database replication
   - Configure connection pooling
   - Implement caching

3. **Load Balancer Configuration**
   - Enable sticky sessions if needed
   - Configure health checks
   - Set up auto-scaling rules

### Maintenance

Regular maintenance tasks:

1. **Updates**
   ```bash
   # Update images
   kubectl set image deployment/user-service user-service=bankapp/user-service:new-version
   ```

2. **Monitoring**
   - Check service health
   - Review error logs
   - Monitor resource usage

3. **Backup Verification**
   - Test backup restoration
   - Verify backup integrity
   - Update backup schedules
``` 
</rewritten_file>