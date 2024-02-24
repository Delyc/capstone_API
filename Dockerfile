FROM openjdk:21
VOLUME /tmp
EXPOSE 8080
RUN mvn clean package
ARG JAR_FILE=target/api.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]