FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

COPY book_user.sql /docker-entrypoint-initdb.d
COPY books.sql /docker-entrypoint-initdb.d
COPY users.sql /docker-entrypoint-initdb.d



ENTRYPOINT ["java", "-jar", "application.jar"]