FROM adoptopenjdk/openjdk11:latest
ADD ./target/landingPage-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/landingPage-0.0.1-SNAPSHOT.jar"]
EXPOSE 8102