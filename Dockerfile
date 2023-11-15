FROM openjdk:11
EXPOSE 8089

WORKDIR /app

COPY sito/db/achatdb /app/sito/db/achatdb

COPY target/your-specific-jar-name.jar achat.jar

ENTRYPOINT ["java", "-jar", "achat.jar"]
