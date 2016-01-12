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

10. Deploy to AWS