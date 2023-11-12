# Use an official OpenJDK JRE Alpine image as a parent image
FROM openjdk:11-jre-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY Achat-Devops.jar /app
EXPOSE 8089

# Specify the command to run on container start
CMD ["java", "-jar", "Achat-Devops.jar"]
