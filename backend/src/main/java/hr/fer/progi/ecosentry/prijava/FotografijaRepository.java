package hr.fer.progi.ecosentry.prijava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotografijaRepository extends JpaRepository<Fotografija, Integer> {
    List<Fotografija> findByPrijavaIdPrijavaOrderByDatumUcitavanja(Integer idPrijava);
}
