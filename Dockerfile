FROM openjdk:11
ARG JAR_FILE=target/graduation-project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gradudationproject.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","gradudationproject.jar"]