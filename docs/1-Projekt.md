# 1. Opseg projekta

## 1.1. Problem i cilj projekta

Nepropisno odlaganje otpada u prirodi i na javnim površinama predstavlja ekološki problem koji negativno utječe na okoliš i kvalitetu života građana. Građani koji uoče divlja odlagališta često nemaju jednostavan način prijave i praćenja postupka njihove sanacije. Istovremeno, ekološkim udrugama potrebne su informacije o lokacijama odlagališta kako bi mogle organizirati akcije čišćenja, dok komunalnim službama treba omogućiti učinkovit pregled i obradu zaprimljenih prijava.

Cilj projekta **EcoSentry** razvoj je aplikacije koja omogućuje digitalnu prijavu divljih odlagališta otpada, njihov pregled na interaktivnoj karti te praćenje statusa prijava i organizaciju akcija čišćenja.

Aplikacija povezuje tri glavne skupine korisnika: građane, ekološke udruge i komunalne redare. Građanima omogućuje prijavu odlagališta uz fotografiju i geografsku lokaciju, ekološkim udrugama planiranje akcija čišćenja i koordinaciju volontera, a komunalnim redarima provjeru prijava i pokretanje postupka službene sanacije.

Očekivana korist aplikacije jest jednostavnija komunikacija između uključenih korisnika, bolja preglednost prijavljenih odlagališta te lakše praćenje i organiziranje aktivnosti povezanih s njihovim uklanjanjem.

## 1.2 Ciljani korisnici i dionici

### Ciljani korisnici
| Korisnik |	Opis |	Potrebe |
| --- | --- | --- |
|Građani (prijavitelji) |	Osobe koje uočavaju nepropisno odložen otpad i žele prijaviti problem.	| Jednostavna prijava otpada, pregled karte, praćenje statusa prijava i primanje obavijesti. |
|Volonteri |	Osobe zainteresirane za sudjelovanje u organiziranim akcijama čišćenja. |	Pregled dostupnih akcija, prijava na akcije te informacije o lokaciji i vremenu održavanja. |
| Predstavnici eko-udruga |	Članovi organizacija koje organiziraju i provode akcije čišćenja. |	Pregled prijava otpada, organizacija akcija, upravljanje volonterima i potvrđivanje sanacije. |
| Komunalni redari |	Ovlašteni službenici koji provjeravaju prijave i koordiniraju službenu sanaciju. |	Provjera prijava, promjena statusa, izdavanje naloga i pregled statistike. |

### Dionici
| Dionik |	Interes u sustavu |	Utjecaj na projekt |
| --- | --- | --- |
| Građani i lokalna zajednica |	Čistiji okoliš, jednostavnije prijavljivanje otpada i transparentnost postupanja. |	Povratnim informacijama i prijavama utječu na korisnost sustava. |
| Ekološke udruge |	Učinkovitija organizacija akcija čišćenja i koordinacija volontera. |	Definiraju potrebe povezane s organizacijom akcija. |
| Komunalne službe i lokalna samouprava |	Brže otkrivanje divljih odlagališta, pouzdane prijave i učinkovitija sanacija. |	Utječu na zahtjeve za provjeru prijava, službeno postupanje i izvještavanje. |
| Volonteri |	Jednostavno pronalaženje akcija i sudjelovanje u očuvanju okoliša. |	Njihove potrebe utječu na oblikovanje procesa prijave na akcije. |
| Projektni tim |	Uspješna implementacija i održavanje aplikacije u skladu sa zahtjevima projekta.	| Donosi tehničke i organizacijske odluke tijekom razvoja. |
| Nastavnik i asistent kolegija |	Usklađenost rješenja sa zadatkom i kriterijima vrednovanja.	| Pregledavaju projekt, daju povratne informacije i vrednuju rezultate. |


## 1.3. Granice projekta

Projekt EcoSentry obuhvaća razvoj aplikacije za prijavu, pregled i praćenje divljih odlagališta otpada te koordinaciju aktivnosti njihove sanacije. Sustav povezuje građane, ekološke udruge i komunalne redare putem zajedničke baze podataka i interaktivne karte.

### Uključeno u opseg projekta

