/*
 Navicat Premium Data Transfer

 Source Server         : wanxinp2p
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:32523
 Source Schema         : p2p_depository_agent

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 04/12/2022 21:40:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for depository_record
-- ----------------------------
DROP TABLE IF EXISTS `depository_record`;
CREATE TABLE `depository_record`  (
  `ID` bigint(0) NOT NULL COMMENT '主键',
  `REQUEST_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求流水号',
  `REQUEST_TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求类型:1.用户信息(新增、编辑)、2.绑卡信息',
  `OBJECT_TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '业务实体类型',
  `OBJECT_ID` bigint(0) NULL DEFAULT NULL COMMENT '关联业务实体标识',
  `CREATE_DATE` datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
  `IS_SYN` tinyint(1) NULL DEFAULT NULL COMMENT '是否是同步调用',
  `REQUEST_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '数据同步状态',
  `CONFIRM_DATE` datetime(0) NULL DEFAULT NULL COMMENT '消息确认时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '存管交易记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depository_record
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
