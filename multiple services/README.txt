#############################################################################################
elaster stack
#############################################################################################

��װ \\172.16.232.48\FileServer\public\liukai\Elaster-CDROM\ElasterStack-5.0-v1.0.iso
����ϵͳ��¼��root/password

#cd /etc/sysconfig/network-scripts/
#vi ifcfg-eth1
[IPADDR]
[GATEWAY]
[NETMASK]
[ONBOOT]
#ifup ifcfg-eth1
����url�� 
	http://172.16.206.188:8080/client/
�û������룺
	admin/password
	
�޸�ȫ�����ã�
management.network.cidr 
��������������CIDR  
172.16.206.0/24 

#vi /etc/cloud/management/web.xml
    <session-config>
        <session-timeout>0</session-timeout> //��������
    </session-config>
	
#/etc/init.d/cloud-management restart

#############################################################################################
web servers
#############################################################################################

1 ��װjava-1.8, mysql-5.5
2 ��ѹportal.rar
3 �����Ż�����
	portal\start.bat 
		[path]
	portal\application.properties
		[logging.path]
		[spring.datasource.url]
		[spring.datasource.username]
		[spring.datasource.password]
4 �޸���תIP
	portal\demo-0.0.1-SNAPSHOT.war\views\user\navbar.html
		[href="http://172.16.206.69:9090/#/service/data_source"]
5 �����������
	portal\nginx-1.8.0\conf\nginx.conf 
		[root]
	portal\nginx-1.8.0\ROOT\views\service\cloud_platform.html
		[src="http://172.16.206.180:8080/client/"]	//��ƽ̨IP
6 ���� portal\start.bat����һ�����л��Զ�����db��
7 ���� default.sql ���������Ϣ
8 ���� portal\nginx-1.8.0\nginx.exe
9 ͣ����ǽ

#############################################################################################
Linux ��װ����
#############################################################################################
[DB] ��װϵͳѡ��mysql
create database demo character set utf8; 
--��һ������java, nohup...
use demo;
set names utf8;
--��������

[system]
#/etc/init.d/iptables stop
#/etc/init.d/mysqld start

[java]
#tar zxvf jdk-8u171-linux-x64.tar.gz 
#vi application.properties 
#nohup /opt/jdk1.8.0_171/bin/java -jar /opt/project/demo-0.0.1-SNAPSHOT.war 1>/dev/null &


[nginx]
#yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel
#tar zxvf ngx_openresty-1.7.7.1.tar.gz 
#cd ngx_openresty-1.7.7.1
#./configure;make;make install

#vi /opt/project/nginx.conf
--edit   include     /usr/local/openresty/nginx/conf/mime.types;
#/usr/local/openresty/nginx/sbin/nginx -p /usr/local/openresty/nginx -c /opt/project/nginx.conf




