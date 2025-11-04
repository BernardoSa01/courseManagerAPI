package com.example.courseManager.controllers;

import com.example.courseManager.dtos.AlunoRequestDTO;
import com.example.courseManager.dtos.AlunoResponseDTO;
import com.example.courseManager.exceptions.EntityNotFoundException;
import com.example.courseManager.mappers.AlunoMapper;
import com.example.courseManager.models.Aluno;
import com.example.courseManager.models.Curso;
import com.example.courseManager.repositories.CursoRepository;
import com.example.courseManager.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private AlunoMapper alunoMapper;

    @MockBean
    private CursoRepository cursoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AlunoRequestDTO requestDTO;
    private AlunoResponseDTO responseDTO;
    private Aluno aluno;
    private Curso curso;

    @BeforeEach
    void setup() {
        // Curso base
        curso = new Curso();
        curso.setId(1L);
        curso.setNome("Java Avançado");
        curso.setDescricao("Curso completo de Java");
        curso.setCargaHoraria(80);

        // DTO de entrada
        requestDTO = new AlunoRequestDTO();
        requestDTO.setNome("Bernardo Sá");
        requestDTO.setCpf("12345678900");
        requestDTO.setEmail("bernardo@example.com");
        requestDTO.setMatricula("2025001");
        requestDTO.setCursoId(1L);

        // Entidade e resposta
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Bernardo Sá");
        aluno.setCpf("12345678900");
        aluno.setEmail("bernardo@example.com");
        aluno.setMatricula("2025001");
        aluno.setCurso(curso);

        responseDTO = new AlunoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Bernardo Sá");
        responseDTO.setEmail("bernardo@example.com");
        responseDTO.setMatricula("2025001");

        // Mocks globais
        Mockito.when(alunoMapper.toEntity(any(AlunoRequestDTO.class))).thenReturn(aluno);
        Mockito.when(alunoMapper.toDto(any(Aluno.class))).thenReturn(responseDTO);
        Mockito.when(alunoService.save(any(Aluno.class), eq(1L))).thenReturn(aluno);
    }

    @Test
    void deveCriarAlunoComSucesso() throws Exception {
        mockMvc.perform(post("/alunos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Bernardo Sá"))
                .andExpect(jsonPath("$.email").value("bernardo@example.com"))
                .andExpect(jsonPath("$.matricula").value("2025001"));
    }

    @Test
    void deveBuscarAlunoPorId() throws Exception {
        Mockito.when(alunoService.findById(1L)).thenReturn(aluno);

        mockMvc.perform(get("/alunos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Bernardo Sá"));
    }

    @Test
    void deveRetornarNotFoundQuandoAlunoNaoExiste() throws Exception {
        Mockito.when(alunoService.findById(99L))
                .thenThrow(new EntityNotFoundException("Aluno não encontrado"));

        mockMvc.perform(get("/alunos/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarTodosOsAlunos() throws Exception {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Bernardo Sá");
        aluno1.setEmail("bernardo@example.com");
        aluno1.setMatricula("2025001");
        aluno1.setCurso(curso);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Ana Silva");
        aluno2.setEmail("ana@example.com");
        aluno2.setMatricula("2025002");
        aluno2.setCurso(curso);

        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);

        Mockito.when(alunoService.findAll()).thenReturn(alunos);
        Mockito.when(alunoMapper.toDto(any(Aluno.class)))
                .thenAnswer(invocation -> {
                    Aluno a = invocation.getArgument(0);
                    AlunoResponseDTO dto = new AlunoResponseDTO();
                    dto.setId(a.getId());
                    dto.setNome(a.getNome());
                    dto.setEmail(a.getEmail());
                    dto.setMatricula(a.getMatricula());
                    return dto;
                });

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Bernardo Sá"))
                .andExpect(jsonPath("$[1].nome").value("Ana Silva"));
    }

    @Test
    void deveAtualizarAluno() throws Exception {
        AlunoRequestDTO dto = new AlunoRequestDTO();
        dto.setNome("Bernardo Atualizado");
        dto.setCpf("12345678900");
        dto.setEmail("bernardo.novo@example.com");
        dto.setMatricula("2025009");
        dto.setCursoId(1L);

        Aluno atualizado = new Aluno();
        atualizado.setId(1L);
        atualizado.setNome("Bernardo Atualizado");
        atualizado.setCpf("12345678900");
        atualizado.setEmail("bernardo.novo@example.com");
        atualizado.setMatricula("2025009");
        atualizado.setCurso(curso);

        AlunoResponseDTO atualizadoResponse = new AlunoResponseDTO();
        atualizadoResponse.setId(1L);
        atualizadoResponse.setNome("Bernardo Atualizado");
        atualizadoResponse.setEmail("bernardo.novo@example.com");
        atualizadoResponse.setMatricula("2025009");

        Mockito.when(alunoMapper.toEntity(any(AlunoRequestDTO.class))).thenReturn(atualizado);
        Mockito.when(alunoMapper.toDto(any(Aluno.class))).thenReturn(atualizadoResponse);
        Mockito.when(alunoService.update(eq(1L), any(Aluno.class), eq(1L))).thenReturn(atualizado);

        mockMvc.perform(put("/alunos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bernardo Atualizado"))
                .andExpect(jsonPath("$.email").value("bernardo.novo@example.com"))
                .andExpect(jsonPath("$.matricula").value("2025009"));
    }

    @Test
    void deveExcluirAluno() throws Exception {
        mockMvc.perform(delete("/alunos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
