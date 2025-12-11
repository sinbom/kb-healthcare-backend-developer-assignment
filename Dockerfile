FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace
COPY build/libs/kb-healthcare-application.jar application.jar
RUN mkdir extracted && (java -Djarmode=layertools -jar application.jar extract --destination extracted)

FROM eclipse-temurin:17-jdk-alpine
COPY --from=builder /workspace/extracted/dependencies/ ./
COPY --from=builder /workspace/extracted/spring-boot-loader/ ./
COPY --from=builder /workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder /workspace/extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]