# 🎧 Playmix 

**Playmix** é uma aplicação de streaming de música que permite aos usuários criar e gerenciar suas próprias playlists de maneira simples e intuitiva. Com uma interface amigável, você pode descobrir novas músicas, explorar diferentes gêneros e personalizar suas coleções musicais de acordo com suas preferências.


## 📚 Estrutura do Banco de Dados

A aplicação utiliza um modelo de banco de dados relacional para gerenciar as informações das músicas, playlists e suas interações. Abaixo está o diagrama de Entidade e Relacionamento com as tabelas principais da estrutura do banco de dados:

```mermaid
erDiagram
    MUSIC {
        Long id PK "Identificador único"
        String titulo "Título da música"
        String artista "Nome do artista"
        int ano_lancamento "Ano de lançamento"
        String album "Nome do álbum"
        String genero "Gênero da música (varchar, com restrição de valores)"
        String album_art_url "URL da capa do álbum"
    }

    PLAYLIST {
        Long id PK "Identificador único"
        String nome "Nome da playlist"
        Date data_criacao "Data de criação"
    }

    PLAYLIST_MUSIC {
        Long music_id FK "Referência à música"
        Long playlist_id FK "Referência à playlist"
    }

    MUSIC ||--o{ PLAYLIST_MUSIC : contains
    PLAYLIST ||--o{ PLAYLIST_MUSIC : includes

```
---
## 🛤️ Rotas da Aplicação

### 🏠 Home (`index.html`)

- **Página Inicial**: `GET /`  
  A página inicial do Playmix, com opções para gerenciar músicas e playlists.   

> [!NOTE]
> **🔗 Acesse a página inicial através da URL:**
> ```plaintext
> http://playmixrm552258.eastus.azurecontainer.io:8080
> ```

### 🎵 Músicas (`MusicController`)
- **Listar**: `GET /musics`  - Exibe todas as músicas.  
- **Detalhes**: `GET /musics/{id}`  - Detalhes de uma música.  
- **Adicionar**: `GET /musics/new`  - Formulário para nova música.  
- **Salvar**: `POST /musics`  - Cria uma nova música. 🎤  
- **Editar**: `GET /musics/edit/{id}`  - Formulário para editar música. ✏️  
- **Atualizar**: `POST /musics/{id}`  - Atualiza uma música existente. 🔄  
- **Excluir**: `GET /musics/delete/{id}`  - Remove uma música. ❌  

### 🎶 Gerenciamento de Playlists (`PlaylistController`) 
- **Listar**: `GET /playlists`  - Exibe todas as playlists.  
- **Detalhes**: `GET /playlists/{id}`  - Detalhes de uma playlist. 📜  
- **Adicionar**: `GET /playlists/new`  - Formulário para nova playlist. ➕  
- **Salvar**: `POST /playlists`  - Cria uma nova playlist. 🎼  
- **Editar**: `GET /playlists/edit/{id}`  - Formulário para editar playlist. ✏️  
- **Atualizar**: `POST /playlists/{id}`  - Atualiza uma playlist existente. 🔄  
- **Excluir**: `GET /playlists/delete/{id}`  - Remove uma playlist. ❌  
---

## CRUD - Exemplos 📋

### 1. Criar 🎤

#### *Música*

Para criar uma nova música, você deve enviar um formulário com as seguintes informações:

```plaintext
Título: Baby
Artista: Justin Bieber
Ano de Lançamento: 2010
Álbum: My World 2.0
Gênero: POP
URL da Capa do Álbum: https://abrir.link/jtJPk
```

#### *Playlist*

Para criar uma nova playlist, você pode usar o seguinte formulário:

```plaintext
Nome: Nostalgia Playlist
(Selecione as músicas )
```


### 2. Ler 📖

#### *Música*

Para ler os detalhes de uma música específica, aperte em 'Detalhes'.


#### *Playlist*

Para ler os detalhes de uma playlist específica, aperte em 'Detalhes'.


### 3. Atualizar ✏️

#### *Música*

Para atualizar as informações de uma música existente, você pode enviar o seguinte formulário:

```plaintext
Título: Baby Music
Artista: Justin
Ano de Lançamento: 2011
Álbum: My World 2.0.0
Gênero: SOUL
URL da Capa do Álbum: https://abrir.link/jtJPk
```

