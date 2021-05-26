FROM centos:8

RUN yum install -y java-11-openjdk-devel

VOLUME /tmp
ADD /target/recipe-0.0.1-SNAPSHOT.jar myapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/myapp.jar"]
