FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN apk update && apk add --no-cache curl

COPY build/libs/pedido.jar mikes-pedido.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mikes-pedido.jar"]