/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : school

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-09-03 11:39:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_admin` varchar(255) DEFAULT NULL,
  `class_code` varchar(255) NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `counts` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_delete` enum('NO','YES') DEFAULT 'YES',
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oeegy6vxpfrs578m02nmdjto3` (`class_code`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES ('1', 'GHH', 'C_201806251813219', '六年级4班', '8', '2018-06-25 18:13:38', 'YES', '2018-08-08 21:39:32', '1');
INSERT INTO `clazz` VALUES ('2', '   恩恩', 'C_201806251816164', 'ee', '0', '2018-06-25 18:16:18', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('3', '   恩恩', 'C_201806251818850', 'ee', '0', '2018-06-25 18:18:41', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('4', '   恩恩', 'C_201806251831919', 'ee', '0', '2018-06-25 18:31:51', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('5', 'DDD', 'C_201808042051557', '六年级3班', '2', '2018-08-04 20:51:03', 'YES', '2018-08-08 21:39:13', '1');
INSERT INTO `clazz` VALUES ('6', 'QQQ', 'C_201808042055485', '六年级2班', '1', '2018-08-04 20:55:39', 'YES', '2018-08-08 21:38:54', '1');
INSERT INTO `clazz` VALUES ('7', '是法国e', 'Class_201808042153443', '岁的法国', '0', '2018-08-04 21:53:25', 'NO', '2018-08-05 12:22:49', '1');
INSERT INTO `clazz` VALUES ('8', ' ghjk34444444444444445555555555', 'Class_201808051206583', '6ui ', '0', '2018-08-05 12:06:02', 'NO', '2018-08-05 12:12:33', '1');
INSERT INTO `clazz` VALUES ('9', '456', 'C_201808051710739', 'qw', '0', '2018-08-05 17:10:05', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('10', 'y', 'C_20180805171174', 'wee', '0', '2018-08-05 17:11:16', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('11', '56', 'C_201808051711604', 'wer', '0', '2018-08-05 17:11:42', 'NO', null, '1');
INSERT INTO `clazz` VALUES ('12', 'EREFDQQQQQQQQQQQQQQ', 'C_201808082021549', 'CFDEF', '0', '2018-08-08 20:21:18', 'NO', '2018-08-08 20:51:36', '1');
INSERT INTO `clazz` VALUES ('13', '张三', 'C_20180808213777', '六年级1班', '1', '2018-08-08 21:37:13', 'YES', '2018-08-17 22:09:54', '1');
INSERT INTO `clazz` VALUES ('14', '计算机', 'C_201808241957787', '98989890', '2', '2018-08-24 19:57:17', 'YES', '2018-08-24 20:11:56', '1');
INSERT INTO `clazz` VALUES ('15', '杨老师', 'C_201808250943666', '六年级一班', '0', '2018-08-25 09:43:04', 'YES', null, '2');

-- ----------------------------
-- Table structure for group_contacts
-- ----------------------------
DROP TABLE IF EXISTS `group_contacts`;
CREATE TABLE `group_contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_id` bigint(20) NOT NULL,
  `counts` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `group_name` varchar(255) NOT NULL,
  `is_delete` varchar(255) DEFAULT NULL,
  `stu_id` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group_contacts
-- ----------------------------
INSERT INTO `group_contacts` VALUES ('7', '5', '1', '2018-08-08 13:42:35', '觉得今生今世的', 'YES', '7', '1');
INSERT INTO `group_contacts` VALUES ('8', '5', '1', '2018-08-08 13:42:58', '一次两', 'YES', '7', '1');
INSERT INTO `group_contacts` VALUES ('9', '1', '3', '2018-08-08 13:45:36', '一次两面', 'YES', '1,2,3', '1');
INSERT INTO `group_contacts` VALUES ('10', '2', '1', null, '一次两面墙面朝天11', 'YES', '3', '1');
INSERT INTO `group_contacts` VALUES ('12', '13', '1', '2018-08-08 21:45:32', '777', 'YES', '8', '1');

-- ----------------------------
-- Table structure for pin_money
-- ----------------------------
DROP TABLE IF EXISTS `pin_money`;
CREATE TABLE `pin_money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `money` varchar(4000) NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ry4yi0mpds9pe6e0hvieibxrn` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pin_money
-- ----------------------------
INSERT INTO `pin_money` VALUES ('10', 'JHNz6VTFGktNwDh7lIZtJhOErGrcpHRBzey788s8Eg1yVGu526F8aGCTPzPqdLFNKAsyYP+IZhfKt/PjZUKMVuSRcc1uE7MVlBUpWyI3Myqt5Kmr8SeJkw96oaI+QKYWfIsvqBZTF3aS7ItLL1tkCUKjUQo39SGul1tlSXCTrLs=', '9');
INSERT INTO `pin_money` VALUES ('11', 'Tj8tVtPnm/4U36esutXZqeGsOkG43xxE/kPtzB/kqB/oPwgWHdg97OXjfiD5fEgTbvAcuo0CO/wI8Q7pT0xUMDcXZg9mnDpobLb2eto8XEVmWe9UQNqC8Skl7FRT3Kz2lGDQgOeaR9kTBnr0IPtaOcWJ4dQH6ZegDOXLWjkh9pg=', '10');
INSERT INTO `pin_money` VALUES ('12', 'ShljfDknYrcwKc8eafP73SMFai8MAwVG09rRUz9IaifOSHDTp7KW9TtwQkL3m2fPoeHZm4Plk/ZcrwdsTqURX699Vx/BxibVSin5jFgh6jm8RJS4Mkp2kUQPpmQoT08d8w8n/tGT3FqJUhcvdhAYrtCs4oya2VMwkICQeqlKYss=', '3');
INSERT INTO `pin_money` VALUES ('13', 'G2toyJrbMlsdDbpP1ZZOzWPgK21CHDQ1hm0iCf1Xt0lS9XyBLTiYOgzaR/ipEW7Ao7oFCWZ6uRVzSjxSNEjsOluPaUIcYcR6YEn1YtBQpOW9CrJcYW7y4ugu3nEQV1eu1iW1AxNqaGAhh7nBzG/NW1x4Ft1NMfJRSHE3mNEz+sQ=', '8');
INSERT INTO `pin_money` VALUES ('14', 'YTcwcX9CD2Ph8vXEIPQLHNeVXGhSCjAtI4jp5XlcUr52jfAyKQivTJuuCMpRuMboLGWLHyjAgww6GPnHxAq1RY/ARGQnFVQBpn2u7GbMle8FssPHcD6C60dXuTMYTpZ47iPLeMIyBqpxhVqTPX2z3sogNxYIhfzU0K26W4aeJrA=', '6');
INSERT INTO `pin_money` VALUES ('15', 'BMJOF2zM1jd+1IgUndmvc3wlpmEXu7N34bJfHGv9SlMkKluZRXj0L10AIMbo8xckgg04hfuy0Yrn7Ss75k5aZyqN8M+gV+SGwI58CLD/db2gu5+UUq7M1aZpnHcSN29OhDu0oGVCVJWeMxfJoubc1hcoILa8KPwhf1aTL7tmrIk=', '7');

-- ----------------------------
-- Table structure for pin_money_detail
-- ----------------------------
DROP TABLE IF EXISTS `pin_money_detail`;
CREATE TABLE `pin_money_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `money` varchar(4000) NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  `flag` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pin_money_detail
-- ----------------------------
INSERT INTO `pin_money_detail` VALUES ('22', '在2018-08-15 16:12:102存入1元', '2018-08-15 16:12:55', 'AQlpsx/yvVW9GprWrUF72BzUxihE6EKnDwHdwsp4bDn41pOtrkEE72a437CTLJPHknMjokaPhYQRKRIMKZOziXG77jM6KqCLsIAUuifRKUO69pjkhxUnmqdysoOAMuG7T1h4NEjdbdbYN0G1ISPkn7GpsflxiraZUIYgvc4bWg8=', '9', '1', '解放军风景', '1');
INSERT INTO `pin_money_detail` VALUES ('23', '在2018-08-15 16:35:925取出0元', '2018-08-15 16:35:54', 'uzZQNt9l/20dbainc3gaQd3pIIcQGF24gj4UAOHdoRMb0OZ1eVopL48u23JUm34Z3nPpijBMAB7kl8/n4mOaiVGnrWlRqtkbF8Pa2uMwDrdpUn+xQgAfGfXPRpsMQ0JgpamYLhsVQZZ4ylVWabdaE3/wod4+6z/tKF7PERVzA0A=', '9', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('24', '在2018-08-15 16:49:619存入1元', '2018-08-15 16:49:06', 'ubZJd6drEmRUpMftqxLX77R7iibZuq8NCS20RGAkVG20yXB89VqD8ErXQtpXR6lIAR139KhD+hfN3bOfkImTdUbMG04/Ika4hx+OYnJ1rsg6jXpGwoekw9qa9fDAIutUsHWmT6xqzBAYk8V3gjS27vawGumvsSFkn4ZybgLdaKY=', '9', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('25', '在2018-08-15 16:52:277存入1元', '2018-08-15 16:52:47', 'JRlzizsBT/H9qW1KRxcz7L8QL1zjDXoeAKtjlYGxh98O+IxKdcdxSlBmdjWgMcaWh0q1iQhlF+N2on65FirP1a3PwfAjtCBs0ppBEPzje3xRIrmAtBK7Zqr6VM8mybqwLFVGR/O4L0P/SuCys3f196NjqSm9wspPeIiZxLpeVZc=', '9', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('26', '在2018-08-15 16:54:553取出5元', '2018-08-15 16:54:07', 'tY4nTYNcGisuvK94n+vUFDdQPBjIw1/Oc0jQBXrydwSIs8YCivDNQ8ZRWmnOzB4Rjjgsypp17VAaEvNUh2Xzxf7piEMXhKfIEb20k7qhdsxKRK9FC5HwsJ89eIkfI8uy5Hz0lHLCguTVVYnyAIVMXGHXhn40VwRC7uTzv6Gk00I=', '9', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('27', '在2018-08-15 16:54:94存入0元', '2018-08-15 16:54:50', 'qQ9sPXzphCCRhRRjVmAyXgdwpk7fqz92oFud1FWub3hDqbWHlK2UzdZ0ccsR89UlQLkGMYOO3mn/vgCPkvcIyvua+yfV9vEtIom5cVQim+J0rbJD20I30qmMXEyxqg7XtP/7XijIHFX19TCJOJIByTO8fZBOevVQUt5h/dLGiQk=', '9', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('28', '在2018-08-15 16:55:159取出1元', '2018-08-15 16:55:02', 'qt+XiJdcdY+nwcxrz7lqssjfxTW4cE7VNReCRyNHIHSnAgLztErSeMUeIomVgZ52ujXkQkUtNt7m5adqDPL28aRr0ttiPeylabpr8NCxUhPbWIvYASBl0Vykn7BQnxUM/JQvfqi1NVm3NlDlDV4nSVAmnIwoq6ZSKeMpmnaYr8s=', '9', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('29', '在2018-08-15 16:55:81取出1元', '2018-08-15 16:55:17', 'f529OeEpdPjRDvg2wMJly5hxlCBPEdzdFZYuuQu3Whypz6dCqbTWvXPtR6XDrEGipG/24wBwOmi99uvH90iK6YWj+p51fTxR8JyhdtJV2dbVhI87N8obl0/6hgJNycwDfhAygx02Me4awnJNOrVf+58T6/ojGSxSvHE9Bpudf+4=', '9', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('30', '在2018-08-15 17:05:972存入2元', '2018-08-15 17:05:50', 'hP2qNOqclO7uhhCx+zdKSA+Tyw6ZhhEJoai39Q849AWPmCnvIDy8GPuuRjjSZq7QTNouNyARCpQQ7UGjvrgZ+S1v2cohctTfM2ucyPV8chxDzW4+e9nBEecRZ8w0MIPbZUUl73hO4mWVcb5j+nkHZDafnJHncif2NaP5ZYsSrdY=', '9', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('31', '在2018-08-15 17:37:323存入1元', '2018-08-15 17:37:59', 'Tv2qaMPvK20uEF+JnA24Y3DvXvGpfe6jkqFMAbU60QmzNGxvh+yorZkwkVuIfpjgRbdDzZxS1MGELuVDQkRbd8oiTl2TrroLwymxbZvCZ5OI7wyFafqlQ9XZt4krvd6bY+RhyIYE9o5v6jd4DTCeG4FU05/nf2KzlnRQCMxrm0k=', '10', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('32', '在2018-08-15 17:39:79存入0元', '2018-08-15 17:39:49', 'v2fzMDWCEm9pvHstwD6bbj2mObc0RlLnFQVgKL4QsqU4mJRUWnVXVyS2CHozhMdK9ZCWp51kcjQlaGLxc5v18MQw0ZjAkDLWAljbDED1R5Rug2wUJtCgzEgH5P/giveH2tibPNKTw9fADv69LRXmpjmpxlbE30n4gvebC2OrK4o=', '10', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('33', '在2018-08-15 17:50:929存入1.67元', '2018-08-15 17:50:29', 'bt6Bk/n9nYJMo1eETYJjNYk0eLxK3Hh6/x3HS1ZX+Xjgr52F+XixhxC2+E0EY4jJDCEcEkZ2BJ9K9TA3S++8F4r8G1gTNfp66asTKLtuCDFLiQ4s7VtKgz4JaSXvqT5DPgt5wn5xuh6IYdMWh2VMscNkc0VYgbBGYq9Y7aQosOc=', '10', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('34', '在2018-08-15 17:50:194取出0.89元', '2018-08-15 17:50:45', 'kvPLUHTb7pwk35FYSQPfzVlxcnHDiHJjnMfQGM5Ij+ffy1sKHxr91egUi4IfdjzmFHbk0bTNQvtEvtPEYH6RqW0f3wvJHM1kUUpDFFJD5P997+eT+pUtbcbs/DCLqum0rtjtS+Eg8ma6BmS0f+kDGRjMBJIcQpN0uqKsF92zZ0o=', '10', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('35', '在2018-08-15 20:07:981存入1.348元', '2018-08-15 20:07:10', 'ShljfDknYrcwKc8eafP73SMFai8MAwVG09rRUz9IaifOSHDTp7KW9TtwQkL3m2fPoeHZm4Plk/ZcrwdsTqURX699Vx/BxibVSin5jFgh6jm8RJS4Mkp2kUQPpmQoT08d8w8n/tGT3FqJUhcvdhAYrtCs4oya2VMwkICQeqlKYss=', '3', '1', '放电管', '1');
INSERT INTO `pin_money_detail` VALUES ('36', '在2018-08-24 19:53:349存入90元', '2018-08-24 19:53:11', 'G2toyJrbMlsdDbpP1ZZOzWPgK21CHDQ1hm0iCf1Xt0lS9XyBLTiYOgzaR/ipEW7Ao7oFCWZ6uRVzSjxSNEjsOluPaUIcYcR6YEn1YtBQpOW9CrJcYW7y4ugu3nEQV1eu1iW1AxNqaGAhh7nBzG/NW1x4Ft1NMfJRSHE3mNEz+sQ=', '8', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('37', '在2018-08-24 19:57:203存入99元', '2018-08-24 19:57:00', 'YTcwcX9CD2Ph8vXEIPQLHNeVXGhSCjAtI4jp5XlcUr52jfAyKQivTJuuCMpRuMboLGWLHyjAgww6GPnHxAq1RY/ARGQnFVQBpn2u7GbMle8FssPHcD6C60dXuTMYTpZ47iPLeMIyBqpxhVqTPX2z3sogNxYIhfzU0K26W4aeJrA=', '6', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('38', '在2018-08-24 20:03:375存入88元', '2018-08-24 20:03:18', 'anuSwnGaHvGszoCT13F/xacULTw+eG2YtNwVFeoPgtJtU4HPZoHIx9fy6ExWq5FA65kYYcsUARcn0z8vUW23Y+/c/aeJvbNIKIeZCMH9J7SVHTeUtQSj5Izf6mWdDbvgVF3z4i8l7eGMxV1XiVUdwHzK8l/XCw3A+h2KEk4j6ZU=', '7', '1', '', '1');
INSERT INTO `pin_money_detail` VALUES ('39', '在2018-08-24 20:03:616取出66元', '2018-08-24 20:03:29', 'iStB+sUDXlsmgkf96utJWEk0QDuFR+325pqS6aJkk8D3NfEYysXpONrO2pzK2iduoaH2aO9XhOYRzO8FmVdhpN2ST/dCAPUwXMaB28RQv2rS+SZKdJHJW9KhvQ3+t04P8ZkGzF+7meTGaCGJYa/d1Nzq/SDEzjgn13fZR6w0ihc=', '7', '0', '', '1');
INSERT INTO `pin_money_detail` VALUES ('40', '在2018-08-24 20:03:32取出99元', '2018-08-24 20:03:40', 'i/wSAJVL89jg6LonWvv9i4hhsEb9OS1Zf4hHnkds9+NL05/X6lEdicZcuJKBzeqjNQK5HeYTvOhnQ4502+guZznMa3onL2SWioAjdrobEHmFrHWnae7HHwfj5dEiBH48nWPprKoQoVCn7No4Kc8yGx2eUsjpH0GAAsOU6NXl3yk=', '7', '0', '', '1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `class_id` bigint(20) NOT NULL,
  `is_delete` varchar(255) DEFAULT NULL,
  `jiazhang` varchar(255) NOT NULL,
  `phone` varchar(4000) NOT NULL,
  `sex` int(11) NOT NULL,
  `stu_code` varchar(255) NOT NULL,
  `stu_name` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jd63ws9jqens8bgcl2bn2qpoo` (`stu_code`),
  UNIQUE KEY `stu_name` (`stu_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '20', '1', 'YES', 'sdf', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', '423432', '32432', '2018-08-05 16:19:27');
INSERT INTO `student` VALUES ('2', '0', '1', 'YES', '發的幹部', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', '2342', '熱帖', '2018-08-05 17:29:33');
INSERT INTO `student` VALUES ('3', '0', '1', 'YES', '健康良好', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '0', '768765', '將很快', '2018-08-05 17:35:02');
INSERT INTO `student` VALUES ('4', '0', '2', 'YES', '高科技', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '0', '76867', '3激光焊接', '2018-08-05 17:35:15');
INSERT INTO `student` VALUES ('5', '0', '6', 'YES', 'tyutyu', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', 'ytu', '的风格化的', '2018-08-05 17:51:39');
INSERT INTO `student` VALUES ('6', '0', '5', 'YES', '132121313', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', '131213', '232', '2018-08-07 11:03:13');
INSERT INTO `student` VALUES ('7', '0', '5', 'YES', '得到的就是我们', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', '；ffgg', '是iijjj', '2018-08-08 14:03:35');
INSERT INTO `student` VALUES ('8', '0', '13', 'YES', 'FFF', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '1', '001', 'CCC', '2018-08-08 21:42:04');
INSERT INTO `student` VALUES ('9', '0', '1', 'YES', '234', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=', '0', '002', 'WWW', '2018-08-08 22:44:29');
INSERT INTO `student` VALUES ('10', '0', '6', 'NO', '十点', 'fcDrA9t8/twhfgQ7GhSbg4m0lBMU0p6/pLwggjvofn+w+FeJb3Rq+4CJotjwPhijNIHnida7Pof7HJMGegi0lybThWd5xzOeOBUNHKMfqkcl5HdcLDDkLCH4kqDzDz0lLlcsYvwNu+QzethbmDvZBz3VjTFQor7azBncuxS+Z8o=', '1', '81272', '肯定', '2018-08-15 17:36:51');
INSERT INTO `student` VALUES ('12', '0', '14', 'YES', '345435', 'ZOOYOziZKMg+LewvUWocQiQqjhMLZE61zmzagfqZObkV8iFhN6LnGurKffr+73KoO8mWEeOKvgwATbRfktQOLT91M8JhaeNBVtVX08u29GAHKb7ArFYSKmR0bGrBUhml1mVGWJXaEUfuaxAn/yPJRm0MzzVtFNX/KwUytKBaNLQ=', '1', '345435', '56345', '2018-08-24 20:02:02');
INSERT INTO `student` VALUES ('13', '0', '1', 'YES', '346546', 'rUuDWKmh+juqbAwO/RNpEGRMqwpS00Bg/v2I9C2kPBK3s12LUspIQKmvLhRXXpJdnwGrcn7wkeSd5ohL5Tn8avoqBGHi1S/VZe3huyIwp4vkYBUcXem68hDqHklOqJSmULzFtD1X5HDKI/rIfukCc+nRey5JS7tqkOiTI4pOu34=', '1', '45345', '345435', '2018-08-24 20:02:41');
INSERT INTO `student` VALUES ('14', '0', '1', 'YES', '54654', 'rVEg2c4wkLNIvFk3Hp8LgZn8zdUaC65PVR1VFhVVcX6tKRgiOLvhA9ZJHFHS0uVebQ6eNertGZ92jNaumDxfNQHv8zuiOQRR614uyDDhdCtBi9cAN9358TQC8p1zlZ7ovazzChcB9+svKG8RFFfEkzCIqybmTDXGWV/F26oXrNw=', '1', '546546', '456546', '2018-08-24 20:03:05');
INSERT INTO `student` VALUES ('15', '0', '1', 'YES', 'ertert', 'WN7BDdZ7e7Z9VF9se7H3BDaO0PyTDF4j1xHD086Ua09rpzmnmY95Am4CV2juOUSTBXPVx3t2Fm3zU0yzoWClCIcBI+N7H8j65RJs5Uw1VZxLioj9dWJGMgvDx4O2BKTa8GEDeBt3J9+5nueQJBN1WoICNATBgMLhcA8dbjJrKyo=', '1', 'retert', 'tyet', '2018-08-24 20:04:10');
INSERT INTO `student` VALUES ('16', '0', '1', 'YES', '滴滴', 'AZQe768+kSme3sMZXuYxsEt1zze49yY826qFst4fdzSN6SZaPtt+fgxlRoPj+6Ec8g+SHYSSnmrycMlROWG8+4AXrNhfXkIj3U0dJVNjM9FDVQdobaXEsZ9eheuFn4gJLvhFCEyXtg4y4LMoYACylRx+eZjOKLkHMS7KinO0Wew=', '1', '383833', '溶解', '2018-08-24 21:35:02');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `background_picture` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) NOT NULL,
  `introduction` text,
  `last_modify_time` bigint(20) NOT NULL,
  `out_date` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `validata_code` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `phone` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
  UNIQUE KEY `UK_d2ia11oqhsynodbsi46m80vfc` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '1529675133612', null, '1529675133612', null, '0172fe4f9eca0d9722d048f11a31e869', 'img/favicon.png', 'admin', null, '媳妇最漂亮!', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=');
INSERT INTO `user` VALUES ('2', null, '1533717586614', null, '1533717586614', null, '0172fe4f9eca0d9722d048f11a31e869', 'img/favicon.png', 'yangxingxing', null, '媳妇是最美的！', 'cgQ3tOBVxDno+PUqjalieE0I6c3RSlrurT65lAFAVobSjoehXgDDfZ0OVclogqgodvnZquZ/JwFfb1/zunp4aBRsgzBLO/1F1bHd/NjonGoORdmgps9QdB80FUX+nrRosHknc5MKo07Qv2Wu+QyiEiJDWoxeurPx16QOiBuAwic=');
