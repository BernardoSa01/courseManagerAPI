package com.example.courseManager.dtos;

public class AlunoResponseDTO {

    private long id;
    private String nome;
    private String email;
    private String matricula;

    private Long cursoId;
    private String cursoNome;



    public AlunoResponseDTO() {
    }

    public AlunoResponseDTO(long id, String nome, String email, String matricula, Long cursoId, String cursoNome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.cursoId = cursoId;
        this.cursoNome = cursoNome;
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

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }
}

