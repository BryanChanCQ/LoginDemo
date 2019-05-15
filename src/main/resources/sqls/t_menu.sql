/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50644
Source Host           : localhost:3306
Source Database       : cqns

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-05-06 13:42:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `parent_id` bigint(20) NOT NULL COMMENT '父id',
  `parent_name` varchar(50) NOT NULL COMMENT '父菜单名',
  `icon` varchar(50) NOT NULL DEFAULT '' COMMENT '图标',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT 'url',
  `type` int(11) DEFAULT '0' COMMENT '菜单类型',
  `raw_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `raw_add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '系统功能', '0', '', 'example', '1', '', '0', '2018-12-03 14:56:08', '2018-12-03 14:56:08');
INSERT INTO `t_menu` VALUES ('2', '角色管理', '1', '系统功能', 'user', '1', '/admin/role/index', '1', '2018-12-03 21:40:36', '2018-12-03 14:56:08');
INSERT INTO `t_menu` VALUES ('3', '资源配置', '1', '系统功能', 'role', '2', '/admin/resource/index', '1', '2018-12-03 14:56:08', '2018-12-03 14:56:08');
INSERT INTO `t_menu` VALUES ('4', '用户管理', '1', '系统功能', 'peoples', '3', '/admin/user/index', '1', '2018-12-03 14:56:08', '2018-12-03 14:56:08');
INSERT INTO `t_menu` VALUES ('13', '角色资源管理', '1', '', 'icon', '0', '/admin/permission/index', '1', '2018-12-06 15:07:31', '2018-12-06 15:07:31');
INSERT INTO `t_menu` VALUES ('14', '用户角色管理', '1', '', 'nested', '0', '/admin/userrole/index', '1', '2018-12-06 15:08:40', '2018-12-06 15:08:40');
