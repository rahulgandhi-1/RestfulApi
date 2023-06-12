# Use the official OpenJDK image as the base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY myapprahul-1.0-SNAPSHOT.jar app.jar

# Set the environment variables
ENV EMAIL_RECIPIENT gandhirahul190@gmail.com
ENV WEATHER_API_KEY 0de875566c6a1d90f3ca4d00d9415602

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
