FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/pedido.jar mikes-pedido.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mikes-pedido.jar"]