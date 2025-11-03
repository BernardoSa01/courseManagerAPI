package com.example.courseManager.service;

import com.example.courseManager.models.Curso;
import com.example.courseManager.repositories.CursoRepository;
import com.example.courseManager.exceptions.EntityNotFoundException;
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

    public Curso findById(Long id) {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }

    public List<Curso> findAll() {
        List<Curso> cursos = cursoRepository.findAll();

        if (cursos.isEmpty()) {
            throw new EntityNotFoundException("Nenhum curso encontrado no sistema");
        }

        return cursos;
    }

    public Curso update(Long id, Curso curso) {
        // Encontrando o curso pelo id
        Curso cursoExistente = findById(id);

        // Alterando os atributos com set + get
        cursoExistente.setNome(curso.getNome());
        cursoExistente.setDescricao(curso.getDescricao());
        cursoExistente.setCargaHoraria(curso.getCargaHoraria());

        // Salva as atualizações no banco
        return cursoRepository.save(cursoExistente);
    }

    public void delete(Long id) {
        Curso curso = findById(id);

        cursoRepository.delete(curso);
    }

}

