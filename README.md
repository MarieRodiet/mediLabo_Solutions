# Medilabo Solutions

## Introduction
Medilabo Solutions is designed to support healthcare professionals in managing patient care more effectively. This comprehensive application allows doctors to perform a variety of critical functions including registering new patients, updating patient information, documenting clinical notes, assessing health risks, and removing patient records when necessary. To ensure the confidentiality and integrity of patient data, the application enforces secure access controls requiring both a username and password for user authentication.

## Technologies Used
This project utilizes the following technologies:

- **Java**: OpenJDK 21
- **Spring Boot**: Version 3.2.4
- **Spring Security**: Version 6.2.3
- **Spring Cloud**: Version 2023.0.2
- **Thymeleaf**: Version 3.3.3
- **MySQL**: Version 8.0.33
- **MongoDB**: Version latest

## Getting Started
To get started with Medilabo Solutions, install Maven, Docker and Docker Compose:

1. **Clone the Repository**:
   ```bash
   https://github.com/MarieRodiet/mediLabo_Solutions.git
   ```
  
2. **Go to the root of the directory**:
   ```bash
   cd mediLabo_Solutions
    ```

3. **Generate a security key**:
   - Go to the gateway/src/main/java/com/mariemoore/gateway/security/KeyGenerator.java
   - Run main to generate a BASE64-encoded secret key in the console
   - Paste the key in gateway/src/main/resources/application.properties
   
4. **Run the Gateway and the backend microservices with docker**:
   ```bash
   docker compose-up build
   ```
   
5. **Run the client UI**:
   Open the client UI microservice in an IDE and start the app
   
6. Login with these credentials:
   - user@gmail.com
   - password1

   
## Green Code Recommendations
Here are several recommendations for implementing Green Code practices in this project:

### 1. Writing Clean Code
   - **Minimize redundant computations** and recursive logic.
   - Use **efficient algorithms** and optimized classes.
   - Conduct **code reviews** to eliminate errors such as infinite loops and bugs.

### 2. Optimizing Decentralized Cloud Computing
   - Assess the feasibility of using **serverless cloud computing** services to optimize resource utilization.

### 3. Incorporating Energy-Efficient Design Models
   - Implement **lazy-loading** to load resources only when necessary.
   - Optimize **database queries** and utilize caching techniques to reduce the need for repeated data retrieval.

### 4. Choosing Green Hosting and Sustainable Data Centers
   - Select hosting providers and data centers that utilize **renewable energy sources** and energy-efficient cooling systems.

### 5. Monitoring and Optimizing Performance
   - Perform regular **benchmarks** to monitor application performance.
   - Use **monitoring tools** to identify areas of code that need optimization.

### 6. Software Lifecycle Management
   - Implement **version control systems** and automated testing to ensure smooth deployments and updates.

## Contribution
We encourage all team members to contribute to the implementation of these Green Code practices. If you have additional ideas or suggestions for improvement, please feel free to share them!

## Architecture
| Name                | Port | Definition                                                                                         |
|---------------------|------|----------------------------------------------------------------------------------------------------|
| **Client UI**       | 9004 | The user interface for healthcare professionals to access and interact with patient data and assessments.   |
| **Gateway**         | 9002 | Routes requests from the client UI to the appropriate backend microservices. Takes care of authentication and authorization   |
| **Patients Service**| 9001 | Microservice connected to a MariaDB database to manage patient registrations and records.          |
| **Notes Service**   | 9000 | Microservice interacting with MongoDB to handle medical notes associated with patients.            |
| **Health Risk Service** | 9003 | Provides assessments of health risks based on patient data processed through the application. No persisted data     |

![alt text](architecture.png)

## Security

1. The user enters their email address and password on the client's login page. The POST request is then sent to the gateway with this data.

2. The gateway checks if there is a match with the user pre-registered at its initialization.

3. The client receives a 403 error if the authentication failed or a token if it succeeded.

4. The client stores the token in a cookie, which will be inserted into the headers of all requests to the microservices secured by the gateway. The token serves as proof of the user's identity and authorization to access secure content.
![alt text](security.png)
