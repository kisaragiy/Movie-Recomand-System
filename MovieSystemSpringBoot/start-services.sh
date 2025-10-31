#!/bin/bash

# 电影推荐系统微服务启动脚本

echo "🎬 启动电影推荐系统微服务..."

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "❌ 错误: 未找到Java环境，请安装Java 17+"
    exit 1
fi

# 检查Maven环境
if ! command -v mvn &> /dev/null; then
    echo "❌ 错误: 未找到Maven环境，请安装Maven 3.8+"
    exit 1
fi

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}📋 启动顺序:${NC}"
echo "1. Eureka Server (服务注册中心)"
echo "2. Config Server (配置中心)" 
echo "3. API Gateway (API网关)"
echo "4. User Service (用户服务)"
echo "5. Movie Service (电影服务)"
echo "6. Recommendation Service (推荐服务)"
echo ""

# 启动Eureka Server
echo -e "${YELLOW}🚀 启动 Eureka Server...${NC}"
cd eureka-server
nohup mvn spring-boot:run > ../logs/eureka.log 2>&1 &
EUREKA_PID=$!
echo "Eureka Server PID: $EUREKA_PID"
cd ..

# 等待Eureka启动
echo -e "${BLUE}⏳ 等待 Eureka Server 启动完成...${NC}"
sleep 30

# 启动Config Server
echo -e "${YELLOW}🚀 启动 Config Server...${NC}"
cd config-server
nohup mvn spring-boot:run > ../logs/config.log 2>&1 &
CONFIG_PID=$!
echo "Config Server PID: $CONFIG_PID"
cd ..

# 等待Config Server启动
sleep 15

# 启动API Gateway
echo -e "${YELLOW}🚀 启动 API Gateway...${NC}"
cd api-gateway
nohup mvn spring-boot:run > ../logs/gateway.log 2>&1 &
GATEWAY_PID=$!
echo "API Gateway PID: $GATEWAY_PID"
cd ..

# 等待Gateway启动
sleep 15

# 启动User Service
echo -e "${YELLOW}🚀 启动 User Service...${NC}"
cd user-service
nohup mvn spring-boot:run > ../logs/user.log 2>&1 &
USER_PID=$!
echo "User Service PID: $USER_PID"
cd ..

# 启动Movie Service
echo -e "${YELLOW}🚀 启动 Movie Service...${NC}"
cd movie-service
nohup mvn spring-boot:run > ../logs/movie.log 2>&1 &
MOVIE_PID=$!
echo "Movie Service PID: $MOVIE_PID"
cd ..

# 启动Recommendation Service
echo -e "${YELLOW}🚀 启动 Recommendation Service...${NC}"
cd recommendation-service
nohup mvn spring-boot:run > ../logs/recommendation.log 2>&1 &
RECOMMENDATION_PID=$!
echo "Recommendation Service PID: $RECOMMENDATION_PID"
cd ..

# 等待所有服务启动完成
echo -e "${BLUE}⏳ 等待所有服务启动完成...${NC}"
sleep 60

echo -e "${GREEN}✅ 所有服务启动完成!${NC}"
echo ""
echo -e "${BLUE}📊 服务访问地址:${NC}"
echo "🌐 应用首页: http://localhost:9999"
echo "🔧 Eureka控制台: http://localhost:8761"
echo "🔍 API文档: http://localhost:9999/doc.html"
echo ""
echo -e "${BLUE}📋 服务进程ID:${NC}"
echo "Eureka Server: $EUREKA_PID"
echo "Config Server: $CONFIG_PID"
echo "API Gateway: $GATEWAY_PID"
echo "User Service: $USER_PID"
echo "Movie Service: $MOVIE_PID"
echo "Recommendation Service: $RECOMMENDATION_PID"
echo ""
echo -e "${YELLOW}💡 提示: 使用 ./stop-services.sh 停止所有服务${NC}"

# 保存PID到文件
echo "$EUREKA_PID" > pids/eureka.pid
echo "$CONFIG_PID" > pids/config.pid
echo "$GATEWAY_PID" > pids/gateway.pid
echo "$USER_PID" > pids/user.pid
echo "$MOVIE_PID" > pids/movie.pid
echo "$RECOMMENDATION_PID" > pids/recommendation.pid