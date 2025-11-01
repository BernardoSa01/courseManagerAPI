package com.example.courseManager.dtos;

import java.util.List;

public class CursoResponseDTO {

    private long id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;

    private List<AlunoResponseDTO> alunos;


    public CursoResponseDTO() {
    }

    public CursoResponseDTO(long id, String nome, String descricao, Integer cargaHoraria, List<AlunoResponseDTO> alunos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.alunos = alunos;
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

    public List<AlunoResponseDTO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoResponseDTO> alunos) {
        this.alunos = alunos;
    }
}

