package hr.fer.progi.ecosentry.sanacija;

import hr.fer.progi.ecosentry.akcija.AkcijaCiscenja;
import hr.fer.progi.ecosentry.akcija.AkcijaCiscenjaRepository;
import hr.fer.progi.ecosentry.datoteke.PohranaSlika;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.prijava.*;
import hr.fer.progi.ecosentry.udruga.Udruga;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SanacijaService {
    private final PrijavaService prijavaService;
    private final AkcijaCiscenjaRepository akcijaRepo;
    private final PotvrdaSanacijeRepository potvrde;
    private final FotografijaRepository fotografije;
    private final PohranaSlika pohrana;

    public SanacijaService(PrijavaService prijavaService, AkcijaCiscenjaRepository akcijaRepo, PotvrdaSanacijeRepository potvrde, FotografijaRepository fotografije, PohranaSlika pohrana) {
        this.prijavaService = prijavaService;
        this.akcijaRepo = akcijaRepo;
        this.potvrde = potvrde;
        this.fotografije = fotografije;
        this.pohrana = pohrana;
    }

    @Transactional
    public SanacijaDto.Potvrda posaljiDokaz(Integer idPrijava, MultipartFile foto, Udruga udruga, Korisnik k){
        Prijava p = prijavaService.nadji(idPrijava);
        if(foto == null || foto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dokaz o sanaciji mora sadržavati fotografiju");
        }
        if(p.getStatus() != StatusPrijave.AKCIJA_U_TIJEKU){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Prijava #" + idPrijava + " nije u akciji čišćenja");
        }
        if (p.getIdAkcija() == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Prijava #" + idPrijava + " nije povezana s akcijom");
        }

        AkcijaCiscenja akcija = akcijaRepo.findById(p.getIdAkcija())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Prijava nije povezana s akcijom"));

        if (!akcija.getUdruga().getIdUdruga().equals(udruga.getIdUdruga())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Prijava #" + idPrijava + " pripada akciji druge udruge");
        }
        if (potvrde.existsByPrijavaIdPrijavaAndStatus(idPrijava, StatusPotvrde.NA_CEKANJU)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Za prijavu #" + idPrijava + " već postoji zahtjev koji čeka redara");
        }
        String url = pohrana.spremi(foto);

        Fotografija fotografija = new Fotografija();
        fotografija.setPrijava(p);
        fotografija.setKorisnik(k);
        fotografija.setUrl(url);
        fotografija.setTip(Fotografija.Tip.POSLIJE);
        fotografije.save(fotografija);

        PotvrdaSanacije potvrdaSanacije = new PotvrdaSanacije();
        potvrdaSanacije.setPrijava(p);
        potvrdaSanacije.setFotografija(fotografija);
        potvrdaSanacije.setPodnositelj(k);
        potvrde.save(potvrdaSanacije);

        return SanacijaDto.Potvrda.od(potvrdaSanacije);


    }
}
