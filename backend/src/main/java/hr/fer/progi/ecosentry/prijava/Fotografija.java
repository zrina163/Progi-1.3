package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "FOTOGRAFIJA")
public class Fotografija {

    /** PRIJE = fotografija otpada, POSLIJE = dokaz o sanaciji. */
    public enum Tip { PRIJE, POSLIJE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFotografija;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPrijava", nullable = false)
    private Prijava prijava;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKorisnik", nullable = false)
    private Korisnik korisnik;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tip tip;

    @Column(nullable = false)
    private Instant datumUcitavanja;

    @PrePersist
    void prijeSpremanja() {
        if (datumUcitavanja == null) {
            datumUcitavanja = Instant.now();
        }
    }

    public Integer getIdFotografija() { return idFotografija; }

    public Prijava getPrijava() { return prijava; }
    public void setPrijava(Prijava v) { prijava = v; }

    public Korisnik getKorisnik() { return korisnik; }
    public void setKorisnik(Korisnik v) { korisnik = v; }

    public String getUrl() { return url; }
    public void setUrl(String v) { url = v; }

    public Tip getTip() { return tip; }
    public void setTip(Tip v) { tip = v; }

    public Instant getDatumUcitavanja() { return datumUcitavanja; }
}
