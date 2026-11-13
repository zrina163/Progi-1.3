package hr.fer.progi.ecosentry.prijava;


import hr.fer.progi.ecosentry.akcija.AkcijaCiscenja;
import hr.fer.progi.ecosentry.kategorija.KategorijaOtpada;
import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "PRIJAVA")
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrijava;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPrijavitelj")
    private Korisnik idPrijavitelj;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAkcija")
    private AkcijaCiscenja idAkcija;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKategorija", nullable = false)
    private KategorijaOtpada idKategorija;

    @Column(columnDefinition = "text")
    private String opis;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal sirina;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal duzina;

    private String adresa;
    private String grad;
    private String kvart;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPrijave status = StatusPrijave.PRIJAVLJENO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolumenOtpada procijenjeniVolumenM3 = VolumenOtpada.LOW;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime datumPrijave;

    public Korisnik getIdPrijavitelj() {
        return idPrijavitelj;
    }

    public void setIdPrijavitelj(Korisnik idPrijavitelj) {
        this.idPrijavitelj = idPrijavitelj;
    }

    public Integer getIdprijava() {
        return idPrijava;
    }


    public AkcijaCiscenja getIdAkcija() {
        return idAkcija;
    }

    public void setIdAkcija(AkcijaCiscenja idAkcija) {
        this.idAkcija = idAkcija;
    }

    public KategorijaOtpada getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(KategorijaOtpada idKategorija) {
        this.idKategorija = idKategorija;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getSirina() {
        return sirina;
    }

    public void setSirina(BigDecimal sirina) {
        this.sirina = sirina;
    }

    public BigDecimal getDuzina() {
        return duzina;
    }

    public void setDuzina(BigDecimal duzina) {
        this.duzina = duzina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getKvart() {
        return kvart;
    }

    public void setKvart(String kvart) {
        this.kvart = kvart;
    }

    public StatusPrijave getStatus() {
        return status;
    }

    public void setStatus(StatusPrijave status) {
        this.status = status;
    }

    public VolumenOtpada getProcijenjeniVolumenM3() {
        return procijenjeniVolumenM3;
    }

    public void setProcijenjeniVolumenM3(VolumenOtpada procijenjeniVolumenM3) {
        this.procijenjeniVolumenM3 = procijenjeniVolumenM3;
    }

    public OffsetDateTime getDatumPrijave() {
        return datumPrijave;
    }
}
