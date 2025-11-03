package com.example.courseManager.service;

import com.example.courseManager.models.Aluno;
import com.example.courseManager.models.Curso;
import com.example.courseManager.repositories.AlunoRepository;
import com.example.courseManager.repositories.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    //Injetando as interfaces
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;


    public Aluno save(Aluno aluno, Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        aluno.setCurso(curso);
        return alunoRepository.save(aluno);
    }


    public Aluno findById(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    public List<Aluno> findAll() {
        List<Aluno> alunos = alunoRepository.findAll();
        if (alunos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum aluno encontrado no sistema");
        }
        return alunos;
    }

    public Aluno update(Long id, Aluno aluno, Long cursoId) {
        // Localizando o aluno
        Aluno alunoExistente = findById(id);

        // Localizando o curso
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        // Altera os atributos do aluno com set + get
        alunoExistente.setNome(aluno.getNome());
        alunoExistente.setCpf(aluno.getCpf());
        alunoExistente.setEmail(aluno.getEmail());
        alunoExistente.setMatricula(aluno.getMatricula());
        alunoExistente.setCurso(curso);

        return alunoRepository.save(alunoExistente);
    }


    public void delete(Long id) {
        // Localizando o aluno
        Aluno aluno = findById(id);
        alunoRepository.delete(aluno);
    }
}

