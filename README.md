# Task Management System (TMS)

A Java-based Task Management System with a modern UI built using FlatLaf and DarkLaf themes.

## 🚀 Version

**v0.1.0-alpha** — Initial release

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

## 📦 Building

```bash
mvn clean package
```

## ▶️ Running

```bash
java -jar target/taskmanagement-1.0-SNAPSHOT.jar
```

## ⚠️ Known Security Vulnerabilities

> **Important:** This alpha release contains known security vulnerabilities that will be addressed in the next release.

| Dependency | Version | CVE | Severity | Status |
|------------|---------|-----|----------|--------|
| mysql-connector-java | 8.0.26 | [CVE-2023-22102](https://github.com/advisories/GHSA-m6vm-37g8-gqvh) | **HIGH** | 🔴 Open |
| mysql-connector-java | 8.0.26 | [CVE-2022-21363](https://github.com/advisories/GHSA-g76j-4cxx-23h9) | MEDIUM | 🟡 Open |
| mysql-connector-java | 8.0.26 | [CVE-2021-2471](https://github.com/advisories/GHSA-w6f2-8wx4-47r5) | MEDIUM | 🟡 Open |

### Planned Fixes (v0.2.0)
- Upgrade `mysql-connector-java` to 8.1.2+ to resolve all listed CVEs

## 📄 License

This project is provided as-is for educational and development purposes.

## 🤝 Contributing

Contributions are welcome! Please open an issue or submit a pull request.
