# 毕业设计后端 - 新疆兵团电子政务

> 类型：优化

> 开启时间：2018年2月10日

> 结束时间


## Mac 操作

`Command + Shift + .`可以显示隐藏文件、文件夹，再按一次，恢复隐藏

finder下使用`Command + Shift + G`可以前往任何文件夹，包括隐藏文件夹

查询WiFi密码

```
security find-generic-password -ga "你的WIFI的名字" | grep "password:"
```

## Maven 	指令

`mvn dependency:tree > a`

rm -rf a

`mvn clean install`

java -jar /Users/lisiqi/Desktop/xj/project-service-1.0-SNAPSHOT.jar 

## GitLab 操作

解决代码冲突

```
# 保存本地版本
git stash save "简化版"
# 将当前Git栈信息打印出来
git stash list
# 暂存了本地修改之后，pull内容
git pull
# 指定版本号为stash@{0}的工作取出
git stash pop stash@{0}
# 删除stash
git stash drop stash@{0}
# 清空
git stash clear
```

打标签

```
git log
git tag -a 'v1.0.2' -m '新疆电子政务v1.0.2'
git push origin v1.0.2
```

```
cd Apollo01
git init
git remote add origin https://gitlab.com/wohuifude123/Apollo01.git
git add .
git commit -m "Initial commit"
git push -u origin master
```

## IDEA 快速编写代码

```
sout = System.out.println();
soutp = System.out.println(" ");
soutv = System.out.println("变量名 = " + 变量);
soutm = System.out.println("当前类名.当前方法");
psvm = public static void main(String[] args) {}
```

## 数据库 操作

### 进入数据库

```
/usr/local/mysql/bin/mysql -u root -p
```

```
cd /usr/local/mysql/bin/
sudo su
./mysqld_safe --skip-grant-tables &
./mysql
FLUSH PRIVILEGES;
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('123456');
```

查看创建的数据库是否成功

```
show databases;
```

### 创建一个名字为student数据库

```
create database student;
```

### 进入数据库

```
use student;
```

### 查看该数据库中的表

```
show tables;
```

### 设置字符编码

```
-- 创建数据库时，设置数据库的编码方式 
-- CHARACTER SET 指定数据库采用的字符集 utf8不能写成utf-8
-- COLLATE 指定数据库字符集的排序规则 utf8的默认排序规则为utf8_general_ci（通过show character set查看）
drop database if EXISTS dbtest;
create database dbtest CHARACTER SET utf8 COLLATE utf8_general_ci;
-- 修改数据库编码
alter database dbtest CHARACTER SET GBK COLLATE gbk_chinese_ci;
alter database dbtest CHARACTER SET utf8 COLLATE utf8_general_ci;
```

```
-- 创建表时，设置表、字段编码
use dbtest;
drop table if exists tbtest;
create table tbtest(
id int(10) auto_increment,
user_name varchar(60) CHARACTER SET GBK COLLATE gbk_chinese_ci,
email varchar(60),
PRIMARY key(id)
)CHARACTER SET utf8 COLLATE utf8_general_ci;
```

```
-- 修改表编码
alter table tbtest character set utf8 COLLATE utf8_general_ci;
-- 修改字段编码
ALTER TABLE tbtest MODIFY email VARCHAR(60) CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 查看所有的字符编码
SHOW CHARACTER SET;
-- 查看创建数据库的指令并查看数据库使用的编码
show create database dbtest;

-- 查看数据库编码：
show variables like '%char%';
```

## 创建表

### 查询表字段名

```
select COLUMN_NAME from information_schema.COLUMNS where table_name = 'roles';
```

### 用户表

```
drop table if exists user;
CREATE TABLE `user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
 `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
 `roleId` int(11) NOT NULL,
 PRIMARY KEY (`id`),
 foreign key(`roleId`) references roles(roleId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```

```
drop table user;
```

### 用户基本信息

```
drop table if exists user_information;
CREATE TABLE `user_information` (
 `id` int(11) NOT NULL,
 `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
 `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
 `telephone` varchar(50) CHARACTER SET utf8,
 `question` varchar(50) CHARACTER SET utf8,
 `answer` varchar(50) CHARACTER SET utf8,
 `address` varchar(50) CHARACTER SET utf8,
 `email` varchar(50) CHARACTER SET utf8,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```

