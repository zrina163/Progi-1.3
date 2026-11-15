package hr.fer.progi.ecosentry.datoteke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

/**
 * Sprema učitane fotografije na lokalni disk (app.upload-dir).
 * Slike se poslužuju preko GET /api/public/slike/{naziv}.
 * Kasnije se može zamijeniti s vanjskom pohranom (npr. S3, Cloudinary) bez promjene ostatka koda.
 */
@Service
public class PohranaSlika {

    public static final String URL_PREFIKS = "/api/public/slike/";

    private static final Map<String, String> DOPUSTENI_TIPOVI = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp",
            "image/heic", ".heic");

    private final Path direktorij;

    public PohranaSlika(@Value("${app.upload-dir:uploads}") String direktorij) throws IOException {
        this.direktorij = Path.of(direktorij).toAbsolutePath().normalize();
        Files.createDirectories(this.direktorij);
    }

    /** Sprema sliku i vraća URL pod kojim je dostupna. */
    public String spremi(MultipartFile datoteka) {
        if (datoteka == null || datoteka.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fotografija je obavezna");
        }
        String ekstenzija = DOPUSTENI_TIPOVI.get(datoteka.getContentType());
        if (ekstenzija == null) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Dopuštene su samo slike (JPEG, PNG, WEBP, HEIC)");
        }

        String naziv = UUID.randomUUID() + ekstenzija;
        try (InputStream in = datoteka.getInputStream()) {
            Files.copy(in, direktorij.resolve(naziv), StandardCopyOption.REPLACE_EXISTING);
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
