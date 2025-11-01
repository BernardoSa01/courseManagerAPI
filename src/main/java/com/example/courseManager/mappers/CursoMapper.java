package com.example.courseManager.mappers;

import com.example.courseManager.dtos.AlunoRequestDTO;
import com.example.courseManager.dtos.AlunoResponseDTO;
import com.example.courseManager.dtos.CursoRequestDTO;
import com.example.courseManager.dtos.CursoResponseDTO;
import com.example.courseManager.models.Aluno;
import com.example.courseManager.models.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CursoMapper {

    @Autowired
    private AlunoMapper alunoMapper;


    public Curso toEntity(CursoRequestDTO dto) {

        if (dto == null) return null;

        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        curso.setCargaHoraria(dto.getCargaHoraria());

        // Lista vazia para armazenar os alunos convertidos
        List<Aluno> alunos = new ArrayList<>();

        if (dto.getAlunos() != null) {

            for (AlunoRequestDTO alunoDTO : dto.getAlunos()) {
                // Converte cada dto em entidade
                Aluno aluno = alunoMapper.toEntity(alunoDTO);

                // Relaciona o aluno ao curso
                aluno.setCurso(curso);

                // Adiciona o aluno convertido à lista
                alunos.add(aluno);
            }
        }

        // Associa a lista de alunos ao curso
        curso.setAlunos(alunos);

        return curso;
    }

    public CursoResponseDTO toDto(Curso entity) {

        if (entity == null) return null;

        CursoResponseDTO cursoDto = new CursoResponseDTO();
        cursoDto.setId(entity.getId());
        cursoDto.setNome(entity.getNome());
        cursoDto.setDescricao(entity.getDescricao());
        cursoDto.setCargaHoraria(entity.getCargaHoraria());

        List<AlunoResponseDTO> alunosDto = new ArrayList<>();


        if (entity.getAlunos() != null) {

            for (Aluno alunoEntity: entity.getAlunos()) {
                AlunoResponseDTO aluno = alunoMapper.toDto(alunoEntity);

                alunosDto.add(aluno);
            }

            // Relaciona a lista de alunos ao curso
            cursoDto.setAlunos(alunosDto);
        }

        return cursoDto;
    }
}

