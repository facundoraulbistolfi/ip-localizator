FROM tomcat:8.5-alpine

MAINTAINER FacundoBistolfi

# Refer to Maven build -> finalName
ARG WAR_FILE=target/ip-localizator-0.0.1-SNAPSHOT.war

VOLUME /tmp
ADD ${WAR_FILE} /usr/local/tomcat/webapps/app.war
RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/app.war" ]

