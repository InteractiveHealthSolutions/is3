

=== IS3web === Contributors: IHS Software Type: Free, Open-source Requires: Microsoft Windows 7 or higher, Oracle Java Runtime Environment (JRE) v7.0 or higher, MySQL Server License: GPLv3

== Description == Interactive SMS Survey Service is a general purpose messaging service that enables users to conduct Surveys based on SMS. The software connects with SMSTarseel and provides a web interface to define Surveys and generate reports from data.

== Installation ==

    Download and install Oracle JDK v7.0 or higher
    Download and install GWT
    Download and install MySQL

== Configuration ==

1) SMSTarseel Please refer to smstarseel's guide:
2) import dump database file  "smssurvey20150105.sql" file in mysql database .
3) copy war folder  and paste this folder in tomcat/$CATALINA_BASE/webapps/. then change folder name to is3web. then you should restart tomcat server.

# Change Database credentials in hibernate.cfg.xml file 
 <property name="hibernate.connection.username">root</property>
 <property name="hibernate.connection.password">yourpassword </property>
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/smssurvey?zeroDateTimeBehavior=convertToNull</property>
  <property name="hibernate.default_schema">smssurvey</property>

#login credentials 

	username : admin
	password : admin

*remember that username and password are hardcoded and if you want to change it. it can be from ServerServiceImpl.java file.


