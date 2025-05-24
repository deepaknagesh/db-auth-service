# temp container to build using gradle
FROM --platform=linux/amd64 gradle:jdk17-corretto AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build -x test || return 0
COPY . .
RUN gradle clean build -x test

FROM --platform=linux/amd64 eclipse-temurin:17-jre
ENV APP_HOME=/usr/app/
ENV ARTIFACT_NAME=db-auth-service-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME
ARG JAR_FILE=$APP_HOME/build/libs/$ARTIFACT_NAME
COPY --from=TEMP_BUILD_IMAGE $JAR_FILE .

EXPOSE 8080

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}