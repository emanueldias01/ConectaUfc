package br.com.pet.conectaufc.dto.professor;

import br.com.pet.conectaufc.model.professor.Professor;

public record ProfessorResponseDTO(
        Long id,
        String nome
) {
    public ProfessorResponseDTO(Professor professor) {
        this(professor.getId(), professor.getNome());
    }
}
