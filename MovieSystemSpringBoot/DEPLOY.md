# 部署指南

## 🚀 快速部署

### 方式一：Docker Compose (推荐)
```bash
# 1. 克隆项目
git clone https://github.com/kisaragiy/MovieSystemSpringBoot.git
cd MovieSystemSpringBoot

# 2. 配置环境变量
cp .env.example .env
# 编辑 .env 文件，设置数据库密码等

# 3. 启动所有服务
docker-compose up -d

# 4. 等待服务启动完成 (约2-3分钟)
# 访问 http://localhost:9999
```

### 方式二：本地运行
```bash
# 1. 准备环境
# - Java 17+
# - Maven 3.8+
# - MySQL 8.0+
# - Redis 7.0+

# 2. 初始化数据库
mysql -u root -p < sql/movie_system.sql

# 3. 配置环境变量
export DB_USERNAME=root
export DB_PASSWORD=your_password

# 4. 启动服务
./start-services.sh

# 5. 访问应用
# http://localhost:9999
```

## 🔧 生产环境部署

### Kubernetes 部署
```bash
# 1. 创建命名空间
kubectl create namespace movie-system

# 2. 创建配置映射
kubectl create configmap movie-config --from-env-file=.env -n movie-system

# 3. 部署服务
kubectl apply -f k8s/ -n movie-system

# 4. 检查部署状态
kubectl get pods -n movie-system
```

### 负载均衡配置
推荐使用 Nginx 进行负载均衡：
```nginx
upstream movie-gateway {
    server localhost:9999;
    server localhost:9998; # 如果有多实例
}

server {
    listen 80;
    server_name movie.example.com;
    
    location / {
        proxy_pass http://movie-gateway;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 📊 监控配置

### Prometheus + Grafana
```yaml
# docker-compose.monitoring.yml
version: '3.8'
services:
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
      
  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin123
```

## 🔒 安全配置

### HTTPS 配置
```bash
# 1. 获取 SSL 证书 (Let's Encrypt)
certbot --nginx -d movie.example.com

# 2. 更新 Nginx 配置
server {
    listen 443 ssl;
    ssl_certificate /etc/letsencrypt/live/movie.example.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/movie.example.com/privkey.pem;
}
```

### 数据库安全
```sql
-- 创建专用数据库用户
CREATE USER 'movie_user'@'%' IDENTIFIED BY 'secure_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON moviesystem_db.* TO 'movie_user'@'%';
FLUSH PRIVILEGES;
```

## 🔄 备份策略

### 数据库备份
```bash
# 每日备份脚本
#!/bin/bash
BACKUP_DIR="/backup/mysql"
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u root -p moviesystem_db > $BACKUP_DIR/movie_backup_$DATE.sql
find $BACKUP_DIR -name "*.sql" -mtime +7 -delete
```

### 配置备份
```bash
# 备份重要配置文件
tar -czf config_backup_$(date +%Y%m%d).tar.gz \
  docker-compose.yml \
  .env \
  sql/ \
  docs/
```

## 📈 性能优化

### JVM 调优
```bash
# 为每个微服务设置合适的 JVM 参数
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
```

### 数据库优化
```sql
-- 添加关键索引
CREATE INDEX idx_movie_category ON movies(category_id);
CREATE INDEX idx_user_rating ON ratings(user_id, movie_id);
CREATE INDEX idx_movie_rating ON ratings(movie_id);
```

### Redis 优化
```bash
# 设置合适的内存策略
redis-cli CONFIG SET maxmemory-policy allkeys-lru
redis-cli CONFIG SET maxmemory 2gb
```

## 🚨 故障排查

### 常见问题

#### 服务无法启动
```bash
# 检查端口占用
netstat -tlnp | grep :8761

# 查看服务日志
docker-compose logs eureka-server
tail -f logs/eureka.log
```

#### 数据库连接失败
```bash
# 测试数据库连接
mysql -h localhost -u root -p moviesystem_db

# 检查数据库状态
systemctl status mysql
```

#### 内存不足
```bash
# 检查内存使用情况
free -h
docker stats

# 调整 JVM 内存
export JAVA_OPTS="-Xms256m -Xmx512m"
```

## 📱 健康检查

### 应用健康检查
```bash
# 检查所有服务状态
curl http://localhost:8761/actuator/health
curl http://localhost:9999/actuator/health
curl http://localhost:8082/actuator/health
```

### 自动化健康检查脚本
```bash
#!/bin/bash
services=("8761" "9999" "8082" "8083" "8084")
for port in "${services[@]}"; do
    if curl -f http://localhost:$port/actuator/health > /dev/null 2>&1; then
        echo "✅ Service on port $port is healthy"
    else
        echo "❌ Service on port $port is down"
    fi
done
```