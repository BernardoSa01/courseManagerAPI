package com.example.courseManager.mappers;

import com.example.courseManager.dtos.AlunoRequestDTO;
import com.example.courseManager.dtos.AlunoResponseDTO;
import com.example.courseManager.models.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequestDTO dto) {
        if (dto == null) return null;

        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setCpf(dto.getCpf());
        aluno.setEmail(dto.getEmail());
        aluno.setMatricula(dto.getMatricula());

        return aluno;
    }

    public AlunoResponseDTO toDto(Aluno entity) {
        if (entity == null) return null;

        AlunoResponseDTO alunoDto = new AlunoResponseDTO();
        alunoDto.setId(entity.getId());
        alunoDto.setNome(entity.getNome());
        alunoDto.setEmail(entity.getEmail());
        alunoDto.setMatricula(entity.getMatricula());

        if (entity.getCurso() != null) {
            alunoDto.setCursoId(entity.getCurso().getId());
            alunoDto.setCursoNome(entity.getCurso().getNome());
        }

        return alunoDto;

    }
}

