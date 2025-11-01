package com.example.courseManager.service;

import com.example.courseManager.models.Curso;
import com.example.courseManager.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    // Instanciando a interface
    @Autowired
    private CursoRepository cursoRepository;


    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso update(Long id, Curso curso) {
        // Encontrando o curso pelo id
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));

        // Alterando os atributos com set + get
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setDescricao(curso.getDescricao());
        cursoExistente.setCargaHoraria(curso.getCargaHoraria());

        // Salva as atualizações no banco
        return cursoRepository.save(cursoExistente);
    }

    public void deleteById(Long id) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado!"));

        cursoRepository.deleteById(id);
    }

}

