# Como base para nuestra imagen, tomaremos el Alpine Linux con Java
FROM openjdk:8-jdk-alpine
# crea un directorio de trabajo en un contenedor docker.
WORKDIR /app
# Dejamos que Docker copie nuestro archivo jar en la imagen.
COPY target/registro-ot-mongodb-0.0.1-SNAPSHOT.jar registro-ot-mongodb-0.0.1.jar
#Este será el ejecutable que se iniciará cuando el contenedor esté arrancando.
#Debemos definirlos como JSON-Array porque usaremos un ENTRYPOINT en combinación
# con un CMD para algunos argumentos de la aplicación.
ENTRYPOINT ["java","-jar","/registro-ot-mongodb-0.0.1.jar"]