# API接口文档

## 基本信息
- **Base URL**: `http://localhost:9999`
- **认证方式**: JWT Token
- **请求格式**: JSON
- **响应格式**: JSON

## 认证相关接口

### 用户注册
```http
POST /api/user/register
Content-Type: application/json

{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com",
    "phone": "13888888888"
}
```

### 用户登录
```http
POST /api/user/login
Content-Type: application/json

{
    "username": "testuser",
    "password": "password123"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "user": {
            "id": 1,
            "username": "testuser",
            "email": "test@example.com"
        }
    }
}
```

## 电影相关接口

### 获取电影列表
```http
GET /api/movie/list?page=1&size=10&category=动作
Authorization: Bearer <token>
```

### 获取电影详情
```http
GET /api/movie/{movieId}
Authorization: Bearer <token>
```

### 搜索电影
```http
GET /api/movie/search?keyword=复仇者联盟
Authorization: Bearer <token>
```

## 推荐相关接口

### 获取个性化推荐
```http
GET /api/recommend/personal/{userId}
Authorization: Bearer <token>
```

### 获取热门推荐
```http
GET /api/recommend/hot?limit=10
Authorization: Bearer <token>
```

### 提交评分
```http
POST /api/recommend/rating
Authorization: Bearer <token>
Content-Type: application/json

{
    "movieId": 1,
    "rating": 4.5,
    "comment": "很好看的电影"
}
```

## 响应状态码

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 通用响应格式

```json
{
    "code": 200,
    "message": "操作成功",
    "data": {},
    "timestamp": "2024-01-01T12:00:00Z"
}
```