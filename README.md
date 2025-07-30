```text
# Aurora Boot 开源后台管理系统

## 项目简介
Aurora Boot 是一个基于 Spring Boot 的开源后台管理系统，提供了完整的权限管理、认证中心、系统配置、日志管理、字典管理、通知公告、文件上传、微信小程序登录、短信验证码登录等功能。该项目适用于快速搭建企业级后台管理系统。

## 功能模块

### 认证中心
- 账号密码登录
- 短信验证码登录
- 微信授权登录（Web 和小程序）
- 刷新令牌
- 退出登录
- 获取验证码

### 用户管理
- 用户分页列表
- 新增用户
- 修改用户
- 删除用户
- 用户状态管理
- 用户导入/导出
- 用户角色分配
- 用户信息修改（头像、昵称、手机号、邮箱等）
- 密码管理（修改、重置）

### 角色管理
- 角色分页列表
- 新增角色
- 修改角色
- 删除角色
- 角色状态管理
- 角色菜单权限分配

### 菜单管理
- 菜单列表
- 菜单下拉列表
- 菜单路由列表
- 新增菜单
- 修改菜单
- 删除菜单
- 菜单显示状态管理

### 系统配置
- 系统配置分页列表
- 新增系统配置
- 修改系统配置
- 删除系统配置
- 刷新系统配置缓存

### 日志管理
- 日志分页列表
- 访问趋势统计
- 访问量统计

### 通知公告
- 通知公告分页列表
- 新增通知公告
- 修改通知公告
- 删除通知公告
- 发布/撤回通知公告
- 阅读通知公告

### 字典管理
- 字典分页列表
- 字典项管理
- 新增/修改/删除字典及字典项

### 文件管理
- 支持本地、阿里云 OSS、MinIO 等多种存储方式
- 文件上传
- 文件删除

### 短信服务
- 支持阿里云短信服务
- 发送短信验证码
- 短信验证码登录

### 邮件服务
- 支持邮件发送
- 发送邮箱验证码
- 邮箱绑定/更换

### WebSocket 在线用户管理
- 实时在线用户统计
- 用户连接/断开通知
- 消息广播

## 技术栈

- **Spring Boot**：快速构建微服务架构
- **Spring Security**：权限控制与认证管理
- **JWT / Redis**：Token 管理
- **MyBatis Plus**：ORM 框架
- **Lombok**：简化 Java 代码
- **Swagger / SpringDoc**：API 文档生成
- **Redis**：缓存与验证码管理
- **WebSocket**：实时通信
- **OSS / MinIO / Local**：文件存储支持
- **MapStruct**：对象映射转换
- **Validation**：参数校验
- **Logback**：日志管理

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js（可选，用于前端）

### 启动步骤
1. 克隆项目：
   ```bash
   git clone https://gitee.com/RainSauce/aurora_boot.git
   ```
2. 修改配置文件：
   ```yaml
   # application.yml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/aurora_boot?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
       username: root
       password: 123456
   ```
3. 初始化数据库：
   - 执行 SQL 文件 `src/main/resources/schema.sql` 创建表结构
4. 启动项目：
   ```bash
   cd aurora_boot
   mvn spring-boot:run
   ```

### 接口文档
访问 `/swagger-ui.html` 或 `/v3/api-docs` 查看 API 文档。

## 项目结构
- `auth`：认证中心模块
- `system`：系统核心模块（用户、角色、菜单、日志、配置等）
- `common`：公共工具类与异常处理
- `core`：核心安全与权限控制
- `config`：系统配置类
- `shared`：共享模块（文件、短信、邮件等）

## 贡献指南
欢迎贡献代码！请遵循以下步骤：
1. Fork 项目
2. 创建新分支
3. 提交 Pull Request

## 协议
本项目采用 [MIT License](https://opensource.org/licenses/MIT) 开源协议。

## 联系方式
- 作者：LightRain
- URL：https://rainrem.top/
```