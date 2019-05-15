/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50644
Source Host           : localhost:3306
Source Database       : cqns

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-05-06 13:43:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  `resource_type` int(11) DEFAULT NULL,
  `resource_name` varchar(50) NOT NULL DEFAULT '',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `raw_add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role_name` varchar(50) NOT NULL DEFAULT '',
  `resource_type_name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('83', '1', '1', '1', '系统功能', '2019-03-13 11:38:30', '2019-03-13 11:38:30', 'superuser', '菜单');
INSERT INTO `t_role_resource` VALUES ('89', '1', '2', '1', '角色管理', '2019-03-15 17:45:50', '2019-03-15 17:45:50', 'superuser', '菜单');
INSERT INTO `t_role_resource` VALUES ('90', '1', '3', '1', '资源配置', '2019-03-18 14:00:52', '2019-03-18 14:00:52', 'superuser', '菜单');
INSERT INTO `t_role_resource` VALUES ('110', '1', '4', '1', '用户管理', '2019-03-20 14:14:41', '2019-03-20 14:14:41', 'superuser', '菜单');
INSERT INTO `t_role_resource` VALUES ('116', '1', '13', '1', '角色资源管理', '2019-03-27 18:18:40', '2019-03-27 18:18:40', 'superuser', '菜单');
INSERT INTO `t_role_resource` VALUES ('121', '1', '14', '1', '用户角色管理', '2019-03-27 18:30:02', '2019-03-27 18:30:02', 'superuser', '菜单');
