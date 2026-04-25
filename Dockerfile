# Étape 1 : Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Optimisation : On copie le pom.xml et on télécharge les dépendances d'abord
# Cela permet d'utiliser le cache Docker si tu ne changes pas tes dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Correction ici : Utilise un nom spécifique si possible, ou assure-toi qu'un seul JAR existe
COPY --from=build /app/target/*.jar app.jar

# Render utilise souvent des ports dynamiques, on expose 8080 par défaut
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]