package com.example.courseManager.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CursoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Carga horária é obrigatória")
    @Min(value = 80, message = "A carga horária mínima é de 80 horas")
    private Integer cargaHoraria;

    @Valid
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


