FROM maven:3.6.3-jdk-8-openj9
WORKDIR /app
COPY . /app/
# COPY ./wait-for-it.sh /app/
## Add the wait script to the image
# ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
# RUN chmod +x /wait
# RUN mvn clean && mvn install
# COPY ./ApiUsuarios/* /app
EXPOSE 9200
RUN mvn package
ENTRYPOINT java -jar target/openInfoSystem-0.0.1-SNAPSHOT.jar