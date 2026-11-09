package hr.fer.progi.ecosentry.auth;

import hr.fer.progi.ecosentry.korisnik.KorisnikRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class MeController {

    public record MeDto(Integer id, String ime, String email, String uloga) {}

    private final KorisnikRepository korisnici;

    public MeController(KorisnikRepository korisnici) {
        this.korisnici = korisnici;
    }

    @GetMapping("/me")
    public MeDto me(@AuthenticationPrincipal OidcUser user, CsrfToken csrf) {
        csrf.getToken(); // postavlja XSRF-TOKEN cookie za frontend

        var k = korisnici.findByOidcSub(user.getSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return new MeDto(k.getIdKorisnik(), k.getIme(), k.getEmail(), k.getUloga().name());
    }
}