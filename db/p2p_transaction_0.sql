/*
 Navicat Premium Data Transfer

 Source Server         : wanxinp2p
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:32523
 Source Schema         : p2p_transaction_0

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 04/12/2022 21:40:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for claim_0
-- ----------------------------
DROP TABLE IF EXISTS `claim_0`;
CREATE TABLE `claim_0`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '标的标识',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '发标人用户标识(冗余)',
  `SOURCE_TENDER_ID` bigint(0) NOT NULL COMMENT '投标信息标识(转让来源)',
  `ROOT_PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '原始标的标识(冗余)',
  `ROOT_PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '原始标的编码(冗余)',
  `ASSIGNMENT_REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '债权转让 请求流水号',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_17`(`PROJECT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '债权转让标的附加信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of claim_0
-- ----------------------------

-- ----------------------------
-- Table structure for claim_1
-- ----------------------------
DROP TABLE IF EXISTS `claim_1`;
CREATE TABLE `claim_1`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '标的标识',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '发标人用户标识(冗余)',
  `SOURCE_TENDER_ID` bigint(0) NOT NULL COMMENT '投标信息标识(转让来源)',
  `ROOT_PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '原始标的标识(冗余)',
  `ROOT_PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '原始标的编码(冗余)',
  `ASSIGNMENT_REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '债权转让 请求流水号',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_Reference_17`(`PROJECT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '债权转让标的附加信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of claim_1
-- ----------------------------

-- ----------------------------
-- Table structure for project_0
-- ----------------------------
DROP TABLE IF EXISTS `project_0`;
CREATE TABLE `project_0`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '发标人用户标识',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发标人用户编码',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的名称',
  `DESCRIPTION` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '标的描述',
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的类型',
  `PERIOD` int(0) NULL DEFAULT NULL COMMENT '标的期限(单位:天)',
  `ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(投资人视图)',
  `BORROWER_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(借款人视图)',
  `COMMISSION_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(平台佣金，利差)',
  `REPAYMENT_WAY` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '还款方式',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '募集金额',
  `PROJECT_STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的状态',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '可用状态',
  `IS_ASSIGNMENT` tinyint(0) NULL DEFAULT NULL COMMENT '是否是债权出让标',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '标的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_0
-- ----------------------------

-- ----------------------------
-- Table structure for project_1
-- ----------------------------
DROP TABLE IF EXISTS `project_1`;
CREATE TABLE `project_1`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '发标人用户标识',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发标人用户编码',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的名称',
  `DESCRIPTION` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '标的描述',
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的类型',
  `PERIOD` int(0) NULL DEFAULT NULL COMMENT '标的期限(单位:天)',
  `ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(投资人视图)',
  `BORROWER_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(借款人视图)',
  `COMMISSION_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(平台佣金，利差)',
  `REPAYMENT_WAY` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '还款方式',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '募集金额',
  `PROJECT_STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的状态',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `STATUS` tinyint(0) NULL DEFAULT NULL COMMENT '可用状态',
  `IS_ASSIGNMENT` tinyint(0) NULL DEFAULT NULL COMMENT '是否是债权出让标',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '标的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_1
-- ----------------------------

-- ----------------------------
-- Table structure for tender_0
-- ----------------------------
DROP TABLE IF EXISTS `tender_0`;
CREATE TABLE `tender_0`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '投标人用户标识',
  `CONSUMER_USERNAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标人用户名',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标人用户编码',
  `PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '标的标识',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `AMOUNT` decimal(10, 0) NULL DEFAULT NULL COMMENT '投标冻结金额',
  `TENDER_STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标状态',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标/债权转让 请求流水号',
  `STATUS` tinyint(0) NULL DEFAULT NULL COMMENT '可用状态',
  `PROJECT_NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的名称',
  `PROJECT_PERIOD` int(0) NULL DEFAULT NULL COMMENT '标的期限(单位:天) -- 冗余字段',
  `PROJECT_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(投资人视图) -- 冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '投标信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tender_0
-- ----------------------------

-- ----------------------------
-- Table structure for tender_1
-- ----------------------------
DROP TABLE IF EXISTS `tender_1`;
CREATE TABLE `tender_1`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NOT NULL COMMENT '投标人用户标识',
  `CONSUMER_USERNAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标人用户名',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标人用户编码',
  `PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '标的标识',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `AMOUNT` decimal(10, 0) NULL DEFAULT NULL COMMENT '投标冻结金额',
  `TENDER_STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标状态',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标/债权转让 请求流水号',
  `STATUS` tinyint(0) NULL DEFAULT NULL COMMENT '可用状态',
  `PROJECT_NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的名称',
  `PROJECT_PERIOD` int(0) NULL DEFAULT NULL COMMENT '标的期限(单位:天) -- 冗余字段',
  `PROJECT_ANNUAL_RATE` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(投资人视图) -- 冗余字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '投标信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tender_1
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
