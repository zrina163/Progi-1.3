package hr.fer.progi.ecosentry.obavijest;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "OBAVIJEST")
public class Obavijest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idObavijest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKorisnik", nullable = false)
    private Korisnik korisnik;   // primatelj

    private Integer idPrijava;
    private Integer idAkcija;

    @Column(nullable = false)
    private String poruka;

    private boolean procitana;

    @Column(nullable = false)
    private Instant datumSlanja;

    protected Obavijest() {}

    public Obavijest(Korisnik korisnik, Integer idPrijava, Integer idAkcija, String poruka) {
        this.korisnik = korisnik;
        this.idPrijava = idPrijava;
        this.idAkcija = idAkcija;
        this.poruka = poruka;
        this.datumSlanja = Instant.now();
    }

    public Integer getIdObavijest() { return idObavijest; }
    public Korisnik getKorisnik() { return korisnik; }
    public Integer getIdPrijava() { return idPrijava; }
    public Integer getIdAkcija() { return idAkcija; }
    public String getPoruka() { return poruka; }
    public boolean isProcitana() { return procitana; }
    public void setProcitana(boolean v) { procitana = v; }
    public Instant getDatumSlanja() { return datumSlanja; }
}
