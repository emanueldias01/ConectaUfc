package br.com.pet.conectaufc.repository.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.model.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByNome(String nome);

    @Query("SELECT p FROM Professor p JOIN ProfessorCadeira pc ON p.id = pc.professor.id WHERE pc.cadeira.id = :idCadeira")
    List<Professor> buscaProfessoresDeCadeiraEspecifica(@Param("idCadeira") Long idCadeira);
}
