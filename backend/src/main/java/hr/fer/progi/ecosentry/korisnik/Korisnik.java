package hr.fer.progi.ecosentry.korisnik;

import jakarta.persistence.*;

@Entity
@Table(name = "KORISNIK")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKorisnik;

    @Column(nullable = false, unique = true)
    private String oidcSub;

    private String ime;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Uloga uloga = Uloga.GRADANIN;

    private boolean blokiran;

    public Integer getIdKorisnik() { return idKorisnik; }

    public String getOidcSub() { return oidcSub; }
    public void setOidcSub(String oidcSub) { this.oidcSub = oidcSub; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }

    public boolean isBlokiran() { return blokiran; }
    public void setBlokiran(boolean blokiran) { this.blokiran = blokiran; }
}