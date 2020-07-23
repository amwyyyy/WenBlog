CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(32) NOT NULL COMMENT '用户名',
  password varchar(128) NOT NULL COMMENT '密码',
  email varchar(32) DEFAULT NULL COMMENT '邮箱',
  header varchar(255) DEFAULT NULL COMMENT '头像',
  user_type int(11) NOT NULL COMMENT '用户类型，0普通用户，1超管',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
);

CREATE TABLE menu (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(32) NOT NULL COMMENT '菜单名称',
  level int(11) NOT NULL COMMENT '菜单层级',
  page varchar(32) DEFAULT NULL COMMENT '页面文件',
  url varchar(200) NOT NULL COMMENT '操作链接',
  remark varchar(128) DEFAULT NULL COMMENT '备注',
  parent_id int(11) NOT NULL COMMENT '父级编号，顶级编号为0',
  order_num int(11) DEFAULT NULL COMMENT '模块排序号',
  icon_cls varchar(32) DEFAULT NULL COMMENT '菜单图标样式',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
);

CREATE TABLE user_menu_rel (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL COMMENT '角色id',
  menu_id int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id)
);