### 用户账号表

`utf8_general_ci` 不区分大小写，注册用户名和邮箱的时候就要使用

`utf8_general_cs` 区分大小写

`COLLATE` 校对集，主要是对字符集之间的比较和排序


```
CREATE TABLE `users` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
 `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
 `question` varchar(50) CHARACTER SET utf8 NOT NULL,
 `answer` varchar(50) CHARACTER SET utf8 NOT NULL,
 `roles_id` int(11) NOT NULL,
 PRIMARY KEY (`id`),
 CONSTRAINT `FK_ID` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```

```
insert into users values (1,'wo','123','教程','开始了',1);
insert into users values (2,'you','123456','罗马的日记','不浪漫',2);
insert into users (id,username,password,question,answer,roles_id) values (5,'wozai','123456','红色','不浪漫',2);
```


```
select * from users;
```

### 角色表

```
CREATE TABLE `roles` (
 `roleId` int(11) NOT NULL AUTO_INCREMENT,
 `roleName` varchar(20) CHARACTER SET utf8 NOT NULL,
 PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```

```
insert into roles values (1,'管理员'),(2,'普通用户');
```

```
select * from roles;
```

### 用户信息表

```
create table MyClass(id int(4) not null primary key auto_increment,name char(20) not null,sex int(4) not null default '0',degree double(16,2));
```

### 商品表

```
create table goods(goodsId int(4) not null primary key auto_increment,goodsName char(50) not null,category char(20) not null,price double(16,2));
```

```
alter table goods convert to character SET utf8;
```

```
insert into goods values (1,'红云翎','普通','16.01'),(2,'景龙','特殊','23.51');
```



```
create table user(
   id INT(5) NOT NULL AUTO_INCREMENT,
   userName VARCHAR(20) NOT NULL,
   telephone VARCHAR(20) NOT NULL,
   PRIMARY KEY ( id )
);
```

```
insert into user values(1,'female',1106101);
```

```
select * from user;
```

无法输入中文

```
alter table user convert to character SET utf8;
```

### 状态表

```
CREATE TABLE `state` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`message` VARCHAR(20) NOT NULL,
PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8;
```

```
insert into state (message) values('告警');
```

```
DELETE FROM state WHERE id in(3,17,18,19,21);
```

```
alter table state AUTO_INCREMENT=3;
```

### 创建城市表

```
CREATE TABLE `city` (
`id` int(11) NOT NULL,
`name` VARCHAR(20) NOT NULL,
PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8;
```

```
insert into city (id,name) values(1,'天津');
```

```
insert into city (id,name) values (70,'乌鲁木齐市'),(71,'克拉玛依市'),(72,'石河子市'),(73,'五家渠市'),(74,'阿拉尔市'),(75,'图木舒克市'),(76,'北屯市');
```


### 创建城市状态表

```
CREATE TABLE `city_state` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`city_id` int(11),  
`state_id` int(11),  
PRIMARY KEY (`id`),
foreign key(city_id) references city(id),
foreign key(state_id) references state(id)
);
```

```
insert into city_state (city_id,state_id) values(1,1);
```

```
insert into city_state (city_id,state_id) values(70,1),(71,2),(72,3),(73,1),(74,2),(75,3),(76,3),(70,2),(71,1),(72,3),(73,2),(74,1),(75,2),(76,1),(70,3),(71,1),(72,2),(73,3),(74,1),(75,2),(76,1);
```

```
SELECT c.name,s.message FROM city c,state s,city_state cs WHERE c.id= cs.city_id and s.id = cs.state_id;
```

`city_state`表的外键`city_id`指向`city`表的`id`

若要删除city，city_state这两个表，则必须先删除city_state表，再删除city表。

### 线路状态表

```
drop table if exists line_state;
CREATE TABLE `line_state` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`fromCityId` int(11),  
	`toCityId` int(11), 
	`value` int(11), 
	PRIMARY KEY (`id`),
	foreign key(fromCityId) references city(id),
	foreign key(toCityId) references city(id)
);
```

```
insert into line_state (fromCityId,toCityId,value) values(71,70,1),(72,70,2),(73,70,3),(74,70,1),(75,70,2),(76,70,3),(71,70,3),(72,70,1),(73,70,2),(74,70,3),(75,70,1),(76,70,3),(71,70,2),(72,70,3),(73,70,1),(74,70,2),(75,70,3),(76,70,1);
```

```
SELECT ls.id, c1.name as 'fromName', c2.name as 'toName', ls.value FROM city c1, city c2, line_state ls WHERE c1.id= ls.fromCityId and c2.id= ls.toCityId order by ls.id asc;
```

升序

```
select ls.id, c.name, ls.value, ls.toCityId from line_state ls inner join city c on ls.fromCityId=c.id order by ls.id asc;
```

### 告警内容

```
drop table if exists emergency;
CREATE TABLE `emergency` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`emergencyId` varchar(50),  
	`emergencyName` varchar(50), 
	`stateId` int(11), 
	`emergencyContent` varchar(200), 
	PRIMARY KEY (`id`),
	foreign key(stateId) references state(id)
);
```

```
insert into emergency (emergencyId,emergencyName,stateId,emergencyContent) values ('1900101','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('1900102','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到"),('1900103','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('1900104','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('1900105','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到"),('1900106','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('11','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到"),('12','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('13','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('14','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到"),('15','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('16','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('501','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('502','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('503','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到"),('504','任务5任务5任务5',3,"樵坪山到云篆山樵坪山到"),('505','任务5任务5任务5',1,"樵坪山到云篆山樵坪山到"),('506','任务5任务5任务5',2,"樵坪山到云篆山樵坪山到");
```

```
SELECT e.emergencyId, e.emergencyName, s.message, e.emergencyContent FROM emergency e, state s WHERE e.stateId = s.id order by e.id asc limit 0,6
```

### 链路响应时间

```
drop table if exists responseTime;
CREATE TABLE `responseTime` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`fromCityId` int(11),
	`toCityId` int(11),
	`time` varchar(50),  
	`duration` int(11), 
	PRIMARY KEY (`id`),
	foreign key(fromCityId) references city(id),
	foreign key(toCityId) references city(id)
);
```

```
insert into responseTime (fromCityId,toCityId,time,duration) values(72,71,"1:00",50),(74,73,"1:00",88),(76,75,"1:00",120),(72,71,"2:00",150),(74,73,"2:00",188),(76,75,"2:00",20),(72,71,"3:00",32),(74,73,"3:00",288),(76,75,"3:00",210),(72,71,"4:00",55),(74,73,"4:00",38),(76,75,"4:00",251),(72,71,"5:00",67),(74,73,"5:00",58),(76,75,"5:00",527),(72,71,"6:00",250),(74,73,"6:00",188),(76,75,"6:00",20),(72,71,"7:00",20),(74,73,"7:00",288),(76,75,"7:00",80),(72,71,"8:00",150),(74,73,"8:00",78),(76,75,"8:00",120),(72,71,"9:00",350),(74,73,"9:00",188),(76,75,"9:00",350),(72,71,"10:00",550),(74,73,"10:00",90),(76,75,"10:00",620),(72,71,"11:00",750),(74,73,"11:00",78),(76,75,"11:00",320),(72,71,"12:00",80),(74,73,"12:00",188),(76,75,"12:00",20),(72,71,"13:00",150),(74,73,"13:00",288),(76,75,"13:00",720),(72,71,"14:00",650),(74,73,"14:00",388),(76,75,"14:00",11),
(72,71,"15:00",23),(74,73,"15:00",880),(76,75,"15:00",120),(72,71,"16:00",256),(74,73,"16:00",218),(76,75,"16:00",520),
(72,71,"17:00",115),(74,73,"17:00",158),(76,75,"17:00",20),
(72,71,"18:00",150),(74,73,"18:00",28),(76,75,"18:00",99);
```

```
SELECT rt.id, c1.name as 'fromName', c2.name as 'toName', rt.time,duration FROM city c1, city c2, responseTime rt WHERE c1.id= rt.fromCityId and c2.id= rt.toCityId and c1.name = '石河子市' and c2.name = '克拉玛依市' order by rt.id asc limit 0,6;
```

```
select fromCityId,toCityId,GROUP_CONCAT(duration) AS durantion FROM responseTime group by fromCityId,toCityId
```

```
select fromCityId,toCityId,GROUP_CONCAT(time order by id asc) AS durantion FROM responseTime group by fromCityId,toCityId
```

### 可用率

```
drop table if exists useable;
CREATE TABLE `useable` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`fromCityId` int(11),
	`toCityId` int(11),
	`percentage` int(11), 
	PRIMARY KEY (`id`),
	foreign key(fromCityId) references city(id),
	foreign key(toCityId) references city(id)
);
```

```
insert into `useable` (fromCityId,toCityId,percentage) values (70,72,90),(71,72,80),(76,72,77),(75,72,70),(71,72,65),(71,72,60),(71,72,55),(71,72,50),(70,72,30),(71,72,20),(70,72,88),(71,72,70),(71,72,67),(71,72,60),(71,72,55),(71,72,50),(71,72,45),(71,72,30),(71,72,20),(71,72,10),(70,72,70),(71,72,68),(71,72,65),(71,72,60),(71,72,58),(71,72,55),(71,72,50),(71,72,35),(71,72,30),(71,72,20);

```

```
SELECT c1.name as 'fromName', c2.name as 'toName', u.percentage FROM city c1, city c2, useable u WHERE c1.id= u.fromCityId and c2.id= u.toCityId order by u.id asc limit 0,10;
```

### 时间表

```
CREATE TABLE `dataTime` (
	`id` int(11) NOT NULL,
	`content` varchar(20),
	PRIMARY KEY (`id`)
);
```

```
insert into `dataTime` (id,content) values (1,'0:00'),(2,'1:00'),(3,'2:00'),(4,'3:00'),(5,'4:00'),(6,'5:00'),(7,'6:00'),(8,'7:00'),(9,'8:00'),(10,'9:00'),(11,'10:00'),(12,'11:00'),(13,'12:00'),(14,'13:00'),(15,'14:00'),(16,'15:00'),(17,'16:00'),(18,'17:00'),(19,'18:00'),(20,'19:00'),(21,'20:00'),(22,'21:00'),(23,'22:00'),(24,'23:00');
```


### 数据中心故障情况

```
CREATE TABLE `centre_breakdown` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`day` varchar(20),
	`percentage` double(16,2),
	PRIMARY KEY (`id`)
);
```

```
select day,percentage from centre_breakdown limit 0,31
```

### CPU使用率

```
CREATE TABLE `cpu_useable` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`timeId` int(11),
	`percentage` double(16,2),
	PRIMARY KEY (`id`),
	foreign key(timeId) references dataTime(id)
);
```

```
insert into `cpu_useable` (timeId,percentage) values (1,58.61),(2,73.1),(3,53.2),(4,23.5),(5,11.8),(6,77.9),(7,23.8),(8,90.1),(9,12.5),(10,8.3),(11,10.9),(12,66),(13,56.7),(1,68.61),(2,93.1),(3,93.2),(4,13.5),(5,21.8),(6,97.9),(7,83.8),(8,10.1),(9,82.5),(10,78.3),(11,10.9),(12,16),(13,96.7),(1,73.61),(2,66.1),(3,73.2),(4,13.5),(5,21.8),(6,77.9),(7,83.8),(8,70.1),(9,82.5),(10,28.3),(11,70.9),(12,96),(13,96.7);
```

### 内存使用率

```
CREATE TABLE `storage_useable` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`timeId` int(11),
	`percentage` double(16,2),
	PRIMARY KEY (`id`),
	foreign key(timeId) references dataTime(id)
);
```

```
insert into `storage_useable` (timeId,percentage) values (1,88.61),(2,13.1),(3,73.2),(4,53.5),(5,21.8),(6,17.9),(7,83.8),(8,10.1),(9,82.5),(10,28.3),(11,30.9),(12,16),(13,76.7),(1,25.61),(2,93.1),(3,93.2),(4,53.5),(5,91.8),(6,17.9),(7,13.8),(8,90.1),(9,82.5),(10,28.3),(11,30.9),(12,16),(13,76.7),(1,23.61),(2,56.1),(3,73.2),(4,93.5),(5,81.8),(6,17.9),(7,83.8),(8,70.1),(9,82.5),(10,28.3),(11,10.9),(12,96),(13,86.7);
```

### 磁盘使用率

```
CREATE TABLE `disk_useable` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`timeId` int(11),
	`percentage` double(16,2),
	PRIMARY KEY (`id`),
	foreign key(timeId) references dataTime(id)
);
```

```
insert into `disk_useable` (timeId,percentage) values (1,18.61),(2,93.1),(3,23.2),(4,73.5),(5,11.8),(6,77.9),(7,53.8),(8,20.1),(9,12.5),(10,88.3),(11,60.9),(12,16),(13,96.7),(1,93.61),(2,66.1),(3,73.2),(4,93.5),(5,21.8),(6,17.9),(7,83.8),(8,10.1),(9,82.5),(10,28.3),(11,10.9),(12,96),(13,26.7),(1,83.61),(2,66.1),(3,73.2),(4,23.5),(5,21.8),(6,57.9),(7,83.8),(8,50.1),(9,82.5),(10,28.3),(11,90.9),(12,96),(13,26.7);
```

### 网络吞吐量

```
CREATE TABLE `swallow` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`timeId` int(11),
	`percentage` double(16,2),
	PRIMARY KEY (`id`),
	foreign key(timeId) references dataTime(id)
);
```

```
insert into `swallow` (timeId,percentage) values (1,98.61),(2,13.1),(3,53.2),(4,73.5),(5,81.8),(6,17.9),(7,13.8),(8,90.1),(9,12.5),(10,88.3),(11,60.9),(12,16),(13,96.7);

