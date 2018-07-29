# Dockerfile for sample service using embedded tomcat server

FROM       docker.io/debugroom/wedding:centos7
MAINTAINER debugroom

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel \
       wget tar iproute

RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven
ENV JAVA_HOME /etc/alternatives/jre
RUN git clone https://github.com/debugroom/sample-spring-cloud.git /var/local/sample-spring-cloud
RUN mvn install -f /var/local/sample-spring-cloud/pom.xml

RUN cp /etc/localtime /etc/localtime.org
RUN ln -sf  /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=production /var/local/sample-spring-cloud/target/sample-spring-cloud-1.0-SNAPSHOT.jar
