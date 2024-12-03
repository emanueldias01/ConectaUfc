package br.com.pet.conectaufc.repository.cadeira;

import br.com.pet.conectaufc.model.cadeira.Cadeira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaderiaRepository extends JpaRepository<Cadeira, Long> {
    List<Cadeira> findAllByOrderByNomeAsc();
}
