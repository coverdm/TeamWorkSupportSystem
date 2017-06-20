FROM java:8
EXPOSE 8081
ADD /target/TeamWorkSupportSystem.jar TeamWorkSupportSystem.jar
ENTRYPOINT ["java", "-jar", "TeamWorkSupportSystem.jar"]