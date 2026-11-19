package hr.fer.progi.ecosentry.prijava;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** DTO-ovi za API prijava (sve na jednom mjestu radi preglednosti). */
public final class PrijavaDto {

    private PrijavaDto() {}

    public record Kategorija(Integer id, String naziv) {
        static Kategorija od(KategorijaOtpada k) {
            return new Kategorija(k.getIdKategorija(), k.getNazivKategorije());
        }
    }

    /** Pribadača na karti – bez osobnih podataka, pa smije biti javna. */
    public record Pin(Integer id, BigDecimal sirina, BigDecimal duzina,
                      StatusPrijave status, Kategorija kategorija, Instant datumPrijave) {
        static Pin od(Prijava p) {
            return new Pin(p.getIdPrijava(), p.getSirina(), p.getDuzina(),
                    p.getStatus(), Kategorija.od(p.getKategorija()), p.getDatumPrijave());
        }
    }

    public record Foto(Integer id, String url, Fotografija.Tip tip, Instant datumUcitavanja) {
        static Foto od(Fotografija f) {
            return new Foto(f.getIdFotografija(), f.getUrl(), f.getTip(), f.getDatumUcitavanja());
        }
    }

    public record Promjena(StatusPrijave status, String napomena, Instant datumPromjene) {
        static Promjena od(PovijestStatusa p) {
            return new Promjena(p.getStatus(), p.getNapomena(), p.getDatumPromjene());
        }
    }

    /** Podaci o prijavitelju – šalju se samo komunalnom redaru (moderiranje). */
    public record Prijavitelj(Integer id, String ime, String email) {}

    public record Detalji(Integer id, String opis,
                          BigDecimal sirina, BigDecimal duzina,
                          String adresa, String grad, String kvart,
                          BigDecimal procijenjeniVolumenM3,
                          StatusPrijave status, Kategorija kategorija,
                          Integer idAkcija, Instant datumPrijave,
                          List<Foto> fotografije, List<Promjena> povijest,
                          Prijavitelj prijavitelj) {}

    /** Polja obrasca nove prijave (multipart/form-data, uz dio "fotografija"). */
    public record NovaPrijava(
            @NotNull Integer idKategorija,
            @NotNull @DecimalMin("-90") @DecimalMax("90") BigDecimal sirina,
            @NotNull @DecimalMin("-180") @DecimalMax("180") BigDecimal duzina,
            @Size(max = 2000) String opis,
            @Size(max = 500) String adresa,     // klijent ga dobiva geokodiranjem (Google Maps)
            @Size(max = 120) String grad,
            @Size(max = 120) String kvart,
            @DecimalMin("0") @Digits(integer = 8, fraction = 2) BigDecimal procijenjeniVolumenM3) {}

    public record Odbacivanje(@NotBlank @Size(max = 1000) String napomena) {}

    public record PromjenaKategorije(@NotNull Integer idKategorija) {}
}
