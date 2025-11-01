package com.example.courseManager.controllers;

import com.example.courseManager.dtos.AlunoRequestDTO;
import com.example.courseManager.dtos.AlunoResponseDTO;
import com.example.courseManager.mappers.AlunoMapper;
import com.example.courseManager.models.Aluno;
import com.example.courseManager.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoMapper alunoMapper;

    @PostMapping("/save")
    public ResponseEntity<AlunoResponseDTO> create(@RequestBody AlunoRequestDTO dto) {

        // Convertendo de dto para entidade
        Aluno aluno = alunoMapper.toEntity(dto);

        Aluno alunoCriado = alunoService.save(aluno);

        // Convertendo a entidade persistida em DTO de resposta
        AlunoResponseDTO response = alunoMapper.toDto(alunoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> findAlunoById(@PathVariable Long id) {

        Optional<Aluno> aluno = alunoService.findById(id);

        if (aluno.isPresent()) {
            AlunoResponseDTO alunoDto = alunoMapper.toDto(aluno.get());
            return ResponseEntity.status(HttpStatus.OK).body(alunoDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> findAll() {

        List<Aluno> alunos = alunoService.findAll();

        List<AlunoResponseDTO> alunosDto = new ArrayList<>();

        for (Aluno aluno : alunos) {
            AlunoResponseDTO alunoDto = alunoMapper.toDto(aluno);
            alunosDto.add(alunoDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(alunosDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> updateAluno(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) { // @PathVariable para localizar o aluno + @RequestBody para alterar o body

        Aluno aluno = alunoMapper.toEntity(dto);

        Aluno alunoAtualizado = alunoService.update(id, aluno);

        AlunoResponseDTO response = alunoMapper.toDto(alunoAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}