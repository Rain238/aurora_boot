/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : localhost:3306
 Source Schema         : aurora_boot

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 30/07/2025 16:15:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名称',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统限流QPS', 'IP_QPS_THRESHOLD_LIMIT', '100', '单个IP请求的最大每秒查询数（QPS）阈值Key', '2025-07-21 13:48:27', 1, '2025-07-27 14:41:27', 2, 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名称',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除(1-删除，0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dict_code`(`dict_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '性别', 1, NULL, '2025-07-21 13:48:27', 1, '2025-07-27 20:27:45', 1, 0);
INSERT INTO `sys_dict` VALUES (2, 'notice_type', '通知类型', 1, NULL, '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1, 0);
INSERT INTO `sys_dict` VALUES (3, 'notice_level', '通知级别', 1, NULL, '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1, 0);
INSERT INTO `sys_dict` VALUES (4, 'ge', '测试字典', 1, NULL, '2025-07-27 19:57:16', NULL, '2025-07-27 20:08:41', NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联字典编码，与sys_dict表中的dict_code对应',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项值',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项标签',
  `tag_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签类型，用于前端样式展示（如success、warning等）',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-正常，0-禁用）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 'gender', '1', '男', 'danger', 1, 1, NULL, '2025-07-21 13:48:27', 1, '2025-07-28 12:59:11', 1);
INSERT INTO `sys_dict_item` VALUES (2, 'gender', '2', '女', 'danger', 1, 2, NULL, '2025-07-21 13:48:27', 1, '2025-07-27 21:16:40', 1);
INSERT INTO `sys_dict_item` VALUES (3, 'gender', '0', '保密', 'info', 1, 3, NULL, '2025-07-21 13:48:27', 1, '2025-07-27 19:16:58', 1);
INSERT INTO `sys_dict_item` VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);
INSERT INTO `sys_dict_item` VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', '2025-07-21 13:48:27', 1, '2025-07-21 13:48:27', 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志模块',
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数(批量请求参数可能会超过text)',
  `response_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回参数',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日志内容',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求路径',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `execution_time` bigint NULL DEFAULT NULL COMMENT '执行时间(ms)',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `browser_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器版本',
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '终端系统',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 13, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:07:08', 0);
INSERT INTO `sys_log` VALUES (2, 'LOGIN', 'POST', 'test 1234556', NULL, '登录', '/api/v1/auth/login', NULL, '192.168.1.72', '0', '内网IP', 110, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-07-21 19:07:30', 0);
INSERT INTO `sys_log` VALUES (3, 'LOGIN', 'POST', 'text 1234556', NULL, '登录', '/api/v1/auth/login', NULL, '192.168.1.72', '0', '内网IP', 52, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-07-21 19:07:38', 0);
INSERT INTO `sys_log` VALUES (4, 'LOGIN', 'POST', 'test 123456', NULL, '登录', '/api/v1/auth/login', NULL, '192.168.1.72', '0', '内网IP', 56, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-07-21 19:08:01', 0);
INSERT INTO `sys_log` VALUES (5, 'USER', 'GET', '', NULL, '获取当前登录用户信息', '/api/v1/users/me', NULL, '192.168.1.72', '0', '内网IP', 32, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 3, '2025-07-21 19:08:01', 0);
INSERT INTO `sys_log` VALUES (6, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 5, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:08:20', 0);
INSERT INTO `sys_log` VALUES (7, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 2, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:08:44', 0);
INSERT INTO `sys_log` VALUES (8, 'OTHER', 'GET', '{\"excludeTables\":[\"gen_config\",\"gen_field_config\"],\"pageNum\":1,\"pageSize\":10}', NULL, '代码生成分页列表', '/api/v1/codegen/table/page', NULL, '192.168.1.72', '0', '内网IP', 28, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 3, '2025-07-21 19:08:47', 0);
INSERT INTO `sys_log` VALUES (9, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:09', 0);
INSERT INTO `sys_log` VALUES (10, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:11', 0);
INSERT INTO `sys_log` VALUES (11, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":10}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 16, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:17', 0);
INSERT INTO `sys_log` VALUES (13, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:25', 0);
INSERT INTO `sys_log` VALUES (14, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 2, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:26', 0);
INSERT INTO `sys_log` VALUES (15, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:27', 0);
INSERT INTO `sys_log` VALUES (16, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:12:27', 0);
INSERT INTO `sys_log` VALUES (18, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:20:20', 0);
INSERT INTO `sys_log` VALUES (19, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/2/form', NULL, '192.168.1.72', '0', '内网IP', 14, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:20:33', 0);
INSERT INTO `sys_log` VALUES (20, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/2/form', NULL, '192.168.1.72', '0', '内网IP', 2, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:03', 0);
INSERT INTO `sys_log` VALUES (21, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/3/form', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:08', 0);
INSERT INTO `sys_log` VALUES (22, 'USER', 'PUT', '{} {\"id\":3,\"username\":\"test\",\"nickname\":\"测试小用户\",\"mobile\":\"18812345679\",\"gender\":1,\"email\":\"youlaitech@163.com\",\"status\":0,\"deptId\":3,\"roleIds\":[3]}', NULL, '修改用户', '/api/v1/users/3', NULL, '192.168.1.72', '0', '内网IP', 16, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:13', 0);
INSERT INTO `sys_log` VALUES (23, 'USER', 'GET', '{\"keywords\":\"\",\"createTime\":[],\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 6, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:14', 0);
INSERT INTO `sys_log` VALUES (24, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/3/form', NULL, '192.168.1.72', '0', '内网IP', 1, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:17', 0);
INSERT INTO `sys_log` VALUES (25, 'USER', 'PUT', '{} {\"id\":3,\"username\":\"test\",\"nickname\":\"测试小用户\",\"mobile\":\"18812345679\",\"gender\":2,\"email\":\"youlaitech@163.com\",\"status\":0,\"deptId\":3,\"roleIds\":[3]}', NULL, '修改用户', '/api/v1/users/3', NULL, '192.168.1.72', '0', '内网IP', 13, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:22', 0);
INSERT INTO `sys_log` VALUES (26, 'USER', 'GET', '{\"keywords\":\"\",\"createTime\":[],\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:22', 0);
INSERT INTO `sys_log` VALUES (27, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/3/form', NULL, '192.168.1.72', '0', '内网IP', 1, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:31', 0);
INSERT INTO `sys_log` VALUES (28, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 5, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:21:42', 0);
INSERT INTO `sys_log` VALUES (29, 'USER', 'GET', '{\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:22:04', 0);
INSERT INTO `sys_log` VALUES (30, 'USER', 'GET', '', NULL, '获取个人中心用户信息', '/api/v1/users/profile', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 3, '2025-07-21 19:22:13', 0);
INSERT INTO `sys_log` VALUES (31, 'LOGIN', 'DELETE', '{}', NULL, '退出登录', '/api/v1/auth/logout', NULL, '192.168.1.72', '0', '内网IP', 25, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 3, '2025-07-21 19:22:23', 0);
INSERT INTO `sys_log` VALUES (32, 'LOGIN', 'POST', 'test 123456', NULL, '登录', '/api/v1/auth/login', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-07-21 19:22:50', 0);
INSERT INTO `sys_log` VALUES (33, 'LOGIN', 'POST', 'test 123456', NULL, '登录', '/api/v1/auth/login', NULL, '192.168.1.72', '0', '内网IP', 4, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', NULL, '2025-07-21 19:23:02', 0);
INSERT INTO `sys_log` VALUES (34, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/3/form', NULL, '192.168.1.72', '0', '内网IP', 2, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:23:06', 0);
INSERT INTO `sys_log` VALUES (35, 'USER', 'GET', '{}', NULL, '用户表单数据', '/api/v1/users/3/form', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:23:22', 0);
INSERT INTO `sys_log` VALUES (36, 'USER', 'GET', '{\"keywords\":\"\",\"status\":0,\"createTime\":[],\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 5, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:27:02', 0);
INSERT INTO `sys_log` VALUES (37, 'USER', 'GET', '{\"keywords\":\"\",\"status\":1,\"createTime\":[],\"isRoot\":false,\"pageNum\":1,\"pageSize\":20}', NULL, '用户分页列表', '/api/v1/users/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:27:03', 0);
INSERT INTO `sys_log` VALUES (38, 'OTHER', 'GET', '{\"excludeTables\":[\"gen_config\",\"gen_field_config\"],\"pageNum\":1,\"pageSize\":10}', NULL, '代码生成分页列表', '/api/v1/codegen/table/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:29:23', 0);
INSERT INTO `sys_log` VALUES (40, 'ROLE', 'GET', '{\"pageNum\":1,\"pageSize\":10}', NULL, '角色分页列表', '/api/v1/roles/page', NULL, '192.168.1.72', '0', '内网IP', 3, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:30:09', 0);
INSERT INTO `sys_log` VALUES (41, 'LOGIN', 'DELETE', '{}', NULL, '退出登录', '/api/v1/auth/logout', NULL, '192.168.1.72', '0', '内网IP', 30, 'MSEdge', '138.0.0.0', 'Windows 10 or Windows Server 2016', 2, '2025-07-21 19:31:35', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint NOT NULL COMMENT '父菜单ID',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父节点ID路径',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `type` tinyint NOT NULL COMMENT '菜单类型（1-菜单 2-目录 3-外链 4-按钮）',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称（Vue Router 中用于命名路由）',
  `route_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径（Vue Router 中定义的 URL 路径）',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径（组件页面完整路径，相对于 src/views/，缺省后缀 .vue）',
  `perm` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '【按钮】权限标识',
  `always_show` tinyint NULL DEFAULT 0 COMMENT '【目录】只有一个子路由是否始终显示（1-是 0-否）',
  `keep_alive` tinyint NULL DEFAULT 0 COMMENT '【菜单】是否开启页面缓存（1-是 0-否）',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态（1-显示 0-隐藏）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转路径',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', 2, '', '/system', 'Layout', NULL, NULL, NULL, 1, 1, 'system', '/system/user', '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', NULL, NULL, 1, 1, 1, 'el-icon-User', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', NULL, NULL, 1, 1, 2, 'role', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (4, 1, '0,1', '菜单管理', 1, 'SysMenu', 'menu', 'system/menu/index', NULL, NULL, 1, 1, 3, 'menu', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (6, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', NULL, NULL, 1, 1, 5, 'dict', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (32, 2, '0,1,2', '用户编辑', 4, NULL, '', NULL, 'sys:user:edit', NULL, NULL, 1, 2, '', '', '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (33, 2, '0,1,2', '用户删除', 4, NULL, '', NULL, 'sys:user:delete', NULL, NULL, 1, 3, '', '', '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (70, 3, '0,1,3', '角色新增', 4, NULL, '', NULL, 'sys:role:add', NULL, NULL, 1, 2, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (71, 3, '0,1,3', '角色编辑', 4, NULL, '', NULL, 'sys:role:edit', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (72, 3, '0,1,3', '角色删除', 4, NULL, '', NULL, 'sys:role:delete', NULL, NULL, 1, 4, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (73, 4, '0,1,4', '菜单新增', 4, NULL, '', NULL, 'sys:menu:add', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (74, 4, '0,1,4', '菜单编辑', 4, NULL, '', NULL, 'sys:menu:edit', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (75, 4, '0,1,4', '菜单删除', 4, NULL, '', NULL, 'sys:menu:delete', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (79, 6, '0,1,6', '字典新增', 4, NULL, '', NULL, 'sys:dict:add', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (81, 6, '0,1,6', '字典编辑', 4, NULL, '', NULL, 'sys:dict:edit', NULL, NULL, 1, 2, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (84, 6, '0,1,6', '字典删除', 4, NULL, '', NULL, 'sys:dict:delete', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (88, 2, '0,1,2', '重置密码', 4, NULL, '', NULL, 'sys:user:reset-password', NULL, NULL, 1, 4, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (106, 2, '0,1,2', '用户导入', 4, NULL, '', NULL, 'sys:user:import', NULL, NULL, 1, 5, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (107, 2, '0,1,2', '用户导出', 4, NULL, '', NULL, 'sys:user:export', NULL, NULL, 1, 6, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (120, 1, '0,1', '系统配置', 1, 'Config', 'config', 'system/config/index', NULL, 0, 1, 1, 7, 'setting', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (121, 120, '0,1,120', '系统配置查询', 4, NULL, '', NULL, 'sys:config:query', 0, 1, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (122, 120, '0,1,120', '系统配置新增', 4, NULL, '', NULL, 'sys:config:add', 0, 1, 1, 2, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (123, 120, '0,1,120', '系统配置修改', 4, NULL, '', NULL, 'sys:config:update', 0, 1, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (124, 120, '0,1,120', '系统配置删除', 4, NULL, '', NULL, 'sys:config:delete', 0, 1, 1, 4, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (125, 120, '0,1,120', '系统配置刷新', 4, NULL, '', NULL, 'sys:config:refresh', 0, 1, 1, 5, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (126, 1, '0,1', '通知公告', 1, 'Notice', 'notice', 'system/notice/index', NULL, NULL, NULL, 1, 9, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (127, 126, '0,1,126', '通知查询', 4, NULL, '', NULL, 'sys:notice:query', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (128, 126, '0,1,126', '通知新增', 4, NULL, '', NULL, 'sys:notice:add', NULL, NULL, 1, 2, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (129, 126, '0,1,126', '通知编辑', 4, NULL, '', NULL, 'sys:notice:edit', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (130, 126, '0,1,126', '通知删除', 4, NULL, '', NULL, 'sys:notice:delete', NULL, NULL, 1, 4, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (133, 126, '0,1,126', '通知发布', 4, NULL, '', NULL, 'sys:notice:publish', 0, 1, 1, 5, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (134, 126, '0,1,126', '通知撤回', 4, NULL, '', NULL, 'sys:notice:revoke', 0, 1, 1, 6, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (135, 1, '0,1', '字典项', 1, 'DictItem', 'dict-item', 'system/dict/dict-item', NULL, 0, 1, 0, 6, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (136, 135, '0,1,135', '字典项新增', 4, NULL, '', NULL, 'sys:dict-item:add', NULL, NULL, 1, 2, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (137, 135, '0,1,135', '字典项编辑', 4, NULL, '', NULL, 'sys:dict-item:edit', NULL, NULL, 1, 3, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (138, 135, '0,1,135', '字典项删除', 4, NULL, '', NULL, 'sys:dict-item:delete', NULL, NULL, 1, 4, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (139, 3, '0,1,3', '角色查询', 4, NULL, '', NULL, 'sys:role:query', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (140, 4, '0,1,4', '菜单查询', 4, NULL, '', NULL, 'sys:menu:query', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (142, 6, '0,1,6', '字典查询', 4, NULL, '', NULL, 'sys:dict:query', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);
INSERT INTO `sys_menu` VALUES (143, 135, '0,1,135', '字典项查询', 4, NULL, '', NULL, 'sys:dict-item:query', NULL, NULL, 1, 1, '', NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通知内容',
  `type` tinyint NOT NULL COMMENT '通知类型（关联字典编码：notice_type）',
  `level` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知等级（字典code：notice_level）',
  `target_type` tinyint NOT NULL COMMENT '目标类型（1: 全体, 2: 指定）',
  `target_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标人ID集合（多个使用英文逗号,分割）',
  `publisher_id` bigint NULL DEFAULT NULL COMMENT '发布人ID',
  `publish_status` tinyint NULL DEFAULT 0 COMMENT '发布状态（0: 未发布, 1: 已发布, -1: 已撤回）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `revoke_time` datetime NULL DEFAULT NULL COMMENT '撤回时间',
  `create_by` bigint NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0: 未删除, 1: 已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, 'v2.12.0 新增系统日志，访问趋势统计功能。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, -1, '2025-07-28 13:05:05', '2025-07-28 13:05:30', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (2, 'v2.13.0 新增菜单搜索。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-07-28 13:05:00', '2025-07-28 13:04:51', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (3, 'v2.14.0 新增个人中心。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, -1, '2025-07-21 13:48:27', '2025-07-27 18:57:35', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (4, 'v2.15.0 登录页面改造。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, -1, '2025-07-21 13:48:27', '2025-07-27 18:58:03', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (5, 'v2.16.0 通知公告、字典翻译组件。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (6, '系统将于本周六凌晨 2 点进行维护，预计维护时间为 2 小时。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 2, 'H', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (7, '最近发现一些钓鱼邮件，请大家提高警惕，不要点击陌生链接。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 3, 'L', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (8, '国庆假期从 10 月 1 日至 10 月 7 日放假，共 7 天。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 4, 'L', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (9, '公司将在 10 月 15 日举办新产品发布会，敬请期待。', '公司将在 10 月 15 日举办新产品发布会，敬请期待。', 5, 'H', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (10, 'v2.16.1 版本发布。', 'v2.16.1 版本修复了 WebSocket 重复连接导致的后台线程阻塞问题，优化了通知公告。', 1, 'M', 1, '2', 2, 1, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 2, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_notice` VALUES (11, '测试', '<p>测试系统升级</p>', 1, 'H', 1, NULL, NULL, 0, NULL, NULL, 2, '2025-07-27 18:59:04', NULL, '2025-07-27 18:59:04', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态(1-正常 0-停用)',
  `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(1-所有数据 2-部门及子部门数据 3-本部门数据 4-本人数据)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人 ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE COMMENT '角色名称唯一索引',
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-21 13:48:27', 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 17:00:43', 0);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 4, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 17:46:42', 0);
INSERT INTO `sys_role` VALUES (4, '系统管理员1', 'ADMIN1', 4, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:14', 0);
INSERT INTO `sys_role` VALUES (5, '系统管理员2', 'ADMIN2', 5, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:18', 0);
INSERT INTO `sys_role` VALUES (6, '系统管理员3', 'ADMIN3', 6, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:20', 0);
INSERT INTO `sys_role` VALUES (7, '系统管理员4', 'ADMIN4', 7, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:22', 0);
INSERT INTO `sys_role` VALUES (8, '系统管理员5', 'ADMIN5', 8, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:25', 0);
INSERT INTO `sys_role` VALUES (9, '系统管理员6', 'ADMIN6', 9, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:27', 0);
INSERT INTO `sys_role` VALUES (10, '系统管理员7', 'ADMIN7', 10, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:30', 0);
INSERT INTO `sys_role` VALUES (11, '系统管理员8', 'ADMIN8', 11, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:33', 0);
INSERT INTO `sys_role` VALUES (12, '系统管理员9', 'ADMIN9', 12, 1, 1, NULL, '2025-07-21 13:48:27', NULL, '2025-07-27 16:16:36', 0);
INSERT INTO `sys_role` VALUES (13, '访问游客2', 'GUEST2', 1, 1, 1, NULL, '2025-07-27 16:56:38', NULL, '2025-07-27 17:48:05', 1);
INSERT INTO `sys_role` VALUES (14, 'dsa', 'sd', 1, 1, 4, NULL, '2025-07-27 17:48:23', NULL, '2025-07-27 17:48:27', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  UNIQUE INDEX `uk_roleid_menuid`(`role_id` ASC, `menu_id` ASC) USING BTREE COMMENT '角色菜单唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 70);
INSERT INTO `sys_role_menu` VALUES (2, 71);
INSERT INTO `sys_role_menu` VALUES (2, 72);
INSERT INTO `sys_role_menu` VALUES (2, 73);
INSERT INTO `sys_role_menu` VALUES (2, 74);
INSERT INTO `sys_role_menu` VALUES (2, 75);
INSERT INTO `sys_role_menu` VALUES (2, 79);
INSERT INTO `sys_role_menu` VALUES (2, 81);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 89);
INSERT INTO `sys_role_menu` VALUES (2, 90);
INSERT INTO `sys_role_menu` VALUES (2, 95);
INSERT INTO `sys_role_menu` VALUES (2, 97);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 118);
INSERT INTO `sys_role_menu` VALUES (2, 119);
INSERT INTO `sys_role_menu` VALUES (2, 120);
INSERT INTO `sys_role_menu` VALUES (2, 121);
INSERT INTO `sys_role_menu` VALUES (2, 122);
INSERT INTO `sys_role_menu` VALUES (2, 123);
INSERT INTO `sys_role_menu` VALUES (2, 124);
INSERT INTO `sys_role_menu` VALUES (2, 125);
INSERT INTO `sys_role_menu` VALUES (2, 126);
INSERT INTO `sys_role_menu` VALUES (2, 127);
INSERT INTO `sys_role_menu` VALUES (2, 128);
INSERT INTO `sys_role_menu` VALUES (2, 129);
INSERT INTO `sys_role_menu` VALUES (2, 130);
INSERT INTO `sys_role_menu` VALUES (2, 133);
INSERT INTO `sys_role_menu` VALUES (2, 134);
INSERT INTO `sys_role_menu` VALUES (2, 135);
INSERT INTO `sys_role_menu` VALUES (2, 136);
INSERT INTO `sys_role_menu` VALUES (2, 137);
INSERT INTO `sys_role_menu` VALUES (2, 138);
INSERT INTO `sys_role_menu` VALUES (2, 139);
INSERT INTO `sys_role_menu` VALUES (2, 140);
INSERT INTO `sys_role_menu` VALUES (2, 142);
INSERT INTO `sys_role_menu` VALUES (2, 143);
INSERT INTO `sys_role_menu` VALUES (2, 144);
INSERT INTO `sys_role_menu` VALUES (2, 145);
INSERT INTO `sys_role_menu` VALUES (2, 146);
INSERT INTO `sys_role_menu` VALUES (2, 147);
INSERT INTO `sys_role_menu` VALUES (2, 148);
INSERT INTO `sys_role_menu` VALUES (2, 149);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 6);
INSERT INTO `sys_role_menu` VALUES (3, 31);
INSERT INTO `sys_role_menu` VALUES (3, 32);
INSERT INTO `sys_role_menu` VALUES (3, 33);
INSERT INTO `sys_role_menu` VALUES (3, 70);
INSERT INTO `sys_role_menu` VALUES (3, 71);
INSERT INTO `sys_role_menu` VALUES (3, 72);
INSERT INTO `sys_role_menu` VALUES (3, 73);
INSERT INTO `sys_role_menu` VALUES (3, 74);
INSERT INTO `sys_role_menu` VALUES (3, 75);
INSERT INTO `sys_role_menu` VALUES (3, 79);
INSERT INTO `sys_role_menu` VALUES (3, 81);
INSERT INTO `sys_role_menu` VALUES (3, 84);
INSERT INTO `sys_role_menu` VALUES (3, 88);
INSERT INTO `sys_role_menu` VALUES (3, 89);
INSERT INTO `sys_role_menu` VALUES (3, 90);
INSERT INTO `sys_role_menu` VALUES (3, 97);
INSERT INTO `sys_role_menu` VALUES (3, 105);
INSERT INTO `sys_role_menu` VALUES (3, 106);
INSERT INTO `sys_role_menu` VALUES (3, 107);
INSERT INTO `sys_role_menu` VALUES (3, 110);
INSERT INTO `sys_role_menu` VALUES (3, 111);
INSERT INTO `sys_role_menu` VALUES (3, 112);
INSERT INTO `sys_role_menu` VALUES (3, 117);
INSERT INTO `sys_role_menu` VALUES (3, 118);
INSERT INTO `sys_role_menu` VALUES (3, 119);
INSERT INTO `sys_role_menu` VALUES (3, 120);
INSERT INTO `sys_role_menu` VALUES (3, 121);
INSERT INTO `sys_role_menu` VALUES (3, 122);
INSERT INTO `sys_role_menu` VALUES (3, 123);
INSERT INTO `sys_role_menu` VALUES (3, 124);
INSERT INTO `sys_role_menu` VALUES (3, 125);
INSERT INTO `sys_role_menu` VALUES (3, 126);
INSERT INTO `sys_role_menu` VALUES (3, 127);
INSERT INTO `sys_role_menu` VALUES (3, 128);
INSERT INTO `sys_role_menu` VALUES (3, 129);
INSERT INTO `sys_role_menu` VALUES (3, 130);
INSERT INTO `sys_role_menu` VALUES (3, 133);
INSERT INTO `sys_role_menu` VALUES (3, 134);
INSERT INTO `sys_role_menu` VALUES (3, 135);
INSERT INTO `sys_role_menu` VALUES (3, 136);
INSERT INTO `sys_role_menu` VALUES (3, 137);
INSERT INTO `sys_role_menu` VALUES (3, 138);
INSERT INTO `sys_role_menu` VALUES (3, 139);
INSERT INTO `sys_role_menu` VALUES (3, 140);
INSERT INTO `sys_role_menu` VALUES (3, 142);
INSERT INTO `sys_role_menu` VALUES (3, 143);
INSERT INTO `sys_role_menu` VALUES (3, 148);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别((1-男 2-女 0-保密)',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  `openid` char(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信 openid',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', 'root', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345677', 1, 'youlaitech@163.com', '2025-07-21 13:48:27', NULL, '2025-07-21 13:48:27', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345678', 1, 'youlaitech@163.com', '2025-07-21 13:48:27', NULL, '2025-07-27 19:02:20', 2, 0, NULL);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 2, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345670', 1, 'yuoulaitech@163.com', '2025-07-21 13:48:27', NULL, '2025-07-28 13:01:34', 2, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_notice`;
CREATE TABLE `sys_user_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `notice_id` bigint NOT NULL COMMENT '公共通知id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `is_read` bigint NULL DEFAULT 0 COMMENT '读取状态（0: 未读, 1: 已读）',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除(0: 未删除, 1: 已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_notice
-- ----------------------------
INSERT INTO `sys_user_notice` VALUES (1, 1, 2, 1, NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 1);
INSERT INTO `sys_user_notice` VALUES (2, 2, 2, 1, NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 1);
INSERT INTO `sys_user_notice` VALUES (3, 3, 2, 1, NULL, '2025-07-21 13:48:27', '2025-07-21 13:48:27', 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
