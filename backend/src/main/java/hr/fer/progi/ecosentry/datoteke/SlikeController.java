package hr.fer.progi.ecosentry.datoteke;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class SlikeController {

    private final PohranaSlika pohrana;

    public SlikeController(PohranaSlika pohrana) {
        this.pohrana = pohrana;
    }

    @GetMapping("/api/public/slike/{naziv:.+}")
    public ResponseEntity<Resource> slika(@PathVariable String naziv) {
        Resource r = pohrana.ucitaj(naziv);
        MediaType tip = MediaTypeFactory.getMediaType(r).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .contentType(tip)
                .cacheControl(CacheControl.maxAge(Duration.ofDays(30)))
                .body(r);
    }
}
