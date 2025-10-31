#!/bin/bash

# 电影推荐系统微服务停止脚本

echo "🛑 停止电影推荐系统微服务..."

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 创建pids目录如果不存在
mkdir -p pids

# 停止服务的函数
stop_service() {
    local service_name=$1
    local pid_file="pids/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if ps -p $pid > /dev/null; then
            echo -e "${YELLOW}🛑 停止 ${service_name^} Service (PID: $pid)...${NC}"
            kill $pid
            # 等待进程结束
            local count=0
            while ps -p $pid > /dev/null && [ $count -lt 10 ]; do
                sleep 1
                count=$((count + 1))
            done
            
            # 如果进程仍在运行，强制杀死
            if ps -p $pid > /dev/null; then
                echo -e "${RED}⚠️  强制停止 ${service_name^} Service...${NC}"
                kill -9 $pid
            fi
            
            rm "$pid_file"
            echo -e "${GREEN}✅ ${service_name^} Service 已停止${NC}"
        else
            echo -e "${BLUE}ℹ️  ${service_name^} Service 未运行${NC}"
            rm "$pid_file"
        fi
    else
        echo -e "${BLUE}ℹ️  未找到 ${service_name^} Service PID文件${NC}"
    fi
}

# 按相反顺序停止服务
echo -e "${BLUE}📋 停止顺序:${NC}"
echo "1. Recommendation Service (推荐服务)"
echo "2. Movie Service (电影服务)"
echo "3. User Service (用户服务)"
echo "4. API Gateway (API网关)"
echo "5. Config Server (配置中心)"
echo "6. Eureka Server (服务注册中心)"
echo ""

stop_service "recommendation"
stop_service "movie"
stop_service "user"
stop_service "gateway"
stop_service "config"
stop_service "eureka"

# 检查是否还有相关Java进程
echo -e "${BLUE}🔍 检查剩余Java进程...${NC}"
remaining_processes=$(ps aux | grep -E "(spring-boot|eureka|gateway|user-service|movie-service|recommendation-service)" | grep -v grep | wc -l)

if [ $remaining_processes -gt 0 ]; then
    echo -e "${YELLOW}⚠️  发现 $remaining_processes 个相关进程仍在运行${NC}"
    echo "如需强制停止所有相关Java进程，请运行:"
    echo "pkill -f 'spring-boot'"
else
    echo -e "${GREEN}✅ 所有服务已成功停止${NC}"
fi

echo ""
echo -e "${BLUE}💡 提示: 使用 ./start-services.sh 重新启动所有服务${NC}"