- Registracija i prijava korisnika putem vanjskog pružatelja identiteta uz OpenID Connect / OAuth 2.0.
- Podrška za tri korisničke uloge: građanin, ekološka udruga i komunalni redar.
- Podnošenje prijava odlagališta uz fotografiju, geografsku lokaciju i kategoriju otpada.
- Pregled prijavljenih odlagališta na interaktivnoj karti s oznakama različitih statusa.
- Pregled povijesti vlastitih prijava i obavijesti o promjenama njihova statusa.
- Provjera, uređivanje kategorije i odbijanje neispravnih prijava od strane komunalnog redara.
- Organizacija akcija čišćenja s mogućnošću definiranja termina, lokacije i maksimalnog broja volontera.
- Prijava građana za sudjelovanje u organiziranim akcijama čišćenja.
- Evidentiranje završetka akcije i učitavanje fotografije očišćene lokacije.
- Izdavanje službenih naloga za sanaciju odlagališta koja nisu prikladna za volontersko čišćenje.
- Upravljanje korisničkim ovlastima, odobravanje profila ekoloških udruga i blokiranje problematičnih korisnika.
- Pregled osnovnih statističkih podataka o prijavama i njihovom rješavanju.
- Integracija s vanjskim uslugama za prikaz karte, organizaciju događaja i vremensku prognozu.

### Izvan opsega projekta

- **Fizičko uklanjanje otpada:** aplikacija služi za prijavu i koordinaciju sanacije, ali ne provodi samo uklanjanje otpada.
- **Automatsko prepoznavanje otpada:** sustav ne predviđa automatsku klasifikaciju vrste otpada analizom fotografija.
- **Automatska provjera istinitosti prijava:** valjanost prijava provjerava komunalni redar, a ne sustav umjetne inteligencije.
- **Potpuna digitalizacija poslovanja komunalnih službi:** aplikacija omogućuje izradu i prosljeđivanje naloga, ali ne obuhvaća cjelokupno interno poslovanje komunalnih poduzeća.

Navedena ograničenja omogućuju usmjeravanje razvoja na ključne funkcionalnosti zadane projektnim zadatkom.

### Vanjske ovisnosti

Za ostvarivanje predviđenih funkcionalnosti aplikacija koristi sljedeće vanjske usluge:

| Vanjska usluga | Svrha korištenja |
|---|---|
| **Google Maps API** | Prikaz prijavljenih odlagališta i lokacija okupljanja na interaktivnoj karti, geokodiranje te prikaz rute do mjesta održavanja akcije. |
| **Google Calendar API** | Kreiranje kalendarskih događaja za organizirane akcije čišćenja, uz podatke o terminu, lokaciji i podsjetnicima. |
| **OpenWeatherMap API** | Dohvat vremenske prognoze za lokaciju i termin planirane akcije te prikaz upozorenja u slučaju nepovoljnih vremenskih uvjeta. |
| **Vanjski pružatelj identiteta (OpenID Connect / OAuth 2.0)** | Sigurna autentifikacija korisnika bez potrebe za implementacijom vlastitog sustava za upravljanje korisničkim lozinkama. |

Aplikacija je odgovorna za pohranu i upravljanje vlastitim podacima o korisnicima, odlagalištima, prijavama i akcijama čišćenja. Vanjske usluge koriste se za specifične funkcionalnosti koje nadopunjuju osnovni rad sustava.

## 1.4. Postojeći pristupi i opravdanost projekta

Građani mogu prijavljivati probleme povezane s nepropisno odloženim otpadom putem postojećih komunikacijskih kanala nadležnih lokalnih službi. Međutim, takvi postupci ne moraju omogućavati objedinjeni pregled prijava, praćenje njihove sanacije i izravno povezivanje s volonterskim akcijama čišćenja.

EcoSentry nastoji objediniti navedene mogućnosti u jednoj aplikaciji. Povezivanjem građana, ekoloških udruga i komunalnih redara sustav omogućuje pregled prijavljenih odlagališta na karti, praćenje promjena njihova statusa te organizaciju aktivnosti usmjerenih na uklanjanje otpada. Aplikacija pritom ne zamjenjuje nadležne komunalne službe, nego pruža dodatni alat za evidentiranje prijava i koordinaciju aktivnosti.
