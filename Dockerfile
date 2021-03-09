
FROM openjdk:11-jre-slim
RUN mkdir -p /usr/local/product
ENV JAVA_OPTS=$JAVA_OPTS
COPY /build/libs/product-0.0.1-SNAPSHOT.jar /usr/local/product/product.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /usr/local/product/product.jar