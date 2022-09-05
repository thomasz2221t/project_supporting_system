FROM amazoncorretto:11

USER 9999

ADD --chown=9999:9999 target/*.jar application.jar

EXPOSE 8080
CMD ["java", "-jar", "application.jar"]