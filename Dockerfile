FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY target/myapprahul-1.0-SNAPSHOT.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
