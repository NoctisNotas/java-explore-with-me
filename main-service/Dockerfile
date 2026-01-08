FROM eclipse-temurin:21-jre

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

USER root
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app.jar"]