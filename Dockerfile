FROM openjdk:21
LABEL authors="Jerry"
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","-Dspring.profiles.active=stag","app.jar"]
