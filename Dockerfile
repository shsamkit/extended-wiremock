FROM openjdk:8-jdk-alpine
WORKDIR /usr/local/app
COPY target/extended-wiremock.jar .
ENV CONFIG_LOCATION .
CMD java -jar extended-wiremock.jar --spring.config.location=${CONFIG_LOCATION} ${CONFIG_OVERRIDES}