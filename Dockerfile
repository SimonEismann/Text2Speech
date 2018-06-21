FROM tomcat:8.5-jre8-alpine

COPY build/libs/*.war /usr/local/tomcat/webapps/

CMD /usr/local/tomcat/bin/catalina.sh run