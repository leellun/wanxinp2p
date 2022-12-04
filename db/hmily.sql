/*
 Navicat Premium Data Transfer

 Source Server         : wanxinp2p
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.10.10:32523
 Source Schema         : hmily

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 04/12/2022 21:39:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hmily_lock
-- ----------------------------
DROP TABLE IF EXISTS `hmily_lock`;
CREATE TABLE `hmily_lock`  (
  `lock_id` bigint(0) NOT NULL COMMENT '主键id',
  `trans_id` bigint(0) NOT NULL COMMENT '全局事务id',
  `participant_id` bigint(0) NOT NULL COMMENT 'hmily参与者id',
  `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源id',
  `target_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '锁定目标表名',
  `target_table_pk` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '锁定表主键',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`lock_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'hmily全局lock表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hmily_lock
-- ----------------------------

-- ----------------------------
-- Table structure for hmily_participant_undo
-- ----------------------------
DROP TABLE IF EXISTS `hmily_participant_undo`;
CREATE TABLE `hmily_participant_undo`  (
  `undo_id` bigint(0) NOT NULL COMMENT '主键id',
  `participant_id` bigint(0) NOT NULL COMMENT '参与者id',
  `trans_id` bigint(0) NOT NULL COMMENT '全局事务id',
  `resource_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源id，tac模式下为jdbc url',
  `undo_invocation` longblob NOT NULL COMMENT '回滚调用点',
  `status` tinyint(0) NOT NULL COMMENT '状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`undo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'hmily事务参与者undo记录，用在AC模式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hmily_participant_undo
-- ----------------------------

-- ----------------------------
-- Table structure for hmily_transaction_global
-- ----------------------------
DROP TABLE IF EXISTS `hmily_transaction_global`;
CREATE TABLE `hmily_transaction_global`  (
  `trans_id` bigint(0) NOT NULL COMMENT '全局事务id',
  `app_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `status` tinyint(0) NOT NULL COMMENT '事务状态',
  `trans_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事务模式',
  `retry` int(0) NOT NULL DEFAULT 0 COMMENT '重试次数',
  `version` int(0) NOT NULL COMMENT '版本号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`trans_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'hmily事务表（发起者）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hmily_transaction_global
-- ----------------------------
INSERT INTO `hmily_transaction_global` VALUES (-5224653390205132800, 'hmily-order', 0, 'TCC', 0, 1, '2022-09-04 01:12:14', '2022-09-04 01:12:14');
INSERT INTO `hmily_transaction_global` VALUES (-5224493465744445440, 'hmily-order', 0, 'TCC', 0, 1, '2022-09-04 01:32:05', '2022-09-04 01:32:05');

-- ----------------------------
-- Table structure for hmily_transaction_participant
-- ----------------------------
DROP TABLE IF EXISTS `hmily_transaction_participant`;
CREATE TABLE `hmily_transaction_participant`  (
  `participant_id` bigint(0) NOT NULL COMMENT '参与者事务id',
  `participant_ref_id` bigint(0) NULL DEFAULT NULL COMMENT '参与者关联id且套调用时候会存在',
  `trans_id` bigint(0) NOT NULL COMMENT '全局事务id',
  `trans_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事务类型',
  `status` tinyint(0) NOT NULL COMMENT '分支事务状态',
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `role` tinyint(0) NOT NULL COMMENT '事务角色',
  `retry` int(0) NOT NULL DEFAULT 0 COMMENT '重试次数',
  `target_class` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '接口名称',
  `target_method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '接口方法名称',
  `confirm_method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'confirm方法名称',
  `cancel_method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'cancel方法名称',
  `confirm_invocation` longblob NULL COMMENT 'confirm调用点',
  `cancel_invocation` longblob NULL COMMENT 'cancel调用点',
  `version` int(0) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`participant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'hmily事务参与者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hmily_transaction_participant
-- ----------------------------
INSERT INTO `hmily_transaction_participant` VALUES (-5224653389534044160, NULL, -5224653390205132800, 'TCC', 0, 'hmily-order', 1, 0, 'com.example.hmilytccorder.service.impl.PaymentServiceImpl', 'makePayment', 'confirmOrderStatus', 'cancelOrderStatus', 0x0101005B4C6A6176612E6C616E672E4F626A656374BB01020101636F6D2E6578616D706C652E686D696C797463636F726465722E646F6D61696E2E4F726465F201010201E60F0904010A2FF4B9A1E60200012D353232343636343935323532353532393038B801823101040C01020A000131303030B0016D616B655061796D656EF4010201010100010102636F6D2E6578616D706C652E686D696C797463636F726465722E736572766963652E495061796D656E74536572766963E500, 0x0101005B4C6A6176612E6C616E672E4F626A656374BB01020101636F6D2E6578616D706C652E686D696C797463636F726465722E646F6D61696E2E4F726465F201010201E60F0904010A2FF4B9A1E60200012D353232343636343935323532353532393038B801823101040C01020A000131303030B0016D616B655061796D656EF4010201010100010102636F6D2E6578616D706C652E686D696C797463636F726465722E736572766963652E495061796D656E74536572766963E500, 1, '2022-09-04 01:12:14', '2022-09-04 01:12:14');
INSERT INTO `hmily_transaction_participant` VALUES (-5224493465476009984, NULL, -5224493465744445440, 'TCC', 0, 'hmily-order', 1, 0, 'com.example.hmilytccorder.service.impl.PaymentServiceImpl', 'makePayment', 'confirmOrderStatus', 'cancelOrderStatus', 0x0101005B4C6A6176612E6C616E672E4F626A656374BB01020101636F6D2E6578616D706C652E686D696C797463636F726465722E646F6D61696E2E4F726465F201010201E60F0904011F34C899E6C70300012D353232343439353133393730373934393035B601823101040C01020A000131303030B0016D616B655061796D656EF4010201010100010102636F6D2E6578616D706C652E686D696C797463636F726465722E736572766963652E495061796D656E74536572766963E500, 0x0101005B4C6A6176612E6C616E672E4F626A656374BB01020101636F6D2E6578616D706C652E686D696C797463636F726465722E646F6D61696E2E4F726465F201010201E60F0904011F34C899E6C70300012D353232343439353133393730373934393035B601823101040C01020A000131303030B0016D616B655061796D656EF4010201010100010102636F6D2E6578616D706C652E686D696C797463636F726465722E736572766963652E495061796D656E74536572766963E500, 1, '2022-09-04 01:32:05', '2022-09-04 01:32:05');

SET FOREIGN_KEY_CHECKS = 1;
