package hr.fer.progi.ecosentry.sanacija;

import hr.fer.progi.ecosentry.korisnik.Korisnik;
import hr.fer.progi.ecosentry.prijava.Fotografija;
import hr.fer.progi.ecosentry.prijava.Prijava;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "POTVRDA_SANACIJE")
public class PotvrdaSanacije {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPotvrda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPrijava", nullable = false)
    private Prijava prijava;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idFotografija", nullable = false)
    private Fotografija fotografija;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPodnositelj")
    private Korisnik podnositelj;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPotvrde status = StatusPotvrde.NA_CEKANJU;

    @Column(nullable = false)
    private Instant datumZahtjeva;

    @PrePersist
    void prijeSpremanja(){
        if(datumZahtjeva == null){
            datumZahtjeva = Instant.now();
        }
    }

    public Integer getIdPotvrda() {
        return idPotvrda;
    }


    public Prijava getPrijava() {
        return prijava;
    }

    public void setPrijava(Prijava prijava) {
        this.prijava = prijava;
    }

    public Fotografija getFotografija() {
        return fotografija;
    }

    public void setFotografija(Fotografija fotografija) {
        this.fotografija = fotografija;
    }

    public Korisnik getPodnositelj() {
        return podnositelj;
    }

    public void setPodnositelj(Korisnik podnositelj) {
        this.podnositelj = podnositelj;
    }

    public StatusPotvrde getStatus() {
        return status;
    }

    public void setStatus(StatusPotvrde status) {
        this.status = status;
    }

    public Instant getDatumZahtjeva() {
        return datumZahtjeva;
    }

}