#### *Playlist*

Para atualizar uma playlist existente, você pode usar o seguinte formulário:

```plaintext
Nome: Pop Nostalgia Favoritas
```

### 4. Excluir 🗑️

#### *Música*

Para excluir uma música, aperte em 'Excluir'.

#### *Playlist*

Para excluir uma playlist, aperte em 'Excluir'.


---

## 🐳 Dockerfile e Rodando Localmente

Para rodar a aplicação localmente usando Docker, você pode usar o seguinte `Dockerfile`:

```Dockerfile
# Use a imagem base do OpenJDK 17 com suporte ao Alpine Linux
FROM eclipse-temurin:17-jdk-alpine

# Instale o Maven para gerenciar dependências e compilar o projeto
RUN apk add --no-cache maven

# Defina o diretório de trabalho para a aplicação
WORKDIR /app

# Copie o arquivo de configuração do Maven para o diretório de trabalho
COPY pom.xml .

# Copie o código-fonte da aplicação para o diretório de trabalho
COPY src ./src

# Compile a aplicação com o Maven e gere o arquivo JAR
RUN mvn clean package

# Crie um usuário não privilegiado para executar a aplicação com segurança
RUN adduser -D appuser

# Altere a propriedade do arquivo JAR gerado para o usuário não privilegiado
RUN chown appuser:appuser target/Playmix-0.0.1-SNAPSHOT.jar

# Altere para o usuário não privilegiado para evitar execução como root
USER appuser

# Exponha a porta 8080 para acesso à aplicação
EXPOSE 8080

# Defina o comando de entrada para iniciar a aplicação usando o JAR gerado
CMD ["java", "-jar", "target/Playmix-0.0.1-SNAPSHOT.jar"]
```

### Rodando a Aplicação

Para construir e rodar a imagem Docker, utilize os seguintes comandos:

- Construir a imagem Docker
```bash
docker build -t playmix-app .
```
- Rodar a imagem localmente
```bash
docker run --name playmix-app-container -d -p 8080:8080 playmix-app
```

- **Acessando a aplicação**: Abra `http://localhost:8080` em seu navegador.
  
- Para parar e remover o contêiner quando não for mais necessário:

```bash
docker rm playmix-app-container -f
```

- Para remover a imagem Docker após o uso:

```bash
docker rmi playmix-app
```

---

## 🚀 Deploy - ACI e ACR Azure

Para realizar o deploy da aplicação no Azure, você pode utilizar o **Azure Container Registry (ACR)** e o **Azure Container Instances (ACI)**. Siga os passos abaixo para completar o processo:

### 🛠️ Pré-requisitos

