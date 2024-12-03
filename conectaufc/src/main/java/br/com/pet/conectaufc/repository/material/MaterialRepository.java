package br.com.pet.conectaufc.repository.material;

import br.com.pet.conectaufc.model.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
