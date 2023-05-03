# Como base para nuestra imagen, tomaremos el Alpine Linux con Java
#FROM openjdk:8-jdk-alpine
# Usar una imagen base con JDK 11 y Maven
FROM maven:3.8.4-openjdk-11 AS build

# crea un directorio de trabajo en un contenedor docker.
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Maven para construir el proyecto
RUN mvn clean package

# Crear una nueva imagen basada en OpenJDK 11
FROM openjdk:11-jre-slim-buster

# Dejamos que Docker copie nuestro archivo jar en la imagen.
#COPY target/registro-ot-mongodb-0.0.1-SNAPSHOT.jar registro-ot-mongodb-0.0.1.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/registro-ot-mongodb-0.0.1-SNAPSHOT.jar /app/registro-ot-mongodb-0.0.1-SNAPSHOT.jar

#Este será el ejecutable que se iniciará cuando el contenedor esté arrancando.
#Debemos definirlos como JSON-Array porque usaremos un ENTRYPOINT en combinación
# con un CMD para algunos argumentos de la aplicación.
ENTRYPOINT ["java","-jar","/app/registro-ot-mongodb-0.0.1.jar"]