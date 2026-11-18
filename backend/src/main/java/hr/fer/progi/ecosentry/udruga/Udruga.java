package hr.fer.progi.ecosentry.udruga;


import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;

@Entity
@Table(name = "UDRUGA")
public class Udruga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUdruga;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKorisnik", nullable = false, unique = true)
    private Korisnik korisnik;


    @Column(nullable = false)
    private String naziv;


    @Column(nullable = false)
    private String registarskiBroj;

    @Column(columnDefinition = "text")
    private String opis;

    @Column(nullable = false)
    private String regija;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOdobrenja statusOdobrenja = StatusOdobrenja.NA_CEKANJU;


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getIdUdruga() {
        return idUdruga;
    }


    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getRegistarskiBroj() {
        return registarskiBroj;
    }

    public void setRegistarskiBroj(String registarskiBroj) {
        this.registarskiBroj = registarskiBroj;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRegija() {
        return regija;
    }

    public void setRegija(String regija) {
        this.regija = regija;
    }

    public StatusOdobrenja getStatusOdobrenja() {
        return statusOdobrenja;
    }

    public void setStatusOdobrenja(StatusOdobrenja statusOdobrenja) {
        this.statusOdobrenja = statusOdobrenja;
    }



}
