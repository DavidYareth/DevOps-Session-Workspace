FROM openjdk:11-jre
ENV APP_FILE app.jar
ENV APP_HOME /app
RUN mkdir $APP_HOME
EXPOSE 8080
COPY ./app/build/libs/$APP_FILE /app/$APP_FILE
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
