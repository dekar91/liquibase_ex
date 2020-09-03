FROM docker-internal.tcsbank.ru/oracle/jdk:11.0.7-release

ARG APP_HOME=/app

WORKDIR $APP_HOME

COPY admin-rest/target/admin-rest.jar $APP_HOME/admin-rest.jar

ENTRYPOINT java $JAVA_OPTS -jar admin-rest.jar $JAVA_ARGS