# Task Management System (TMS)
[![Java](https://img.shields.io/badge/Java-8+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://java.com)
[![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

[![License: Polyform NC](https://img.shields.io/badge/License-Polyform%20NC%201.0.0-blue.svg)](https://polyformproject.org/licenses/noncommercial/1.0.0/)
[![Status: Archived](https://img.shields.io/badge/Status-Archived-lightgrey?style=flat-square)](.)
> **⚠️ Legacy Learning Project** — This is my first Java project, created as a learning exercise. It is **not actively maintained** and should be used for educational purposes only.

A Java-based Task Management System with a modern UI built using FlatLaf and DarkLaf themes.

## 📚 About This Project

This project was created as an educational exercise to learn Java fundamentals including:
- GUI development with Swing
- Database connectivity with JDBC
- Maven build automation
- Basic UI frameworks (FlatLaf & DarkLaf)

**Status:** Archived / Educational  
**Maintenance:** No longer actively maintained

## 🚀 Version

**v0.1.0-alpha** — Initial educational release

## 📋 Features

- Task creation, editing, and deletion
- Modern dark/light theme support (FlatLaf & DarkLaf)
- MySQL database backend
- Clean and intuitive user interface

## 🛠️ Tech Stack

- **Language:** Java 8+
- **Build Tool:** Maven
- **UI Framework:** FlatLaf 2.0, DarkLaf 3.0.2
- **Database:** MySQL (Connector/J 8.0.26)

## 📦 Prerequisites

- Java 8 or higher
- Maven 3.6+
- MySQL Server (configured with credentials via environment variables)

### Configuration

Before running, set the following environment variables or create a `.env.example` template:

```bash
export DB_URL=jdbc:mysql://localhost:3306/task_management
export DB_USER=root
export DB_PASSWORD=your_password_here
```

## 📦 Building

```bash
mvn clean package
```

## ▶️ Running

```bash
java -jar target/taskmanagement-1.0-SNAPSHOT.jar
```

## ⚠️ Known Security Vulnerabilities

> **Important:** This project is not actively maintained and contains known security vulnerabilities. It should **not be used in production**.

| Dependency | Version | CVE | Severity |
|------------|---------|-----|----------|
| mysql-connector-java | 8.0.26 | [CVE-2023-22102](https://github.com/advisories/GHSA-m6vm-37g8-gqvh) | HIGH |
| mysql-connector-java | 8.0.26 | [CVE-2022-21363](https://github.com/advisories/GHSA-g76j-4cxx-23h9) | MEDIUM |
| mysql-connector-java | 8.0.26 | [CVE-2021-2471](https://github.com/advisories/GHSA-w6f2-8wx4-47r5) | MEDIUM |

## 🔒 Security Note

This project previously contained hardcoded database credentials in the source code. These have been removed and replaced with environment variable configuration. **Do not commit credentials to version control.**

## 📄 License

This project is licensed under the [Creative Commons Attribution-NonCommercial 4.0 International License](LICENSE).

You are free to:
- 👁️ View and study the code
- 📝 Modify it for educational purposes
- 🔄 Share it with attribution

With restrictions:
- ❌ Cannot use for commercial purposes
- ❌ Must give appropriate credit

See [LICENSE](LICENSE) file for full details.

## 📖 Learning Resources

This project demonstrates:
- Multi-threaded Java applications
- Swing GUI framework basics
- JDBC database operations
- Maven project structure
- Custom UI theming

## 🚀 Getting Started for Learning

1. Clone the repository
2. Set up MySQL database
3. Configure environment variables
4. Build with Maven
5. Run the application
6. Review the code to understand the implementation

## 🤝 Contributing

While this is a legacy project, educational improvements and bug fixes are welcome as learning exercises. Please open an issue or submit a pull request if you find areas for improvement.

## 📝 Notes for Future Contributors

- Code could benefit from modernization (Java records, NIO, etc.)
- Consider refactoring to use modern frameworks (Spring Boot, JavaFX)
- GUI code could be improved with better separation of concerns
- Database layer could use JPA/Hibernate instead of raw JDBC

## License

Copyright © 2026 Sricharan Suresh (github.com/verycareful)

This project is licensed under the **[Polyform Noncommercial License 1.0.0](https://polyformproject.org/licenses/noncommercial/1.0.0/)**.
You may use, copy, and modify this software for non-commercial purposes only.
Commercial use of any kind is prohibited without explicit written permission from the author.

See the [LICENSE](LICENSE) file for the full license text, or visit
[https://polyformproject.org/licenses/noncommercial/1.0.0/](https://polyformproject.org/licenses/noncommercial/1.0.0/).

For commercial licensing inquiries, contact [sricharanc03@gmail.com](mailto:sricharanc03@gmail.com).
