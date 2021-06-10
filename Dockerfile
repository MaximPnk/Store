FROM openjdk:8-jre-alpine
WORKDIR .
COPY target/store-0.0.1-SNAPSHOT.jar store.jar
ENTRYPOINT ["java", "-jar", "store.jar"]