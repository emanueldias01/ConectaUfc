package br.com.pet.conectaufc.repository.professor;

import br.com.pet.conectaufc.dto.professor.ProfessorResponseDTO;
import br.com.pet.conectaufc.model.professor.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByNome(String nome);

    @Query(value = "SELECT professor_id FROM professor_cadeira WHERE cadeira_id:=idCadeira", nativeQuery = true)
    Page<Long> buscaProfessoresDeCadeiraEspecifica(@Param("idCadeira") Long idCadeira, Pageable pageable);
}
