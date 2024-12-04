package br.com.pet.conectaufc.repository.cadeira;

import br.com.pet.conectaufc.model.cadeira.Cadeira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaderiaRepository extends JpaRepository<Cadeira, Long> {
    Page<Cadeira> findAllByOrderByNomeAsc(Pageable pageable);

    Optional<Cadeira> findByNome(String nome);
}
