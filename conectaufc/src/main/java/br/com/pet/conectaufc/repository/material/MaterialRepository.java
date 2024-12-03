package br.com.pet.conectaufc.repository.material;

import br.com.pet.conectaufc.model.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query(value = "DELETE FROM tab_cadeira WHERE professor_id=:professorId AND cadeira_id=:cadeiraId", nativeQuery = true)
    void removeMaterialDoProfessorReferenteACadeira(@Param("professorId")Long professorId, @Param("cadeiraId") Long cadeiraId);
}
