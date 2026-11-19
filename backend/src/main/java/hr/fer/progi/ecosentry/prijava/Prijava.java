package hr.fer.progi.ecosentry.prijava;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "PRIJAVA")
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrijava;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPrijavitelj", nullable = false)
    private Korisnik prijavitelj;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKategorija", nullable = false)
    private KategorijaOtpada kategorija;

    // Akcija čišćenja u koju je prijava uvrštena. Za sada samo ID,
    // dok se ne doda entitet AkcijaCiscenja (onda zamijeniti s @ManyToOne).
    private Integer idAkcija;

    private String opis;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal sirina;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal duzina;

    private String adresa;
    private String grad;
    private String kvart;

    @Column(precision = 10, scale = 2)
    private BigDecimal procijenjeniVolumenM3;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPrijave status = StatusPrijave.PRIJAVLJENO;

    @Column(nullable = false)
    private Instant datumPrijave;

    @PrePersist
    void prijeSpremanja() {
        if (datumPrijave == null) {
            datumPrijave = Instant.now();
        }
    }

    public Integer getIdPrijava() { return idPrijava; }

    public Korisnik getPrijavitelj() { return prijavitelj; }
    public void setPrijavitelj(Korisnik v) { prijavitelj = v; }

    public KategorijaOtpada getKategorija() { return kategorija; }
    public void setKategorija(KategorijaOtpada v) { kategorija = v; }

    public Integer getIdAkcija() { return idAkcija; }
    public void setIdAkcija(Integer v) { idAkcija = v; }

    public String getOpis() { return opis; }
    public void setOpis(String v) { opis = v; }

    public BigDecimal getSirina() { return sirina; }
    public void setSirina(BigDecimal v) { sirina = v; }

    public BigDecimal getDuzina() { return duzina; }
    public void setDuzina(BigDecimal v) { duzina = v; }

    public String getAdresa() { return adresa; }
    public void setAdresa(String v) { adresa = v; }

    public String getGrad() { return grad; }
    public void setGrad(String v) { grad = v; }

    public String getKvart() { return kvart; }
    public void setKvart(String v) { kvart = v; }

    public BigDecimal getProcijenjeniVolumenM3() { return procijenjeniVolumenM3; }
    public void setProcijenjeniVolumenM3(BigDecimal v) { procijenjeniVolumenM3 = v; }

    public StatusPrijave getStatus() { return status; }
    public void setStatus(StatusPrijave v) { status = v; }

    public Instant getDatumPrijave() { return datumPrijave; }
}
