package hr.fer.progi.ecosentry.obavijest;

import hr.fer.progi.ecosentry.korisnik.TrenutniKorisnik;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/obavijesti")
public class ObavijestController {

    public record ObavijestDto(Integer id, Integer idPrijava, Integer idAkcija,
                               String poruka, boolean procitana, Instant datumSlanja) {
        static ObavijestDto od(Obavijest o) {
            return new ObavijestDto(o.getIdObavijest(), o.getIdPrijava(), o.getIdAkcija(),
                    o.getPoruka(), o.isProcitana(), o.getDatumSlanja());
        }
    }

    private final ObavijestRepository obavijesti;
    private final TrenutniKorisnik trenutni;

    public ObavijestController(ObavijestRepository obavijesti, TrenutniKorisnik trenutni) {
        this.obavijesti = obavijesti;
        this.trenutni = trenutni;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<ObavijestDto> moje(@AuthenticationPrincipal OidcUser user) {
        var k = trenutni.dohvati(user);
        return obavijesti.findByKorisnikIdKorisnikOrderByDatumSlanjaDesc(k.getIdKorisnik())
                .stream().map(ObavijestDto::od).toList();
    }

    @PatchMapping("/{id}/procitana")
    @Transactional
    public ObavijestDto oznaciProcitanom(@PathVariable Integer id, @AuthenticationPrincipal OidcUser user) {
        var k = trenutni.dohvati(user);
        var o = obavijesti.findByIdObavijestAndKorisnikIdKorisnik(id, k.getIdKorisnik())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        o.setProcitana(true);
        return ObavijestDto.od(o);
    }
}
