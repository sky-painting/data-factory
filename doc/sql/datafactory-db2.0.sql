/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.21-log : Database - data_factory
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`data_factory` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `data_factory`;

/*Table structure for table `api_model` */

CREATE TABLE `api_model` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目名称',
                             `api_url` varchar(500) NOT NULL DEFAULT '' COMMENT 'api信息',
                             `api_type` varchar(50) NOT NULL DEFAULT '' COMMENT 'api信息',
                             `api_return_value` varchar(500) NOT NULL DEFAULT '' COMMENT 'api返回值集合',
                             `api_sign` varchar(500) NOT NULL DEFAULT '' COMMENT 'api签名信息',
                             `api_doc` varchar(500) NOT NULL DEFAULT '' COMMENT 'api描述',
                             `return_wrap_type` int(11) DEFAULT '0' COMMENT 'api包装返回类型',
                             `mock_count` int(11) DEFAULT '1' COMMENT '进行接口mock数据的时候接口返回的数据条数',
                             `method_type` varchar(500) NOT NULL DEFAULT '' COMMENT '请求方式',
                             `request_param` json DEFAULT NULL COMMENT '请求参数元信息',
                             `return_param` json DEFAULT NULL COMMENT '响应参数元信息',
                             `status` int(11) NOT NULL DEFAULT '0' COMMENT 'api状态',
                             `date_create` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
                             `date_update` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
                             `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
                             `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API数据表';

/*Data for the table `api_model` */

/*Table structure for table `column_model` */

CREATE TABLE `column_model` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `table_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属数据库',
                                `column_name` varchar(50) NOT NULL DEFAULT '' COMMENT '字段名称',
                                `column_comment` varchar(500) NOT NULL DEFAULT '' COMMENT '字段描述',
                                `column_length` int(11) NOT NULL DEFAULT '0' COMMENT '字段长度',
                                `column_type` varchar(50) NOT NULL DEFAULT '' COMMENT '字段类型',
                                `not_null` int(11) NOT NULL DEFAULT '0' COMMENT '是否可为空',
                                `default_value` varchar(500) NOT NULL DEFAULT '' COMMENT '默认值',
                                `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                                `uniq_column` int(11) NOT NULL DEFAULT '0' COMMENT '是否是唯一键',
                                `date_create` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
                                `date_update` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
                                `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
                                `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_t_c` (`table_id`,`column_name`) COMMENT '联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字段模型';

/*Data for the table `column_model` */

/*Table structure for table `data_source` */

CREATE TABLE `data_source` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `source_name` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源名称',
                               `source_code` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源code,唯一',
                               `source_type` int(11) NOT NULL DEFAULT '0' COMMENT '数据源类型/(nacos,api,enum)',
                               `provider_service` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源提供来源',
                               `struct_type` varchar(400) DEFAULT '' COMMENT '数据源返回格式',
                               `url` varchar(400) NOT NULL DEFAULT '' COMMENT '数据源访问url',
                               `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0正常,1过期)',
                               `regist_server` varchar(200) NOT NULL DEFAULT '' COMMENT '注册中心',
                               `provider_domain_url` varchar(200) NOT NULL DEFAULT '' COMMENT '服务提供者域名',
                               `visit_strategy` int(11) NOT NULL DEFAULT '0' COMMENT '访问策略（0动态获取/1本地缓存）',
                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                               `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
                               `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人id',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='数据源管理表';

/*Data for the table `data_source` */

