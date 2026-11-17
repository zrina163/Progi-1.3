package hr.fer.progi.ecosentry.prijava;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PrijavaRepository extends JpaRepository<Prijava, Integer> {

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByStatusInOrderByDatumPrijaveDesc(Collection<StatusPrijave> statusi);

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByPrijaviteljIdKorisnikOrderByDatumPrijaveDesc(Integer idKorisnik);

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByStatusAndGradIgnoreCaseOrderByDatumPrijaveDesc(StatusPrijave status, String grad);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Prijava> findZaAzuriranjeByIdPrijava(Integer idPrijava);

}