1. **Conta Azure**: Certifique-se de ter uma conta no [Azure](https://azure.microsoft.com/). 🌐
2. **Azure CLI**: Instale a [Azure CLI](https://docs.microsoft.com/pt-br/cli/azure/install-azure-cli) na sua máquina. 💻
3. **Docker**: Tenha o Docker instalado e em execução na sua máquina. 🐳

### 📋 Passo a Passo

#### 1. Criar Grupo de Recursos 🌳

```bash
az group create --name rg-playmix --location eastus
```
- **Análise**: Este comando cria um grupo de recursos chamado `rg-playmix` na região `eastus`.

#### 2. Criar Azure Container Registry (ACR) 🗄️

```bash
az acr create --resource-group rg-playmix --name playmixapprm552258 --sku Basic
```
- **Análise**: Cria um registro de contêiner no Azure. Verifique se o nome é único.

#### 3. Login no ACR 🔑

```bash
az acr login --name playmixapprm552258
```
- **Análise**: Realiza o login no ACR.

#### 4. Tag e Push da Imagem Docker 📦
- Tag da imagem: 
```bash
docker tag playmix-app playmixapprm552258.azurecr.io/playmixapprm552258:v1
```

- Push da imagem para o ACR:
```bash
docker push playmixapprm552258.azurecr.io/playmixapprm552258:v1
```
- **Análise**: Tagueia a imagem local com o repositório ACR e realiza o push.

#### 5. Habilitar Credenciais Administrativas 🔑

```bash
az acr update -n playmixapprm552258 --admin-enabled true
```
- **Análise**: Habilita o acesso administrativo.

```bash
az acr credential show -n playmixapprm552258
```
- **Análise**: Mostra as credenciais para acessar o ACR.

#### 6. Criar Azure Container Instance (ACI) 🏗️

```bash
az container create --resource-group rg-playmix --name playmixrm552258 --image playmixapprm552258.azurecr.io/playmixapprm552258:v1 --cpu 1 --memory 1 --registry-login-server playmixapprm552258.azurecr.io --registry-username playmixapprm552258 --registry-password <senha_forecida> --ip-address Public --dns-name-label playmixrm552258 --ports 8080
```
- **Análise**: Cria uma instância de contêiner no Azure com as especificações fornecidas. **Segurança**: Não exponha suas senhas em scripts. Verifique a unicidade do `dns-name-label`.

### 7. Limpeza dos Recursos 🚮
Para evitar custos desnecessários e manter seu ambiente Azure organizado, exclua o grupo de recursos usando o seguinte comando:
```bash
az group delete --name rg-playmix --yes --no-wait
```

- **Análise**: Quando você deleta o grupo de recursos `rg-playmix`, todos os recursos dentro dele, incluindo a ACI e o ACR, serão excluídos automaticamente. Isso é útil para garantir que não haja recursos residuais. O parâmetro `--no-wait` permite que o comando seja executado em segundo plano, sem esperar pela conclusão do processo.
---

## 🌐 Imagem da Aplicação Rodando na Nuvem

Aqui está uma captura de tela da aplicação **Playmix** rodando na nuvem:

![Playmix Rodando na Nuvem](https://github.com/user-attachments/assets/3ea6e1ac-feba-4ff7-ad5d-6d01229d37d0)

---

## 📂 Recursos Adicionais

- **Docker Documentation**: [https://docs.docker.com/](https://docs.docker.com/) 📖
- **Azure Container Registry**: [https://docs.microsoft.com/pt-br/azure/container-registry/](https://docs.microsoft.com/pt-br/azure/container-registry/) ☁️
- **Azure Container Instances**: [https://docs.microsoft.com/pt-br/azure/container-instances/](https://docs.microsoft.com/pt-br/azure/container-instances/) 🌍
- **Spring Boot Docker Documentation**: [https://spring.io/guides/gs/spring-boot-docker/](https://spring.io/guides/gs/spring-boot-docker/)

---

## 📄 Documentação Adicional

- **Código Fonte**: [GitHub Repository](https://github.com/laiscrz/playmix-application-mvc) 📁
- **Vídeo de Demonstração**: [YouTube Video](https://www.youtube.com/watch?v=88ot8dEYOY0) 📹

## 🫂 Integrantes

Aqui estão os membros do grupo que participaram durante desenvolvimento deste Checkpoint.

* **RM 552258 - Laís Alves da Silva Cruz**
  - Turma: 2TDSPH

* **RM 552267 - Bianca Leticia Román Caldeira**
  - Turma: 2TDSPH
    
* **RM 552252 – Charlene Aparecida Estevam Mendes Fialho**
  - Turma: 2TDSPH

* **RM 97916 – Fabricio Torres Antonio**
  - Turma: 2TDSPH


<table>
  <tr>
    <td align="center">
      <a href="https://github.com/laiscrz">
        <img src="https://avatars.githubusercontent.com/u/133046134?v=4" width="100px;" alt="Lais Alves's photo on GitHub"/><br>
        <sub>
          <b>Lais Alves</b>
        </sub>
      </a>
    </td>
        <td align="center">
      <a href="https://github.com/biancaroman">
        <img src="https://avatars.githubusercontent.com/u/128830935?v=4" width="100px;" border-radius='50%' alt="Bianca Román's photo on GitHub"/><br>
        <sub>
          <b>Bianca Román</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/charlenefialho">
        <img src="https://avatars.githubusercontent.com/u/94643076?v=4" width="100px;" border-radius='50%' alt="Charlene Aparecida's photo on GitHub"/><br>
        <sub>
          <b>Charlene Aparecida</b>
        </sub>
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/Fabs0602">
        <img src="https://avatars.githubusercontent.com/u/111320639?v=4" width="100px;" border-radius='50%' alt="Fabricio Torres's photo on GitHub"/><br>
        <sub>
          <b>Fabricio Torres</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

