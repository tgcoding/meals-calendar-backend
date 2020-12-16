FROM openjdk:14-jdk-alpine
VOLUME /tmp
COPY meals-calendar-backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
