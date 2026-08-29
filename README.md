# Banking App – REST API for Banking Application

## Description

This is a backend application built with Spring Boot that simulates core banking functionalities:

- User management (registration, JWT authentication, update, delete)
- Bank account management (create, list, search by IBAN, delete with zero balance)
- Banking transactions (deposit, withdraw, transfer between accounts)
- Transaction history
- JWT-based security and role-based access control (USER / ADMIN)
- Interactive API documentation via Swagger UI

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21      | Programming language |
| Spring Boot | 4.1.0   | Main framework |
| Spring Security | 6.x     | Security and JWT authentication |
| Spring Data JPA | 3.x     | Database access |
| MySQL | 8.x     | Relational database |
| JWT (JJWT) | 0.12.6  | JWT generation and validation |
| Swagger / OpenAPI | 3.1.0   | Interactive API documentation |
| Maven | 4.0.0   | Dependency management |

## How to Run the Application

### 1. Clone the repository

```bash
git clone https://github.com/alin-tudor-06/Online-Banking-App.git
cd Online-Banking-App
```
---

### 2. Configure MySQL database
Ensure MySQL is running on localhost:3306. Create a database:

```sql
CREATE DATABASE banking_db;
```
You can change the database name, username, or password in application.properties.

---

### 3. Configure application.properties
In src/main/resources/application.properties, set:


```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


jwt.secret=your-very-long-secret-key-at-least-32-characters
jwt.expirationMs=86400000
```
Note: Replace your_password_here with your MySQL password and jwt.secret with a strong secret key (at least 32 characters).

---

### 4. Run the application
Use IntelliJ IDEA or run from the terminal:

```bash
mvn clean spring-boot:run
```
The application will start on http://localhost:8080.

## Interactive Documentation (Swagger UI)
Once the application is running, open:

```text
http://localhost:8080/swagger-ui/index.html
```

### How to test protected endpoints

- Use POST /api/auth/register to create a user.

- Use POST /api/auth/login to obtain a JWT token.

- In Swagger UI, click the Authorize button (the padlock icon in the top right).

- In the Value field, enter only the token (without the Bearer prefix).

- Click Authorize, then Close.

- You can now test any protected endpoint.

## Security and Roles
| Role | Permissions|
|------|-------------|
|USER	|Can view own accounts and transactions, deposit/withdraw/transfer money, update own profile.|
|ADMIN	|Can view all users, all accounts, all transactions; can delete any user or account (with zero balance restriction).|

## Main Endpoints

### Authentication (/api/auth)
| Method | Endpoint   | Description |
|--------|------------|-------------|
| POST	  | /register	 | Register a new user |
|POST	|/login	|Authenticate and return JWT token

### Users (/api/users)
| Method | Endpoint | Description |
|--------|----------|-------------|
|POST	|/	|Create a new user (ADMIN only)
|GET	|/	|List all users (ADMIN only)
|GET	|/cnp/{cnp}	|Find user by CNP (own or ADMIN)
|GET	|/by-name	|Search users by first and last name
|PUT	| /{cnp}   |	Update email and/or address (own or ADMIN)
|DELETE	| /{cnp}   | 	Delete a user (own or ADMIN) 

### Accounts (/api/accounts)
| Method | Endpoint   | Description |
|--------|------------|-------------|
|POST|	/	|Create an account for the authenticated user
|GET|	/	|List all accounts of the authenticated user
|GET|	/number/{accountNumber}	|Find account by IBAN number
|GET|	/admin/all	|List all accounts in the system (ADMIN only)
|DELETE|	/{accountNumber}	|Delete an account (zero balance, owner or ADMIN)

### Transactions (/api/transactions)
| Method | Endpoint   | Description |
|--------|------------|-------------|
|POST	|/deposit	|Deposit money into an account (owner or ADMIN)
|POST	|/withdraw	|Withdraw money from an account (owner or ADMIN)
|POST	|/transfer	|Transfer money between accounts (source account owner or ADMIN)
|GET	|/account/{accountNumber}	|Get transaction history for an account (owner or ADMIN)
|GET	|/admin/all	|List all transactions in the system (ADMIN only)


## Testing with Postman
- Import the Postman collection (if exported) or manually create the requests.

- Set the variable ```baseUrl = http://localhost:8080.```

- For each protected request, add the header:
```Authorization: Bearer <token>```

- Obtain the token from ```POST /api/auth/login.```

## Project Structure
```text
src/
├── main/
│   ├── java/com/alin/banking/
│   │   ├── config/          # OpenAPI (Swagger) configuration
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Global exception handling
│   │   ├── model/           # JPA Entities
│   │   ├── repository/      # JPA Repository interfaces
│   │   ├── security/        # JWT, Spring Security, filters
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.properties  # Configuration settings
│       └── ...
└── pom.xml                  # Maven dependencies
```

## Main Dependencies (pom.xml)
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.0</version>
</parent>

<dependencies>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.1.0</version>
</dependency>

</dependencies>
```

## Notes
- JWT Secret must be at least 32 characters long.

- Token expiration is set to 24 hours (configurable in application.properties).

- Database tables are automatically created on first run (if ddl-auto is set to update).

- Swagger public endpoints do not require a token; protected ones require authorization.

## Author

Constantin-Alin Tudor – GitHub: https://github.com/alin-tudor-06

## License
This project is intended solely for learning and personal development purposes.