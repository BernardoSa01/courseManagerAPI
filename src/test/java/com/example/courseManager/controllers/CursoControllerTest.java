package com.example.courseManager.controllers;

import com.example.courseManager.dtos.CursoRequestDTO;
import com.example.courseManager.dtos.CursoResponseDTO;
import com.example.courseManager.exceptions.EntityNotFoundException;
import com.example.courseManager.mappers.CursoMapper;
import com.example.courseManager.models.Curso;
import com.example.courseManager.service.AlunoService;
import com.example.courseManager.service.CursoService;

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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private CursoMapper cursoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private CursoRequestDTO requestDTO;
    private CursoResponseDTO responseDTO;
    private Curso curso;

    @BeforeEach
    void setup() {
        requestDTO = new CursoRequestDTO();
        requestDTO.setNome("Java Avançado");
        requestDTO.setDescricao("Curso completo de Java com Spring Boot");
        requestDTO.setCargaHoraria(100);

        curso = new Curso();
        curso.setId(1L);
        curso.setNome(requestDTO.getNome());
        curso.setDescricao(requestDTO.getDescricao());
        curso.setCargaHoraria(requestDTO.getCargaHoraria());

        responseDTO = new CursoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Java Avançado");
        responseDTO.setDescricao("Curso completo de Java com Spring Boot");
        responseDTO.setCargaHoraria(100);

        // comportamento dos mocks
        Mockito.when(cursoMapper.toEntity(any(CursoRequestDTO.class))).thenReturn(curso);
        Mockito.when(cursoMapper.toDto(any(Curso.class))).thenReturn(responseDTO);
        Mockito.when(cursoService.save(any(Curso.class))).thenReturn(curso);

    }

    @Test
    void deveCriarCursoComSucesso() throws Exception {
        mockMvc.perform(post("/cursos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Java Avançado"))
                .andExpect(jsonPath("$.descricao").value("Curso completo de Java com Spring Boot"))
                .andExpect(jsonPath("$.cargaHoraria").value(100));
    }

    @Test
    void deveBuscarCursoPorId() throws Exception {
        given(cursoService.findById(1L)).willReturn(curso);

        mockMvc.perform(get("/cursos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Java Avançado"));
    }


    @Test
    void deveRetornarNotFoundQuandoCursoNaoExiste() throws Exception {
        Mockito.when(cursoService.findById(99L))
                .thenThrow(new EntityNotFoundException("Curso não encontrado"));

        mockMvc.perform(get("/cursos/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarTodosOsCursos() throws Exception {
        List<Curso> cursos = Arrays.asList(
                new Curso(1L, "Java", "Curso Java básico", 80),
                new Curso(2L, "Spring Boot", "Curso avançado de Spring", 80)
        );
        // Força o mock para o service e garante que o mapper não interfira
        Mockito.when(cursoService.findAll()).thenReturn(cursos);
        Mockito.when(cursoMapper.toDto(any(Curso.class)))
                .thenAnswer(invocation -> {
                    Curso c = invocation.getArgument(0);
                    CursoResponseDTO dto = new CursoResponseDTO();
                    dto.setId(c.getId());
                    dto.setNome(c.getNome());
                    dto.setDescricao(c.getDescricao());
                    dto.setCargaHoraria(c.getCargaHoraria());
                    return dto;
                });

        mockMvc.perform(get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Java"))
                .andExpect(jsonPath("$[1].nome").value("Spring Boot"));
    }


    @Test
    void deveAtualizarCurso() throws Exception {
        // Cria o DTO de requisição (sem usar construtor)
        CursoRequestDTO dto = new CursoRequestDTO();
        dto.setNome("Java Pro");
        dto.setDescricao("Atualizado");
        dto.setCargaHoraria(145);

        // Cria o objeto atualizado que será retornado pelo service
        Curso atualizado = new Curso(1L, "Java Pro", "Atualizado", 145);

        // Mocka o comportamento dos métodos usados no controller
        Mockito.when(cursoMapper.toEntity(any(CursoRequestDTO.class))).thenReturn(atualizado);
        CursoResponseDTO responseDTO = new CursoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Java Pro");
        responseDTO.setDescricao("Atualizado");
        responseDTO.setCargaHoraria(145);
        Mockito.when(cursoMapper.toDto(any(Curso.class))).thenReturn(responseDTO);

        Mockito.when(cursoService.update(eq(1L), any(Curso.class))).thenReturn(atualizado);

        // Executa a requisição PUT simulando a atualização
        mockMvc.perform(put("/cursos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Java Pro"))
                .andExpect(jsonPath("$.descricao").value("Atualizado"))
                .andExpect(jsonPath("$.cargaHoraria").value(145));
    }


    @Test
    void deveExcluirCurso() throws Exception {
        mockMvc.perform(delete("/cursos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
