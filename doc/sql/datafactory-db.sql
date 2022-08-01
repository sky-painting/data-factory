CREATE TABLE `project_config`(
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
    `project_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '项目应用名',
    `project_desc` varchar(500) NOT NULL DEFAULT  '' COMMENT '应用描述',
    `domain_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '业务领域编码',
    `domain_desc` varchar(50) NOT NULL DEFAULT  '' COMMENT '业务领域描述',
    `bus_line` varchar(50) NOT NULL DEFAULT  '' COMMENT '所属业务线',
    `dept_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '所属部门',
    `date_create` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
    `date_update` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
    `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人',
    `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_p` (`project_code`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '项目配置';


CREATE TABLE `api_model`(
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
                            `project_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '项目名称',
                            `api_url` varchar(500) NOT NULL DEFAULT  '' COMMENT 'api信息',
                            `api_type` varchar(50) NOT NULL DEFAULT  '' COMMENT 'api信息',
                            `api_return_value` varchar(500) NOT NULL DEFAULT  '' COMMENT 'api返回值集合',
                            `api_sign` varchar(500) NOT NULL DEFAULT  '' COMMENT 'api签名信息',
                            `api_doc` varchar(500) NOT NULL DEFAULT  '' COMMENT 'api描述',
                            `method_type` varchar(500) NOT NULL DEFAULT  '' COMMENT '请求方式',
                            `request_param` json  COMMENT '请求参数元信息',
                            `return_param` json  COMMENT '响应参数元信息',
                            `status` int(11) NOT NULL DEFAULT  0 COMMENT 'api状态',
                            `date_create` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
                            `date_update` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
                            `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人',
                            `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人',
                            PRIMARY KEY (`id`))
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = 'API数据表';


CREATE TABLE `param_model`(
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
 `project_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '项目名称',
 `param_class_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '参数类名',
 `param_class_desc` varchar(500) NOT NULL DEFAULT  '' COMMENT '参数类描述',
 `module_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '所属上下文或模块编码',
 `model_suffix` varchar(50) NOT NULL DEFAULT  '' COMMENT '模型后缀',
 `date_create` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
 `date_update` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
 `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人',
 `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uniq_p_p` (`project_code`,`param_class_name`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '参数模型表';


CREATE TABLE `table_model`(
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
 `table_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '数据库表名',
 `table_comment` varchar(500) NOT NULL DEFAULT  '' COMMENT '数据库表描述',
 `db_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '数据库名称',
 `project_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '项目编码',
 `date_create` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
 `date_update` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
 `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人',
 `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uniq_p_t` (`project_code`,`table_name`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '表模型';


CREATE TABLE `column_model`(
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
  `table_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '所属数据库',
  `column_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '字段名称',
  `column_comment` varchar(500) NOT NULL DEFAULT  '' COMMENT '字段描述',
  `column_length` int(11) NOT NULL DEFAULT  0 COMMENT '字段长度',
  `column_type` varchar(50) NOT NULL DEFAULT  '' COMMENT '字段类型',
  `not_null` int(11) NOT NULL DEFAULT  0 COMMENT '是否可为空',
  `default_value` varchar(500) NOT NULL DEFAULT  '' COMMENT '默认值',
  `uniq_column` int(11) NOT NULL DEFAULT  0 COMMENT '是否是唯一键',
  `date_create` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
  `date_update` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
  `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人',
  `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_t_c` (`table_id`,`column_name`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '字段模型';


CREATE TABLE `model_suffix_config`(
         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
         `suffix` varchar(50) NOT NULL DEFAULT  '' COMMENT '模型后缀',
         `suffix_desc` varchar(500) NOT NULL DEFAULT  '' COMMENT '模型后缀描述',
         `model_type` varchar(50) NOT NULL DEFAULT  '' COMMENT '模型类型',
         PRIMARY KEY (`id`),
         UNIQUE KEY `uniq_s` (`suffix`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '后缀配置表';


CREATE TABLE `model_mapping_config` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`mapping_class_first` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '对外api参数属性' COLLATE 'utf8mb4_general_ci',
	`mapping_field_first` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '服务参数属性' COLLATE 'utf8mb4_general_ci',
	`mapping_class_second` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '数据库实体属性' COLLATE 'utf8mb4_general_ci',
	`mapping_field_second` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '数据库表字段' COLLATE 'utf8mb4_general_ci',
	`mapping_type` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '映射类型' COLLATE 'utf8mb4_general_ci',
	`project_code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '项目编码' COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uniq_a_s_e_c` (`mapping_class_first`, `mapping_field_first`, `mapping_class_second`, `mapping_field_second`) USING BTREE
)
COMMENT='模型映射表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB

CREATE TABLE `field_model`(
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
 `field_name` varchar(50) NOT NULL DEFAULT  '' COMMENT '属性名称',
 `field_type` varchar(50) NOT NULL DEFAULT  '' COMMENT '属性类型',
 `field_desc` varchar(500) NOT NULL DEFAULT  '' COMMENT '属性描述',
 `field_doc` varchar(500)  DEFAULT  '' COMMENT '属性中文注释',
 `data_source_code` varchar(100) NOT NULL DEFAULT  '' COMMENT '关联数据源编码',
 `param_class_name` varchar(100) NOT NULL DEFAULT  '' COMMENT '参数类名称',
 `project_code` varchar(50) NOT NULL DEFAULT  '' COMMENT '项目编码',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uniq_p_p_f` (`project_code`,`param_class_name`,`field_name`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '属性模型';


CREATE TABLE `data_source_detail`(
        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
        `data_source_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '所属数据源id',
        `data_content_json` text  COMMENT '数据源内容',
        PRIMARY KEY (`id`))
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '数据源详情表';


CREATE TABLE `data_source_resp_config`(
                                     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
                                     `data_source_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '所属数据源id',
                                     `field_key`  varchar(50)  COMMENT '响应id',
                                     `refer_path`  varchar(100)  COMMENT '请求路径如a.b.c',
                                     `field_desc`  varchar(50)  COMMENT '数据类型',
                                     `field_type`  varchar(50)  COMMENT '数据描述',

                                     PRIMARY KEY (`id`))
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '数据源响应参数配置';


CREATE TABLE `data_source`(
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键',
 `source_name` varchar(200) NOT NULL DEFAULT  '' COMMENT '数据源名称',
 `source_code` varchar(200) NOT NULL DEFAULT  '' COMMENT '数据源code,唯一',
 `source_type` int(11) NOT NULL DEFAULT 0 COMMENT '数据源类型(nacos,api,enum) ',
 `provider_service` varchar(200) NOT NULL DEFAULT  '' COMMENT '数据源提供来源',
 `struct_type` varchar(400) DEFAULT  '' COMMENT '数据源格式',
 `url` varchar(400) NOT NULL DEFAULT  '' COMMENT '数据源访问url',
 `status` int(11) NOT NULL DEFAULT  0 COMMENT '状态(0正常,1过期)',
 `visit_strategy` int(11) NOT NULL DEFAULT  0 COMMENT '访问策略（0动态获取,1本地缓存)',
 `regist_server` varchar(400) NOT NULL DEFAULT  '' COMMENT '注册中心类型',
 `provider_domain_url` varchar(400) NOT NULL DEFAULT  '' COMMENT '服务提供者域名',

 `create_time` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT  '2000-01-01 00:00:00' COMMENT '修改时间',
 `create_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '创建人id',
 `update_user_id` bigint(20) NOT NULL DEFAULT  0 COMMENT '修改人id',
 PRIMARY KEY (`id`),
 UNIQUE KEY `uniq_s` (`source_code`) COMMENT '联合唯一索引')
    ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '数据源管理表';

CREATE TABLE `kv_instance` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `k` varchar(64) NOT NULL DEFAULT '' COMMENT 'key',
                               `v` tinytext NOT NULL COMMENT 'value值',
                               `value_json` json DEFAULT NULL,
                               `group_key` varchar(64) DEFAULT '' COMMENT '分组key',
                               `parent_key` varchar(64) DEFAULT '' COMMENT '父级key',
                               `refer_key` varchar(64) DEFAULT '' COMMENT '引用对象key',
                               `refer_id` varchar(64) DEFAULT '' COMMENT '引用对象实例ID',
                               `value_type` int(11) DEFAULT '0' COMMENT 'value的Java数据类型',
                               `key_type` int(11) DEFAULT '0' COMMENT 'key的Java数据类型',
                               `status` int(11) DEFAULT NULL COMMENT '状态,跟着主对象走',
                               PRIMARY KEY (`id`),
                               KEY `idx_key` (`k`),
                               KEY `idx_group_key` (`group_key`),
                               KEY `idx_refer_id` (`refer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
