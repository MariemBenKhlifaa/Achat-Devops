FROM openjdk:11
EXPOSE 8089

WORKDIR /app

COPY sito/db/achat /app/sito/db/achat

COPY /target/achat-1.0.jar achat.jar

ENTRYPOINT ["java", "-jar", "achat.jar"]
