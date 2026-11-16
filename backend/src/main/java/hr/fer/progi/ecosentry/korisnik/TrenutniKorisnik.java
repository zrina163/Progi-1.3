package hr.fer.progi.ecosentry.korisnik;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Pomoćna klasa: iz prijavljenog Google korisnika (OidcUser) dohvaća redak iz tablice KORISNIK.
 */
@Component
public class TrenutniKorisnik {

    private final KorisnikRepository korisnici;

    public TrenutniKorisnik(KorisnikRepository korisnici) {
        this.korisnici = korisnici;
    }

    public Korisnik dohvati(OidcUser user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Korisnik k = korisnici.findByOidcSub(user.getSubject())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (k.isBlokiran()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Korisnički račun je blokiran");
        }
        return k;
    }
}
