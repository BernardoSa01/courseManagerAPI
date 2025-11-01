<div align="center">

# 🎓 Course Manager API

API desenvolvida em **Java + Spring Boot** com o objetivo de gerenciar **cursos e alunos**, incluindo o relacionamento entre eles (um curso pode ter vários alunos, e cada aluno pertence a um curso).

</div>


---

## 🚀 Tecnologias Utilizadas

- ☕ **Java 21**
- 🌱 **Spring Boot** (Web, Data JPA, Validation)
- 🧠 **H2 Database** (banco em memória para testes)
- 📦 **Gradle:** Gerenciador de dependências e build automation (automatiza compilação, execução e gerenciamento de dependências do projeto)
- 🔧 **Postman / Insomnia** – Testes das requisições

---

## 🧩 Funcionalidades 

### 📚 Curso
-  Criar curso (`POST /curso/save`)
-  Listar todos os cursos (`GET /curso/all`)
-  Buscar curso por ID (Exibe o curso + Lista de alunos cadastrados) (`GET /curso/{id}`)
-  Atualizar curso (`PUT /curso/{id}`)
-  Excluir curso (`DELETE /curso/{id}`)
-  Exibir lista de alunos vinculados a um curso

### 👨‍🎓 Aluno
-  Criar aluno vinculado a um curso (`POST /aluno/save`)
-  Listar todos os alunos (`GET /aluno/all`)
-  Buscar aluno por ID (`GET /aluno/{id}`)
-  Atualizar aluno (`PUT /aluno/{id}`)
-  Excluir aluno (`DELETE /aluno/{id}`)

---

## 📂 Estrutura do Projeto
- src
    - main
        - java
            - com
                - example
                    - coursemanager
                        - controllers
                        - dtos
                        - mappers
                        - models
                        - repositories
                        - service
                        - CourseManagerApplication.java

---

## ▶️ Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/BernardoSa01/courseManagerAPI.git
   
2. Acesse a pasta do projeto:
    ```bash
   cd courseManagerAPI
3. Execute a aplicação:
    ```bash
   ./gradlew bootRun

4. Acesse o console H2 (opcional para visualizar o banco de dados):
    ```bash
    http://localhost:8080/h2-console
   
---

## ⏳ Implementações futuras

- ✅ **Validações** com Bean Validation (`@NotNull`, `@Email`, `@CPF` etc.)
- ⚙️ **Tratamento de exceções** personalizado (ControllerAdvice + ExceptionHandler)
- 📜 **Documentação Swagger/OpenAPI**
- 🧪 **Testes unitários e de integração**

---

## 🧑‍💻 Autor

**Desenvolvido por Bernardo Sá. Conecte-se comigo!**

- [LinkedIn](https://www.linkedin.com/in/bernardosa01/)




