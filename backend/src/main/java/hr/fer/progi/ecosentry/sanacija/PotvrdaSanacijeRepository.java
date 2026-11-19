package hr.fer.progi.ecosentry.sanacija;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PotvrdaSanacijeRepository extends JpaRepository<PotvrdaSanacije, Integer> {
    boolean existsByPrijavaIdPrijavaAndStatus(Integer idPrijava, StatusPotvrde status);
}
