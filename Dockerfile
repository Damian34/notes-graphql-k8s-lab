FROM openjdk:22-jdk-slim
WORKDIR /app
COPY ./target/notes-graphql-k8s-lab.jar notes-graphql-k8s-lab.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "notes-graphql-k8s-lab.jar"]