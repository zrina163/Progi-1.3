package hr.fer.progi.ecosentry.udruga;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UdrugaService {

    private final UdrugaRepository udruge;

    public UdrugaService(UdrugaRepository udruge) {
        this.udruge = udruge;
    }

    public Udruga mojaOdobrenaUdruga(Korisnik korisnik){
        Udruga u = udruge.findByKorisnikIdKorisnik(korisnik.getIdKorisnik())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Nemate profil udruge"));
        if (u.getStatusOdobrenja() != StatusOdobrenja.ODOBRENO){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Profil udruge još nije odobren");
        }
        return u;
    }

}
