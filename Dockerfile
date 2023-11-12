FROM openjdk:11
WORKDIR /app

COPY achat-1.0.jar /app
EXPOSE 8089

CMD ["java", "-jar", "achat.jar"]
