


FROM eclipse-temurin:21-jdk as build
ARG RAILWAY_DEPLOYMENT_ID
ARG RAILWAY_GIT_COMMIT_SHA

RUN echo "RAILWAY_GIT_COMMIT_SHA: $RAILWAY_GIT_COMMIT_SHA"
RUN echo "RAILWAY_DEPLOYMENT_ID: $RAILWAY_DEPLOYMENT_ID"
COPY . /app
WORKDIR /app
RUN ./mvnw --no-transfer-progress clean package -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre
ARG PORT
ENV PORT=${PORT}
COPY --from=build /app/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]