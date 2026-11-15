package hr.fer.progi.ecosentry.prijava;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PrijavaRepository extends JpaRepository<Prijava, Integer> {

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByStatusInOrderByDatumPrijaveDesc(Collection<StatusPrijave> statusi);

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByPrijaviteljIdKorisnikOrderByDatumPrijaveDesc(Integer idKorisnik);

    @EntityGraph(attributePaths = "kategorija")
    List<Prijava> findByStatusAndGradIgnoreCaseOrderByDatumPrijaveDesc(StatusPrijave status, String grad);

}
