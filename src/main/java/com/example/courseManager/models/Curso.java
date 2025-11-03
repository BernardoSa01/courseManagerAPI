package com.example.courseManager.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity // Define a classe como uma entidade gerenciável, permitindo ao JPA mapeá-la para uma tabela no H2
public class Curso {

    @Id // Garante que o Id será uma chave primária no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera um id automático a partir de uma requisição POST
    private long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;

    @OneToMany(mappedBy = "curso")
    List<Aluno> alunos = new ArrayList<>();


    public Curso(long id, String nome, String descricao, Integer cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
    }

    // Construtor personalizado para testes unitários
    public Curso(String nome, String descricao, Integer cargaHoraria) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
    }

    public Curso() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}

