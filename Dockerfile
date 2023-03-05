From openjdk:19
copy ./target/chatAppBackend-0.0.1-SNAPSHOT.jar chatAppBackend-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","chatAppBackend-0.0.1-SNAPSHOT.jar"]
