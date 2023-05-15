# Docker for Content Vault microserivce 
FROM openjdk:17-oracle
ARG env
# local file storage path
RUN mkdir -p /opt/user-central/logs

COPY src/main/resources/application.${env}.properties /opt/user-central/application.properties
COPY target/user-central-*.jar /opt/user-central/user-central.jar

RUN ln -sf /dev/stdout /opt/user-central/logs/user-central.sys.log
WORKDIR /opt/user-central

CMD ["java", "-jar", "user-central.jar", "--spring.config.additional-location=application.properties"]

EXPOSE 8080
