# Etapa 1: Construcción del proyecto
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copiar los archivos de Maven
COPY pom.xml .
COPY src ./src

# Construir el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Construcción de la imagen final
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/SpringTokensUniverse-0.0.1-SNAPSHOT.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
