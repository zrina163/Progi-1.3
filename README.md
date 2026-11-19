# EcoSentry 🌱

EcoSentry je digitalna platforma za prijavu, praćenje i sanaciju divljih odlagališta otpada koja povezuju građane, ekološke udruge i komunalne službe.

**Projekt na kolegiju:** Ovu je aplikaciju razvio studentski tim u sklopu kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu (FER).

## Opis aplikacije

Divlja odlagališta otpada predstavljaju ekološki, estetski i zdravstveni problem, a njihova prijava i sanacija često zahtijevaju suradnju više različitih sudionika. EcoSentry je zamišljen kao centralizirana platforma koja građanima omogućuje prijavu odlagališta, ekološkim udrugama organizaciju akcija čišćenja, a komunalnim redarima provjeru prijava i koordinaciju službene sanacije.

Planirani sustav obuhvaća korisnička sučelja prilagođena pojedinim ulogama, interaktivnu kartu prijava te integracije s vanjskim uslugama za prikaz lokacija, upravljanje kalendarom i vremensku prognozu.

**Ključne funkcionalnosti**

* prijava divljih odlagališta uz fotografiju, kategoriju otpada i geografsku lokaciju
* pregled prijava na karti i praćenje njihova statusa
* organizacija akcija čišćenja i prijava volontera
* verifikacija prijava i koordinacija sanacije putem komunalnih službi

Navedene funkcionalnosti predstavljaju planirani opseg projekta; njihova dostupnost ovisi o trenutačnoj fazi implementacije.

Za potpuni opseg, zahtjeve, oblikovanje i dokaze ispitivanja pogledajte [docs](docs/Home.md).

<a id="deployment"></a>
## Postavljanje (engl. deployment)

<!-- UPUTA: Ovaj odjeljak opisuje već postavljenu aplikaciju koju se  može otvoriti i isprobati bez lokalnog pokretanja izvornog koda.
Za Iteraciju 1 i Završni pregled navedite konkretne podatke. Provjerite vodi li URL na javno dostupnu pokrenutu aplikaciju i radi li navedeni tijek uporabe. Nikada ne objavljujte lozinke ni pristupne tokene. -->

**Javno dostupna aplikacija:** [Javni URL]

**Isprobajte:** [Jedna ili dvije kratke radnje koje pokazuju funkcionalan korisnički tijek.]

**Demonstracija:** [Objasnite kako dobiti potreban ograničeni pristup ako je prijava obavezna; u suprotnom napišite „Korisnički račun nije potreban.” Ovdje ne objavljujte lozinke ni pristupne tokene.]

**Ograničenja:** [Kratko navedite ograničenja postavljene inačice ili postavite poveznicu na jedinstveno mjerodavno mjesto u dokumentaciji s poznatim problemima.]

[Vodič za postavljanje i pokretanje](docs/6-Postavljanje-instalacija-i-konfiguracija.md) opisuje smještaj aplikacije (engl. hosting) koji tim stvarno koristi. Tim može koristiti Render ili drugu platformu koju ste dogovorili s asistentom/demonstratorom.

<a id="quick-start"></a>
## Brzi početak / instalacija

### Preduvjeti

- Java 21 (JDK)
- Node.js 22.13+ (22.x) i npm
- PostgreSQL 13 ili noviji
- Git
- Google OAuth i Google Maps API pristupni podaci

### Preuzimanje repozitorija

```bash
git clone https://github.com/zrina163/Progi-1.3.git
cd Progi-1.3
```

### Baza podataka

Kreirati PostgreSQL bazu podataka `ecosentry` i izvršiti
skriptu `db/ecosentry_baza.sql`.

Za testiranje se mogu učitati demonstracijski podaci
iz skripte `db/demo_podaci.sql`.

Detaljne upute: [Baza podataka](db/Readme.md).

### Pokretanje backenda

U direktoriju `backend` potrebno je kreirati `.env`
datoteku prema predlošku `.env.example` i konfigurirati
Google OAuth pristupne podatke.

Po potrebi konfigurirati DB_URL, DB_USER i DB_PASSWORD
prema lokalnoj PostgreSQL bazi.

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Backend je dostupan na http://localhost:8080.

### Pokretanje frontenda

U direktoriju `frontend` kreirati `.env.local` i
postaviti VITE_GOOGLE_MAPS_API_KEY.
Za lokalno pokretanje ostaviti VITE_API_URL praznim
kako bi se koristio Vite proxy.

U drugom terminalu, iz korijena repozitorija:

```bash
cd frontend
npm ci
npm run dev
```

Aplikacija je dostupna na http://localhost:5173.

### Provjera rada

Otvoriti aplikaciju u pregledniku, provjeriti prikaz
interaktivne karte i dohvat prijava iz baze podataka.

Dohvat prijava moguće je provjeriti i na
http://localhost:8080/api/public/prijave.

## Tehnologije

