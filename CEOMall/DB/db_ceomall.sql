-- MySQL dump 10.13  Distrib 5.6.19, for Win64 (x86_64)
--
-- Host: 192.168.1.200    Database: db_ceomall
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cms_auth`
--

DROP TABLE IF EXISTS `cms_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_auth` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_id` int(11) DEFAULT NULL COMMENT '权限组ID',
  `auth_name` varchar(64) NOT NULL COMMENT '权限名',
  `auth_desc` varchar(200) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `IDXU_cms_auth_auth_name` (`auth_name`),
  KEY `FK_cms_auth_group_id` (`group_id`),
  CONSTRAINT `FK_cms_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `cms_auth_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_auth`
--

LOCK TABLES `cms_auth` WRITE;
/*!40000 ALTER TABLE `cms_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cms_auth_group`
--

DROP TABLE IF EXISTS `cms_auth_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_auth_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(64) DEFAULT NULL COMMENT '权限组名',
  `auth_count` int(11) NOT NULL DEFAULT '0' COMMENT '权限数目',
  `group_desc` varchar(200) DEFAULT NULL COMMENT '权限组描述',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `IDXU_cms_auth_oup_group_name` (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_auth_group`
--

LOCK TABLES `cms_auth_group` WRITE;
/*!40000 ALTER TABLE `cms_auth_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_auth_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cms_options`
--

DROP TABLE IF EXISTS `cms_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_options` (
  `op_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `op_key` varchar(64) NOT NULL COMMENT '名称',
  `op_value` varchar(200) NOT NULL COMMENT '内容',
  `op_desc` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`op_id`),
  UNIQUE KEY `IDXU_cms_options_op_key` (`op_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_options`
--

LOCK TABLES `cms_options` WRITE;
/*!40000 ALTER TABLE `cms_options` DISABLE KEYS */;
INSERT INTO `cms_options` VALUES (1,'theme','default','全局主题');
/*!40000 ALTER TABLE `cms_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cms_role`
--

DROP TABLE IF EXISTS `cms_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `role_time` datetime DEFAULT NULL COMMENT '创建时间',
  `role_desc` varchar(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `IDXU_cms_role_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_role`
--

LOCK TABLES `cms_role` WRITE;
/*!40000 ALTER TABLE `cms_role` DISABLE KEYS */;
INSERT INTO `cms_role` VALUES (0,'平台管理员','2014-01-01 00:00:00','平台管理员');
/*!40000 ALTER TABLE `cms_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cms_role_auth`
--

DROP TABLE IF EXISTS `cms_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `auth_id` int(11) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  KEY `FK_cms_role_auth_role_id` (`role_id`),
  KEY `FK_cms_role_auth_auth_id` (`auth_id`),
  CONSTRAINT `FK_cms_role_auth_auth_id` FOREIGN KEY (`auth_id`) REFERENCES `cms_auth` (`auth_id`),
  CONSTRAINT `FK_cms_role_auth_role_id` FOREIGN KEY (`role_id`) REFERENCES `cms_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理权限分配表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_role_auth`
--

LOCK TABLES `cms_role_auth` WRITE;
/*!40000 ALTER TABLE `cms_role_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cms_user`
--

DROP TABLE IF EXISTS `cms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cms_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) DEFAULT NULL COMMENT '帐号',
  `user_pass` varchar(64) NOT NULL COMMENT '密码',
  `user_email` varchar(20) NOT NULL COMMENT '邮箱',
  `last_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `last_ip` varchar(20) DEFAULT NULL COMMENT '最后登陆IP',
  `is_admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是管理员',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `theme_name` varchar(64) NOT NULL COMMENT '用户主题',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `IDXU_cms_user_user_name` (`user_name`),
  KEY `FK_cms_user_role_id` (`role_id`),
  CONSTRAINT `FK_cms_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `cms_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cms_user`
--

LOCK TABLES `cms_user` WRITE;
/*!40000 ALTER TABLE `cms_user` DISABLE KEYS */;
INSERT INTO `cms_user` VALUES (5,'admin','admin','admin@qq.com',NULL,NULL,'\0',0,'default');
/*!40000 ALTER TABLE `cms_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_order`
--

DROP TABLE IF EXISTS `mall_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mall_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `order_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '订单状态 1未付款 2待发货 3已发货 4已收货 5取消',
  `order_time` datetime NOT NULL COMMENT '生成时间',
  `order_address` varchar(200) NOT NULL COMMENT '订单地址',
  `order_pay_type_id` int(11) DEFAULT NULL COMMENT '支付方式',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `IDXU_mall_order_order_sn` (`order_sn`),
  KEY `FK_mall_order_user_id` (`user_id`),
  KEY `FK_mall_order_order_pay_t_id` (`order_pay_type_id`),
  CONSTRAINT `FK_mall_order_order_pay_t_id` FOREIGN KEY (`order_pay_type_id`) REFERENCES `mall_pay_type` (`pay_id`),
  CONSTRAINT `FK_mall_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `cms_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_order`
--

LOCK TABLES `mall_order` WRITE;
/*!40000 ALTER TABLE `mall_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_pay_record`
--

DROP TABLE IF EXISTS `mall_pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mall_pay_record` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '支付会员',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID 空：转到余额 非空：订单支付',
  `pay_type_id` int(11) DEFAULT NULL COMMENT '支付方式',
  `pay_money` double NOT NULL COMMENT '支付金额',
  `pay_time` datetime NOT NULL COMMENT '支付时间',
  `pay_result` bit(1) NOT NULL COMMENT '支付结果 0失败 1成功',
  PRIMARY KEY (`pay_id`),
  KEY `FK_mall_pay_record_user_id` (`user_id`),
  KEY `FK_mall_pay_record_order_id` (`order_id`),
  KEY `FK_mall_pay_ord_pay_type_id` (`pay_type_id`),
  CONSTRAINT `FK_mall_pay_ord_pay_type_id` FOREIGN KEY (`pay_type_id`) REFERENCES `mall_pay_type` (`pay_id`),
  CONSTRAINT `FK_mall_pay_record_order_id` FOREIGN KEY (`order_id`) REFERENCES `mall_order` (`order_id`),
  CONSTRAINT `FK_mall_pay_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `cms_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_pay_record`
--

LOCK TABLES `mall_pay_record` WRITE;
/*!40000 ALTER TABLE `mall_pay_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_pay_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_pay_type`
--

DROP TABLE IF EXISTS `mall_pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mall_pay_type` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pay_name` varchar(20) NOT NULL COMMENT '支付名称',
  `pay_desc` varchar(4000) DEFAULT NULL COMMENT '支付描述',
  `pay_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '支付状态 0禁用 1启用',
  `pay_code` varchar(20) NOT NULL COMMENT '支付CODE 通过code来区分不同的支付方式',
  PRIMARY KEY (`pay_id`),
  UNIQUE KEY `IDXU_mall_pay_type_pay_code` (`pay_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付方式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_pay_type`
--

LOCK TABLES `mall_pay_type` WRITE;
/*!40000 ALTER TABLE `mall_pay_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_pay_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_user_detail`
--

DROP TABLE IF EXISTS `mall_user_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mall_user_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '会员主键',
  `detail_gender` bit(1) NOT NULL DEFAULT b'1' COMMENT '会员性别',
  `detail_borid` datetime NOT NULL COMMENT '会员生日',
  `detail_phone` varchar(11) NOT NULL COMMENT '会员手机',
  `detail_card` varchar(18) NOT NULL COMMENT '会员身份证',
  `detail_qq` varchar(20) NOT NULL COMMENT '会员QQ',
  `detail_msn` varchar(20) DEFAULT NULL COMMENT '会员MSN',
  `detail_question` varchar(100) DEFAULT NULL COMMENT '密保问题',
  `detail_answer` varchar(100) DEFAULT NULL COMMENT '密保答案',
  `detail_balance` double NOT NULL DEFAULT '0' COMMENT '帐号余额',
  `detail_score` int(11) NOT NULL DEFAULT '0' COMMENT '会员积分',
  `grade_id` int(11) DEFAULT NULL COMMENT '会员等级外键',
  PRIMARY KEY (`detail_id`),
  KEY `FK_mall_user_detail_user_id` (`user_id`),
  KEY `FK_mall_userail_grade_id` (`grade_id`),
  CONSTRAINT `FK_mall_userail_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `mall_user_grade` (`grade_id`),
  CONSTRAINT `FK_mall_user_detail_user_id` FOREIGN KEY (`user_id`) REFERENCES `cms_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息详细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_user_detail`
--

LOCK TABLES `mall_user_detail` WRITE;
/*!40000 ALTER TABLE `mall_user_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_user_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_user_grade`
--

DROP TABLE IF EXISTS `mall_user_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mall_user_grade` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `grade_name` varchar(20) NOT NULL COMMENT '等级名称',
  `grade_score` int(11) NOT NULL COMMENT '等级需要积分',
  `grade_desc` varchar(4000) DEFAULT NULL COMMENT '等级描述',
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_user_grade`
--

LOCK TABLES `mall_user_grade` WRITE;
/*!40000 ALTER TABLE `mall_user_grade` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_user_grade` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-16 13:55:06
