package br.com.pet.conectaufc.repository.professor;

import br.com.pet.conectaufc.model.professor.Professor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByNome(String nome);

    @Query(value = "SELECT professor_id FROM professor_cadeira WHERE cadeira_id=:idCadeira", nativeQuery = true)
    Page<Long> buscaProfessoresDeCadeiraEspecifica(@Param("idCadeira") Long idCadeira, Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO professor_cadeira(professor_id, cadeira_id) VALUES (:idProfessor, :idCadeira)", nativeQuery = true)
    void salvaProfessorNaquelaCadeira(@Param("idProfessor") Long idProfessor, @Param("idCadeira") Long idCadeira);

    @Modifying
    @Query(value = "DELETE FROM professor_cadeira WHERE cadeira_id =:id", nativeQuery = true)
    void removeVinculoDeProfessorComCadeira(@Param("id") Long id);
}
