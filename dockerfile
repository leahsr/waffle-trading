FROM sbtscala/scala-sbt:eclipse-temurin-23.0.2_7_1.10.11_3.7.0 AS builder

WORKDIR /waffle-build

COPY build.sbt .
COPY project/ ./project/
COPY src/ ./src/

RUN sbt clean assembly

FROM eclipse-temurin:23.0.2_7-jre

WORKDIR /waffle

COPY --from=builder /waffle-build/target/scala-3.7.0/waffle-trading-assembly-*.jar waffle.jar
EXPOSE 8080
CMD ["java", "-jar", "waffle.jar"]
