# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-22 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -e -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -B -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:22-jre
WORKDIR /app
# use a nonroot user
RUN useradd -u 10001 -r -s /sbin/nologin appuser
COPY --from=build /workspace/target/*-SNAPSHOT-shaded.jar /app/app.jar
EXPOSE 8080
USER appuser
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

