set names gbk;
--database
drop database demo; 
create database demo character set utf8; 
use demo;

--Rule
--insert into rule(id, name, description) values(1,'门户管理','修改查看用户和角色');
insert into rule(name, description) values('门户管理','修改查看用户和角色');
insert into rule(name, description) values('自助数据管理','自助数据加工和分析');
insert into rule(name, description) values('资源存储管理','修改查看资源存储');
insert into rule(name, description) values('并行分析和数据挖掘管理','修改查看并行分析和数据挖掘');
insert into rule(name, description) values('云平台管理','访问云平台');
insert into rule(name, description) values('大数据平台管理','访问大数据平台');

--Role
insert into role(id, create_date, description, name, rule_str) values(1,now(),"desc",'管理员','1;2;3;4;5;6');
insert into role(id, create_date, description, name, rule_str) values(2,now(),"desc",'审计员','1;2');
insert into role(id, create_date, description, name, rule_str) values(3,now(),"desc",'普通用户','2');

--User
insert into user(id,create_date,description,name,password,role_id,status,telephone_number) 
	values(1,now(),'desc','admin','111111',1,1,'13810002000');
	
--Setting
insert into analyze_setting(id, addr, name, password, root_path, timeout) 
	values(1,"192.168.100.1:2181","admin",'admin','/opt/',10000);