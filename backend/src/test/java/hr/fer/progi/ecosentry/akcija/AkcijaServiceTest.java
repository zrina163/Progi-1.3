package hr.fer.progi.ecosentry.akcija;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.prijava.Prijava;
import hr.fer.progi.ecosentry.prijava.PrijavaService;
import hr.fer.progi.ecosentry.prijava.StatusPrijave;
import hr.fer.progi.ecosentry.udruga.Udruga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AkcijaServiceTest {

    AkcijaCiscenjaRepository akcije = mock(AkcijaCiscenjaRepository.class);
    PrijavaService prijavaService = mock(PrijavaService.class);

    AkcijaService servis;
    Udruga udruga;
    Korisnik voditelj;

    @BeforeEach
    void setUp() {
        servis = new AkcijaService(akcije, prijavaService);

        udruga = new Udruga();
        udruga.setRegija("Zagreb");
        udruga.setNaziv("Test udruga");

        voditelj = new Korisnik();
    }

    // ---------- pomoćne metode ----------

    /** Ispravan zahtjev za akciju s navedenim prijavama. */
    private AkcijaDto.NovaAkcija zahtjev(Integer... idPrijave) {
        return new AkcijaDto.NovaAkcija(
                List.of(idPrijave),
                "Čišćenje nasipa",
                null,
                10,
                Instant.now().plusSeconds(86_400),
                new BigDecimal("45.800000"),
                new BigDecimal("15.900000"),
                null);
    }

    /** Prijava u zadanom statusu i gradu koju će lažni PrijavaService vratiti za zadani ID. */
    private Prijava prijava(Integer id, StatusPrijave status, String grad) {
        Prijava p = new Prijava();
        p.setStatus(status);
        p.setGrad(grad);
        when(prijavaService.nadji(id)).thenReturn(p);
        return p;
    }

    // ---------- provjere ----------

    @Test
    void nemoguceUkljucitiPrijavuKojaNijePrijavljena() {
        prijava(1, StatusPrijave.AKCIJA_U_TIJEKU, "Zagreb");

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.kreiraj(zahtjev(1), udruga, voditelj));

        assertEquals(409, e.getStatusCode().value());
        verifyNoInteractions(akcije);
    }

    @Test
    void nemoguceUkljucitiPrijavuIzDrugogGrada() {
        prijava(1, StatusPrijave.PRIJAVLJENO, "Split");

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.kreiraj(zahtjev(1), udruga, voditelj));

        assertEquals(403, e.getStatusCode().value());
        verifyNoInteractions(akcije);
    }

    @Test
    void prijavaBezGradaSeOdbijaBezRusenja() {
        prijava(1, StatusPrijave.PRIJAVLJENO, null);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.kreiraj(zahtjev(1), udruga, voditelj));

        assertEquals(403, e.getStatusCode().value());
    }

    @Test
    void gradSeUsporedujeBezObziraNaVelikaSlova() {
        prijava(1, StatusPrijave.PRIJAVLJENO, "zagreb");

        assertDoesNotThrow(() -> servis.kreiraj(zahtjev(1), udruga, voditelj));
    }

    @Test
    void akoJednaPrijavaNeValjaNistaSeNeZapisuje() {
        prijava(1, StatusPrijave.PRIJAVLJENO, "Zagreb");     // ispravna
        prijava(2, StatusPrijave.OCISCENO, "Zagreb");        // neispravna

        assertThrows(ResponseStatusException.class,
                () -> servis.kreiraj(zahtjev(1, 2), udruga, voditelj));

        // ni akcija ni promjena statusa prve (ispravne) prijave
        verifyNoInteractions(akcije);
        verify(prijavaService, never()).promijeniStatus(any(), any(), any(), any());
    }

    // ---------- uspješan slučaj ----------

    @Test
    void uspjesnoKreiranjeSpremaAkcijuIMijenjaStatusPrijava() {
        Prijava p1 = prijava(1, StatusPrijave.PRIJAVLJENO, "Zagreb");
        Prijava p2 = prijava(2, StatusPrijave.PRIJAVLJENO, "Zagreb");

        AkcijaDto.Detalji rezultat = servis.kreiraj(zahtjev(1, 2), udruga, voditelj);

        // spremljena je akcija s ispravnim podacima
        ArgumentCaptor<AkcijaCiscenja> spremljena = ArgumentCaptor.forClass(AkcijaCiscenja.class);
        verify(akcije).save(spremljena.capture());
        assertSame(udruga, spremljena.getValue().getUdruga());
        assertEquals("Čišćenje nasipa", spremljena.getValue().getNaziv());
        assertEquals(10, spremljena.getValue().getMaxVolontera());
        assertEquals(StatusAkcije.POKRENUTO, spremljena.getValue().getStatus());

        // obje prijave su prebačene u AKCIJA_U_TIJEKU preko kolegine metode
        verify(prijavaService).promijeniStatus(eq(p1), eq(StatusPrijave.AKCIJA_U_TIJEKU), eq(voditelj), any());
        verify(prijavaService).promijeniStatus(eq(p2), eq(StatusPrijave.AKCIJA_U_TIJEKU), eq(voditelj), any());

        // odgovor sadrži naziv udruge i ID-eve prijava
        assertEquals("Test udruga", rezultat.nazivUdruge());
        assertEquals(List.of(1, 2), rezultat.idPrijave());
    }
}
