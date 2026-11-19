package hr.fer.progi.ecosentry.udruga;


import hr.fer.progi.ecosentry.akcija.AkcijaDto;
import hr.fer.progi.ecosentry.akcija.AkcijaService;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.korisnik.TrenutniKorisnik;
import hr.fer.progi.ecosentry.prijava.PrijavaDto;
import hr.fer.progi.ecosentry.prijava.PrijavaService;
import hr.fer.progi.ecosentry.sanacija.SanacijaDto;
import hr.fer.progi.ecosentry.sanacija.SanacijaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UdrugaController {

    private final UdrugaService udrugaService;
    private final PrijavaService prijavaService;
    private final TrenutniKorisnik trenutni;
    private final AkcijaService akcijaService;
    private final SanacijaService sanacijaService;


    public UdrugaController(UdrugaService servis, PrijavaService prijavaService, TrenutniKorisnik korisnik, AkcijaService akcijaService, SanacijaService sanacijaService) {
        this.udrugaService = servis;
        this.prijavaService = prijavaService;
        this.trenutni = korisnik;
        this.akcijaService = akcijaService;
        this.sanacijaService = sanacijaService;
    }

    @GetMapping("/api/udruga/prijave")
    public List<PrijavaDto.Detalji> prijave(@AuthenticationPrincipal OidcUser user){
        Korisnik k = trenutni.dohvati(user);
        Udruga u = udrugaService.mojaOdobrenaUdruga(k);
        return prijavaService.aktivneURegiji(u.getRegija());


    }
    @PostMapping("/api/udruga/akcije")
    @ResponseStatus(HttpStatus.CREATED)
    public AkcijaDto.Detalji novaAkcija(@Valid @RequestBody AkcijaDto.NovaAkcija zahtjev,
                                        @AuthenticationPrincipal OidcUser user){
        Korisnik k = trenutni.dohvati(user);
        Udruga u = udrugaService.mojaOdobrenaUdruga(k);
        return akcijaService.kreiraj(zahtjev, u, k);
    }

    @PostMapping(value = "/api/udruga/prijave/{id}/sanacija", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SanacijaDto.Potvrda dokazOSanaciji(
            @PathVariable Integer id,
            @RequestPart("fotografija")MultipartFile fotografija,
            @AuthenticationPrincipal OidcUser user){
        Korisnik k = trenutni.dohvati(user);
        Udruga u = udrugaService.mojaOdobrenaUdruga(k);
        return sanacijaService.posaljiDokaz(id, fotografija, u, k);
    }
}
