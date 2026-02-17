FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/consumidor-ubicaciones-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
