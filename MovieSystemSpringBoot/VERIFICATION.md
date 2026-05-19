# 电影推荐系统 — 验证说明

## 简历功能对照

| 功能 | 初版路径 | 微服务路径 | 状态 |
|------|----------|------------|------|
| 用户登录/注册/找回密码 | `/Login`, `/Register`, `/forget` | `/microservice/api/user/login` 等 | 已实现 |
| Session + 拦截器 | Session `sessionmember` | `LoginInterceptor` + Redis Session | 已实现 |
| 电影 CRUD + 分页 | `/admin/movieList` 等 | `/microservice/api/movie/admin/*` | 已实现 |
| 协同过滤推荐 | `/loveRecommend` | `/microservice/api/recommend/love` | 已实现 |
| 混合推荐 | `/getHybridRecommendations` | `/microservice/api/recommend/hybrid` | 已实现 |
| Redis 缓存 | `@Cacheable` 电影列表/推荐 | 各服务 `RedisCacheConfig` | 已实现 |
| Eureka 注册 | — | `eureka-server:8761` | 已配置 |

## 一键脚本（Windows）

```powershell
# 首次或完整流程（需 Docker Desktop 已启动）
cd MovieSystemSpringBoot\scripts
.\setup-and-run.ps1

# 分步执行
.\setup-maven.ps1      # 安装 Maven 到 %USERPROFILE%\tools
.\start-infra.ps1      # Docker 启动 MySQL + Redis
.\start-services.ps1   # 编译并启动微服务
.\run-api-tests.ps1    # 12 项接口测试
.\stop-services.ps1    # 停止 Java 进程
```

Postman 集合：`postman/Movie-Recommendation-API.postman_collection.json`

Maven 已配置：`JAVA_HOME` + `MAVEN_HOME` 写入用户环境变量（需新开终端生效）。

## 手动启动顺序

1. MySQL：Docker `start-infra.ps1` 或导入 `sql/movie_system.sql`
2. Redis：同上
3. 编译：`mvn -f pom-microservices.xml clean package -DskipTests -pl eureka-server,user-service,movie-service,recommendation-service -am`
4. 启动：eureka (8761) → user (8082) → movie (8083) → recommendation (8084)

## 接口测试示例

```bash
# 健康检查
curl http://localhost:8084/microservice/api/recommend/health

# 混合推荐（用户 ID 34 为库中测试用户）
curl "http://localhost:8084/microservice/api/recommend/hybrid?user_id=34"

# 电影列表
curl "http://localhost:8083/microservice/api/movie/admin/list?pageNum=1&pageSize=10"

# 用户登录
curl -X POST http://localhost:8082/microservice/api/user/login \
  -H "Content-Type: application/json" \
  -d "{\"uname\":\"H011\",\"upass\":\"123\"}"
```

环境变量：`DB_USERNAME`、`DB_PASSWORD` 可覆盖默认 `root` / `123456`。
