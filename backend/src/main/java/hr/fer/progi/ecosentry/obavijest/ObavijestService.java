package hr.fer.progi.ecosentry.obavijest;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unutar-aplikacijske obavijesti (npr. "Vaša prijava #124 je uvrštena u akciju čišćenja").
 * Mogu je koristiti i drugi moduli (akcije, nalozi).
 */
@Service
public class ObavijestService {

    private final ObavijestRepository obavijesti;

    public ObavijestService(ObavijestRepository obavijesti) {
        this.obavijesti = obavijesti;
    }

    @Transactional
    public void posaljiZaPrijavu(Korisnik primatelj, Integer idPrijava, String poruka) {
        obavijesti.save(new Obavijest(primatelj, idPrijava, null, poruka));
    }

    @Transactional
    public void posaljiZaAkciju(Korisnik primatelj, Integer idAkcija, String poruka) {
        obavijesti.save(new Obavijest(primatelj, null, idAkcija, poruka));
    }
}