insert  into `data_source`(`id`,`source_name`,`source_code`,`source_type`,`provider_service`,`struct_type`,`url`,`status`,`regist_server`,`provider_domain_url`,`visit_strategy`,`create_time`,`update_time`,`create_user_id`,`update_user_id`) values (15,'身份证号','com.datafactory.user.cardnumber',5,'data-factory',NULL,'datafactory',0,'0','datafactory',3,'2022-09-06 21:25:53','2022-09-06 21:25:53',1,1),(16,'日期','com.datafactory.date.date',5,'data-factory',NULL,'datafactory',0,'0','datafactory',3,'2022-09-06 21:26:01','2022-09-06 21:26:01',1,1),(17,'姓名','com.datafactory.user.chineseName',5,'data-factory',NULL,'datafactory',0,'0','datafactory',3,'2022-09-06 21:26:07','2022-09-06 21:26:07',1,1),(24,'电话号码','com.datafactory.user.telphone',5,'data-factory','','datafactory',0,'0','datafactory',3,'2022-09-06 22:27:03','2022-09-06 22:27:03',1,1),(25,'随机数','com.datafactory.user.getRandom',5,'data-factory','','datafactory',0,'0','datafactory',3,'2022-09-06 22:27:16','2022-09-06 22:27:16',1,1),(26,'星期','com.datafactory.common.week',3,'data-factory','','datafactory',0,'0','datafactory',1,'2022-09-12 16:13:36','2022-09-12 16:13:36',1,1),(27,'评论信息','com.datafactory.user.comment',5,'data-factory',NULL,'data-factory',0,'0','data-factory',3,'2022-09-12 16:12:25','2022-09-12 16:12:25',1,1),(28,'英文名单词','com.datafactory.user.oneEnWord',5,'data-factory',NULL,'data-factory',0,'0','data-factory',3,'2022-09-12 16:13:18','2022-09-12 16:13:18',1,1),(30,'随机浮点数','com.datafactory.random.float',5,'data-factory','VOID','xxxx',0,'0','xxx',3,'2022-09-17 23:13:03','2022-09-17 23:13:03',1,1),(31,'银行卡号','com.datafactory.bank.cardNumber',5,'data-factory','VOID','xxxx',0,'0','xxx',3,'2022-09-17 23:14:58','2022-09-17 23:14:58',1,1),(32,'时间','com.datafactory.date.datetime',5,'data-factory','VOID','xxxxx',0,'0','xxxxx',3,'2022-09-18 16:10:58','2022-09-18 16:10:58',1,1),(33,'UUID','com.datafactory.user.uuid',5,'data-factory','VOID','xxxx',0,'0','xxxx',3,'2022-09-19 22:21:01','2022-09-19 22:21:01',1,1),(34,'SnowflakeId','com.datafactory.user.snowflakeid',5,'data-factory','VOID','xxxx',0,'0','xxx',3,'2022-09-19 22:21:43','2022-09-19 22:21:43',1,1);

/*Table structure for table `data_source_req_config` */

