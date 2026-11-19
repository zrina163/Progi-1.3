package hr.fer.progi.ecosentry.akcija;


import hr.fer.progi.ecosentry.udruga.Udruga;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "AKCIJA_CISCENJA")
public class AkcijaCiscenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAkcija;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUdruga", nullable = false)
    private Udruga udruga;

    @Column(nullable = false)
    private String naziv;

    @Column(columnDefinition = "text")
    private String opis;

    @Column(nullable = false)
    private Integer maxVolontera;

    @Column(nullable = false)
    private Instant pocetak;

    @Column(nullable = false)
    private BigDecimal sastanakSirina;

    @Column(nullable = false)
    private BigDecimal sastanakDuzina;

    @Column(columnDefinition = "text")
    private String sastanakAdresa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAkcije status = StatusAkcije.POKRENUTO;


    private String googleEventId = null;

    public Integer getIdAkcija() {
        return idAkcija;
    }


    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getMaxVolontera() {
        return maxVolontera;
    }

    public void setMaxVolontera(Integer maxVolontera) {
        this.maxVolontera = maxVolontera;
    }

    public Instant getPocetak() {
        return pocetak;
    }

    public void setPocetak(Instant pocetak) {
        this.pocetak = pocetak;
    }

    public BigDecimal getSastanakSirina() {
        return sastanakSirina;
    }

    public void setSastanakSirina(BigDecimal sastanakSirina) {
        this.sastanakSirina = sastanakSirina;
    }

    public BigDecimal getSastanakDuzina() {
        return sastanakDuzina;
    }

    public void setSastanakDuzina(BigDecimal sastanakDuzina) {
        this.sastanakDuzina = sastanakDuzina;
    }

    public String getSastanakAdresa() {
        return sastanakAdresa;
    }

    public void setSastanakAdresa(String sastanakAdresa) {
        this.sastanakAdresa = sastanakAdresa;
    }

    public StatusAkcije getStatus() {
        return status;
    }

    public void setStatus(StatusAkcije status) {
        this.status = status;
    }

    public String getGoogleEventId() {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId) {
        this.googleEventId = googleEventId;
    }

    public Udruga getUdruga() {
        return udruga;
    }

    public void setUdruga(Udruga udruga) {
        this.udruga = udruga;
    }

}
