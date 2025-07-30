

# Aurora Boot Open Source Backend Management System

## Project Introduction
Aurora Boot is an open-source backend management system based on Spring Boot. It provides complete functions including permission management, authentication center, system configuration, log management, dictionary management, notifications and announcements, file upload, WeChat Mini Program login, and SMS verification code login. This project is suitable for quickly building enterprise-level backend management systems.

## Functional Modules

### Authentication Center
- Login with username and password
- Login with SMS verification code
- WeChat authorized login (Web and Mini Program)
- Refresh token
- Logout
- Captcha generation and retrieval

### User Management
- User paginated list
- Add new users
- Edit users
- Delete users
- User status management
- User import/export
- User role assignment
- Edit user information (avatar, nickname, phone number, email, etc.)
- Password management (change, reset)

### Role Management
- Role paginated list
- Add roles
- Edit roles
- Delete roles
- Role status management
- Role menu permission assignment

### Menu Management
- Menu list
- Dropdown menu list
- Menu route list
- Add menus
- Edit menus
- Delete menus
- Menu visibility status management

### System Configuration
- System configuration paginated list
- Add system configuration
- Edit system configuration
- Delete system configuration
- Refresh system configuration cache

### Log Management
- Log paginated list
- Access trend statistics
- Access volume statistics

### Notifications and Announcements
- Notifications and announcements paginated list
- Add notifications
- Edit notifications
- Delete notifications
- Publish/withdraw notifications
- Mark notifications as read

### Dictionary Management
- Dictionary paginated list
- Dictionary item management
- Add/edit/delete dictionaries and dictionary items

### File Management
- Supports multiple storage methods: local, Alibaba Cloud OSS, MinIO, etc.
- File upload
- File deletion

### SMS Service
- Supports Alibaba Cloud SMS service
- Send SMS verification code
- Login with SMS verification code

### Email Service
- Supports email sending
- Send email verification code
- Bind/change email

### WebSocket Online User Management
- Real-time online user statistics
- User connection/disconnection notifications
- Message broadcasting

## Technology Stack

- **Spring Boot**: Rapid construction of microservice architecture
- **Spring Security**: Permission control and authentication management
- **JWT / Redis**: Token management
- **MyBatis Plus**: ORM framework
- **Lombok**: Simplifies Java code
- **Swagger / SpringDoc**: API documentation generation
- **Redis**: Caching and verification code management
- **WebSocket**: Real-time communication
- **OSS / MinIO / Local**: File storage support
- **MapStruct**: Object mapping and conversion
- **Validation**: Parameter validation
- **Logback**: Log management

## Quick Start

### Environment Requirements
- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js (optional, for frontend)

### Startup Steps
1. Clone the project:
   ```bash
   git clone https://gitee.com/RainSauce/aurora_boot.git
   ```
2. Modify the configuration file:
   ```yaml
   # application.yml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/aurora_boot?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
       username: root
       password: root
   ```
3. Initialize the database:
   - Execute the SQL file `src/main/resources/schema.sql` to create table structures
4. Start the project:
   ```bash
   cd aurora_boot
   mvn spring-boot:run
   ```

### API Documentation
Visit `/swagger-ui.html` or `/v3/api-docs` to view the API documentation.

## Project Structure
- `auth`: Authentication center module
- `system`: Core system module (users, roles, menus, logs, configurations, etc.)
- `common`: Common utility classes and exception handling
- `core`: Core security and permission control
- `config`: System configuration classes
- `shared`: Shared module (file, SMS, email, etc.)

## Contribution Guide
Code contributions are welcome! Please follow these steps:
1. Fork the project
2. Create a new branch
3. Submit a Pull Request

## License
This project uses the [MIT License](https://opensource.org/licenses/MIT).

## Contact Information
- Author: LightRain
- URL: https://rainrem.top/
