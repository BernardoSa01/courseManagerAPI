package com.example.courseManager.service;

import com.example.courseManager.models.Aluno;
import com.example.courseManager.models.Curso;
import com.example.courseManager.exceptions.EntityNotFoundException;
import com.example.courseManager.exceptions.EntityNotFoundException;
import com.example.courseManager.repositories.AlunoRepository;
import com.example.courseManager.repositories.CursoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;
    private Curso curso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        curso = new Curso(1L, "Java Avançado", "Curso completo de Java", 80);
        aluno = new Aluno(1L, "Bernardo Sá", "12345678900", "bernardo@email.com", "202501094852");
    }

    @Test
    void deveSalvarAlunoComSucesso() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Aluno alunoSalvo = alunoService.save(aluno, 1L);

        assertNotNull(alunoSalvo);
        assertEquals("Bernardo Sá", alunoSalvo.getNome());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    void deveLancarExcecaoQuandoCursoNaoExistirAoSalvarAluno() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alunoService.save(aluno, 1L));
        verify(alunoRepository, never()).save(any(Aluno.class));
    }

    @Test
    void deveBuscarAlunoPorIdComSucesso() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Aluno alunoEncontrado = alunoService.findById(1L);

        assertEquals("Bernardo Sá", alunoEncontrado.getNome());
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoNaoForEncontrado() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alunoService.findById(1L));
    }

    @Test
    void deveListarTodosOsAlunos() {
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno));

        List<Aluno> alunos = alunoService.findAll();

        assertFalse(alunos.isEmpty());
        assertEquals(1, alunos.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    void deveLancarExcecaoQuandoNaoExistiremAlunos() {
        when(alunoRepository.findAll()).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> alunoService.findAll());
    }

    @Test
    void deveRemoverAlunoComSucesso() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        alunoService.delete(1L);

        verify(alunoRepository, times(1)).delete(aluno);
    }

    @Test
    void deveLancarExcecaoAoRemoverAlunoInexistente() {
        when(alunoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> alunoService.delete(1L));
        verify(alunoRepository, never()).deleteById(anyLong());
    }
}

