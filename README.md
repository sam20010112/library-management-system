# 📚 Library Management System

一個基於 Spring Boot 和 Vue.js 的全端圖書館管理系統，提供完整的書籍借閱管理功能。

![Vue.js](https://img.shields.io/badge/Vue.js-3.x-4FC08D?style=flat&logo=vue.js&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7+-6DB33F?style=flat&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)


## 程式部屬說明請看 DEPLOYMENT.md
## 功能特色

###用戶管理
- 手機號碼註冊與登入
- JWT Token 身份認證
- 密碼加鹽安全加密，資料庫無明碼

###借閱系統
- 線上借書與還書
- 借閱記錄追蹤
- 借閱統計

###安全防護
- SQL 注入防護
- XSS 攻擊防護
- 輸入驗證與過濾
- 安全的 API 設計

##技術棧

### 後端技術
- **Java 8+** - 核心開發語言
- **Spring Boot 2.7+** - 應用框架
- **Spring Security** - 安全認證
- **Spring Data JPA** - 數據持久化
- **MySQL 8.0** - 關係型資料庫
- **Maven** - 專案管理工具

### 前端技術
- **Vue.js 3** - 前端框架
- **Element Plus** - UI 組件庫
- **Vuex** - 狀態管理
- **Vue Router** - 路由管理
- **Axios** - HTTP 客戶端

### 資料庫設計
- **MySQL** - 主資料庫
- **Stored Procedures** - 業務邏輯封裝
- **JPA Entities** - 物件關聯映射
- **Transaction Management** - 事務管理

##專案結構

```
library-management-system/
├── 📁 backend/                    # Spring Boot 後端
│   ├── 📁 src/main/java/com/library/
│   │   ├── 📁 controller/         # REST API 控制器
│   │   ├── 📁 service/            # 業務邏輯層
│   │   ├── 📁 repository/         # 數據存取層
│   │   ├── 📁 entity/             # JPA 實體類
│   │   ├── 📁 dto/                # 數據傳輸物件
│   │   ├── 📁 config/             # 配置類
│   │   └── 📁 util/               # 工具類
│   ├── 📁 src/main/resources/
│   │   └── 📄 application.yml     # 應用配置
│   └── 📄 pom.xml                 # Maven 配置
├── 📁 frontend/                   # Vue.js 前端
│   ├── 📁 src/
│   │   ├── 📁 components/         # 可重用組件
│   │   ├── 📁 views/              # 頁面組件
│   │   ├── 📁 services/           # API 服務
│   │   ├── 📁 store/              # Vuex 狀態管理
│   │   ├── 📁 router/             # 路由配置
│   │   └── 📁 utils/              # 工具函數
│   ├── 📄 package.json            # NPM 配置
│   └── 📄 vue.config.js           # Vue 配置
├── 📁 DB/                         # 資料庫腳本
│   ├── 📄 DDL.sql                 # 資料庫結構
│   └── 📄 DML.sql                 # 測試數據
├── 📄 README.md                   # 專案說明
├── 📄 DEPLOYMENT.md               # 部署指南
├── 📄 start.bat                   # Windows 啟動腳本
└── 📄 start.sh                    # Linux/Mac 啟動腳本
```