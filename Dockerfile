FROM openjdk:11
WORKDIR /app

COPY target/achat-1.0.jar achat.jar
EXPOSE 8089

CMD ["java", "-jar", "achat.jar"]
