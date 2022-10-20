/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.21-log : Database - data_factory
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`data_factory` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `data_factory`;

/*Table structure for table `data_source` */

DROP TABLE IF EXISTS `data_source`;

CREATE TABLE `data_source` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_name` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源名称',
  `source_code` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源code,唯一',
  `source_type` int(11) NOT NULL DEFAULT '0' COMMENT '数据源类型/(nacos,api,enum)',
  `provider_src` varchar(200) NOT NULL DEFAULT '' COMMENT '数据源提供来源',
  `token` varchar(400) NOT NULL DEFAULT '' COMMENT '访问token',
  `url` varchar(400) NOT NULL DEFAULT '' COMMENT '数据源访问url',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0正常,1过期)',
  `visit_strategy` int(11) NOT NULL DEFAULT '0' COMMENT '访问策略（0动态获取/1本地缓存）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `update_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='数据源管理表';

/*Data for the table `data_source` */

insert  into `data_source`(`id`,`source_name`,`source_code`,`source_type`,`provider_src`,`token`,`url`,`status`,`visit_strategy`,`create_time`,`update_time`,`create_user_id`,`update_user_id`) values (2,'数据源类型枚举','com.coderman.datafactory.demo.DataSourceTypeEnum',2,'datafactory.DataSourceTypeEnum','','',0,1,'2020-12-09 15:42:06','2020-12-09 15:42:06',1,1),(5,'数据源类型枚举','com.lightsnail.snail.room:room_source_type',0,'nacos','','',0,1,'2020-12-09 15:52:52','2020-12-09 15:52:52',1,1),(6,'数据源类型枚举','com.lightsnail.snail.room.room_source_type',0,'nacos','','',0,1,'2020-12-10 15:25:47','2020-12-10 15:25:47',1,1),(9,'虚拟部门列表','com.lightsnail.infosys.department',3,'自定义注册','','',0,1,'2021-01-20 15:13:48','2021-01-20 15:13:48',1,1),(13,'虚拟员工类型枚举数据','com.lightsnail.infosys.staffType',3,'自定义注册','','',0,1,'2021-01-21 15:03:29','2021-01-21 15:03:29',1,1),(14,'省份数据','com.lightsnail.app.dict.common.province_group',0,'自定义注册','','',0,0,'2021-01-23 15:58:54','2021-01-23 15:58:54',1,1);

/*Table structure for table `data_source_detail` */

DROP TABLE IF EXISTS `data_source_detail`;

CREATE TABLE `data_source_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `data_source_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属数据源id',
  `data_content_json` text COMMENT '数据源内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='数据源详情表';

/*Data for the table `data_source_detail` */

insert  into `data_source_detail`(`id`,`data_source_id`,`data_content_json`) values (1,1,'[{\"k\":0,\"v\":\"NACOS\"},{\"k\":1,\"v\":\"API\"},{\"k\":2,\"v\":\"ENUM\"},{\"k\":3,\"v\":\"自定义\"}]'),(4,9,'[{\"departName\":\"信息技术部\",\"id\":100,\"managerId\":4005,\"superId\":50},{\"departName\":\"架构技术部\",\"id\":101,\"managerId\":4000,\"superId\":57},{\"departName\":\"前端技术部\",\"id\":103,\"managerId\":4000,\"superId\":57},{\"departName\":\"测试技术部\",\"id\":104,\"managerId\":40060,\"superId\":52},{\"$ref\":\"$[3]\"}]'),(8,13,'[{\"code\":1,\"name\":\"正式员工\"},{\"code\":2,\"name\":\"非正式员工-实习生\"},{\"code\":3,\"name\":\"非正式员工-外包\"},{\"code\":4,\"name\":\"非正式员工-顾问\"}]');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
