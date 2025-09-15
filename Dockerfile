# Dockerfile
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
# Copy your built jar into the image:
# If using Maven: mvn -DskipTests package -> target/app.jar
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]