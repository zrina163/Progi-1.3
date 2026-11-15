package hr.fer.progi.ecosentry.prijava;

import jakarta.persistence.*;

@Entity
@Table(name = "KATEGORIJA_OTPADA")
public class KategorijaOtpada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKategorija;

    @Column(nullable = false, unique = true)
    private String nazivKategorije;

    public Integer getIdKategorija() { return idKategorija; }

    public String getNazivKategorije() { return nazivKategorije; }
    public void setNazivKategorije(String v) { nazivKategorije = v; }
}