| Tehnologija | Uloga u projektu | Razlog uporabe |
| --- | --- | --- |
| React | Korisničko sučelje | Modularna izrada komponenti |
| Vite | Razvoj i izgradnja frontenda | Brzo pokretanje i razvoj aplikacije |
| Java / Spring Boot | Backend i REST API | Implementacija poslovne logike |
| PostgreSQL | Baza podataka | Relacijski model i integritet podataka |
| Spring Security | Autentikacija i autorizacija | Upravljanje korisničkim ovlastima |
| Google OAuth 2.0 / OpenID Connect | Prijava korisnika | Prijava bez pohrane lozinki |
| Google Maps API | Interaktivna karta | Prikaz lokacija prijavljenog otpada |
| Google Calendar API | Planirana integracija kalendara | Organizacija akcija čišćenja |
| OpenWeatherMap API | Planirana integracija vremenske prognoze | Informiranje organizatora akcija |

**Analiza primjera:** Svaki redak opisuje ulogu ili posljedicu konkretne tehnologije u ovoj aplikaciji. Verzije paketa pripadaju datotekama s ovisnostima projekta; verzije izvršnog okruženja i konfiguracija važna za postavljanje pripadaju vodiču za instalaciju. Važne kompromise u oblikovanju (engl. design trade-offs) objasnite na stranici arhitekture u direktoriju `docs/` umjesto da ovdje ponavljate cijeli zapis odluke. Navodite samo usluge koje vaš tim stvarno integrira.

</details>

<a id="team-members"></a>
## Članovi tima

| Član | Glavna uloga ili doprinos |
| --- | --- |
| Antonio Dragun | backend |
| Fran Hercig | frontend |
| Jure Jerneić | backend |
| Zrina Ozana Medić | baze podataka |
| Ivona Mihaljević | frontend |
| Tin Pavlić | baze podataka |
| Lorena Pratljačić | backend |

Uloge se tijekom projekta mogu mijenjati. Repozitorij i dogovoreni alat za praćenje zadataka (engl. task tracker) služe kao dodatni dokazi timskog rada.

## Doprinos projektu

Zadaci se evidentiraju putem GitHub Issues.
Članovi tima razvijaju funkcionalnosti na zasebnim
granama, a promjene se uključuju u glavnu granu
putem pull requestova nakon pregleda članova tima.

- [GitHub Issues](https://github.com/zrina163/Progi-1.3/issues)
- [Pull requests](https://github.com/zrina163/Progi-1.3/pulls)

<a id="license"></a>

**Status licence:** Tim još nije odabrao licencu aplikacije.

Dokumentacijski predložak kolegija sadrži otvorene obrazovne sadržaje (engl. Open Educational Resources, OER) i licenciran je licencom Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International. Ta licenca **ne određuje automatski licencu studentske aplikacije**; tim treba jasno navesti licencu svojeg projekta u prethodnom retku i datoteci `LICENSE`, ako je primjenjivo.

Kod trećih strana, skupovi podataka, slike, ikone i drugi sadržaji zadržavaju vlastite licence i uvjete uporabe. [Po potrebi postavite poveznicu na zahvale i izvore.]

<a id="ai-usage"></a>
## Uporaba umjetne inteligencije [![AI Usage: Disclosed](https://img.shields.io/badge/AI%20Usage-Disclosed-blue.svg)](https://www.fer.unizg.hr/_download/repository/Policy%20on%20the%20appropriate%20use%20of%20artificial%20intelligence%20at%20the%20faculty%20of%20electrical%20engineering%20and%20computing%5B1%5D.pdf)

Ovaj projekt slijedi FER-ova [pravila o primjerenoj uporabi umjetne inteligencije](https://www.fer.unizg.hr/_download/repository/Policy%20on%20the%20appropriate%20use%20of%20artificial%20intelligence%20at%20the%20faculty%20of%20electrical%20engineering%20and%20computing%5B1%5D.pdf).

**Izjava:** Tijekom razvoja projekta korišteni su alati umjetne inteligencije kao pomoć pri programiranju, otklanjanju pogrešaka i provjeri projektne dokumentacije. Svaki član pregledao je nastali kod te može objasniti svoj doprinos.

<a id="code-of-conduct-and-support"></a>
## Kodeks ponašanja i podrška [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)

Od članova tima očekuje se pridržavanje **Kodeksa ponašanja studenata** Fakulteta elektrotehnike i računarstva Sveučilišta u Zagrebu, pravila timskog rada na kolegiju i [Etičkog kodeksa IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html). [Postavite poveznicu na `CODE_OF_CONDUCT.md` ako se ta datoteka koristi u vašem repozitoriju.]

---

Struktura dokumentacije prilagođena je predlošku kolegija Programsko inženjerstvo FER-a autora [Vlade Sruka](https://www.fer.unizg.hr/vlado.sruk), licenciranom pod [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/). Aplikaciju i njezin sadržaj izradio je gore navedeni studentski tim.
