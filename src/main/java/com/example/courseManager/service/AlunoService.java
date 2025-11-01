package com.example.courseManager.service;

import com.example.courseManager.models.Aluno;
import com.example.courseManager.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    //Instanciando a interface
    @Autowired
    private AlunoRepository alunoRepository;


    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }


    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno update(Long id, Aluno aluno) {
        // Localizando o aluno
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        // Altera os atributos do aluno com set + get
        alunoExistente.setNome(aluno.getNome());
        alunoExistente.setCpf(aluno.getCpf());
        alunoExistente.setEmail(aluno.getEmail());
        alunoExistente.setMatricula(aluno.getMatricula());

        return alunoRepository.save(alunoExistente);
    }


    public void deleteById(Long id) {
        // Localizando o aluno
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        alunoRepository.deleteById(id);
    }
}

