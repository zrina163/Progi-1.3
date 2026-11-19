package hr.fer.progi.ecosentry.obavijest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObavijestRepository extends JpaRepository<Obavijest, Integer> {
    List<Obavijest> findByKorisnikIdKorisnikOrderByDatumSlanjaDesc(Integer idKorisnik);

    Optional<Obavijest> findByIdObavijestAndKorisnikIdKorisnik(Integer idObavijest, Integer idKorisnik);
}
