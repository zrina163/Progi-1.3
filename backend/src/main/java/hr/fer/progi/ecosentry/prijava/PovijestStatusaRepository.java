package hr.fer.progi.ecosentry.prijava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PovijestStatusaRepository extends JpaRepository<PovijestStatusa, Integer> {
    List<PovijestStatusa> findByPrijavaIdPrijavaOrderByDatumPromjene(Integer idPrijava);
}
