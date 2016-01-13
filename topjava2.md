1. Spring Boot generate/run
2. Profiles.
3. Add H2. WebServer/TcpServer
4. Model User/Role. Repository UserRepository.
5. Security/ UserDetailService/ LoggedUser
6. Add Rest
7. Add mail
8. Switch to war: http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-create-a-deployable-war-file
9. Customize path with Maven profiles.
   9.1 Customize resources and add profiles ${}.
       Mvn process resources (debug)

   9.2 Fix: default configuration and delimiter @:  https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.3-Release-Notes#maven-resources-filtering

10. AWS. Deploy to AWS
http://www.excelsior-usa.com/articles/tomcat-amazon-ec2-advanced.html

11. Set active profile:

1. Modify Tomcat config
Tomcat location:
http://stackoverflow.com/questions/12280372/where-can-i-find-the-tomcat-7-installation-folder-on-linux-ami-in-elastic-beanst
/usr/share/tomcat8
http://stackoverflow.com/questions/32327522/spring-boot-yml-and-standalone-tomcat-8-server

2. Set in props
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html#howto-set-active-spring-profiles