insert into `swallow` (timeId,percentage) values (11,23.61),(12,16.1),(13,73.2),(14,93.5),(15,51.8),(16,17.9),(17,23.8),(18,10.1),(19,82.5),(20,28.3),(21,90.9),(22,96),(23,26.7),(1,13.61),(2,66.1),(3,73.2),(4,23.5),(5,91.8),(6,57.9),(7,83.8),(8,50.1),(9,82.5),(10,88.3),(11,70.9),(12,96),(13,96.7),(6,23.61),(7,16.1),(8,73.2),(9,93.5),(10,51.8),(11,17.9),(12,23.8),(13,10.1),(14,82.5),(15,28.3),(16,90.9),(17,96),(18,26.7);
```

```
SELECT t.content,s.percentage FROM swallow s,dataTime t WHERE s.timeId= t.id;
```

```
UPDATE mytable SET myfield = 'value' WHERE other_field = 'other_value'
```

```
drop table swallow;
```

### 电信3期任务表

```
CREATE TABLE `telecom_task` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(20),
	`ids` varchar(20),
	PRIMARY KEY (`id`)
);
```

```
insert into `telecom_task` (name,ids) values ('电信任务1','1,0,1'),('联通任务1','2,0,1'),('宽带任务1','3,0,1'),('业余任务1','5,0,1'),('移动任务1','7,0,1'),('电信任务1','1,0,1'),('联通任务2','2,0,1'),('宽带任务2','3,0,1'),('业余任务5','5,0,1'),('移动任务2','7,0,1'),('电信任务1','1,0,1'),('联通任务3','2,0,1'),('宽带任务3','3,0,1'),('业余任务3','5,0,1'),('移动任务7','7,0,1');
```

### 基本信息表

```
CREATE TABLE `device` (
	`id` int(11) NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8;

```

```
insert into device (id,name) values (1,'服务器'),(2,'网络设备'),(3,'安全设备'),(4,'存储设备'),(5,'虚拟机'),(6,'其他');
```

```
CREATE TABLE `basic_message` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`device_id` int(11),  
	`quantity` int(11),  
	PRIMARY KEY (`id`),
	foreign key(device_id) references device(id)
);

