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




