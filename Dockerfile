# Use uma imagem base do OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Instale o Maven
RUN apk add --no-cache maven

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo de configuração do Maven
COPY pom.xml .

# Copie o código fonte da aplicação
COPY src ./src

# Compile a aplicação e gere o arquivo JAR
RUN mvn clean package

# Crie um usuário não privilegiado
RUN adduser -D appuser

# Altere a propriedade do JAR para o usuário appuser
RUN chown appuser:appuser target/Playmix-0.0.1-SNAPSHOT.jar

# Altere para o usuário não privilegiado
USER appuser

# Exponha a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/Playmix-0.0.1-SNAPSHOT.jar"]
