/*
 Navicat Premium Data Transfer

 Source Server         : wanxinp2p
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:32523
 Source Schema         : p2p_repayment

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 04/12/2022 21:40:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for receivable_detail
-- ----------------------------
DROP TABLE IF EXISTS `receivable_detail`;
CREATE TABLE `receivable_detail`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `RECEIVABLE_ID` bigint(0) NULL DEFAULT NULL COMMENT '应收项标识',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '实收本息',
  `RECEIVABLE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '实收时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '投资人实收明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receivable_detail
-- ----------------------------

-- ----------------------------
-- Table structure for receivable_plan
-- ----------------------------
DROP TABLE IF EXISTS `receivable_plan`;
CREATE TABLE `receivable_plan`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NULL DEFAULT NULL COMMENT '投标人用户标识',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投标人用户编码',
  `TENDER_ID` bigint(0) NOT NULL COMMENT '投标信息标识',
  `REPAYMENT_ID` bigint(0) NULL DEFAULT NULL COMMENT '还款计划项标识',
  `NUMBER_OF_PERIODS` int(0) NULL DEFAULT NULL COMMENT '期数',
  `INTEREST` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收利息',
  `PRINCIPAL` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收本金',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收本息',
  `SHOULD_RECEIVABLE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '应收时间',
  `RECEIVABLE_STATUS` tinyint(0) NULL DEFAULT NULL COMMENT '状态：0,.未收 1.已收  2.部分收到',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `COMMISSION` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(平台佣金，利差)',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '投资人应收明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receivable_plan
-- ----------------------------

-- ----------------------------
-- Table structure for repayment_detail
-- ----------------------------
DROP TABLE IF EXISTS `repayment_detail`;
CREATE TABLE `repayment_detail`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `REPAYMENT_PLAN_ID` bigint(0) NULL DEFAULT NULL COMMENT '还款计划项标识',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '实还本息',
  `REPAYMENT_DATE` datetime(0) NULL DEFAULT NULL COMMENT '实际还款时间',
  `REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冻结用户资金请求流水号(用于解冻合并整体还款)，\r\n            有漏洞，存管不支持单次“确定还款”，合并多个还款预处理的操作，折中做法。',
  `STATUS` tinyint(0) NULL DEFAULT NULL COMMENT '可用状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '借款人还款明细，针对一个还款计划可多次进行还款' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repayment_detail
-- ----------------------------

-- ----------------------------
-- Table structure for repayment_plan
-- ----------------------------
DROP TABLE IF EXISTS `repayment_plan`;
CREATE TABLE `repayment_plan`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `CONSUMER_ID` bigint(0) NULL DEFAULT NULL COMMENT '发标人用户标识',
  `USER_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发标人用户编码',
  `PROJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '标的标识',
  `PROJECT_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标的编码',
  `NUMBER_OF_PERIODS` int(0) NULL DEFAULT NULL COMMENT '期数',
  `INTEREST` decimal(10, 2) NULL DEFAULT NULL COMMENT '还款利息',
  `PRINCIPAL` decimal(10, 2) NULL DEFAULT NULL COMMENT '还款本金',
  `AMOUNT` decimal(10, 2) NULL DEFAULT NULL COMMENT '本息',
  `SHOULD_REPAYMENT_DATE` datetime(0) NULL DEFAULT NULL COMMENT '应还时间',
  `REPAYMENT_STATUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '应还状态0.待还,1.已还， 2.部分还款',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '计划创建时间',
  `COMMISSION` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率(平台佣金，利差)',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '借款人还款计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repayment_plan
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
