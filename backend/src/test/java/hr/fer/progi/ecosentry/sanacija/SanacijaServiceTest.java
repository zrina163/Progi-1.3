package hr.fer.progi.ecosentry.sanacija;

import hr.fer.progi.ecosentry.akcija.AkcijaCiscenja;
import hr.fer.progi.ecosentry.akcija.AkcijaCiscenjaRepository;
import hr.fer.progi.ecosentry.datoteke.PohranaSlika;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.prijava.*;
import hr.fer.progi.ecosentry.udruga.Udruga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SanacijaServiceTest {

    PrijavaService prijavaService = mock(PrijavaService.class);
    AkcijaCiscenjaRepository akcijaRepo = mock(AkcijaCiscenjaRepository.class);
    PotvrdaSanacijeRepository potvrde = mock(PotvrdaSanacijeRepository.class);
    FotografijaRepository fotografije = mock(FotografijaRepository.class);
    PohranaSlika pohrana = mock(PohranaSlika.class);

    SanacijaService servis;

    Udruga mojaUdruga;
    Korisnik voditelj;
    Prijava prijava;
    MultipartFile foto;

    @BeforeEach
    void setUp() {
        servis = new SanacijaService(prijavaService, akcijaRepo, potvrde, fotografije, pohrana);

        // Udruga nema setIdUdruga (ID dodjeljuje baza), pa je za test mockamo
        mojaUdruga = mock(Udruga.class);
        when(mojaUdruga.getIdUdruga()).thenReturn(1);

        voditelj = new Korisnik();

        // prijava 17 je u akciji 5, a akcija 5 pripada mojoj udruzi
        prijava = new Prijava();
        prijava.setStatus(StatusPrijave.AKCIJA_U_TIJEKU);
        prijava.setIdAkcija(5);
        when(prijavaService.nadji(17)).thenReturn(prijava);

        AkcijaCiscenja akcija = new AkcijaCiscenja();
        akcija.setUdruga(mojaUdruga);
        when(akcijaRepo.findById(5)).thenReturn(Optional.of(akcija));

        foto = new MockMultipartFile("fotografija", "ocisceno.jpg", "image/jpeg", new byte[]{1, 2, 3});
        when(pohrana.spremi(foto)).thenReturn("/uploads/ocisceno.jpg");
    }

    // ---------- provjere ----------

    @Test
    void bezFotografije400() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.posaljiDokaz(17, null, mojaUdruga, voditelj));

        assertEquals(400, e.getStatusCode().value());
        verifyNoInteractions(pohrana, fotografije, potvrde);
    }

    @Test
    void praznaFotografija400() {
        MultipartFile prazna = new MockMultipartFile("fotografija", "x.jpg", "image/jpeg", new byte[0]);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.posaljiDokaz(17, prazna, mojaUdruga, voditelj));

        assertEquals(400, e.getStatusCode().value());
    }

    @Test
    void prijavaKojaNijeUAkciji409() {
        prijava.setStatus(StatusPrijave.PRIJAVLJENO);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.posaljiDokaz(17, foto, mojaUdruga, voditelj));

        assertEquals(409, e.getStatusCode().value());
        verifyNoInteractions(pohrana);
    }

    @Test
    void akcijaDrugeUdruge403() {
        Udruga drugaUdruga = mock(Udruga.class);
        when(drugaUdruga.getIdUdruga()).thenReturn(2);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.posaljiDokaz(17, foto, drugaUdruga, voditelj));

        assertEquals(403, e.getStatusCode().value());
        verifyNoInteractions(pohrana);
    }

    @Test
    void zahtjevVecCekaRedara409() {
        when(potvrde.existsByPrijavaIdPrijavaAndStatus(17, StatusPotvrde.NA_CEKANJU)).thenReturn(true);

        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> servis.posaljiDokaz(17, foto, mojaUdruga, voditelj));

        assertEquals(409, e.getStatusCode().value());
        verifyNoInteractions(pohrana);   // fotka se ne smije spremiti na disk
    }

    // ---------- uspješan slučaj ----------

    @Test
    void uspjesnoSlanjeSpremaFotografijuIPotvrdu() {
        servis.posaljiDokaz(17, foto, mojaUdruga, voditelj);

        // fotka spremljena na disk
        verify(pohrana).spremi(foto);

        // Fotografija tipa POSLIJE, vezana uz prijavu i voditelja
        ArgumentCaptor<Fotografija> f = ArgumentCaptor.forClass(Fotografija.class);
        verify(fotografije).save(f.capture());
        assertEquals(Fotografija.Tip.POSLIJE, f.getValue().getTip());
        assertEquals("/uploads/ocisceno.jpg", f.getValue().getUrl());

        // PotvrdaSanacije čeka redara
        ArgumentCaptor<PotvrdaSanacije> ps = ArgumentCaptor.forClass(PotvrdaSanacije.class);
        verify(potvrde).save(ps.capture());
        assertSame(prijava, ps.getValue().getPrijava());
        assertSame(f.getValue(), ps.getValue().getFotografija());
        assertSame(voditelj, ps.getValue().getPodnositelj());
        assertEquals(StatusPotvrde.NA_CEKANJU, ps.getValue().getStatus());

        // status prijave se NE mijenja dok redar ne potvrdi (AS-04)
        assertEquals(StatusPrijave.AKCIJA_U_TIJEKU, prijava.getStatus());
        verify(prijavaService, never()).promijeniStatus(any(), any(), any(), any());
    }
}
