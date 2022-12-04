/*
 Navicat Premium Data Transfer

 Source Server         : wanxinp2p
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:32523
 Source Schema         : p2p_account

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 04/12/2022 21:39:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USERNAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `MOBILE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `PASSWORD` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `SALT` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '账号状态',
  `DOMAIN` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1129286208275427331 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '账号信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1129286208275427330, 'admin', '18683446911', 'c9ed3d27e29562391682311cb17f6283ad8f04a86fc0a474', NULL, 1, 'b');

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ACCOUNT_ID` bigint(0) NULL DEFAULT NULL COMMENT '账号标识',
  `ROLE_ID` bigint(0) NULL DEFAULT NULL COMMENT '角色标识',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `Fk_Reference_15`(`ACCOUNT_ID`) USING BTREE,
  INDEX `FK-Reference_16`(`ROLE_ID`) USING BTREE,
  CONSTRAINT `FK-Reference_16` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `Fk_Reference_15` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '账号-角色关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES (1, 1129286208275427330, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENT_ID` bigint(0) NULL DEFAULT NULL COMMENT '父id',
  `TITLE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单标题',
  `URL` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '链接url',
  `ICON` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标',
  `SORT` int(0) NOT NULL COMMENT '排序',
  `COMMENT` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '说明',
  `STATUS` int(0) NOT NULL COMMENT '状态',
  `PRIVILEGE_CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '绑定权限',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `PRIVILEGE_GROUP_ID` bigint(0) NULL DEFAULT NULL COMMENT '所属权限组id',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_6`(`PRIVILEGE_GROUP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of privilege
-- ----------------------------

-- ----------------------------
-- Table structure for privilege_group
-- ----------------------------
DROP TABLE IF EXISTS `privilege_group`;
CREATE TABLE `privilege_group`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PARENT_ID` bigint(0) NULL DEFAULT NULL COMMENT '父id',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限组名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '权限组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of privilege_group
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `CODE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'b端用户', 'admin');
INSERT INTO `role` VALUES (2, 'c端用户', 'user');

-- ----------------------------
-- Table structure for role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `role_privilege`;
CREATE TABLE `role_privilege`  (
  `ID` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  `PRIVILEGE_ID` bigint(0) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色-权限关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_privilege
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
