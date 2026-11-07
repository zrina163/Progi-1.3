##BAZA PODATAKA

PostgreSQL baza za aplikaciju EcoSentry.

sadržaj:  - "ecosentry_baza.sql" -> skripta za stvaranje tablica
          - "ecosentry_dijagram.dbml" -> izvor ER dijagrama

-korišten PostgreSQL 13 na novije

#POKRETANJE
bash
psql -U postgres -c "CREATE DATABASE ecosentry;"
psql -U postgres -d ecosentry -f db/ecosentry_baza.sql

#NAPOMENE
- PostgreSQL imena stupaca pretvara u mala slova (recimo "idKorisnik" -> "idkorisnik")
- Ne spremaju se fotografije u bazu, već njihov URL
- Lozinke i pristupni podatci se ne stavljaju u reprositorij

## Demonstracijski podaci

Datoteka `demo_podaci.sql` sadrži testne podatke
za razvoj i provjeru aplikacije EcoSentry.

Podaci uključuju:
- testnog građanina
- testnog organizatora i odobrenu eko-udrugu
- dvije prijave divljih odlagališta
- dva zapisa fotografija
- jednu akciju čišćenja povezanu s prijavom

### Pokretanje

1. Kreirati bazu `ecosentry`.
2. Izvršiti `ecosentry_baza.sql`.
3. Izvršiti `demo_podaci.sql`.

Primjer iz terminala, iz korijena projekta:

```bash
psql -U postgres -d ecosentry -f db/demo_podaci.sql
```

Skripta je namijenjena razvojnom i testnom
okruženju, a ne produkcijskoj bazi.

Sadrži provjere koje sprječavaju dupliciranje
predviđenih demo zapisa pri ponovnom pokretanju.

URL-ovi fotografija su demonstracijski i
ne predstavljaju stvarno dostupne slike otpada.