package hr.fer.progi.ecosentry.datoteke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Sprema učitane fotografije na lokalni disk (app.upload-dir).
 * Prije spremanja svaka slika prolazi kroz ObradaSlike: provjera stvarnog formata
 * (samo JPEG/PNG), uklanjanje EXIF metapodataka i ispravak orijentacije.
 * Slike se poslužuju preko GET /api/public/slike/{naziv}.
 *
 * NAPOMENA: na Renderu se lokalni disk briše pri svakom deployu – prije produkcije
 * treba odlučiti o trajnoj pohrani (baza ili vanjska usluga) i zamijeniti ovu klasu.
 */
@Service
public class PohranaSlika {

    public static final String URL_PREFIKS = "/api/public/slike/";

    /** NF-2.6: najviše 10 MB po datoteci (isto ograničenje je i u application.yaml). */
    static final long MAX_BAJTOVA = 10L * 1024 * 1024;

    private final Path direktorij;

    public PohranaSlika(@Value("${app.upload-dir:uploads}") String direktorij) throws IOException {
        this.direktorij = Path.of(direktorij).toAbsolutePath().normalize();
        Files.createDirectories(this.direktorij);
    }

    /** Provjerava i čisti sliku, sprema je i vraća URL pod kojim je dostupna. */
    public String spremi(MultipartFile datoteka) {
        if (datoteka == null || datoteka.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fotografija je obavezna");
        }
        if (datoteka.getSize() > MAX_BAJTOVA) {
            throw new ResponseStatusException(HttpStatus.CONTENT_TOO_LARGE, "Fotografija smije imati najviše 10 MB");
        }

        byte[] ulaz;
        try {
            ulaz = datoteka.getBytes();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Učitavanje fotografije nije uspjelo", e);
        }

        ObradaSlike.Rezultat ociscena = ObradaSlike.ocisti(ulaz);

        String naziv = UUID.randomUUID() + ociscena.ekstenzija();
        try {
            Files.write(direktorij.resolve(naziv), ociscena.bajtovi());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Spremanje slike nije uspjelo", e);
        }
        return URL_PREFIKS + naziv;
    }

    public Resource ucitaj(String naziv) {
        Path put = direktorij.resolve(naziv).normalize();
        // zaštita od "../" u nazivu
        if (!put.startsWith(direktorij) || !Files.isRegularFile(put)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            return new UrlResource(put.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
