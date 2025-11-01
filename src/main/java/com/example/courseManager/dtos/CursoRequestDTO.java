package com.example.courseManager.dtos;

import java.util.List;

public class CursoRequestDTO {

    private String nome;
    private String descricao;
    private Integer cargaHoraria;

    private List<AlunoRequestDTO> alunos;

    public CursoRequestDTO() {
    }

    public CursoRequestDTO(String nome, String descricao, Integer cargaHoraria, List<AlunoRequestDTO> alunos) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.alunos = alunos;
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

    public List<AlunoRequestDTO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoRequestDTO> alunos) {
        this.alunos = alunos;
    }
}


