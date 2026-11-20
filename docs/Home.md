# EcoSentry — Projektna dokumentacija

**Kolegij:** [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz), Fakultet elektrotehnike i računarstva Sveučilišta u Zagrebu  
**Akademska godina:** 2026./2027.  
**Tim:** G01 — Progi 1.3
**Nastavnik:** Vlado Sruk  
**Asistent / demonstratori:** Goran Rajić / Nik Čolić, Eva Huljaj

## Pregled projekta

**EcoSentry** je aplikacija namijenjena prijavi i praćenju divljih odlagališta otpada te organizaciji akcija njihova čišćenja. Sustav povezuje građane, ekološke udruge i komunalne redare kako bi omogućio jednostavnije evidentiranje odlagališta, pregled prijava na interaktivnoj karti i koordinaciju aktivnosti sanacije.

Građani mogu prijavljivati odlagališta uz fotografiju i geografsku lokaciju, pratiti status prijava i sudjelovati u volonterskim akcijama. Ekološke udruge mogu organizirati akcije čišćenja i upravljati prijavama volontera, dok komunalni redari provjeravaju prijave, upravljaju njihovim statusima i pokreću postupke službene sanacije.

**Aplikacija i osnovno postavljanje:** [README repozitorija](../README.md)  
**Javno dostupna aplikacija:** [EcoSentry](https://eco-sentry-araka0b8efhyfbfx.austriaeast-01.azurewebsites.net)

## Članovi tima i uloge

Ažurirani popis članova tima i njihovih glavnih uloga i doprinosa nalazi se u [README-u repozitorija](../README.md#team-members).

## Kazalo dokumentacije

| Stranica | Svrha |
|---|---|
| [1. Opseg projekta](1-Projekt.md) | Problem, cilj, korisnici, postojeći pristupi i granice projekta |
| [2. Analiza zahtjeva](2-Analiza-zahtjeva.md) | Funkcijski i nefunkcijski zahtjevi, ograničenja i sljedivost |
| [3. Obrasci uporabe](3-Obrasci-uporabe.md) | Akteri, obrasci uporabe te glavni i alternativni scenariji |
| [4. Arhitektura i oblikovanje](4-Arhitektura-i-oblikovanje.md) | Arhitektura sustava, model podataka te relevantni UML dijagrami |
| [5. Ispitivanje](5-Ispitivanje.md) | Planiranje i provedba ispitivanja, rezultati i poznati nedostaci |
| [6. Postavljanje, instalacija i konfiguracija](6-Postavljanje-instalacija-i-konfiguracija.md) | Upute za lokalno pokretanje, konfiguraciju i javno postavljanje aplikacije |
| [7. Zaključak i budući rad](7-Zakljucak-i-buduci-rad.md) | Ostvareni rezultati, ograničenja i moguća buduća poboljšanja |
| [A. Pregled aktivnosti grupe](A-Pregled-aktivnosti-grupe.md) | Aktivnosti, doprinosi članova i izazovi tijekom razvoja |

Dokumentacija se izrađuje i ažurira tijekom cijelog semestra, u skladu s napretkom projekta.

## Ključne točke projekta

| Kontrolna točka | Demonstracija | Video |
|---|---|---|
| M0 — Prezentacija i rasprava | Analiza zahtjeva, ključni obrasci uporabe i otvorena pitanja | Nije predviđen |
| M1 — Prva predaja (20. 11. 2026.) | [EcoSentry](https://eco-sentry-araka0b8efhyfbfx.austriaeast-01.azurewebsites.net) | Nije predviđen |
| M2 — Alfa prezentacija | Demonstracija razvijenih funkcionalnosti i rezultata ispitivanja | [DOPUNITI ako je snimljen] |
| M3 — Završna predaja (22. 1. 2027.) | [DOPUNITI poveznicom na završnu inačicu] | [DOPUNITI ako je snimljen] |

Dokumentacija se kontinuirano ažurira kako se aplikacija razvija.

## Organizacija projekta i razvojni proces

**Način rada:** Tim je podijeljen na razvoj frontenda, backenda i baze podataka. Članovi rade na zasebnim granama, a promjene se pregledavaju i uključuju u glavnu granu putem pull requestova.

**Praćenje trenutačnih zadataka i nedostataka:**
[GitHub Issues](https://github.com/zrina163/Progi-1.3/issues).

**Kontinuirana integracija i postavljanje:** GitHub Actions automatizira provjeru, izgradnju i postavljanje aplikacije na Azure App Service.

## Pravila dokumentacije

Projektna dokumentacija nalazi se u direktoriju `/docs` unutar GitHub repozitorija. Dokumenti se međusobno povezuju kako bi se omogućilo jednostavno snalaženje i praćenje razvoja projekta.

Za potrebne UML dijagrame koristi se PlantUML. Uz izvorne `.puml` datoteke čuvaju se i odgovarajuće slike dijagrama.

Dokumentacija se kontinuirano usklađuje sa stvarnim stanjem aplikacije. Planirane funkcionalnosti razlikuju se od implementiranih, a promjene zahtjeva i projektnih odluka evidentiraju se tijekom razvoja.

## Akademski integritet, ponašanje i licenciranje

README repozitorija sadrži projektnu [izjavu o uporabi umjetne inteligencije](../README.md#ai-usage), [informacije o kodeksu ponašanja i podršci](../README.md#code-of-conduct-and-support) te [status licence ili ponovne uporabe](../README.md#license). Pristupni podaci i druge tajne nikada se ne objavljuju u dokumentaciji niti dijele u običnim porukama.


## Izjava o uporabi umjetne inteligencije

Kratak sažetak: [README – Uporaba umjetne inteligencije](../README.md#ai-usage). Ovaj odjeljak bilježi koji su alati korišteni, za koju dokumentaciju ili kod te kako je tim provjerio rezultat. Slijedi FER-ova [pravila o primjerenoj uporabi umjetne inteligencije](https://www.fer.unizg.hr/_download/repository/Policy%20on%20the%20appropriate%20use%20of%20artificial%20intelligence%20at%20the%20faculty%20of%20electrical%20engineering%20and%20computing%5B1%5D.pdf).

### Korišteni alati

| Alat | Inačica ili model | Koristio/la | Glavna svrha |
| --- | --- | --- | --- |
| Claude | Claude Opus | Tim | Pomoć pri razvoju aplikacije, otklanjanju pogrešaka, ispravljanju dokumentacije |
| ChatGPT | - | Fran Hercig | Pomoć pri postavljanju aplikacije |

### Opseg doprinosa umjetne inteligencije

| Stranica, datoteka ili artefakt | Vrsta pomoći | Što je alat proizveo | Što je tim zadao i promijenio | Lokacija |
| --- | --- | --- | --- | --- |
| Poglavlja 1–4 projektne dokumentacije | Izrada nacrta | Početni nacrti opisa projekta, zahtjeva, obrazaca uporabe i arhitekture | Tim je definirao zahtjeve te pregledao i prilagodio sadržaj projektu | `docs/`, poglavlja 1–4 |
| Frontend, Backend | Pomoć pri programiranju | Prijedlozi implementacije i oblikovanja | Prilagodba komponenti i zahtjevi aplikacije | `frontend/` , `backend/`|
| Postavljanje aplikacije | Tehnička pomoć | Upute za konfiguraciju Azurea, baze podataka i CI/CD postupka te otklanjanje greški | Odabir usluga, konfiguracija i provjera rada aplikacije | [PR #41](https://github.com/zrina163/Progi-1.3/pull/41) i [PR #42](https://github.com/zrina163/Progi-1.3/pull/42) |

### Provjera generiranog sadržaja

| Provjera | Kako je provedena | Dokaz |
| --- | --- | --- |
| Činjenična točnost | Usporedba sadržaja s izvornim kodom i pokrenutom aplikacijom | Pull requestovi i ispitivanje aplikacije |
| Nema tvrdnji o neostvarenim funkcionalnostima | Razlikovanje implementiranih i planiranih funkcionalnosti | `README.md` i projektna dokumentacija |
| Dosljednost s drugim stranicama | Usklađivanje naziva, zahtjeva i opisa funkcionalnosti | Dokumentacija u direktoriju `docs/`, [PR #35](https://github.com/zrina163/Progi-1.3/pull/35), [PR #36](https://github.com/zrina163/Progi-1.3/pull/36) |
| Izvori dijagrama | Pregled i provjera izrađenih UML dijagrama | PlantUML datoteke i slike dijagrama |
| Povjerljivi sadržaj | Odvajanje pristupnih podataka od javnog repozitorija | `.env.example`, GitHub Secrets i Azure Environment variables |

### Pogreške pronađene u izlazu umjetne inteligencije

| Problem | Kako je pronađen | Rješenje |
| --- | --- | --- |
| Predložena konfiguracija PostgreSQL baze na Azureu nije bila dostupna | Pokušaj postavljanja baze podataka | PostgreSQL baza postavljena je na platformi Neon |