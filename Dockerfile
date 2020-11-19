FROM openjdk:14-alpine
COPY target/yira-*.jar yira.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "yira.jar"]