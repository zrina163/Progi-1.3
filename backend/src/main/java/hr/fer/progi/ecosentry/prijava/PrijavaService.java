package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.datoteke.PohranaSlika;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.korisnik.Uloga;
import hr.fer.progi.ecosentry.obavijest.ObavijestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class PrijavaService {

    /** Statusi koji se prikazuju na javnoj karti (odbačene prijave se ne prikazuju). */
    public static final Set<StatusPrijave> VIDLJIVI_NA_KARTI = EnumSet.of(
            StatusPrijave.PRIJAVLJENO,
            StatusPrijave.AKCIJA_U_TIJEKU,
            StatusPrijave.SLUZBENA_SANACIJA,
            StatusPrijave.OCISCENO);

    private final PrijavaRepository prijave;
    private final KategorijaOtpadaRepository kategorije;
    private final FotografijaRepository fotografije;
    private final PovijestStatusaRepository povijest;
    private final PohranaSlika pohrana;
    private final ObavijestService obavijesti;

    public PrijavaService(PrijavaRepository prijave,
                          KategorijaOtpadaRepository kategorije,
                          FotografijaRepository fotografije,
                          PovijestStatusaRepository povijest,
                          PohranaSlika pohrana,
                          ObavijestService obavijesti) {
        this.prijave = prijave;
        this.kategorije = kategorije;
        this.fotografije = fotografije;
        this.povijest = povijest;
        this.pohrana = pohrana;
        this.obavijesti = obavijesti;
    }

    // ---------- čitanje ----------

    @Transactional(readOnly = true)
    public List<PrijavaDto.Kategorija> sveKategorije() {
        return kategorije.findAll().stream().map(PrijavaDto.Kategorija::od).toList();
    }

    @Transactional(readOnly = true)
    public List<PrijavaDto.Pin> pinoviZaKartu(Set<StatusPrijave> statusi) {
        Set<StatusPrijave> filter = (statusi == null || statusi.isEmpty())
                ? EnumSet.copyOf(VIDLJIVI_NA_KARTI)
                : EnumSet.copyOf(statusi);
        filter.retainAll(VIDLJIVI_NA_KARTI);
        if (filter.isEmpty()) {
            return List.of();
        }
        return prijave.findByStatusInOrderByDatumPrijaveDesc(filter)
                .stream().map(PrijavaDto.Pin::od).toList();
    }

    @Transactional(readOnly = true)
    public List<PrijavaDto.Detalji> mojePrijave(Korisnik k) {
        return prijave.findByPrijaviteljIdKorisnikOrderByDatumPrijaveDesc(k.getIdKorisnik())
                .stream().map(p -> uDetalje(p, false)).toList();
    }

    /**
     * Detalji jedne prijave. Odbačenu prijavu vide samo njezin prijavitelj i komunalni redar.
     */
    @Transactional(readOnly = true)
    public PrijavaDto.Detalji detalji(Integer id, Korisnik k) {
        Prijava p = nadji(id);
        boolean redar = k.getUloga() == Uloga.KOMUNALNI_REDAR;
        boolean vlasnik = p.getPrijavitelj().getIdKorisnik().equals(k.getIdKorisnik());
        if (p.getStatus() == StatusPrijave.ODBACENO && !redar && !vlasnik) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return uDetalje(p, redar);
    }

    @Transactional(readOnly = true)
    public List<PrijavaDto.Detalji> zaRedara(Set<StatusPrijave> statusi) {
        Set<StatusPrijave> filter = (statusi == null || statusi.isEmpty())
                ? EnumSet.allOf(StatusPrijave.class)
                : statusi;
        return prijave.findByStatusInOrderByDatumPrijaveDesc(filter)
                .stream().map(p -> uDetalje(p, true)).toList();
    }

    // ---------- pisanje ----------

    /** Građanin kreira prijavu s terena: GPS koordinate + fotografija + kategorija. */
    @Transactional
    public PrijavaDto.Detalji kreiraj(PrijavaDto.NovaPrijava zahtjev, MultipartFile foto, Korisnik prijavitelj) {
        KategorijaOtpada kat = kategorije.findById(zahtjev.idKategorija())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nepoznata kategorija otpada"));

        // slika se sprema prije baze; ako baza padne, ostaje "siroče" datoteka – prihvatljivo za ovaj opseg
        String url = pohrana.spremi(foto);

        Prijava p = new Prijava();
        p.setPrijavitelj(prijavitelj);
        p.setKategorija(kat);
        p.setSirina(zahtjev.sirina().setScale(6, RoundingMode.HALF_UP));
        p.setDuzina(zahtjev.duzina().setScale(6, RoundingMode.HALF_UP));
        p.setOpis(prazanUNull(zahtjev.opis()));
        p.setAdresa(prazanUNull(zahtjev.adresa()));
        p.setGrad(prazanUNull(zahtjev.grad()));
        p.setKvart(prazanUNull(zahtjev.kvart()));
        p.setProcijenjeniVolumenM3(zahtjev.procijenjeniVolumenM3());
        p.setStatus(StatusPrijave.PRIJAVLJENO);
        prijave.save(p);

        Fotografija f = new Fotografija();
        f.setPrijava(p);
        f.setKorisnik(prijavitelj);
        f.setUrl(url);
        f.setTip(Fotografija.Tip.PRIJE);
        fotografije.save(f);

        povijest.save(new PovijestStatusa(p, prijavitelj, StatusPrijave.PRIJAVLJENO, null));

        return uDetalje(p, false);
    }

    /** Redar odbacuje lažnu prijavu (spam, slika nema veze s otpadom). */
    @Transactional
    public PrijavaDto.Detalji odbaci(Integer id, String napomena, Korisnik redar) {
        Prijava p = nadji(id);
        if (p.getStatus() != StatusPrijave.PRIJAVLJENO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Odbaciti se može samo prijava u statusu PRIJAVLJENO");
        }
        promijeniStatus(p, StatusPrijave.ODBACENO, redar, napomena);
        return uDetalje(p, true);
    }

    /** Redar ispravlja kategoriju ako je građanin pogriješio. */
    @Transactional
    public PrijavaDto.Detalji promijeniKategoriju(Integer id, Integer idKategorija) {
        Prijava p = nadji(id);
        KategorijaOtpada kat = kategorije.findById(idKategorija)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nepoznata kategorija otpada"));
        p.setKategorija(kat);
        return uDetalje(p, true);
    }

    /**
     * Jedino mjesto gdje se mijenja status prijave: upisuje povijest i obavještava prijavitelja.
     * Koristite ga i iz drugih modula (akcije čišćenja, službeni nalozi, potvrda sanacije).
     */
    @Transactional
    public void promijeniStatus(Prijava p, StatusPrijave novi, Korisnik tko, String napomena) {
        if (p.getStatus() == novi) {
            return;
        }
        p.setStatus(novi);
        povijest.save(new PovijestStatusa(p, tko, novi, napomena));
        obavijesti.posaljiZaPrijavu(p.getPrijavitelj(), p.getIdPrijava(), poruka(p.getIdPrijava(), novi));
    }

    public Prijava nadji(Integer id) {
        return prijave.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prijava ne postoji"));
    }

    // ---------- pomoćno ----------

    private PrijavaDto.Detalji uDetalje(Prijava p, boolean sPrijaviteljem) {
        var fotke = fotografije.findByPrijavaIdPrijavaOrderByDatumUcitavanja(p.getIdPrijava())
                .stream().map(PrijavaDto.Foto::od).toList();
        var promjene = povijest.findByPrijavaIdPrijavaOrderByDatumPromjene(p.getIdPrijava())
                .stream().map(PrijavaDto.Promjena::od).toList();
        PrijavaDto.Prijavitelj prijavitelj = null;
        if (sPrijaviteljem) {
            Korisnik k = p.getPrijavitelj();
            prijavitelj = new PrijavaDto.Prijavitelj(k.getIdKorisnik(), k.getIme(), k.getEmail());
        }
        return new PrijavaDto.Detalji(
                p.getIdPrijava(), p.getOpis(), p.getSirina(), p.getDuzina(),
                p.getAdresa(), p.getGrad(), p.getKvart(), p.getProcijenjeniVolumenM3(),
                p.getStatus(), PrijavaDto.Kategorija.od(p.getKategorija()),
                p.getIdAkcija(), p.getDatumPrijave(), fotke, promjene, prijavitelj);
    }

    static String poruka(Integer id, StatusPrijave status) {
        return switch (status) {
            case PRIJAVLJENO -> "Vaša prijava #" + id + " je zaprimljena.";
            case AKCIJA_U_TIJEKU -> "Vaša prijava #" + id + " je uvrštena u akciju čišćenja.";
            case SLUZBENA_SANACIJA -> "Za vašu prijavu #" + id + " izdan je službeni nalog za sanaciju.";
            case OCISCENO -> "Lokacija iz vaše prijave #" + id + " je očišćena. Hvala!";
            case ODBACENO -> "Vaša prijava #" + id + " je odbačena.";
        };
    }

    private static String prazanUNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
