package com.example.courseManager.service;

import com.example.courseManager.models.Curso;
import com.example.courseManager.repositories.CursoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // usa o application-test.properties
class CursoServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    @DisplayName("Deve salvar um curso com sucesso")
    void deveSalvarCursoComSucesso() {
        Curso curso = new Curso();
        curso.setNome("Java Spring");
        curso.setDescricao("Curso de API REST com Spring Boot");
        curso.setCargaHoraria(100);

        Curso cursoSalvo = cursoRepository.save(curso);

        assertThat(cursoSalvo.getId()).isNotNull();
        assertThat(cursoSalvo.getNome()).isEqualTo("Java Spring");
    }

    @Test
    @DisplayName("Deve buscar um curso existente pelo ID com sucesso")
    void deveBuscarCursoPorId() {
        Curso curso = new Curso();
        curso.setNome("Spring Boot Avançado");
        curso.setDescricao("API com testes e swagger");
        curso.setCargaHoraria(160);
        entityManager.persist(curso);

        Optional<Curso> encontrado = cursoRepository.findById(curso.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Spring Boot Avançado");
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar curso inexistente")
    void deveRetornarVazioAoBuscarCursoInexistente() {
        Optional<Curso> curso = cursoRepository.findById(999L);
        assertThat(curso).isEmpty();
    }

    @Test
    @DisplayName("Deve listar todos os cursos cadastrados")
    void deveListarCursos() {
        Curso curso1 = new Curso("Java Básico", "Curso introdutório", 100);
        Curso curso2 = new Curso("Spring Boot", "Curso intermediário", 140);

        entityManager.persist(curso1);
        entityManager.persist(curso2);

        List<Curso> cursos = cursoRepository.findAll();

        assertThat(cursos).hasSize(2);
        assertThat(cursos).extracting(Curso::getNome)
                .containsExactlyInAnyOrder("Java Básico", "Spring Boot");
    }

    @Test
    @DisplayName("Deve deletar curso pelo ID")
    void deveDeletarCurso() {
        Curso curso = new Curso("Java Web", "Curso de servlets", 130);
        entityManager.persist(curso);

        cursoRepository.deleteById(curso.getId());
        Optional<Curso> cursoDeletado = cursoRepository.findById(curso.getId());

        assertThat(cursoDeletado).isEmpty();
    }




}

