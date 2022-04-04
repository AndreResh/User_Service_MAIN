FROM adoptopenjdk:11-jre-hotspot
COPY target/UserService-0.0.1-SNAPSHOT.jar /demo.jar
ENTRYPOINT ["java", "-jar", "/demo.jar"]