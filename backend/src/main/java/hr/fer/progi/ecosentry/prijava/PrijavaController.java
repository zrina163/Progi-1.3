package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.korisnik.TrenutniKorisnik;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * API za prijave otpada.
 *
 * Javno (bez prijave):
 *   GET  /api/public/kategorije           – popis kategorija otpada
 *   GET  /api/public/prijave?status=...   – pribadače za kartu
 *
 * Prijavljeni korisnik:
 *   POST /api/prijave                     – nova prijava (multipart: polja + "fotografija")
 *   GET  /api/prijave/moje                – povijest mojih prijava
 *   GET  /api/prijave/{id}                – detalji prijave
 */
@RestController
public class PrijavaController {

    private final PrijavaService servis;
    private final TrenutniKorisnik trenutni;

    public PrijavaController(PrijavaService servis, TrenutniKorisnik trenutni) {
        this.servis = servis;
        this.trenutni = trenutni;
    }

    @GetMapping("/api/public/kategorije")
    public List<PrijavaDto.Kategorija> kategorije() {
        return servis.sveKategorije();
    }

    @GetMapping("/api/public/prijave")
    public List<PrijavaDto.Pin> karta(@RequestParam(required = false) Set<StatusPrijave> status) {
        return servis.pinoviZaKartu(status);
    }

    @PostMapping(value = "/api/prijave", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PrijavaDto.Detalji nova(@Valid @ModelAttribute PrijavaDto.NovaPrijava zahtjev,
                                   @RequestPart("fotografija") MultipartFile fotografija,
                                   @AuthenticationPrincipal OidcUser user) {
        return servis.kreiraj(zahtjev, fotografija, trenutni.dohvati(user));
    }

    @GetMapping("/api/prijave/moje")
    public List<PrijavaDto.Detalji> moje(@AuthenticationPrincipal OidcUser user) {
        return servis.mojePrijave(trenutni.dohvati(user));
    }

    @GetMapping("/api/prijave/{id}")
    public PrijavaDto.Detalji detalji(@PathVariable Integer id, @AuthenticationPrincipal OidcUser user) {
        return servis.detalji(id, trenutni.dohvati(user));
    }
}
