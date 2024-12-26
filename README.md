# Projeto Ponto Eletrônico

## Descrição
Este é um sistema de ponto eletrônico desenvolvido com Spring Boot. Ele permite que usuários registrem e gerenciem jornadas de trabalho, pontos e autenticações.

## Tecnologias Utilizadas

### Linguagens e Versões
- **Java**: 21
- **Maven**: Gerenciador de dependências

### Principais Bibliotecas/Frameworks
- **Spring Boot 3.4.1**: Framework principal
    - **Spring Boot Starter Web**: Para criação de APIs REST
    - **Spring Boot Starter Data JPA**: Para integração com banco de dados
    - **Spring Boot Starter Security**: Para segurança e autenticação
    - **Spring Boot Starter Validation**: Para validações
- **Flyway**: Gerenciamento de migrações de banco de dados
- **Lombok**: Redução de boilerplate no código
- **MySQL Connector**: Conexão com banco de dados MySQL
- **Springdoc OpenAPI**: Documentação da API
- **Java JWT**: Manipulação de tokens JWT

## Estrutura do Projeto

- **Controller**: Controladores responsáveis por expor as APIs REST
- **Service**: Camada de lógica de negócios
- **Repository**: Camada de persistência de dados
- **DTOs**: Objetos de transferência de dados
- **Security**: Configurações e serviços de segurança
- **Exception**: Tratamento de exceções

## Funcionalidades Implementadas
- Registro de ponto eletrônico
- Gestão de jornadas de trabalho
- Autenticação e autorização com JWT
- Validação e tratamento de erros
- Documentação interativa da API com OpenAPI

## Funcionalidades Planejadas
- Relatórios de frequência e produtividade
- Integração com sistemas externos
- Notificações automáticas para atrasos ou ausência de registro
- Interface gráfica para gestão do sistema

## Como Executar o Projeto

### Pré-requisitos
- Java 21 instalado
- Maven instalado
- Banco de dados MySQL configurado

### Passos
1. Clone o repositório do projeto:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
2. Configure o banco de dados no arquivo `application.properties` ou `application.yml`.
3. Execute as migrações de banco de dados com Flyway:
   ```bash
   mvn flyway:migrate
   ```
4. Inicie o servidor Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
5. Acesse a documentação da API em:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

**Sistema de ponto eletrônico**
