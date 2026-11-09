package hr.fer.progi.ecosentry.auth;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.korisnik.KorisnikRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Poziva se kod svake Google prijave.
 * Ako korisnik ne postoji u tablici KORISNIK, stvara ga (= registracija).
 */
@Service
public class KorisnikOidcUserService extends OidcUserService {

    private final KorisnikRepository korisnici;

    public KorisnikOidcUserService(KorisnikRepository korisnici) {
        this.korisnici = korisnici;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        OidcUser google = super.loadUser(request);

        Korisnik k = korisnici.findByOidcSub(google.getSubject()).orElseGet(() -> {
            Korisnik novi = new Korisnik();
            novi.setOidcSub(google.getSubject());
            return novi;
        });
        k.setIme(google.getFullName());
        k.setEmail(google.getEmail());
        korisnici.save(k);

        if (k.isBlokiran()) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("korisnik_blokiran"), "Korisnički račun je blokiran");
        }

        var ovlasti = List.of(new SimpleGrantedAuthority("ROLE_" + k.getUloga().name()));
        // "sub" -> authentication.getName() vraća oidcSub
        return new DefaultOidcUser(ovlasti, google.getIdToken(), google.getUserInfo(), "sub");
    }
}