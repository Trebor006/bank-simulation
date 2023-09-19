FROM adoptopenjdk/maven-openjdk11
EXPOSE 8080
COPY /target/bank-simulation-0.0.1-SNAPSHOT.jar bank-simulation.jar
# specify default command
ENTRYPOINT ["java", "-jar", "bank-simulation.jar"]
