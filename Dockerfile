FROM eclipse-temurin:17-jdk-jammy

WORKDIR /bot
COPY run/* .

ENTRYPOINT ["java", "-jar", "Bot.jar"]