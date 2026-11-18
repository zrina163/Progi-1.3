package hr.fer.progi.ecosentry.udruga;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UdrugaRepository extends JpaRepository<Udruga, Integer> {
    Optional<Udruga> findByKorisnikIdKorisnik(Integer idKorisnik);
}
