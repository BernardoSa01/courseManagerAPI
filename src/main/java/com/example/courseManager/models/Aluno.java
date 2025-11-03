package com.example.courseManager.models;

import jakarta.persistence.*;

@Entity // Define a classe como uma entidade gerenciável, permitindo ao JPA mapeá-la para uma tabela no H2
public class Aluno {

    @Id // Garante que o Id será uma chave primária no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera um id automático a partir de uma requisição POST
    private long id;
    private String nome;
    private String cpf;
    private String email;
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "curso_id") // Definindo o nome da foreign key no banco de dados
    private Curso curso;

    public Aluno(long id, String nome, String cpf, String email, String matricula, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.matricula = matricula;
        this.curso = curso;
    }

    // Construtor personalizado para testes unitários
    public Aluno(Long id, String nome, String cpf, String email, String matricula) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.matricula = matricula;
    }


    public Aluno() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}

