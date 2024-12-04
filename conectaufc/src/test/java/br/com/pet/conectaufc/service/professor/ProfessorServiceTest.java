package br.com.pet.conectaufc.service.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorRequestDTO;
import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.model.professor.Professor;
import br.com.pet.conectaufc.repository.professor.ProfessorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProfessorServiceTest {

    @Mock
    ProfessorRepository professorRepository;

    @InjectMocks
    ProfessorService professorService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Cria professor com sucesso")
    void criaProfessor(){
        String nome = "professor";
        ProfessorRequestDTO dtoArgumento = new ProfessorRequestDTO(nome);

        when(professorRepository.findByNome(nome)).thenReturn(Optional.empty());

        assertThat(professorService.criaProfessor(dtoArgumento).nome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("Nao cria professor devido a nome repetido")
    void naoCriaProfessorNomeRepetido(){
        String nome = "professor";
        ProfessorRequestDTO dtoArgumento = new ProfessorRequestDTO(nome);

        Professor professor = new Professor();

        when(professorRepository.findByNome(nome)).thenReturn(Optional.of(professor));

        Assertions.assertThrows(IllegalArgumentException.class, () -> professorService.criaProfessor(dtoArgumento));
    }

}