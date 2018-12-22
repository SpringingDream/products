FROM openjdk:11
MAINTAINER docker@dekinci.com
VOLUME /logs
COPY /target/marketplace-products.jar /app
CMD java -jar app/marketplace-products.jar
EXPOSE 10000