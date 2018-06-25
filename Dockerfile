FROM tomcat:8.5-jre8-alpine

COPY build/libs/*.war /usr/local/tomcat/webapps/
RUN sed -i "s|redirectPort=\"8443\"|redirectPort=\"8443\" maxPostSize=\"52428800\"|g" /usr/local/tomcat/conf/server.xml

CMD /usr/local/tomcat/bin/catalina.sh run