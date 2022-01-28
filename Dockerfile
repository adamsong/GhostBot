FROM gradle:7.3.3-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Yes, this is the same base image as before, unfortunately I cannot find an arm/v7 JRE17 image
FROM gradle:7.3.3-jdk17
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/GhostBot.jar
ENTRYPOINT ["java", "-jar", "/app/GhostBot.jar"]
