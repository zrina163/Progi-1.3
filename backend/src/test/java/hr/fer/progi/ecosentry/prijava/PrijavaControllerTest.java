package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.auth.KorisnikOidcUserService;
import hr.fer.progi.ecosentry.config.SecurityConfig;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.korisnik.TrenutniKorisnik;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testira HTTP sloj zajedno s pravim SecurityConfig-om:
 * javni pristup karti, obavezna prijava, CSRF zaštita i prava po ulogama.
 * Servis je zamijenjen mockom, pa baza nije potrebna.
 */
@WebMvcTest(
        controllers = {PrijavaController.class, RedarPrijavaController.class},
        properties = {
                "app.frontend-url=http://localhost:5173",
                "spring.security.oauth2.client.registration.google.client-id=test",
                "spring.security.oauth2.client.registration.google.client-secret=test"
        })
@Import(SecurityConfig.class)
class PrijavaControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    PrijavaService servis;

    @MockitoBean
    TrenutniKorisnik trenutni;

    @MockitoBean
    KorisnikOidcUserService oidcUserService;

    @MockitoBean
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeEach
    void setUp() {
        when(trenutni.dohvati(any())).thenReturn(new Korisnik());
        when(servis.pinoviZaKartu(any())).thenReturn(List.of());
        when(servis.zaRedara(any())).thenReturn(List.of());
    }

    @Test
    void kartaJeJavna() throws Exception {
        mvc.perform(get("/api/public/prijave"))
                .andExpect(status().isOk());
    }

    @Test
    void novaPrijavaBezPrijaveVraca401() throws Exception {
        mvc.perform(novaPrijava("45.8").with(csrf()))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(servis);
    }

    @Test
    void novaPrijavaBezCsrfTokenaVraca403() throws Exception {
        mvc.perform(novaPrijava("45.8").with(oidcLogin()))
                .andExpect(status().isForbidden());
        verifyNoInteractions(servis);
    }

    @Test
    void novaPrijavaUspijeva() throws Exception {
        mvc.perform(novaPrijava("45.8").with(oidcLogin()).with(csrf()))
                .andExpect(status().isCreated());
        verify(servis).kreiraj(any(), any(), any());
    }

    @Test
    void neispravneKoordinateVracaju400() throws Exception {
        mvc.perform(novaPrijava("200").with(oidcLogin()).with(csrf()))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(servis);
    }

    @Test
    void gradaninNemaPristupRedarskomDijelu() throws Exception {
        mvc.perform(get("/api/redar/prijave")
                        .with(oidcLogin().authorities(new SimpleGrantedAuthority("ROLE_GRADANIN"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void redarImaPristupRedarskomDijelu() throws Exception {
        mvc.perform(get("/api/redar/prijave")
                        .with(oidcLogin().authorities(new SimpleGrantedAuthority("ROLE_KOMUNALNI_REDAR"))))
                .andExpect(status().isOk());
    }

    private static MockMultipartHttpServletRequestBuilder novaPrijava(String sirina) {
        var foto = new MockMultipartFile("fotografija", "otpad.jpg", "image/jpeg", new byte[]{1, 2, 3});
        return multipart("/api/prijave")
                .file(foto)
                .param("idKategorija", "1")
                .param("sirina", sirina)
                .param("duzina", "15.97");
    }
}
