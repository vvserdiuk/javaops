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

11. Set active profile:

1. Modify Tomcat config
Tomcat location:
http://stackoverflow.com/questions/12280372/where-can-i-find-the-tomcat-7-installation-folder-on-linux-ami-in-elastic-beanst
/usr/share/tomcat8
http://stackoverflow.com/questions/32327522/spring-boot-yml-and-standalone-tomcat-8-server

2. Set in props
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html#howto-set-active-spring-profiles



AWS:
10. AWS. Deploy to AWS
<a href="http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/get-set-up-for-amazon-ec2.html">Setting Up with Amazon EC2</a>
- Sign-Up
- Launch instance
- Generate and convert key
- <a href="http://docs.aws.amazon.com/AmazonVPC/latest/GettingStartedGuide/getting-started-assign-eip.html">Assign an Elastic IP Address to Your Instance</a>
- <a href="https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html">Enter by SSH (putty)</a>
- <a href="https://serverfault.com/questions/664643/how-can-i-upgrade-to-java-1-8-on-an-amazon-linux-server#727254">Install Java JDK 1.8</a>
- <a href="https://andrewelkins.com/2012/01/08/how-to-add-git-to-an-amazon-ami-ec2-instance/">Install git</a>
- <a href="http://stackoverflow.com/questions/7532928/how-do-i-install-maven-with-yum">Install maven (without java 7)</a>

http://www.excelsior-usa.com/articles/tomcat-amazon-ec2-advanced.html
http://mtdevuk.com/2015/02/10/how-to-deploy-a-spring-boot-application-to-amazon-aws-using-elastic-beanstalk/

11. Run at 80 port: http://stackoverflow.com/questions/33703965/spring-boot-running-app-on-port-80

Install nginx:
`sudo su`
`yum install -y nginx`
`yum install mc`

cat /etc/init.d/nginx
`Ctrl+D (exit)`

# config:      /etc/nginx/nginx.conf
# config:      /etc/sysconfig/nginx

cat /etc/sysconfig/nginx
cat /etc/nginx/nginx.conf

<a href="http://askubuntu.com/questions/376199/sudo-su-vs-sudo-i-vs-sudo-bin-bash-when-does-it-matter-which-is-used">su/sudo</a>

https://www.tollmanz.com/nginx-location-match-homepage/
http://stackoverflow.com/questions/11954255/nginx-how-to-set-index-html-as-root-file

`sudo service nginx reload`

https://www.digitalocean.com/community/tutorials/how-to-optimize-nginx-configuration

Open 80 port: http://stackoverflow.com/questions/5004159/opening-port-80-ec2-amazon-web-services

https://en.wikipedia.org/wiki/Chmod

http://stackoverflow.com/questions/6795350/nginx-403-forbidden-for-all-files
namei -om /home/ec2-user/javaops/static/index.html


## External properties
http://www.javabeat.net/spring-boot-external-configurations/

## H2
http://stackoverflow.com/questions/221379/hibernate-hbm2ddl-auto-update-in-production
http://stackoverflow.com/questions/18077327/hibernate-hbm2ddl-auto-possible-values-and-what-they-do-any-official-explanat
http://stackoverflow.com/questions/20089470/hibernate-is-not-auto-creating-a-table-that-does-not-exist-in-the-db

## Preconditions in java
<a href="http://www.sw-engineering-candies.com/blog-1/comparison-of-ways-to-check-preconditions-in-java">Comparison Preconditions in Java</a>

## Configuration props
Add AppConfig
http://blog.codeleak.pl/2014/09/using-configurationproperties-in-spring.html
http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#configuration-metadata-annotation-processor

## @PropertySource
http://www.jayway.com/2014/02/16/spring-propertysource/
http://www.mkyong.com/spring/spring-propertysources-example/
http://blog.jamesdbloom.com/UsingPropertySourceAndEnvironment.html
http://stackoverflow.com/questions/13728000/value-not-resolved-when-using-propertysource-annotation-how-to-configure-prop

## OAuth2
https://github.com/spring-cloud-samples/authserver

## Resource:
- <a href="http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/">Spring Boot Reference Guide</a>
- <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/">Spring Framework Reference Documentation</a>

## Execution and Scheduling
<a href="http://stackoverflow.com/questions/4912228/when-should-i-use-a-completionservice-over-an-executorservice">CompletionService over an ExecutorService</a>

Omitting many details:

ExecutorService = incoming queue + worker threads
CompletionService = incoming queue + worker threads + output queue

http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#scheduling
http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#scheduling-enable-annotation-support

<a href="https://habrahabr.ru/post/260953/">10 советов по использованию ExecutorService</a>

<a href="Referencing beans across @Configuration classes">http://docs.spring.io/spring-javaconfig/docs/1.0.0.M4/reference/html/ch04s02.html</a>

## JSON
@JsonAutoDetect(fieldVisibility= ANY, getterVisibility= NONE, isGetterVisibility= NONE, setterVisibility= NONE)


## Test
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html