package hr.fer.progi.ecosentry.korisnik;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByOidcSub(String oidcSub);
}