FROM openjdk:8-jre
RUN mkdir app
ADD /target/gestao-normas-0.0.1-SNAPSHOT.jar /app/gestao-normas-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT java -jar gestao-normas-0.0.1-SNAPSHOT.jar
