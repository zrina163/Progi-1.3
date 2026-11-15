package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "POVIJEST_STATUSA")
public class PovijestStatusa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPovijest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPrijava", nullable = false)
    private Prijava prijava;

    // tko je promijenio status (null ako ga je promijenio sustav)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKorisnik")
    private Korisnik korisnik;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPrijave status;

    private String napomena;

    @Column(nullable = false)
    private Instant datumPromjene;

    protected PovijestStatusa() {}

    public PovijestStatusa(Prijava prijava, Korisnik korisnik, StatusPrijave status, String napomena) {
        this.prijava = prijava;
        this.korisnik = korisnik;
        this.status = status;
        this.napomena = napomena;
        this.datumPromjene = Instant.now();
    }

    public Integer getIdPovijest() { return idPovijest; }
    public Prijava getPrijava() { return prijava; }
    public Korisnik getKorisnik() { return korisnik; }
    public StatusPrijave getStatus() { return status; }
    public String getNapomena() { return napomena; }
    public Instant getDatumPromjene() { return datumPromjene; }
}
