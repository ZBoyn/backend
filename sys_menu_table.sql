-- 创建sys_menu表
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `type` varchar(20) NOT NULL COMMENT '菜单类型：menu, catalog, button, embedded, link',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `auth_code` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  
  -- 元数据字段
  `meta_icon` varchar(100) DEFAULT NULL COMMENT 'meta.icon',
  `meta_title` varchar(100) DEFAULT NULL COMMENT 'meta.title',
  `meta_order` int(11) DEFAULT 0 COMMENT 'meta.order',
  `meta_affix_tab` tinyint(1) DEFAULT 0 COMMENT 'meta.affixTab',
  `meta_badge` varchar(50) DEFAULT NULL COMMENT 'meta.badge',
  `meta_badge_type` varchar(20) DEFAULT NULL COMMENT 'meta.badgeType',
  `meta_badge_variants` varchar(20) DEFAULT NULL COMMENT 'meta.badgeVariants',
  `meta_iframe_src` varchar(500) DEFAULT NULL COMMENT 'meta.iframeSrc',
  `meta_link` varchar(500) DEFAULT NULL COMMENT 'meta.link',
  
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`),
  KEY `idx_status` (`status`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- 插入示例数据
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `path`, `component`, `type`, `icon`, `status`, `auth_code`, `meta_icon`, `meta_title`, `meta_order`, `meta_affix_tab`) VALUES
(1, NULL, 'Workspace', '/workspace', '/dashboard/workspace/index', 'menu', 'mdi:dashboard', 1, NULL, 'carbon:workspace', 'page.dashboard.workspace', 0, 1),
(2, NULL, 'System', '/system', NULL, 'catalog', NULL, 1, NULL, 'carbon:settings', 'system.title', 9997, 0),
(201, 2, 'SystemMenu', '/system/menu', '/system/menu/list', 'menu', NULL, 1, 'System:Menu:List', 'carbon:menu', 'system.menu.title', NULL, 0),
(20101, 201, 'SystemMenuCreate', NULL, NULL, 'button', NULL, 1, 'System:Menu:Create', NULL, 'common.create', NULL, 0),
(20102, 201, 'SystemMenuEdit', NULL, NULL, 'button', NULL, 1, 'System:Menu:Edit', NULL, 'common.edit', NULL, 0),
(20103, 201, 'SystemMenuDelete', NULL, NULL, 'button', NULL, 1, 'System:Menu:Delete', NULL, 'common.delete', NULL, 0),
(202, 2, 'SystemDept', '/system/dept', '/system/dept/list', 'menu', NULL, 1, 'System:Dept:List', 'carbon:container-services', 'system.dept.title', NULL, 0),
(20401, 202, 'SystemDeptCreate', NULL, NULL, 'button', NULL, 1, 'System:Dept:Create', NULL, 'common.create', NULL, 0),
(20402, 202, 'SystemDeptEdit', NULL, NULL, 'button', NULL, 1, 'System:Dept:Edit', NULL, 'common.edit', NULL, 0),
(20403, 202, 'SystemDeptDelete', NULL, NULL, 'button', NULL, 1, 'System:Dept:Delete', NULL, 'common.delete', NULL, 0),
(9, NULL, 'Project', '/vben-admin', NULL, 'catalog', NULL, 1, NULL, 'carbon:data-center', 'demos.vben.title', 9998, 0),
(901, 9, 'VbenDocument', '/vben-admin/document', 'IFrameView', 'embedded', NULL, 1, NULL, 'carbon:book', 'demos.vben.document', NULL, 0),
(902, 9, 'VbenGithub', '/vben-admin/github', 'IFrameView', 'link', NULL, 1, NULL, 'carbon:logo-github', 'Github', NULL, 0),
(903, 9, 'VbenAntdv', '/vben-admin/antdv', 'IFrameView', 'link', NULL, 0, NULL, 'carbon:hexagon-vertical-solid', 'demos.vben.antdv', NULL, 0),
(10, NULL, 'About', '/about', '_core/about/index', 'menu', NULL, 1, NULL, 'lucide:copyright', 'demos.vben.about', 9999, 0);

-- 更新meta字段
UPDATE `sys_menu` SET `meta_badge` = 'new', `meta_badge_type` = 'normal', `meta_badge_variants` = 'primary' WHERE `id` = 2;
UPDATE `sys_menu` SET `meta_iframe_src` = 'https://doc.vben.pro' WHERE `id` = 901;
UPDATE `sys_menu` SET `meta_link` = 'https://github.com/vbenjs/vue-vben-admin' WHERE `id` = 902;
UPDATE `sys_menu` SET `meta_badge_type` = 'dot', `meta_link` = 'https://ant.vben.pro' WHERE `id` = 903; 