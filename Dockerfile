# Utiliser une base Java
FROM openjdk:8-jre-alpine
# Copier le fichier JAR compilé dans l'image
ADD target/Achat-Devops.jar Achat-Devops.jar

# Exposer le port sur lequel l'application va s'exécuter
EXPOSE 8089

# Exécuter l'application
ENTRYPOINT ["java","-jar","/Achat-Devops.jar"]
