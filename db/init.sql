CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `header` varchar(255) DEFAULT NULL COMMENT '头像',
  `user_type` int(11) NOT NULL COMMENT '用户类型，0普通用户，1超管',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '菜单名称',
  `level` int(11) NOT NULL COMMENT '菜单层级',
  `page` varchar(32) DEFAULT NULL COMMENT '页面文件',
  `url` varchar(200) NOT NULL DEFAULT ''COMMENT '操作链接',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父级编号，顶级编号为0',
  `order_num` int(11) DEFAULT NULL COMMENT '模块排序号',
  `icon_cls` varchar(32) DEFAULT NULL COMMENT '菜单图标样式',
  `hidden` TINYINT(1) DEFAULT '0' NOT NULL COMMENT '是否隐藏',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

CREATE TABLE `user_menu_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) COMMENT='用户菜单关系表';

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='角色表';


-- --------------------------------------------------------------------------

INSERT INTO test.menu
(id, name, `level`, page, url, remark, parent_id, order_num, icon_cls, create_time, update_time)
VALUES(1, '设置', 1, '', '', NULL, 0, 1, 'el-icon-setting', '2017-11-01 16:45:10.000', '2017-11-01 16:46:48.000');
INSERT INTO test.menu
(id, name, `level`, page, url, remark, parent_id, order_num, icon_cls, create_time, update_time)
VALUES(2, '菜单管理', 2, '/setting/Menu.vue', '/menu', NULL, 1, 1, NULL, '2017-11-01 16:47:14.000', '2017-11-03 17:08:22.000');
INSERT INTO test.menu
(id, name, `level`, page, url, remark, parent_id, order_num, icon_cls, create_time, update_time)
VALUES(3, '角色管理', 2, '/system/RoleManager.vue', '/role', NULL, 1, 2, NULL, '2017-11-01 16:48:10.000', '2017-11-01 16:48:10.000');
INSERT INTO test.menu
(id, name, `level`, page, url, remark, parent_id, order_num, icon_cls, create_time, update_time)
VALUES(4, '用户信息', 2, '/setting/UserInfo.vue', '/user_info', NULL, 1, 3, NULL, '2017-11-02 16:57:37.000', '2017-11-02 16:57:37.000');


INSERT INTO test.`user`
(id, username, password, email, header, user_type, deleted, create_time, update_time)
VALUES(1, 'wen', 'ICy5YqxZB1uWSwcVLSNLcA==', 'amwyyyy@qq.com', NULL, 1, 0, '2017-11-01 12:30:20.000', '2017-11-02 18:11:44.000');


INSERT INTO test.user_menu_rel
(id, user_id, menu_id)
VALUES(1, 1, 1);
INSERT INTO test.user_menu_rel
(id, user_id, menu_id)
VALUES(2, 1, 2);
INSERT INTO test.user_menu_rel
(id, user_id, menu_id)
VALUES(3, 1, 3);
INSERT INTO test.user_menu_rel
(id, user_id, menu_id)
VALUES(4, 1, 4);
