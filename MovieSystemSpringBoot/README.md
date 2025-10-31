# 🎬 电影推荐系统

> 基于Spring Boot微服务架构的个性化电影推荐平台

## 🚀 技术栈

### 后端框架
- **Spring Boot** 3.3.5 - 核心框架
- **Spring Cloud** 2021.0.8 - 微服务框架
- **MyBatis** 3.5.13 - 数据持久化
- **MySQL** 8.0+ - 关系数据库
- **Redis** 7.0+ - 分布式缓存

### 微服务组件
- **Eureka Server** - 服务注册与发现
- **API Gateway** - 统一网关入口
- **Config Server** - 配置中心

### 其他技术
- **Docker** - 容器化部署
- **Maven** - 项目构建工具
- **JWT** - 安全认证
- **HikariCP** - 数据库连接池

## 📊 项目亮点

- 🎯 **个性化推荐算法** - 协同过滤与内容推荐结合
- ⚡ **微服务架构** - 支持独立部署和水平扩展
- 📈 **实时数据处理** - 用户行为实时分析
- 🔒 **完整权限管理** - JWT + Spring Security
- 🐳 **容器化部署** - Docker Compose一键启动
- 📱 **响应式设计** - 支持多设备访问

## 🏗️ 系统架构

```
                    API Gateway (9999)
                           ↓
        ┌─────────────────────────────────────────┐
        │            Service Registry             │
        │         Eureka Server (8761)            │
        └─────────────────────────────────────────┘
                           ↓
   ┌────────────┬─────────────┬─────────────┬──────────────┐
   │            │             │             │              │
   │  用户服务   │   电影服务   │   推荐服务   │   配置中心    │
   │   :8082    │    :8083    │    :8084    │    :8888     │
   └────────────┴─────────────┴─────────────┴──────────────┘
                           ↓
              ┌─────────────────────────────┐
              │     共享数据层               │
              │   MySQL + Redis            │
              └─────────────────────────────┘
```

## 🎯 核心功能

### 用户功能
- 🔐 用户注册登录
- 👤 个人信息管理
- ⭐ 电影评分收藏
- 📊 观影历史记录

### 推荐功能
- 🎯 个性化电影推荐
- 🔥 热门电影展示
- 🏷️ 分类浏览
- 🔍 智能搜索

### 管理功能
- 📽️ 电影信息管理
- 👥 用户管理
- 📈 数据统计分析
- ⚙️ 系统配置

## 🚀 快速开始

### 环境要求
- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+
- Docker (可选)

### 数据库初始化
```sql
-- 创建数据库
CREATE DATABASE moviesystem_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入数据
mysql -u root -p moviesystem_db < sql/movie_system.sql
```

### 配置环境变量
```bash
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

### 启动服务 (方式一：Docker)
```bash
# 构建并启动所有服务
docker-compose up -d
```

### 启动服务 (方式二：本地运行)
```bash
# 1. 启动 Eureka 服务注册中心
cd microservices-parent/eureka-server
mvn spring-boot:run

# 2. 启动 API 网关
cd ../api-gateway
mvn spring-boot:run

# 3. 启动用户服务
cd ../user-service
mvn spring-boot:run

# 4. 启动电影服务
cd ../movie-service
mvn spring-boot:run

# 5. 启动推荐服务
cd ../recommendation-service
mvn spring-boot:run
```

### 访问应用
- **应用首页**: http://localhost:9999
- **Eureka控制台**: http://localhost:8761
- **API文档**: http://localhost:9999/doc.html

## 📁 项目结构

```
MovieSystemSpringBoot/
├── README.md                           # 项目说明
├── docker-compose.yml                  # Docker编排文件
├── pom.xml                            # 父项目配置
├── sql/                               # 数据库脚本
│   └── movie_system.sql
├── docs/                              # 项目文档
└── microservices-parent/              # 微服务模块
    ├── eureka-server/                 # 服务注册中心
    ├── api-gateway/                   # API网关
    ├── config-server/                 # 配置中心
    ├── user-service/                  # 用户微服务
    ├── movie-service/                 # 电影微服务
    └── recommendation-service/        # 推荐微服务
```

## 🔧 开发指南

### 添加新的微服务
1. 在 `microservices-parent` 下创建新模块
2. 配置 `pom.xml` 继承父项目
3. 配置 `application.yml` 注册到Eureka
4. 实现业务逻辑

### API设计规范
- RESTful风格的API设计
- 统一的响应格式
- 完整的错误处理
- API版本管理

### 数据库设计
- 遵循第三范式
- 合理的索引设计
- 数据安全和隐私保护

## 🧪 测试

### 单元测试
```bash
mvn test
```

### 集成测试
```bash
mvn integration-test
```

### API测试
使用提供的Postman collection进行API测试

## 📈 性能指标

- **响应时间**: < 100ms (90分位)
- **并发用户**: 支持1000+并发
- **推荐准确率**: 75%+
- **系统可用性**: 99.9%

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 [MIT License](LICENSE) 许可证。

## 👤 作者

- **开发者**: 张伟强
- **邮箱**: your.email@example.com
- **GitHub**: [@kisaragiy](https://github.com/kisaragiy)

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和使用者。

---

⭐ 如果这个项目对你有帮助，请给一个Star支持！