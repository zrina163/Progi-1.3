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
- Ne spremaju se fotogravije u bazu, već njihov URL
- Lozinke i pristupni podatci se ne stavljaju u reprositorij

