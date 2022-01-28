FROM gradle:7.3.3-jdk17
RUN mkdir /app
COPY ./build/libs/*.jar /app/GhostBot.jar
ENTRYPOINT ["java", "-jar", "/app/GhostBot.jar"]
