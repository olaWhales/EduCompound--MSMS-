#🛠️ Stage 1: Build the app
#FROM eclipse-temurin:17-jdk-alpine AS build
#WORKDIR /workspace/app
#
## Prepare Maven
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#RUN chmod +x mvnw
#RUN ./mvnw dependency:go-offline -B
#
## Copy and build source
#COPY src src
## Remove these lines:
## RUN ./mvnw package -DskipTests
#
## Replace with:
#COPY target/extention-manager-0.0.1-SNAPSHOT.jar /app/app.jar
#
#
## 🧼 Stage 2: Minimal runtime image
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#
## Copy the exact built JAR from Stage 1
#COPY --from=build /workspace/app/target/extention-manager-0.0.1-SNAPSHOT.jar /app/app.jar
#
## Run securely as non-root user
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#
## Expose the correct port your app uses
#EXPOSE 9000
#
## 💡 Startup command
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
#🧼 Stage: Minimal runtime image only (no build inside Docker)
