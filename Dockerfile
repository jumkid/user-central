# Docker for Content Vault microserivce 
FROM openjdk:17-oracle
ARG env
# local file storage path
RUN mkdir -p /opt/user-central/log
COPY src/main/resources/application.${env}.properties /opt/user-central/application.properties
COPY target/user-central-*.jar /opt/user-central/user-central.jar
RUN ln -sf /dev/stdout /opt/user-central/log/user-central.sys.log

CMD ["java", "-jar", "/opt/user-central/user-central.jar", "--spring.config.additional-location=/opt/user-central/application.properties"]

EXPOSE 8080
