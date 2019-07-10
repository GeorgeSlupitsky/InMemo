FROM openjdk:12
ADD target/InMemo.jar InMemo.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "InMemo.jar"]
