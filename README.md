# challenge-conversor-de-moedas
Projeto desenvolvido no desafio Alura + Oracle One para praticar lógica de programação em Java.

### O que o projeto faz?
Este programa é um conversor de moedas em Java que utiliza a API ExchangeRate API para buscar taxas de câmbio em tempo real.
Ele permite converter valores entre:
- USD → BRL
- BRL → USD
- USD → EUR
- EUR → USD
O usuário interage pelo terminal, escolhendo a opção desejada e digitando o valor a ser convertido.

### Como rodar o projeto
**1.** Pré-requisitos
- Java 17+ instalado
- Maven instalado
- Uma API Key válida da ExchangeRate API

**2.** Configuração
No arquivo Main.java, substitua:
Client api = new Client("DIGITE_SUA_API_KEY_AQUI");
pela sua chave real da API.

**3.** Certifique-se de que o projeto está organizado assim:
ConversorMoeda tem um pom.xml e dentro de src/main/java está o Main.java.

**4.** No terminal, dentro da pasta do projeto:
mvn clean compile
mvn exec:java

### Observações
- Se a chave da API estiver incorreta ou expirada, o programa exibirá erro 401 Unauthorized.
- Você pode adicionar mais moedas editando o menu e o switch no código.
- Utilizei o Visual Studio Code como ambiente de desenvolvimento. 