insert into basic_message (device_id,quantity) values (1,20),(2,500),(3,35),(4,860),(5,120),(6,66);


SELECT d.name,b.quantity FROM basic_message b,device d WHERE b.device_id= d.id limit 0,6;
```

## 常用的 lombok 注解

`@EqualsAndHashCode` 实现equals()方法和hashCode()方法 

`@ToString` 实现toString()方法 

`@Data` 注解在类上；提供类所有属性的`getting`和`setting`方法，此外还提供了 equals、canEqual、hashCode、toString 方法 

`@Setter` 注解在属性上；为属性提供`setting`方法 

`@Getter` 注解在属性上；为属性提供`getting`方法 

`@Log4j` 注解在类上；为类提供一个 属性名为 log 的 log4j 日志对象 

`@NoArgsConstructor` 注解在类上；为类提供一个无参的构造方法 

`@AllArgsConstructor` 注解在类上；为类提供一个全参的构造方法 

`@Cleanup` 关闭流

`@Synchronized` 对象同步

`@SneakyThrows` 抛出异常

## 重庆巴南区

`mysql -h10.11.12.72 -P3306 -udevuser -pcloudwise@110119`

```
/*
 Navicat Premium Data Transfer

 Source Server         : 10.11.12.72
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : 10.11.12.72
 Source Database       : chongqing-highway

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : utf-8

 Date: 12/26/2017 18:50:29 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `msg_content`
-- ----------------------------
DROP TABLE IF EXISTS `msg_content`;
CREATE TABLE `msg_content` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `task_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '监控的任务ID',
  `task_name` varchar(255) NOT NULL COMMENT '监控任务名称',
  `alert_time` varchar(50) NOT NULL COMMENT '告警发送时间\n',
  `msg_content` varchar(300) NOT NULL COMMENT '告警信息',
  `led` varchar(20) NOT NULL COMMENT '监控状态的颜色',
  `timestamp` bigint(50) DEFAULT NULL COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `task_indicator`
-- ----------------------------
DROP TABLE IF EXISTS `task_indicator`;
CREATE TABLE `task_indicator` (
  `id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '主键',
  `task_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '监控的任务ID',
  `task_name` varchar(255) NOT NULL COMMENT '监控任务名称',
  `from_name` varchar(255) NOT NULL COMMENT '链路起点名称',
  `to_name` varchar(255) NOT NULL COMMENT '链路终点名称',
  `uprate` double(50,2) NOT NULL COMMENT '可用率',
  `last_check_time` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '最近监控时间',
  `last_check_group_time` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '用于分组的时间依据。格式：yyyy-MM-dd HH',
  `resptime_avg` double(50,2) NOT NULL COMMENT '平均响应时间',
  `status_led` varchar(20) NOT NULL COMMENT '监控状态的颜色',
  `timestamp` bigint(30) DEFAULT NULL COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


SET FOREIGN_KEY_CHECKS = 1;
```

```
insert into msg_content (id,task_id,task_name,alert_time,msg_content,led,timestamp) values 
('1', '3124', '41234', '412341234', 'gsdfasdfasdf', '234', 4123412341234);
```

```
insert into task_indicator (id,task_id,task_name,from_name,to_name,uprate,last_check_time,last_check_group_time,resptime_avg,status_led,timestamp) values ('01b22d85-793b-4dc3-a121-523336bd8fcd', '695180', '重庆市公路局——巴南区交委防火墙', '重庆市公路局', '巴南区交委防火墙', 100.00, '2017-12-29 14:23:36', '2018-02-07 11', 0.00, 'green', 1517975468380),('024a245d-d25e-4a12-af7e-9b5a3d073ce1', '695189', '重庆市公路局——巴南区交委交换机', '重庆市公路局', '巴南区交委交换机', 80.03, '2017-12-29 14:23:36', '2018-02-07 11', 0.00, 'yellow', 1517975348377),('030c1275-62d0-4cfe-a727-b0bed5c335b5', '695193', '重庆市公路局——木洞治超站', '重庆市公路局', '木洞治超站', 50.05, '2017-12-29 14:23:36', '2018-02-07 11', 0.00, 'red', 1517975648377)
```

### 查询表中字段类型

```
select COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT from information_schema.COLUMNS where table_name = 'users';
```

```
CREATE TABLE tb_employee (
	ID INT(11) PRIMARY KEY AUTO_INCREMENT,
	loginname VARCHAR(18),
	PASSWORD VARCHAR(18),
	NAME VARCHAR(18) DEFAULT NULL,
	SEX CHAR(2) DEFAULT NULL,
	AGE INT(11) DEFAULT NULL,
	phone VARCHAR(21),
	sal DOUBLE,
	state VARCHAR(18)
)CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO tb_employee (loginname,PASSWORD,NAME,sex,age,phone,sal,state) VALUES ('jack','123456','杰克','男',26,'13902019999',9800,'ACTIVE');

```

## Maven 打包

`mvn clean install`

### URI is not registered 

```
URI is not registered (Settings | Languages & Frameworks | Schemas and DTDs)

Click the light (your is red) -> Fetch external resource
```
