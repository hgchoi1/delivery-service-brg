FROM openjdk:21

ARG JAR_FILE=./build/libs/barogo-user-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 20381

CMD [ "java", "-jar", "app.jar" ]
