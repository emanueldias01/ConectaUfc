package br.com.pet.conectaufc.repository.professor;

import br.com.pet.conectaufc.model.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