CREATE TABLE `data_source_req_config` (
                                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `data_source_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属数据源id',
                                          `param_key` varchar(100) DEFAULT NULL COMMENT '参数key',
                                          `param_value` varchar(500) DEFAULT NULL COMMENT '参数值',
                                          `param_desc` varchar(500) DEFAULT NULL COMMENT '参数描述',
                                          `required` int(11) DEFAULT NULL COMMENT '是否是可选参数',
                                          `interface_param` int(11) DEFAULT NULL COMMENT '是否是接口参数',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='数据源请求参数配置';


/*Table structure for table `data_source_resp_config` */

CREATE TABLE `data_source_resp_config` (
                                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `data_source_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属数据源id',
                                           `field_key` varchar(50) DEFAULT NULL COMMENT '响应id',
                                           `refer_path` varchar(100) DEFAULT NULL COMMENT '请求路径如a.b.c',
                                           `field_desc` varchar(50) DEFAULT NULL COMMENT '数据类型',
                                           `field_type` varchar(50) DEFAULT NULL COMMENT '数据描述',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='数据源响应参数配置';


/*Table structure for table `field_model` */

CREATE TABLE `field_model` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `field_name` varchar(50) NOT NULL DEFAULT '' COMMENT '属性名称',
                               `field_type` varchar(50) NOT NULL DEFAULT '' COMMENT '属性类型',
                               `field_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '属性描述',
                               `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                               `field_doc` varchar(500) DEFAULT '' COMMENT '属性中文注释',
                               `field_ext_json` json DEFAULT NULL COMMENT '关联数据源编码',
                               `param_class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '参数类名称',
                               `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编码',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uniq_p_p_f` (`project_code`,`param_class_name`,`field_name`) COMMENT '联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='属性模型';

/*Data for the table `field_model` */

/*Table structure for table `kv_instance` */

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;


/*Table structure for table `model_mapping_config` */

CREATE TABLE `model_mapping_config` (
                                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `mapping_class_first` varchar(100) NOT NULL DEFAULT '' COMMENT '对外api参数属性',
                                        `mapping_field_first` varchar(100) NOT NULL DEFAULT '' COMMENT '服务参数属性',
                                        `mapping_class_second` varchar(100) NOT NULL DEFAULT '' COMMENT '数据库实体属性',
                                        `mapping_field_second` varchar(100) NOT NULL DEFAULT '' COMMENT '数据库表字段',
                                        `mapping_type` varchar(50) NOT NULL DEFAULT '' COMMENT '映射类型',
                                        `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编码',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `uniq_a_s_e_c` (`mapping_class_first`,`mapping_field_first`,`mapping_class_second`,`mapping_field_second`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模型映射表';

/*Data for the table `model_mapping_config` */

/*Table structure for table `model_suffix_config` */

CREATE TABLE `model_suffix_config` (
                                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `suffix` varchar(50) NOT NULL DEFAULT '' COMMENT '模型后缀',
                                       `suffix_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '模型后缀描述',
                                       `model_type` varchar(50) NOT NULL DEFAULT '' COMMENT '模型类型',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `uniq_s` (`suffix`) COMMENT '联合唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='后缀配置表';

/*Data for the table `model_suffix_config` */

insert  into `model_suffix_config`(`id`,`suffix`,`suffix_desc`,`model_type`) values (1,'BO','领域层业务模型','serviceModel'),(2,'DTO','RPC参数模型','apiModel'),(3,'VO','http请求响应模型','apiModel'),(4,'DO','数据库模型','entityModel'),(5,'Event','领域事件','serviceModel');

/*Table structure for table `param_model` */

CREATE TABLE `param_model` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编码',
                               `param_class_name` varchar(50) NOT NULL DEFAULT '' COMMENT '参数类名',
                               `param_class_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '参数类描述',
                               `module_code` varchar(50) NOT NULL DEFAULT '' COMMENT '所属上下文或模块编码',
                               `model_suffix` varchar(50) NOT NULL DEFAULT '' COMMENT '模型后缀',
                               `date_create` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
                               `date_update` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
                               `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
                               `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
                               `param_var_name` varchar(50) DEFAULT '' COMMENT '参数模型名称，api引用的时候会用到',
                               `status` int(11) NOT NULL COMMENT '状态',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uniq_p_p` (`project_code`,`param_class_name`) COMMENT '联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数模型表';

/*Data for the table `param_model` */

/*Table structure for table `project_config` */

CREATE TABLE `project_config` (
                                  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目应用名',
                                  `project_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '应用描述',
                                  `domain_code` varchar(50) NOT NULL DEFAULT '' COMMENT '业务领域编码',
                                  `domain_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '业务领域描述',
                                  `bus_line` varchar(50) NOT NULL DEFAULT '' COMMENT '所属业务线',
                                  `dept_name` varchar(50) NOT NULL DEFAULT '' COMMENT '所属部门',
                                  `date_create` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
                                  `date_update` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
                                  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
                                  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
                                  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uniq_p` (`project_code`) COMMENT '联合唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='项目配置';

/*Data for the table `project_config` */

insert  into `project_config`(`id`,`project_code`,`project_desc`,`domain_code`,`domain_desc`,`bus_line`,`dept_name`,`date_create`,`date_update`,`update_user_id`,`create_user_id`,`status`) values (1,'data-factory','数据工厂','tianhua','数据工厂平台','tech','技术部','2022-10-05 05:45:47','2022-10-05 05:45:47',1,1,0);

/*Table structure for table `table_model` */

CREATE TABLE `table_model` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `table_name` varchar(50) NOT NULL DEFAULT '' COMMENT '数据库表名',
                               `table_comment` varchar(500) NOT NULL DEFAULT '' COMMENT '数据库表描述',
                               `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
                               `db_name` varchar(50) NOT NULL DEFAULT '' COMMENT '数据库名称',
                               `project_code` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编码',
                               `date_create` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
                               `date_update` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
                               `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人',
                               `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uniq_p_t` (`project_code`,`table_name`) COMMENT '联合唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表模型';

/*Data for the table `table_model` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
