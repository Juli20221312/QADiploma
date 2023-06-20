FROM openjdk:11
COPY aqa-shop.jar /app.jar
CMD ["java", "-jar", "/app.jar"]