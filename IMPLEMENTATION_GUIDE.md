# 📖 IMPLEMENTATION GUIDE

## Recent Additions (March 9, 2026)

### 1️⃣ Category Management Module
**Files Created:**
- `CategoryController.java` - REST endpoints
- `CategoryService.java` - Business logic
- `CategoryMapper.java` - DTO mapping
- `CategoryCreationRequest.java` - Create DTO
- `CategoryUpdateRequest.java` - Update DTO
- `CategoryRespone.java` - Response DTO

**Endpoints:**
```
GET    /categories           - Get all (public)
GET    /categories/{id}      - Get by ID (public)
POST   /categories           - Create (STAFF only)
PUT    /categories/{id}      - Update (STAFF only)
DELETE /categories/{id}      - Delete (STAFF only)
```

### 2️⃣ Order Enhancements
**Updated Files:**
- `OrderController.java` - Added 3 endpoints
- `OrderService.java` - Added logic

**New Endpoints:**
```
GET    /orders/{orderId}         - Get order details
DELETE /orders/{orderId}         - Cancel order (restore inventory)
GET    /orders/status/{status}   - Get by status (STAFF)
```

**Features:**
- Permission validation (owner or STAFF)
- Status validation before cancellation
- Automatic inventory restoration
- Comprehensive error handling

### 3️⃣ Cart Improvements
**Updated Files:**
- `CartController.java` - Added 2 endpoints
- `CartService.java` - Added logic

**New Endpoints:**
```
PUT    /cart/items/{productId}   - Update quantity (validate stock)
DELETE /cart                     - Clear entire cart
```

### 4️⃣ User Account Management
**Files Created:**
- `ChangePasswordRequest.java` - Password change DTO

**Updated Files:**
- `UserController.java` - Added endpoint
- `UserService.java` - Added logic

**New Endpoint:**
```
PUT    /users/change-password    - Change password securely
```

**Security:**
- Old password validation
- Password confirmation check
- Password hashing with BCrypt
- Minimum 8 character requirement

### 5️⃣ Product Filtering & Discovery
**Updated Files:**
- `ProductController.java` - Added 4 endpoints
- `ProductService.java` - Added logic
- `ProductRepository.java` - Added queries

**New Endpoints:**
```
GET    /products/category/{catId}           - By category
GET    /products/filter?minPrice=X&maxPrice=Y - Price range
GET    /products/trending?limit=10          - Top sellers
```

**Repository Methods Added:**
```java
List<Product> findByPriceBetween(Double min, Double max);
List<Product> findTopSellingProducts(int limit);
```

---

## 🔧 Architecture Overview

### Package Structure
```
com.iuh.kidclothes/
├── configuration/          - Security, JWT, app config
├── controller/             - REST endpoints
├── dto/
│   ├── request/           - Input DTOs
│   └── respone/           - Output DTOs (note: typo in original)
├── entity/                - MongoDB documents
├── enums/                 - Enums (OrderStatus, Role, etc.)
├── exception/             - Custom exceptions & handlers
├── mapper/                - MapStruct mappers
├── repository/            - MongoDB repositories
└── service/               - Business logic
```

### Request/Response Pattern
```
Controller → Service → Repository → Database
    ↓
  DTO Mapping
    ↓
  Response
```

### Error Handling
All endpoints return consistent `ApiResponse` format:
```json
{
  "code": 1000,
  "message": "Success message (optional)",
  "result": {...}
}
```

---

## 🔐 Security Features

### Authentication
- JWT tokens (Bearer)
- Token validation on secured endpoints
- Configurable token expiration

### Authorization
- Role-based (USER, STAFF, ADMIN)
- Method-level authorization with @PreAuthorize
- Permission validation in service layer

### Validation
- Input validation with Jakarta Validation
- Custom error messages
- Request constraint checking

### Password Security
- BCrypt hashing (strength 10)
- Old password verification on change
- Minimum 8 characters
- Confirmation matching

---

## 🧪 Testing Checklist

### Unit Tests
```bash
mvn test
```

### Manual API Testing
1. Authenticate (POST /auth/signin)
2. Test public endpoints (no auth)
3. Test authenticated endpoints (with token)
4. Test authorized endpoints (STAFF role)
5. Test error cases (invalid inputs)

### Key Test Cases
- ✅ Create category with valid data
- ❌ Create category without auth
- ❌ Create category with invalid role
- ✅ Update order status
- ❌ Cancel completed order
- ✅ Update cart quantity
- ❌ Quantity exceeds stock
- ✅ Change password
- ❌ Wrong old password
- ✅ Filter products by price
- ❌ Min price > max price

---

## 📦 Dependencies

### Core
- Spring Boot 3.x
- Spring Data MongoDB
- Spring Security
- Spring Web

### JWT & Auth
- Spring OAuth2 Resource Server
- JJWT or similar JWT library

### Utilities
- Lombok
- MapStruct (annotation processor)
- Jakarta Validation API

### Build
- Maven 3.8+
- Java 17+

---

## 🚀 Running the Application

### 1. Prerequisites
```bash
# MongoDB running on localhost:27017
mongod

# Or use Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 2. Build
```bash
mvn clean install
```

### 3. Run
```bash
mvn spring-boot:run
```

### 4. API Accessible at
```
http://localhost:8080/api
```

---

## 📝 Configuration

### application.properties
```properties
# Server Port
server.port=8080

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/kidclothes

# JWT Settings
jwt.signerKey=<your-secret-key>
jwt.valid=3600  # 1 hour

# Logging
logging.level.com.iuh.kidclothes=INFO
logging.level.org.springframework.security=DEBUG
```

---

## 🔍 Code Quality

### Conventions Applied
- ✅ Consistent naming (camelCase for variables, PascalCase for classes)
- ✅ Proper package organization
- ✅ Meaningful variable names
- ✅ Comprehensive logging
- ✅ Documentation comments
- ✅ Error message clarity

### Design Patterns
- ✅ Service layer pattern
- ✅ Repository pattern
- ✅ Mapper pattern (MapStruct)
- ✅ DTO pattern
- ✅ Exception handling pattern
- ✅ Authorization pattern

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring Security](https://spring.io/projects/spring-security)
- [MapStruct](https://mapstruct.org/)

---

## 👨‍💻 Development Tips

### Common Tasks

**Adding a New Endpoint:**
1. Create Request DTO in `dto/request/`
2. Create Response DTO in `dto/respone/`
3. Add mapping in appropriate Mapper
4. Add method in Service
5. Add @GetMapping/@PostMapping in Controller
6. Add validation and authorization

**Testing an Endpoint:**
1. Open Postman
2. Create new request
3. Set method and URL
4. Add headers (if needed)
5. Add body (if needed)
6. Send and check response

**Debugging:**
1. Enable DEBUG logging in application.properties
2. Use IDE debugger (set breakpoints)
3. Check logs in console
4. Use Postman request history

---

## ✅ Final Checklist

- [x] All DTOs have Lombok annotations
- [x] All Controllers have @RestController & @RequestMapping
- [x] All Services are @Service with dependency injection
- [x] All Repositories extend MongoRepository
- [x] Authorization checks in place
- [x] Proper error handling
- [x] Input validation on requests
- [x] Logging for important operations
- [x] Documentation files created

---

**Last Updated:** March 9, 2026  
**Version:** 1.2.0

