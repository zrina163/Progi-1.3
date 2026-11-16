package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.datoteke.PohranaSlika;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.obavijest.ObavijestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PrijavaServiceTest {

    PrijavaRepository prijave = mock(PrijavaRepository.class);
    KategorijaOtpadaRepository kategorije = mock(KategorijaOtpadaRepository.class);
    FotografijaRepository fotografije = mock(FotografijaRepository.class);
    PovijestStatusaRepository povijest = mock(PovijestStatusaRepository.class);
    PohranaSlika pohrana = mock(PohranaSlika.class);
    ObavijestService obavijesti = mock(ObavijestService.class);

    PrijavaService servis;

    @BeforeEach
    void setUp() {
        servis = new PrijavaService(prijave, kategorije, fotografije, povijest, pohrana, obavijesti);
    }

    @Test
    void promjenaStatusaUpisujePovijestIObavjestavaPrijavitelja() {
        Korisnik gradanin = new Korisnik();
        Prijava p = new Prijava();
        p.setPrijavitelj(gradanin);

        servis.promijeniStatus(p, StatusPrijave.AKCIJA_U_TIJEKU, null, null);

        assertEquals(StatusPrijave.AKCIJA_U_TIJEKU, p.getStatus());
        verify(povijest).save(any(PovijestStatusa.class));
        verify(obavijesti).posaljiZaPrijavu(eq(gradanin), any(), contains("akciju čišćenja"));
    }

    @Test
    void istiStatusNeRadiNista() {
        Prijava p = new Prijava();   // zadani status PRIJAVLJENO
        servis.promijeniStatus(p, StatusPrijave.PRIJAVLJENO, null, null);
        verifyNoInteractions(povijest, obavijesti);
    }

    @Test
    void nemoguceOdbacitiVecOciscenuPrijavu() {
        Prijava p = new Prijava();
        p.setStatus(StatusPrijave.OCISCENO);
        when(prijave.findById(1)).thenReturn(Optional.of(p));

        assertThrows(ResponseStatusException.class, () -> servis.odbaci(1, "spam", new Korisnik()));
        assertEquals(StatusPrijave.OCISCENO, p.getStatus());
    }

    @Test
    void kartaNikadNeVracaOdbacene() {
        assertTrue(servis.pinoviZaKartu(Set.of(StatusPrijave.ODBACENO)).isEmpty());
        verifyNoInteractions(prijave);
    }
}
