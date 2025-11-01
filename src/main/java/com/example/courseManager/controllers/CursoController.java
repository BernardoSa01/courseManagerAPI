package com.example.courseManager.controllers;

import com.example.courseManager.dtos.CursoRequestDTO;
import com.example.courseManager.dtos.CursoResponseDTO;
import com.example.courseManager.mappers.CursoMapper;
import com.example.courseManager.models.Curso;
import com.example.courseManager.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoMapper cursoMapper;

    @PostMapping("/save")
    public ResponseEntity<CursoResponseDTO> create(@RequestBody CursoRequestDTO dto) {

        Curso curso = cursoMapper.toEntity(dto);

        Curso cursoCriado = cursoService.save(curso);

        CursoResponseDTO response = cursoMapper.toDto(cursoCriado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findCursoById(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.findById(id);

        if (curso.isPresent()) {
            CursoResponseDTO cursoDto = cursoMapper.toDto(curso.get());
            return ResponseEntity.status(HttpStatus.OK).body(cursoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> findAll() {
        List<Curso> cursos = cursoService.findAll();

        // Lista vazia para receber dtos
        List<CursoResponseDTO> cursosDto = new ArrayList<>();

        for (Curso curso : cursos) {
            CursoResponseDTO cursoDto = cursoMapper.toDto(curso);
            cursosDto.add(cursoDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(cursosDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> updateCurso(@PathVariable Long id, @RequestBody CursoRequestDTO curso) {
        Curso cursoEntity = cursoMapper.toEntity(curso);

        Curso cursoAtualizado = cursoService.update(id, cursoEntity);

        CursoResponseDTO response = cursoMapper.toDto(cursoAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

