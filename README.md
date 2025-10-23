# HTTP Inspector

A Spring Boot application for inspecting HTTP requests and responses. This tool provides comprehensive endpoints to analyze HTTP traffic, test different status codes, and monitor application health.

## 🚀 Features

- **HTTP Request Inspection**: Detailed analysis of incoming requests
- **Response Testing**: Generate custom HTTP status codes for testing
- **Request Echoing**: Echo requests back for debugging
- **Client Information**: Extract client details and headers
- **Health Monitoring**: Built-in health checks and metrics
- **Modern UI**: Beautiful web interface with clickable endpoints
- **Docker Support**: Ready-to-use containerized deployment

## 📋 Available Endpoints

### HTTP Inspector Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/inspector/request` | Get detailed information about the current HTTP request |
| `POST` | `/inspector/echo` | Echo the request back as a response for testing purposes |
| `GET` | `/inspector/headers` | Get all request headers |
| `GET` | `/inspector/parameters` | Get request parameters |
| `GET` | `/inspector/client` | Get client information |
| `GET` | `/inspector/health` | Health check endpoint |
| `GET` | `/inspector/status` | Return response with status code based on query parameter |

### Actuator Endpoints (Optional)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/actuator/env` | Environment variables and properties |
| `GET` | `/actuator/health` | Application health status |
| `GET` | `/actuator/info` | Application information |
| `GET` | `/actuator/metrics` | Application metrics |

## ⚙️ Configuration

### Application Properties

Create or modify `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=httpinspector

# Actuator configuration
management.endpoints.web.exposure.include=env,health,info,metrics
management.endpoint.env.show-values=always
management.endpoint.env.show-details=always

# Application configuration
app.show-actuator-endpoints=false
```

### Configuration Options

| Property | Default | Description |
|----------|---------|-------------|
| `app.show-actuator-endpoints` | `false` | Show actuator endpoints on home page |
| `management.endpoints.web.exposure.include` | `env,health,info,metrics` | Exposed actuator endpoints |
| `management.endpoint.env.show-values` | `always` | Show environment variable values |
| `management.endpoint.env.show-details` | `always` | Show detailed environment information |

### Environment Variables

You can override configuration using environment variables:

```bash
# Show actuator endpoints on home page
export APP_SHOW_ACTUATOR_ENDPOINTS=true

# Custom JVM options
export JAVA_OPTS="-Xmx1g -Xms512m"
```

## 🛠️ Development

### Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd httpinspector
   ```

2. **Build the application**
   ```bash
   ./gradlew clean build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**
   - Home page: http://localhost:8080/
   - API endpoints: http://localhost:8080/inspector/*

### Testing Endpoints

```bash
# Get request information
curl http://localhost:8080/inspector/request

# Test custom status code
curl http://localhost:8080/inspector/status?code=404

# Echo a request
curl -X POST http://localhost:8080/inspector/echo -d "Hello World"

# Get client information
curl http://localhost:8080/inspector/client
```

## 🐳 Docker Deployment

### Build and Run

1. **Build the application and Docker image**
   ```bash
   ./build-and-docker.sh
   ```

2. **Run with Docker**
   ```bash
   docker run -p 8080:8080 httpinspector
   ```

3. **Run with Docker Compose**
   ```bash
   docker-compose up
   ```

### Docker Configuration

The application includes:
- **Multi-stage Dockerfile** for optimized builds
- **Docker Compose** for easy deployment
- **Health checks** for container monitoring
- **Non-root user** for security

## 📊 Usage Examples

### Status Code Testing

```bash
# Test different HTTP status codes
curl http://localhost:8080/inspector/status?code=200
curl http://localhost:8080/inspector/status?code=404
curl http://localhost:8080/inspector/status?code=500
curl http://localhost:8080/inspector/status?code=418&message="I'm a teapot"
```

### Request Inspection

```bash
# Get detailed request information
curl http://localhost:8080/inspector/request

# Get only headers
curl http://localhost:8080/inspector/headers

# Get request parameters
curl http://localhost:8080/inspector/parameters?param1=value1&param2=value2
```

### Echo Testing

```bash
# Echo POST request
curl -X POST http://localhost:8080/inspector/echo \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello World"}'
```

## 🔧 Advanced Configuration

### Custom JVM Options

```bash
# Set custom JVM options
export JAVA_OPTS="-Xmx1g -Xms512m -XX:+UseG1GC"

# Run the application
java $JAVA_OPTS -jar build/libs/*.war
```

### Profile-specific Configuration

Create profile-specific properties files:

- `application-dev.properties` - Development settings
- `application-prod.properties` - Production settings

```bash
# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 🚀 Production Deployment

### Environment Variables

```bash
# Production configuration
export SPRING_PROFILES_ACTIVE=prod
export APP_SHOW_ACTUATOR_ENDPOINTS=false
export JAVA_OPTS="-Xmx1g -Xms512m -XX:+UseContainerSupport"
```

### Docker Production

```bash
# Build production image
docker build -t httpinspector:latest .

# Run with production settings
docker run -d \
  --name httpinspector \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e APP_SHOW_ACTUATOR_ENDPOINTS=false \
  httpinspector:latest
```

## 📝 API Documentation

### Request Information Response

```json
{
  "method": "GET",
  "uri": "/inspector/request",
  "protocol": "HTTP/1.1",
  "remoteAddress": "127.0.0.1",
  "headers": {
    "User-Agent": "curl/7.68.0",
    "Accept": "*/*"
  },
  "timestamp": "2024-01-15T10:30:45.123"
}
```

### Status Code Response

```json
{
  "statusCode": 404,
  "message": "Not Found",
  "timestamp": "2024-01-15T10:30:45.123",
  "requestUri": "/inspector/status",
  "requestMethod": "GET"
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For issues and questions:
- Create an issue in the repository
- Check the application logs
- Review the actuator endpoints for debugging

---

**Happy HTTP Inspecting!** 🔍
