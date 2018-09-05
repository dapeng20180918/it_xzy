elaster stack
#############################################################################################

安装 \\172.16.232.48\FileServer\public\liukai\Elaster-CDROM\ElasterStack-5.0-v1.0.iso
操作系统登录：root/password

#cd /etc/sysconfig/network-scripts/
#vi ifcfg-eth1
[IPADDR]
[GATEWAY]
[NETMASK]
[ONBOOT]
#ifup ifcfg-eth1
服务url： 
	http://172.16.206.188:8080/client/
用户名密码：
	admin/password
	
修改全局配置：
management.network.cidr 
管理服务器网络的CIDR  
172.16.206.0/24 

#vi /etc/cloud/management/web.xml
    <session-config>
        <session-timeout>0</session-timeout> //永不过期
    </session-config>
	
#/etc/init.d/cloud-management restart

web servers
#############################################################################################

1 安装java-1.8, mysql-5.5
2 解压portal.rar
3 配置门户服务
	portal\start.bat 
		[path]
	portal\application.properties
		[logging.path]
		[spring.datasource.url]
		[spring.datasource.username]
		[spring.datasource.password]
4 修改跳转IP
	portal\demo-0.0.1-SNAPSHOT.war\views\user\navbar.html
		[href="http://172.16.206.69:9090/#/service/data_source"]
5 配置气象服务
	portal\nginx-1.8.0\conf\nginx.conf 
		[root]
	portal\nginx-1.8.0\ROOT\views\service\cloud_platform.html
		[src="http://172.16.206.180:8080/client/"]	//云平台IP
6 运行 portal\start.bat，第一次运行会自动创建db表
7 根据 default.sql 插入基本信息
8 运行 portal\nginx-1.8.0\nginx.exe
9 停防火墙




