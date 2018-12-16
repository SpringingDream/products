FROM openjdk:11
MAINTAINER docker@dekinci.com
VOLUME /logs
COPY . /app
CMD java -jar *.jar
EXPOSE 8080