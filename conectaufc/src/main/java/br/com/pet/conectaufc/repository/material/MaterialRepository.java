package br.com.pet.conectaufc.repository.material;

import br.com.pet.conectaufc.dto.material.MaterialRequestDTO;
import br.com.pet.conectaufc.model.material.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Page<Material> findAllByOrderByNomeAsc(Pageable pageable);

    @Query(value = "DELETE FROM tab_material WHERE professor_id=:professorId AND cadeira_id=:cadeiraId", nativeQuery = true)
    void removeMaterialDoProfessorReferenteACadeira(@Param("professorId")Long professorId, @Param("cadeiraId") Long cadeiraId);

    @Query(value = "DELETE FROM tab_material WHERE cadeira_id=:id",nativeQuery = true)
    void deletaTodoMaterialDaCadeira(@Param("id") Long id);

    @Query(value = "DELETE FROM tab_material WHERE professor_id=:id", nativeQuery = true)
    void deletaTodoMaterialDoProfessor(@Param("id") Long id);

    @Query(value = "DELETE from tab_material WHERE cadeira_id=:idCadeira AND professor_id=:idProfessor", nativeQuery = true)
    Page<Material> buscaMateriaisDaCadeiraEProfessorEspecifico(@Param("idCadeira") Long idCadeira, @Param("idProfessor") Long idProfessor, Pageable pageable);

    Optional<Material> findByNome(String nome);

    Optional<Material> findByLink(String link);
}
