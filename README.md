# API de Cadastro de Alunos com Spring Security

Esta API foi desenvolvida para gerenciar o cadastro e autenticação de alunos, AQVs e coordenadores, utilizando Spring Security para garantir a segurança das informações. O sistema permite a diferenciação de permissões entre os usuários, garantindo um controle de acesso eficiente.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security (com suporte a JWT)
- Maven
- Hibernate/JPA para persistência de dados
- PostgreSQL para armazenamento de informações dos usuários
- Auth0 para geração de tokens JWT
- BCrypt para criptografia de senhas
### Estrutura de Permissões
- USER: Role padrão atribuída aos alunos, com acesso restrito.
- ADMIN: Role atribuída aos AQVs e coordenadores, permitindo acesso a funcionalidades administrativas.

## Rotas da API
### Autenticação (Auth Controller)
#### Endpoint principal = /auth

| Método | Endpoint | Descrição | Corpo da requisição 
|--------| -------- |-----------|---------------------|
| POST   | /login | Realiza login e retorna o token JWT. | { "email": "user@mail.com", "password": "senha" } |
| POST | /register | Cria um novo usuário na API. |{ "email": "user@mail.com", "password": "senha" } |

### Cadastro e Gerenciamento de Alunos
#### Endpoint principal = /aluno

| Método | Endpoint | Descrição | Corpo da requisição 
| ------ | -------- | --------- |---------------------|
| GET | / |Retorna todos os alunos cadastrados. | .                   |
| GET | /cpf/{cpf} | Retorna um aluno específico pelo CPF. | cpf                 |
| GET | /nome/{nome} | Retorna alunos pelo nome. | nome                |
| GET | /matricula/{matricula} | Retorna um aluno pela matrícula. | matricula |
| POST | /create | Cria um novo aluno. | { "cpf": "...", "nome": "...", "email": "...", "senha": "...", "telefone": "...", "matricula": "..." }
| PUT | /update/{cpf} | Atualiza informações de um aluno existente. | { "nome": "...", "email": "...", "senha": "...", "telefone": "..." }
| DELETE | /cpf/{cpf} | Deleta um aluno pelo CPF (somente administradores). | cpf

### Configuração e Execução

#### Clone este repositório:

```
git clone https://github.com/EricSouzaDosSantos/API-auth0.git
cd API-auth0.git
```
### Configure as variáveis de ambiente no application.properties:

```
spring.datasource.url=${URL_POSTGRES}
spring.datasource.username=${USERNAME_POSTGRES}
spring.datasource.password=${PASSWORD_POSTGRES}
spring.jpa.hibernate.ddl-auto=update
api.security.token.secret=${JWT_SECRET}
```

### Execute a aplicação:

```
mvn spring-boot:run
```

Ou utilize a opção de "Run" na sua IDE.

### Testando a API

Recomenda-se o uso do Postman ou Insomnia para testar os endpoints.

## Contribuição

Contribuições são bem-vindas! Caso encontre bugs ou queira sugerir melhorias, fique à vontade para abrir uma issue ou enviar um pull request.