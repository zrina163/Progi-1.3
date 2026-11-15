package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.korisnik.TrenutniKorisnik;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Moderiranje prijava – samo KOMUNALNI_REDAR (zaštićeno u SecurityConfig: /api/redar/**).
 *
 *   GET   /api/redar/prijave?status=PRIJAVLJENO  – prijave za provjeru (s podacima o prijavitelju)
 *   POST  /api/redar/prijave/{id}/odbaci         – {"napomena": "..."}
 *   PATCH /api/redar/prijave/{id}/kategorija     – {"idKategorija": 3}
 */
@RestController
@RequestMapping("/api/redar/prijave")
public class RedarPrijavaController {

    private final PrijavaService servis;
    private final TrenutniKorisnik trenutni;

    public RedarPrijavaController(PrijavaService servis, TrenutniKorisnik trenutni) {
        this.servis = servis;
        this.trenutni = trenutni;
    }

    @GetMapping
    public List<PrijavaDto.Detalji> popis(@RequestParam(required = false) Set<StatusPrijave> status) {
        return servis.zaRedara(status);
    }

    @PostMapping("/{id}/odbaci")
    public PrijavaDto.Detalji odbaci(@PathVariable Integer id,
                                     @Valid @RequestBody PrijavaDto.Odbacivanje zahtjev,
                                     @AuthenticationPrincipal OidcUser user) {
        return servis.odbaci(id, zahtjev.napomena(), trenutni.dohvati(user));
    }

    @PatchMapping("/{id}/kategorija")
    public PrijavaDto.Detalji kategorija(@PathVariable Integer id,
                                         @Valid @RequestBody PrijavaDto.PromjenaKategorije zahtjev) {
        return servis.promijeniKategoriju(id, zahtjev.idKategorija());
    }
}
