FROM openjdk:11-jre-alpine

WORKDIR /app

COPY Achat-Devops.jar /app
EXPOSE 8089

CMD ["java", "-jar", "Achat-Devops.jar"]