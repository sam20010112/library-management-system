# Library Management System - Deployment Guide

## 系統架構

本系統採用三層式架構：
- **Web Server**: Vue.js 前端 (Port 3000)
- **Application Server**: Spring Boot 後端 (Port 8080)
- **Database**: MySQL 資料庫 (Port 3306)

## 部屬完成後請在 http://localhost:3000 使用


## 環境需求

### 後端環境
- Java 11 或以上
- Maven 3.6 或以上
- MySQL 8.0 或以上

### 前端環境
- Node.js 14 或以上
- npm 6 或以上

## 部署步驟

### 1. 資料庫設定

1. 安裝 MySQL 8.0
2. 建立資料庫：(可使用XAMPP)
```sql
CREATE DATABASE library_system;
```

3. 修改資料庫連線設定（`backend/src/main/resources/application.yml`）：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

4. 執行 DDL 腳本：
(可使用XAMPP dashboard執行腳本)
```bash
mysql -u root -p library_system < DB/DDL.sql
```

5. 執行 DML 腳本（載入範例資料）：
(可使用XAMPP dashboard執行腳本)
```bash
mysql -u root -p library_system < DB/DML.sql
```


### 自動化部署

執行start.bat  !!!請在完成資料庫建置後再執行!!!

### 手動部屬

### 1. 後端部署

1. 進入後端目錄：
```bash
cd backend
```


3. 編譯並執行：
```bash
mvn clean compile
mvn spring-boot:run
```

後端服務將在 http://localhost:8080 啟動

### 2. 前端部署

1. 進入前端目錄：
```bash
cd frontend
```

2. 安裝依賴：
```bash
npm install
```

3. 啟動開發伺服器：
```bash
npm run serve
```

前端應用將在 http://localhost:3000 啟動
