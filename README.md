![Thumbnail GitHub](img/Mobile-Mobflix.png)


# Mobflix

O Mobflix é um app desenvolvido para o Alura Challenges - Mobile 1º Edição. Ele realiza o CRUD de vídeos do Youtube e salva os dados no armazenamento interno do dispositivo.

## 🔨 Funcionalidades do projeto
O App permite cadastrar vídeos do Youtube nas categorias: 
- Front End; 
- Programação; 
- Mobile; 
- Data Science; 
- DevOps; 
- UX e Design.

Na página inicial ele lista os vídeos, exibindo a imagem de capa e a categoria de cada vídeo. Ao clicar em um vídeo o usuário é encaminhado para o respectivo vídeo no aplicativo do Youtube. Os vídeos são armazenados em um banco de dados local para que o usuário não perca sua lista de vídeos.

<p float="left" align="center">
  <img alt="homescreen" width="20%" src="img/homescreen.gif"/>
  <img alt="video form" width="20%" src="img/videoform.gif"/>
  <img alt="update and delete" width="20%" src="img/remove.gif"/>
  <img alt="youtube intent" width="20%" src="img/youtube.gif"/>
  
## ✔️ Técnicas e tecnologias utilizadas

As técnicas e tecnologias utilizadas pra isso são:

- `MVVM`: padrão de arquitetura do projeto
- `Jetpack Compose`: framework para criação de interfaces
- `Navigation Components`: framework que simplifica a navegação entre telas
- `Coroutines`: solução para execução de código de forma assíncrona
- `Flow`: API para criação de fluxos de dados que podem ser computados de forma assíncrona e reativa.
- `Coil`: API para carregar imagens via requisição HTTP
- `Room`: bibioteca de persistência que facilita a implantação de bancos de dados SQLite
- `Koin`: framework de injeção de dependência

## 🎯 Desafio

O desafio extra do challenge foi criar uma exibição dos itens filtrados por categoria. 

<img alt="category filter" width="30%" src="img/filter.gif"/>

## 📁 Acesso ao projeto

Você pode [acessar o código fonte do projeto](https://github.com/Goesbruno/Mobflix/tree/main) ou [baixá-lo](https://github.com/Goesbruno/Mobflix/archive/refs/heads/main.zip).

## 🛠️ Abrir e rodar o projeto

Após baixar o projeto, você pode abrir com o Android Studio. Para isso, na tela de launcher clique em:

- **Open an Existing Project** (ou alguma opção similar)
- Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo)
- Por fim clique em OK

O Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆 

## 📚 Mais informações do challenge

Quer saber mais sobre o Alura Challenge Mobile? Você pode [acessar o desafio](https://www.alura.com.br/challenges/mobile) que deu origem a esse projeto!
