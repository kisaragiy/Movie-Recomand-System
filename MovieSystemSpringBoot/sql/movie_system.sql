/*
 Navicat Premium Data Transfer

 Source Server         : pratice
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : moviesystemspringboot_db

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 10/06/2025 23:04:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES (1, '华语');
INSERT INTO `area` VALUES (2, '美国');
INSERT INTO `area` VALUES (3, '日本');
INSERT INTO `area` VALUES (4, '韩国');
INSERT INTO `area` VALUES (5, '印度');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `savetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `lookcs` int NULL DEFAULT NULL,
  `delstatus` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `sectionid` int NULL DEFAULT NULL,
  `isjh` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `dznum` int NULL DEFAULT NULL,
  `movieid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_member`(`memberid` ASC) USING BTREE,
  INDEX `fk_article_section`(`sectionid` ASC) USING BTREE,
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`sectionid`) REFERENCES `section` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (12, 35, '啊啊啊', NULL, '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2025-03-02 20:49:39', 3, NULL, 1, 'no', 1, 11);
INSERT INTO `article` VALUES (13, 36, '66666666太强了', NULL, '2345235345', '2025-03-02 21:11:44', 3, NULL, 1, 'no', 0, 9);
INSERT INTO `article` VALUES (14, 37, '这是什么抛瓦', NULL, '<p style=\"text-indent:2em;\">\n	I am the storm that is aproching\n</p>', '2025-03-02 21:21:31', 6, NULL, 2, 'yes', 2, 13);
INSERT INTO `article` VALUES (15, 40, '111', NULL, '222', '2025-05-17 17:01:29', 1, NULL, 2, 'no', 0, 13);
INSERT INTO `article` VALUES (16, 34, '十二生肖', NULL, '<p>\n	豪看\n</p>\n<p>\n	<br />\n</p>', '2025-06-10 22:55:57', 0, NULL, 1, 'no', 0, 19);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (12, '犯罪');
INSERT INTO `category` VALUES (13, '枪战');
INSERT INTO `category` VALUES (19, '动作');
INSERT INTO `category` VALUES (21, '爱情');
INSERT INTO `category` VALUES (22, '悲剧');
INSERT INTO `category` VALUES (24, '喜剧');
INSERT INTO `category` VALUES (27, '科幻');
INSERT INTO `category` VALUES (28, '奇幻');
INSERT INTO `category` VALUES (29, '悬疑');
INSERT INTO `category` VALUES (30, '冒险');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `movieid` int NULL DEFAULT NULL,
  `sheetid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_collect_member`(`memberid` ASC) USING BTREE,
  INDEX `fk_collect_movieid`(`movieid` ASC) USING BTREE,
  CONSTRAINT `fk_collect_member` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_collect_movieid` FOREIGN KEY (`movieid`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (39, 37, 16, 18);
INSERT INTO `collect` VALUES (40, 37, 13, 18);
INSERT INTO `collect` VALUES (41, 37, 12, 18);
INSERT INTO `collect` VALUES (43, 40, 10, 21);
INSERT INTO `collect` VALUES (45, 40, 13, 21);
INSERT INTO `collect` VALUES (46, 40, 11, 21);
INSERT INTO `collect` VALUES (47, 40, 9, 21);
INSERT INTO `collect` VALUES (48, 34, 1, 23);
INSERT INTO `collect` VALUES (49, 34, 25, 23);
INSERT INTO `collect` VALUES (50, 34, 19, 24);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `movieid` int NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `savetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `hfcontent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_member`(`memberid` ASC) USING BTREE,
  INDEX `fk_comment_news`(`movieid` ASC) USING BTREE,
  CONSTRAINT `fk_comment_member` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_movie` FOREIGN KEY (`movieid`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (2, 14, 4, 4, 'first and last name in the “To” field to personalize their emails. Gravity letters it amongst herself dearest an windows by. Wooded ladies she basket season age her uneasy saw.', '2025-03-03 12:59:21', 'etters it amongst herself dearest an windows by.');
INSERT INTO `comment` VALUES (4, 28, 9, 5, 'Gravity letters it amongst herself dearest an windows by. Wooded ladies she basket season age her uneasy saw.', '2025-03-03 13:42:25', NULL);
INSERT INTO `comment` VALUES (5, 29, 8, 4, '6666666666', '2025-03-03 12:11:32', '696969');
INSERT INTO `comment` VALUES (6, 30, 11, 5, '666', '2025-03-03 19:12:05', NULL);
INSERT INTO `comment` VALUES (7, 31, 9, 5, '6555555555', '2025-03-03 14:15:53', NULL);
INSERT INTO `comment` VALUES (8, 32, 10, 5, '喜欢 ', '2025-03-03 16:01:00', NULL);
INSERT INTO `comment` VALUES (9, 33, 4, 5, '德国鸡腿堡', '2025-03-03 23:50:03', '555');
INSERT INTO `comment` VALUES (10, 34, 4, 5, '恩恩', '2025-03-04 22:33:33', NULL);
INSERT INTO `comment` VALUES (11, 34, 12, 5, 'aaa', '2025-03-04 22:34:00', NULL);
INSERT INTO `comment` VALUES (12, 36, 9, 5, '667788 7788414', '2025-03-04 22:40:27', '666');
INSERT INTO `comment` VALUES (13, 37, 16, 5, '真地好', '2025-04-04 22:19:11', NULL);
INSERT INTO `comment` VALUES (14, 37, 13, 5, '蒙古牛奶', '2025-04-04 22:20:59', NULL);
INSERT INTO `comment` VALUES (15, 34, 9, 4, '1964866187', '2025-04-04 22:25:35', '1');
INSERT INTO `comment` VALUES (16, 40, 4, 5, '111', '2025-05-20 13:49:37', NULL);
INSERT INTO `comment` VALUES (17, 40, 9, 5, '耗堪！', '2025-05-20 13:50:37', NULL);
INSERT INTO `comment` VALUES (18, 40, 10, 4, '不戳', '2025-05-20 14:05:12', NULL);
INSERT INTO `comment` VALUES (19, 34, 11, 5, '66666', '2025-05-20 14:11:54', NULL);

-- ----------------------------
-- Table structure for dzrecord
-- ----------------------------
DROP TABLE IF EXISTS `dzrecord`;
CREATE TABLE `dzrecord`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `movieid` int NULL DEFAULT NULL,
  `categroyid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dzrecord
-- ----------------------------
INSERT INTO `dzrecord` VALUES (2, 14, 11, 24);
INSERT INTO `dzrecord` VALUES (3, 14, 10, 24);
INSERT INTO `dzrecord` VALUES (4, 14, 13, 24);
INSERT INTO `dzrecord` VALUES (5, 28, 11, 24);
INSERT INTO `dzrecord` VALUES (6, 28, 8, 21);
INSERT INTO `dzrecord` VALUES (7, 28, 9, 21);
INSERT INTO `dzrecord` VALUES (8, 35, 9, 21);
INSERT INTO `dzrecord` VALUES (9, 35, 4, 24);
INSERT INTO `dzrecord` VALUES (10, 36, 9, 21);
INSERT INTO `dzrecord` VALUES (11, 36, 4, 24);
INSERT INTO `dzrecord` VALUES (12, 36, 16, 24);
INSERT INTO `dzrecord` VALUES (13, 37, 16, 24);
INSERT INTO `dzrecord` VALUES (14, 37, 13, 24);
INSERT INTO `dzrecord` VALUES (15, 34, 9, 21);
INSERT INTO `dzrecord` VALUES (16, 40, 13, 24);
INSERT INTO `dzrecord` VALUES (17, 40, 11, 13);
INSERT INTO `dzrecord` VALUES (18, 40, 3, 21);
INSERT INTO `dzrecord` VALUES (19, 40, 10, 24);
INSERT INTO `dzrecord` VALUES (20, 34, 11, 13);
INSERT INTO `dzrecord` VALUES (21, 34, 4, 24);
INSERT INTO `dzrecord` VALUES (22, 34, 16, 24);
INSERT INTO `dzrecord` VALUES (23, 34, 1, 24);
INSERT INTO `dzrecord` VALUES (24, 34, 2, 24);
INSERT INTO `dzrecord` VALUES (25, 34, 25, 24);
INSERT INTO `dzrecord` VALUES (26, 34, 19, 24);

-- ----------------------------
-- Table structure for imgadv
-- ----------------------------
DROP TABLE IF EXISTS `imgadv`;
CREATE TABLE `imgadv`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imgadv
-- ----------------------------
INSERT INTO `imgadv` VALUES (26, 'f2383e08-b5de-4931-ba8d-2efbcfd60822.jpg', 'moviedetails.html?id=1');
INSERT INTO `imgadv` VALUES (27, 'd4a0a27f-9840-4dcc-ae59-c09a28b4f44b.jpg', 'moviedetails.html?id=4');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `upass` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `isgly` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (14, 'H001', '123', '彭立华', '0c8d4483-736c-4e2b-969b-213051c918d4.jpg', '13877665523', '女', '3809283092', 'H001@126.com', 'n');
INSERT INTO `member` VALUES (28, 'H002', '123', 'H002', 'icon1.png', '13890765544', '女', '438043894', 'H002@126.com', 'n');
INSERT INTO `member` VALUES (29, 'H003', '123', 'H003', '104fdfee-1846-4fe3-920e-544f18b52968.jpg', '13890765544', '女', '4830984093', 'H003@126.com', 'n');
INSERT INTO `member` VALUES (30, 'H005', '123', '小鑫子', 'f6d365b3-c8c2-4da5-9da8-565a47cdee1f.jpg', '15526525874', '男', '9876357', 'H005@163.com', 'n');
INSERT INTO `member` VALUES (31, 'H009', '123', '小强', '55fd5a6c-7cb3-4ed2-af47-595f6f0b7498.jpg', '18854587485', '男', '347345345', 'H009@163.com', 'n');
INSERT INTO `member` VALUES (32, 'W001', '123', '小武', 'd9035526-4bb6-48b6-8a88-b8f940053165.jpg', '18852541585', '男', '6846354', 'W001@163.com', 'n');
INSERT INTO `member` VALUES (33, 'H010', '123', '小强', '5c9ae619-dd0c-41fe-bb1b-807f5d934bb7.jpg', '15521541585', '男', '45743553', 'h010@163.com', 'n');
INSERT INTO `member` VALUES (34, 'H011', '123', '小黑子', '3a4b17c7-21b2-4b40-9b8f-74160c524464.jpg', '15523658485', '男', '987354648', 'h011@163.com', 'y');
INSERT INTO `member` VALUES (35, 'H115', '123', '小白子', '083a52fe-fecb-403a-b866-f01c9079668e.jpg', '15523625985', '男', 'w456456345', 'H115@163.com', 'n');
INSERT INTO `member` VALUES (36, 'H116', '123', '小伙子', 'd8b8de18-8cf1-4ea2-8cf2-638c1fd37e23.jpg', '15522221111', '男', '98746354', 'h116@163.com', 'n');
INSERT INTO `member` VALUES (37, 'H159', '123', '小牛子', '002af7f8-dfa4-4670-8241-c7f62d0dfaed.jpg', '15922220000', '男', '98735464', 'h159@163.com', 'n');
INSERT INTO `member` VALUES (38, 'admin', '123', 'wq z', 'icon1.png', '13688900098', '男', '111', '1141417294@qq.com', 'n');
INSERT INTO `member` VALUES (40, 'qwz', '123456', 'wq z', 'icon1.png', '13688900098', '男', '2345', '1141417294@qq.com', 'y');
INSERT INTO `member` VALUES (41, 'ayano', '123456', 'wq z', 'icon1.png', '13688900098', '男', '111', '1141417294@qq.com', 'n');
INSERT INTO `member` VALUES (42, 'zwq', '123456', 'wq z', 'icon1.png', '13688900098', '男', '111', '1141417294@qq.com', 'n');
INSERT INTO `member` VALUES (43, '52453', '123456', '张伟强', 'icon1.png', '15377798768', '男', '555', '1141417294@qq.com', 'y');
INSERT INTO `member` VALUES (44, 'dsfrstsd', '123456', 'kisaragi', 'icon1.png', '15377798768', '男', '12314', '231341', 'n');
INSERT INTO `member` VALUES (45, 'aestgfrdg', '123456', '张伟强', 'icon1.png', '15377798768', '男', '555', '1141417294@qq.com', 'n');
INSERT INTO `member` VALUES (46, 'H222', '123', 'jsc', 'icon1.png', '13688893761', '男', '11454', '999111', 'n');
INSERT INTO `member` VALUES (47, 'H012', '123', 'jss', 'icon1.png', '13688893763', '男', '11454', '999111', 'n');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `savetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `hfcontent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_message_member`(`memberid` ASC) USING BTREE,
  CONSTRAINT `fk_message_member` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (15, 29, '99999999', '2025-03-08 12:11:54', '0000000000');
INSERT INTO `message` VALUES (19, 34, '3245', '2025-03-08 22:34:49', 'tt');
INSERT INTO `message` VALUES (20, 35, '3345', '2025-03-08 21:50:25', '对...对吗?');
INSERT INTO `message` VALUES (21, 36, '123', '2025-03-08 22:13:48', 'aaa');
INSERT INTO `message` VALUES (22, 37, '一一二二三三', '2025-03-09 22:22:34', '言言言言言言言');
INSERT INTO `message` VALUES (23, 40, '111', '2025-05-10 06:50:09', 'ok');
INSERT INTO `message` VALUES (24, 34, '999', '2025-06-10 22:54:24', NULL);

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `videoname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `categoryid` int NULL DEFAULT NULL,
  `areaid` int NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `yeartime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `playtime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `cs` int NULL DEFAULT NULL,
  `score` decimal(10, 2) NULL DEFAULT NULL,
  `isfree` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `fee` decimal(10, 2) NULL DEFAULT NULL,
  `zan` int NULL DEFAULT NULL,
  `memberid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `shstatus` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_movie_category`(`categoryid` ASC) USING BTREE,
  INDEX `fk_movie_area`(`areaid` ASC) USING BTREE,
  CONSTRAINT `fk_movie_area` FOREIGN KEY (`areaid`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_movie_category` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (1, '血仍未冷', 'c318a12d-d310-45a8-891b-d8ffce578d89.jpg', '92ce9bac-fc35-4fed-bc62-25da2ad67cda.mp4', 12, 1, '周润发', '2011', '2025-03-09', '<p style=\"text-indent:2em;\">\n	来自大陆的杀手李强只身来到了美国，为了将还在家乡的母亲和妹妹接来美国一家团聚，无奈之下他只好向纽约曼哈顿唐人街的黑帮老大魏先生借钱。此时，魏先生的儿子因为在毒品交易中逃避警察而意外坠楼身亡，李强正好成了魏先生复仇的棋子。李强被派去杀害执行这次缉毒行动的负责人斯坦警官和他的家人，当他发现斯坦警官的儿子年仅7岁时，对家人的牵挂令他怎么也下不了手。行动失败后，不仅李强的生命受到了威胁，连他原在大陆的家人也面临杀身之祸。为了逃回家乡拯救家人，李强和魏先生派来的杀手展开了一场角力！\n</p>', 11, 3.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (2, '金刚川', '461c9b5c-4ce0-4100-990b-be110a4a897e.jpg', 'ed41b667-48b3-4359-a5d7-ebbc4c874338.mp3', 13, 1, '张译', '2020', '2025-03-09', '<p style=\"text-indent:2em;\">\n	1953年，抗美援朝战争进入最终阶段，志愿军在金城发动最后一场大型战役。为在指定时间到达，向 金城前线投放更多战力，志愿军战士们在物资匮乏、武装悬殊的情况下，不断抵御敌机狂轰滥炸，以血肉 之躯一次次修补战火中的木桥。一段鲜为人知的历史，在暗流涌动的金刚川上徐徐展开......\n</p>', 2, 1.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (3, '战火黎明', '8baacc68-1ff8-4562-855e-82cd6dd96587.jpg', '713acdbb-632e-49c8-853b-78bdcaafb84e.mp4', 19, 1, '任柯诺', '2010', '2025-03-09', '<p style=\"text-indent:2em;\">\n	本片根据二战真实事件“戈山厂惨案”改编，1945年，八路军在泗水城全歼伪军第十军，击毙伪军军长荣子恒，取得泗水大捷。其中一股正在赶去增援的日军部队，得知伪军被全歼的消息，不敢应战，急速后撤，在撤退途中，竟然将路过的戈山厂村屠戮一空，制造出骇人听闻的“戈山厂惨案”。\n</p>', 1, 3.50, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (4, '小虎墩大英雄', 'ae841627-c714-406e-8400-67202c1161f5.jpg', '1d4cee4e-5d4f-455a-a99e-2364e5b21276.mp4', 30, 1, '邹亮', '2018', '2025-03-09', '<span>测试</span><span>测试</span><span>测试</span><span>测试</span>', 17, 4.88, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (5, '阴阳镇怪谈', 'f193d3bb-ee81-4b49-b5a1-cff991c937d2.jpg', 'bbc77b3d-8959-4069-8e1c-89288d581550.mp4', 29, 1, '李立群', '2016', '2025-03-09', '<p style=\"text-indent:2em;\">\n	憋宝人阎阳一和宝葫芦误入诡异阴阳镇，这里只能进不能出，凡擅自离开者，便会离奇死亡，阴阳镇遍布的恐怖传说，阎阳一虽然不信，但却频频遭遇邪事，阎王让人三更死，能否留命到五更？令阎阳一和宝葫芦更没想到的是，掩藏在诅咒之下的阴森小镇，背后竟然有惊人的秘密……\n</p>', 2, 0.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (6, '长江妖姬', '5351cc2f-ff00-49a4-b50f-56bc3feae3c6.jpg', 'b181fb94-41ec-4e2d-abe7-4826277a78ec.mp4', 21, 1, '安唯绫', '2021', '2025-03-09', '<p style=\"text-indent:2em;\">\n	长江之水，始于昆仑，终于东溟，万物得江水滋养化为妖灵者不可计数，进京求学的书生隋源误入锦鲤鱼妖江小虞的“鸳鸯火锅”陷阱，爱上了她，镇妖师东方行云救下隋源，却发现这一切都是上古凶兽横公鱼在暗中作祟，江水沸腾，民不聊生，一场拯救苍生与爱恋之战在乌江之上打响……\n</p>', 5, 2.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (7, '青蛇:前缘', 'f0f4c922-f1c6-413e-8582-c6657a1bc62e.jpg', 'e85da7e1-df21-4ec5-b7ab-fca45f875d2f.mp4', 21, 1, '钟欣潼', '2010', '2025-03-09', '<p style=\"text-indent:2em;\">\n	千年前，青蛇在道清的点化下逐渐开化，生出与道清相守之心。却因自己对道清的执念，导致道清进入轮回之劫，遭受世世横死之难。为了化解道清的劫难，数百年间，青蛇不断寻找道清转世，在历经人间的误解与磨难之后，最终获得成长与救赎、领悟真爱的故事。\n</p>', 0, 0.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (8, '魔法满屋', 'e9186de9-c3bb-4fcc-82d9-b01b75287160.jpg', 'bfe3dadc-c7fc-4bd7-b490-a742ac356f21.mp4', 28, 2, '陈慧娴', '2010', '2025-03-09', '<p style=\"text-indent:2em;\">\n	&nbsp;讲述一个非同寻常的魔法家庭，隐居在哥伦比亚的山区一座神奇的房子里，生活在一个充满活力的小镇：一个奇妙的、充满魅力的地方：奇迹谷。奇迹谷的魔力使这个家庭的每个孩子都拥有独特魔法天赋，从超强大神力到治愈他人的能力……除了米拉贝。但是，当她发现围绕奇迹谷的魔法处于危险之中时，米拉贝下定决心，她这唯一无魔法天赋的马利加人，将成为这个特殊家庭的希望。\n</p>', 1, 4.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (9, '速度与激情9', '9b10c6f1-7d1f-49fa-b332-2e22c4646a95.jpg', '7d091d74-f9ab-429d-8960-b9b37aac0672.mp4', 19, 1, '范·迪塞尔', '2010', '2025-03-09', '<p style=\"text-indent:2em;\">\n	“唐老大”多姆·托莱多（范·迪塞尔 饰）与莱蒂（米歇尔·罗德里格兹 饰）和他的儿子小布莱恩，过上了远离纷扰的平静生活。然而他们也知道，安宁之下总潜藏着危机。这一次，为了保护他所爱的人，唐老大不得不直面过去。他和伙伴们面临的是一场足以引起世界动荡的阴谋，以及一个前所未遇的一流杀手和高能车手。而这个名叫雅各布（约翰·塞纳 饰）的人，恰巧是多姆遗落在外的亲弟弟。\n</p>', 28, 4.75, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (10, '不要忘记我爱你', 'c88d7391-6bec-4766-9cc3-dbd6e7017f07.jpg', 'f0a78599-6aac-4216-99fb-44b5c9871de8.mp4', 21, 1, '古力娜扎', '2019', '2025-03-09', '<p style=\"text-indent:2em;\">\n	2022年情人节，用一部电影告白——不要忘记我爱你。星玥爱上了一个特别的人，她的男友陆尧每天醒来都会忘记昨天发生的一切。昨日的亲密爱人转眼变成陌生人，一次次的重来，是不堪承受的伤痛煎熬，还是人生只如初见的幸运？爱的直觉，是否真的无法隐藏、不会忘记？\n</p>', 20, 4.50, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (11, '狙击手', 'b8ea9074-fedb-45be-9c18-b2fc96c15431.jpg', 'ea91f186-d7d6-4832-8e38-4b10df97f4d4.mp4', 13, 1, '陈永胜', '2019', '2025-03-09', '<p style=\"text-indent:2em;\">\n	影片根据抗美援朝战争“冷枪冷炮”运动中神枪手群体事迹改编。1952年冬至1953年初，中国人民志愿军与联合国军在朝鲜战场形成僵持，双方发起了低强度的密集狙击战，史称\"冷枪冷炮运动\"。在连长（张译 饰）带领下的狙击五班战士枪法过人，成为敌军的心头大患，班长刘文武（章宇 饰）更成为重点狙击对象。为重创狙击五班，敌方调配精英狙击小队，配以最先进的武器装备，更迫使狙击五班战士大永（陈永胜 饰）等人为救同伴进入其设好的险境之地。但正当敌军打响自己如意算盘之时，他们未料到，被他们当作诱饵的侦察兵亮亮（刘奕铁 饰）身上其实隐藏着更大的秘密......\n</p>', 26, 5.00, '是', 0.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (12, '大哥，别闹了', 'ea37ae65-a7ad-4e9b-be53-5789a17f93af.jpg', 'a3a1b599-85cd-4ab1-8b68-c51ad296ac13.mp4', 24, 1, '许君聪', '2021', '2025-03-09', '<div>\n	这是一部都市喜剧。为了使浪子李纵回归正途，表哥高程用沉浸式体验的超级剧本杀让表弟开启了一场刺激，又笑料不断的改造之旅......\n</div>\n<div>\n	<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%BD%92%E6%9D%A5%E5%90%A7/19186516\"></a><a target=\"_blank\" href=\"https://baike.baidu.com/item/%E7%BA%A2%E8%8C%B6%E9%A6%86\"></a><a></a><a target=\"_blank\" href=\"https://baike.baidu.com/item/Welcome%20back\"></a><a target=\"_blank\" href=\"https://baike.baidu.com/item/%E4%B8%BA%E4%BD%A0%E5%A5%BD/2935892\"></a><a></a><a></a><a target=\"_blank\" href=\"https://baike.baidu.com/item/%E9%9B%AA%E9%A3%9E%E8%8A%B1/2472493\"></a><a></a><a target=\"_blank\" href=\"https://baike.baidu.com/item/Evolve/22069823\"></a><a></a> \n</div>', 7, 5.00, '否', 1.00, NULL, '0', '通过');
INSERT INTO `movie` VALUES (13, '李茂扮太子', '8c9d9e37-02a7-41f9-a5f6-3ae8330487fc.jpg', 'caaaa4a7-75de-48f8-8a20-73ad093a16e9.mp4', 24, 1, '马丽', '2022', '2025-03-09', '<p style=\"text-indent:2em;\">\n	影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情影片详情\n</p>', 23, 5.00, '是', 0.00, 0, '14', '通过');
INSERT INTO `movie` VALUES (16, '这个杀手不太冷', '402511dc-b71e-452f-a165-8bb8b37fbf85.jpg', 'd9d194c0-c953-4a4b-967d-5492e542028d.mp4', 24, 2, '马丽', '2022', '2025-03-09', '11111112222222223333333', 16, 5.00, '是', 0.00, 0, '35', '通过');
INSERT INTO `movie` VALUES (17, '盗梦空间', 'bfc833f4-a9b5-4a41-b4e6-662595cc9721.png', '81012a88-03a2-4b74-a305-950fa1f37874.mp4', 27, 2, '莱昂纳多·迪卡普里奥', '2010', '2025-03-10', '<span style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">影片剧情游走于梦境与现实之间，被定义为“发生在意识结构内的当代动作科幻片”，讲述了由莱昂纳多·迪卡普里奥扮演的造梦师，带领特工团队进入他人梦境，从他人的潜意识中盗取机密，并重塑他人梦境的故事。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (18, '头号玩家', '2bf9370a-55cc-4c5c-b853-813817e591ac.png', '195d150f-cb84-453b-8100-6ec55c3ab539.mp4', 27, 2, '泰尔·谢里丹', '2018', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">《头号玩家》根据恩斯特·克莱恩</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">同名小说《玩家一号》改编</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">，讲述了在虚拟技术十分发达的2045年，现实社会令人失望，人们沉浸于一款名为“绿洲”（OASIS）的虚拟游戏。18岁的韦德·沃兹和他的朋友们一起，突破重重关卡，拿到了游戏创始人詹姆斯·哈利迪在游戏中隐藏的彩蛋，成为绿洲继承人的故事</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (19, '十二生肖闯江湖', '464ded81-8553-472a-b42d-d81d76bd196c.jpg', 'bdbd5182-d1f3-45b0-a32c-77f58e2f1f05.mp4', 24, 1, '万丹青', '2010', '2025-03-10', '<span style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#F3F3F3;\">远古时期，天地一片混沌，还没有完全成形，人世间一片混乱、骚动。人间界形成之初，人类还没有计时、计年的观念，人们总是忘记自己出生在哪一年，过了多少岁，完全没有辈分的观念。为了解决人类的困扰，仙界的统治者--玉皇大帝想出个好办法，决定用动物名称来代表年份。于是，玉皇大帝派遣信使-信天翁广发英雄帖，召集动物界的各路英雄好汉参与遴选，以选出十二只动物担任生肖属相。</span>', 1, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (20, '误杀', '1e51dec4-3ec8-48f8-abce-9386ef7399d3.png', '4c029f6a-6889-410e-ad5f-6ecbcdcef694.mp4', 12, 1, '肖央', '2019', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">该电影改编印度电影误杀瞒天记</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">，讲述了李维杰与妻子阿玉打拼多年，膝下育有两个女儿，一个雨夜，一场意外，打破了这个家庭的宁静。而李维杰作为一个父亲，为了保全自己的家人，他不顾一切地决定瞒天过海的故事。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (21, '星际穿越', 'de9676b2-ad74-45f6-96f5-76cce33de81a.png', 'f20b09d2-70a0-462a-986a-1600ea50832f.mp4', 27, 2, '马修·麦康纳', '2014', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">该片在物理学家基普~索恩</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">的黑洞理论之上进行改编</span><span class=\"supWrap_G3kPp J-supWrap\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">&nbsp;[15]<em id=\"sup-15\"></em></span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">，主要讲述了一组宇航员通过穿越虫洞</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">来为人类寻找新家园的冒险故事。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (22, '哪吒之魔童闹海', '5fb98711-d2c6-4c59-b4ca-d3e4df32c9a0.png', '186798cb-9312-4410-8c3f-b00e2730ccec.mp4', 24, 1, '吕艳婷', '2025', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">该片是《哪吒</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">》系列电影的第二部，讲述了天劫之后，哪吒、敖丙的灵魂保住了，但很快会魂飞魄散，太乙真人打算用七色宝莲给二人重塑肉身，但是在重塑肉身的过程中却遇到重重困难，哪吒、敖丙的命运将迎来更多的挑战</span><span class=\"supWrap_G3kPp J-supWrap\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">&nbsp;</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (23, '无名之辈', '88cb5f42-6f89-40aa-b087-15e01cfdd966.png', '823d65fa-2012-48c4-9a63-a5d9506f232d.mp4', 24, 1, '陈建斌', '2018', '2025-03-10', '<span style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">该片讲述了在一座山间小城中，一对低配劫匪、一个落魄的泼皮保安、一个身体残疾却性格彪悍的残毒舌女以及一系列生活在社会不同轨迹上的小人物，在一个貌似平常的日子里，因为一把丢失的老枪和一桩当天发生在城中的乌龙劫案，从而被阴差阳错地拧到一起，发生的一幕幕令人啼笑皆非的荒诞喜剧。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (24, '无敌破坏王2：大闹互联网', '77cede08-ca3e-4a16-b954-79a9e9bc3c3f.png', '09a4e58e-1c8f-41c1-b0e7-b4d75dab582f.mp4', 24, 2, '约翰·C·赖利', '2018', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">电玩游戏破坏王拉尔夫和调皮女孩云妮洛普从电玩世界来到了广阔、未知又激动人心的虚拟网络世界，寻找可以修复《甜蜜冲刺》游戏的组件，他们需要在网民们的帮助下在网络世界中不断前行。在这过程中，他们遇到了热门网站BuzzTube的核心人物——充满企业家精神的椰丝小姐</span><span class=\"supWrap_G3kPp J-supWrap\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">&nbsp;</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">。</span>', 0, 0.00, '是', 0.00, 0, '0', '通过');
INSERT INTO `movie` VALUES (25, '憨豆特工3', '7dbcf12a-0c37-4a80-b6ff-a6181e911a85.png', '48b7e640-f561-469f-9c68-b196ea5e8a2f.mp4', 24, 2, '罗温·艾金森', '2018', '2025-03-10', '<span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">在英国召开首届G12峰会的前夕，军情七处的网络遭到黑客攻击，所有外勤特工的身份被悉数曝光。为了抓住罪犯，只能从已经退休的特工里找出一个人来担当重任。经过一番筛选后，留给军情七处的唯一选择就是已经改行当老师的强尼</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">。重新出山的强尼带着跟班大宝鲍夫</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">来到法国蔚蓝海岸寻找线索，发现美国科技巨头杰森·沃尔塔有重大嫌疑，还邂逅了一位神秘的俄罗斯女郎奥菲莉亚</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">。然而首相</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">完全不相信他的话，于是，强尼只能又一次以身犯险，靠他的笨拙来拯救世界</span><span class=\"supWrap_G3kPp J-supWrap\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">&nbsp;</span><span class=\"text_FRDIl\" style=\"color:#333333;font-family:&quot;font-size:14px;background-color:#FFFFFF;\">。</span>', 4, 5.00, '是', 0.00, 0, '0', '通过');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `savetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (14, '《时代强国》青少年外交实践基地启动，首期学员走进非洲四国驻华使馆', '088f628a-ff53-489c-8086-91a5133f3290.jpg', '2025年2月13日，由中国新闻网、共青团中央联合发起的“青少年外交实践基地”在北京正式启动。该项目延续《外交风云》剧集精神。借鉴《时代强国》栏目成功经验，首期选拔50名中学生开展为期三个月的沉浸式外交实训。<br />\n<br />\n启动仪式上，马达加斯加驻华大使让·路易·罗班松作为特邀嘉宾致辞：“去年与贵国少年的交流令人难忘，今年我们联合肯尼亚、埃及、南非使馆共同开放外交实训场景。”据悉，学员们将分组参与四国使馆开放日筹备，体验国书递交仪式模拟、跨文化谈判演练等实务，并通过AI外交官培训系统（技术由花漾搜索Pro提供）完成数字外交文书写作、元宇宙国际会议主持等新型技能考核<br />\n<br />\n项目创新融合三大特色：<br />\n1. **历史传承**：特邀《外交风云》顾问团队设计课程，还原新中国首批“将军大使”学习西餐礼仪、外交文书等经典场景；<br />\n2. **科技赋能**：采用中国搜索3.0平台的区块链技术认证学习成果，优秀学员可获得外交部下属机构颁发的“数字外交官”资格认证；<br />\n3. **文体融合**：引入《战至巅峰》电竞综艺模式，组织“中非文明对话”主题《王者荣耀》表演赛，通过游戏角色解说非洲历史文明。<br />\n<br />\n首批学员代表许忆蓝表示：“在埃及使馆实训时，我们用AR技术复原了亚历山大图书馆数字影像，这种跨时空的文化对话令人震撼。”项目负责人透露，下半年将推出“一带一路青少年电竞外交峰会”，拟邀请《这！就是灌篮》选手与东南亚国家青年开展篮球+电竞主题交流<br />', '2025-03-10 18:38:39');
INSERT INTO `news` VALUES (15, '第五届中国电影视觉艺术高峰论坛在琼开幕', '4f98258f-c057-4af6-a48d-4842464db5cc.jpg', '&nbsp;2025年1月24-25日，中国电影美术学会联合海南国际电影节组委会在海口举办第五届中国电影视觉艺术高峰论坛。本届论坛聚焦\"自贸港建设五周年背景下的影视工业化\"，中影集团CINITY实验室首度公开\"虚拟制片海南实践基地\"建设进展。<br />\n<br />\n&nbsp;论坛特设\"实时渲染引擎技术革新\"专题研讨，华为云展示了基于盘古大模型的影视资产智能生成系统。中国电影美术学会会长霍廷霄透露：\"海南正打造全球首个基于区块链的影视数字资产交易平台，预计2026年试运行。\" 美国电影摄影师协会（ASC）代表现场演示了与中国团队联合开发的LED虚拟拍摄实时校色系统，该系统已应用于《封神第二部》的海南摄制。<br />', '2025-03-10 18:42:36');
INSERT INTO `news` VALUES (16, '电竞题材电影《巅峰对决》开机 樊昊仑探索体育电影新形态', '19b95ed1-73ff-4b09-9c28-9b281e1cb8bd.png', '2025年3月5日，由腾讯影业与耀客传媒联合出品的电竞励志电影《巅峰对决》在苏州阳澄湖电竞馆开机。该片基于KPL真实选手成长故事改编，导演樊昊仑首次尝试\"实时虚拟拍摄+电竞数据可视化\"技术，王者荣耀职业联赛官方提供赛事数据支持。<br />\n<br />\n影片讲述天才少年陆阳（吴磊饰）在电竞职业道路上面临传统体育观念的冲突，其父（王景春饰）作为退役田径运动员，从反对到理解最终成为战队心理教练的亲情故事。拍摄现场启用Unreal Engine 5实时渲染系统，选手比赛时的意识流数据将通过华为河图技术转化为沉浸式视觉呈现。樊昊仑表示：\"这不是传统意义的搏击题材，我们要展现数字时代的新型体育精神。', '2025-03-10 18:43:13');
INSERT INTO `news` VALUES (17, 'eqe', 'noimg.jpg', 'aerawra', '2025-06-10 22:57:21');

-- ----------------------------
-- Table structure for playrecord
-- ----------------------------
DROP TABLE IF EXISTS `playrecord`;
CREATE TABLE `playrecord`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `movieid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_playrecord_member`(`memberid` ASC) USING BTREE,
  INDEX `fk_playrecord_movieid`(`movieid` ASC) USING BTREE,
  CONSTRAINT `fk_playrecord_member` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_playrecord_movieid` FOREIGN KEY (`movieid`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of playrecord
-- ----------------------------
INSERT INTO `playrecord` VALUES (1, 14, 9);
INSERT INTO `playrecord` VALUES (2, 14, 4);
INSERT INTO `playrecord` VALUES (3, 28, 9);
INSERT INTO `playrecord` VALUES (4, 29, 8);
INSERT INTO `playrecord` VALUES (5, 30, 9);
INSERT INTO `playrecord` VALUES (6, 30, 2);
INSERT INTO `playrecord` VALUES (7, 30, 11);
INSERT INTO `playrecord` VALUES (8, 14, 11);
INSERT INTO `playrecord` VALUES (9, 14, 12);
INSERT INTO `playrecord` VALUES (10, 31, 1);
INSERT INTO `playrecord` VALUES (11, 31, 1);
INSERT INTO `playrecord` VALUES (12, 31, 1);
INSERT INTO `playrecord` VALUES (13, 31, 9);
INSERT INTO `playrecord` VALUES (14, 31, 9);
INSERT INTO `playrecord` VALUES (15, 31, 2);
INSERT INTO `playrecord` VALUES (16, 32, 9);
INSERT INTO `playrecord` VALUES (17, 32, 9);
INSERT INTO `playrecord` VALUES (18, 32, 9);
INSERT INTO `playrecord` VALUES (19, 32, 10);
INSERT INTO `playrecord` VALUES (20, 32, 10);
INSERT INTO `playrecord` VALUES (21, 32, 10);
INSERT INTO `playrecord` VALUES (22, 32, 10);
INSERT INTO `playrecord` VALUES (23, 33, 4);
INSERT INTO `playrecord` VALUES (24, 33, 4);
INSERT INTO `playrecord` VALUES (25, 33, 4);
INSERT INTO `playrecord` VALUES (26, 33, 4);
INSERT INTO `playrecord` VALUES (27, 33, 4);
INSERT INTO `playrecord` VALUES (28, 33, 12);
INSERT INTO `playrecord` VALUES (29, 33, 12);
INSERT INTO `playrecord` VALUES (30, 34, 5);
INSERT INTO `playrecord` VALUES (31, 34, 5);
INSERT INTO `playrecord` VALUES (32, 34, 4);
INSERT INTO `playrecord` VALUES (33, 34, 4);
INSERT INTO `playrecord` VALUES (34, 34, 12);
INSERT INTO `playrecord` VALUES (35, 14, 13);
INSERT INTO `playrecord` VALUES (36, 35, 4);
INSERT INTO `playrecord` VALUES (37, 35, 4);
INSERT INTO `playrecord` VALUES (38, 35, 9);
INSERT INTO `playrecord` VALUES (39, 35, 9);
INSERT INTO `playrecord` VALUES (40, 35, 16);
INSERT INTO `playrecord` VALUES (41, 35, 16);
INSERT INTO `playrecord` VALUES (42, 36, 9);
INSERT INTO `playrecord` VALUES (43, 36, 9);
INSERT INTO `playrecord` VALUES (44, 36, 9);
INSERT INTO `playrecord` VALUES (45, 36, 16);
INSERT INTO `playrecord` VALUES (46, 36, 16);
INSERT INTO `playrecord` VALUES (47, 37, 16);
INSERT INTO `playrecord` VALUES (48, 37, 16);
INSERT INTO `playrecord` VALUES (49, 37, 16);
INSERT INTO `playrecord` VALUES (50, 37, 16);
INSERT INTO `playrecord` VALUES (51, 37, 13);
INSERT INTO `playrecord` VALUES (52, 37, 13);
INSERT INTO `playrecord` VALUES (53, 37, 9);
INSERT INTO `playrecord` VALUES (54, 37, 16);
INSERT INTO `playrecord` VALUES (55, 34, 9);
INSERT INTO `playrecord` VALUES (56, 40, 10);
INSERT INTO `playrecord` VALUES (57, 40, 10);
INSERT INTO `playrecord` VALUES (58, 40, 16);
INSERT INTO `playrecord` VALUES (59, 40, 16);
INSERT INTO `playrecord` VALUES (60, 40, 11);
INSERT INTO `playrecord` VALUES (61, 40, 13);
INSERT INTO `playrecord` VALUES (62, 40, 16);
INSERT INTO `playrecord` VALUES (63, 40, 10);
INSERT INTO `playrecord` VALUES (64, 40, 10);
INSERT INTO `playrecord` VALUES (65, 40, 10);
INSERT INTO `playrecord` VALUES (66, 40, 9);
INSERT INTO `playrecord` VALUES (67, 40, 9);
INSERT INTO `playrecord` VALUES (68, 40, 11);
INSERT INTO `playrecord` VALUES (69, 40, 4);
INSERT INTO `playrecord` VALUES (70, 40, 4);
INSERT INTO `playrecord` VALUES (71, 40, 13);
INSERT INTO `playrecord` VALUES (72, 40, 13);
INSERT INTO `playrecord` VALUES (73, 40, 13);
INSERT INTO `playrecord` VALUES (74, 40, 13);
INSERT INTO `playrecord` VALUES (75, 40, 6);
INSERT INTO `playrecord` VALUES (76, 40, 6);
INSERT INTO `playrecord` VALUES (77, 40, 6);
INSERT INTO `playrecord` VALUES (78, 40, 6);
INSERT INTO `playrecord` VALUES (79, 40, 9);
INSERT INTO `playrecord` VALUES (80, 40, 9);
INSERT INTO `playrecord` VALUES (81, 40, 3);
INSERT INTO `playrecord` VALUES (82, 40, 9);
INSERT INTO `playrecord` VALUES (83, 40, 10);
INSERT INTO `playrecord` VALUES (84, 40, 10);
INSERT INTO `playrecord` VALUES (85, 40, 10);
INSERT INTO `playrecord` VALUES (86, 40, 10);
INSERT INTO `playrecord` VALUES (87, 40, 10);
INSERT INTO `playrecord` VALUES (88, 40, 10);
INSERT INTO `playrecord` VALUES (89, 40, 13);
INSERT INTO `playrecord` VALUES (90, 40, 13);
INSERT INTO `playrecord` VALUES (91, 40, 13);
INSERT INTO `playrecord` VALUES (92, 34, 11);
INSERT INTO `playrecord` VALUES (93, 34, 11);
INSERT INTO `playrecord` VALUES (94, 34, 11);
INSERT INTO `playrecord` VALUES (95, 34, 11);
INSERT INTO `playrecord` VALUES (96, 34, 11);
INSERT INTO `playrecord` VALUES (97, 34, 13);
INSERT INTO `playrecord` VALUES (98, 34, 6);
INSERT INTO `playrecord` VALUES (99, 34, 16);
INSERT INTO `playrecord` VALUES (100, 34, 10);
INSERT INTO `playrecord` VALUES (101, 34, 1);
INSERT INTO `playrecord` VALUES (102, 34, 25);
INSERT INTO `playrecord` VALUES (103, 34, 19);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `articleid` int NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `savetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_reply_member`(`memberid` ASC) USING BTREE,
  INDEX `fk_reply_article`(`articleid` ASC) USING BTREE,
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`articleid`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`memberid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (11, 35, 12, '234234', '2025-03-05 21:49:43');
INSERT INTO `reply` VALUES (12, 35, 12, '356356', '2025-03-05 21:49:45');
INSERT INTO `reply` VALUES (13, 35, 12, '489496', '2025-03-05 21:50:57');
INSERT INTO `reply` VALUES (14, 36, 13, '11342453', '2025-03-05 22:11:54');
INSERT INTO `reply` VALUES (15, 36, 13, '4674564', '2025-03-05 22:11:56');
INSERT INTO `reply` VALUES (16, 37, 14, '11111', '2025-03-05 22:21:37');
INSERT INTO `reply` VALUES (17, 37, 14, '2222222222222', '2025-03-05 22:21:39');
INSERT INTO `reply` VALUES (19, 40, 14, 'nb', '2025-05-10 06:49:48');
INSERT INTO `reply` VALUES (20, 40, 13, '2131231', '2025-05-10 07:09:38');
INSERT INTO `reply` VALUES (21, 34, 14, '666', '2025-06-10 22:54:08');

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `delstatus` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES (1, '观后感专区', '0');
INSERT INTO `section` VALUES (2, '影评专区', '0');
INSERT INTO `section` VALUES (4, '新版块', '0');

-- ----------------------------
-- Table structure for sheet
-- ----------------------------
DROP TABLE IF EXISTS `sheet`;
CREATE TABLE `sheet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `memberid` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `delstatus` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sheet
-- ----------------------------
INSERT INTO `sheet` VALUES (17, 36, '测试的', '0');
INSERT INTO `sheet` VALUES (18, 37, '我的第一个收藏夹', '0');
INSERT INTO `sheet` VALUES (21, 40, '123', '0');
INSERT INTO `sheet` VALUES (23, 34, '新日暮里', '0');
INSERT INTO `sheet` VALUES (24, 34, 'qqq', '0');

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `upass` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `utype` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `delstatus` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES (9, 'admin', '123', '蒸糕手', '13887890066', '管理员', '0', 'a5f17be3-be04-497c-b5d0-f730f757d9bb.jpg');

SET FOREIGN_KEY_CHECKS = 1;
