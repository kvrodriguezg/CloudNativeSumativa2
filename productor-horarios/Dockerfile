FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/productor-horarios-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
