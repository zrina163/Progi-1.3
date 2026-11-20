# 2. Analiza zahtjeva

## 2.1 Funkcijski zahtjevi

| ID zahtjeva | Opis | Prioritet | Izvor | Kriteriji prihvaćanja |
| --- | --- | --- | --- | --- |
| *F-01.1* | Korisnik se mora moći prijaviti putem vanjskog pružatelja identiteta. | Visok |Zahtjev dionika | Korisniku mora biti omogućena prijava i registracija preko OpenID Connect/OAuth 2.0 servisa. Pri prvoj prijavi korisnik dobiva ulogu Građanin. |
| *F-01.2* | Korisnik se mora moći odjaviti iz sustava. | Visok | Odluka tima | Mora biti omogućena odjava korisničkog računa. Nakon odjave zaštićene radnje nisu dostupne bez ponovne prijave. |
| *F-01.3* | Korisnik mora moći izbrisati svoj profil. | Srednji | Odluka tima | Korisnik ima mogućnost u postavkama profila obrisati profil. Podnesene prijave ostaju u sustavu bez podataka o podnositelju. |
| *F-02.1* | Korisnik mora moći zatražiti profil eko-udruge. | Visok |Zahtjev dionika | Korisnik mora moći poslati zahtjev s nazivom udruge, brojem iz registra udruga i regijom djelovanja. Nema ovlasti organizatora do odobrenja komunalnog redara |
| *F-02.2* | Komunalni redar mora moći odobriti profil eko-udruge. | Visok |Zahtjev dionika | Redaru mora biti omogućeno odobravanje ili odbijanje zahtjeva za profil udruge. |
| *F-02.3* | Komunalni redar mora moći blokirati korisnika. | Srednji |Zahtjev dionika | Redaru mora biti omogućeno blokiranje korisnika koji šalju lažne prijave. Blokirani korisnik ne može podnositi prijave ni prijavljivati se na akcije. |
| *F-03.1* | Građanin mora moći prijaviti divlje odlagalište otpada. | Visok |Zahtjev dionika | Prijava mora sadržavati GPS lokaciju, fotografiju (kamera ili galerija) i kategoriju otpada (plastika, građevinski, glomazni otpad i sl.). Prijava bez fotografije ili kategorije ne smije se pohraniti. |
| *F-03.2* | Građanin mora moći pregledati povijest svojih prijava. | Visok |Zahtjev dionika | Na profilu moraju biti vidljive sve prijave korisnika s datumom, kategorijom i trenutnim statusom. |
| *F-03.3* | Građanin mora dobiti obavijest o promjeni statusa prijave. | Visok |Zahtjev dionika | Obavijest se mora poslati kao push ili unutaraplikacijska obavijest i sadržavati broj prijave i novi status. |
| *F-04.1* | Prijave moraju biti prikazane na interaktivnoj karti. | Visok |Zahtjev dionika | Prijave se prikazuju kao pribadače na Google Maps karti, obojene prema statusu: crveno – Prijavljeno, žuto – Akcija u tijeku, zeleno – Očišćeno. Klikom na pribadaču otvaraju se detalji prijave s fotografijom. |
| *F-04.2* | Sustav mora podržavati geokodiranje lokacija. | Srednji |Zahtjev dionika | Koordinate prijave moraju se pretvarati u tekstualnu adresu, a upisana adresa u koordinate na karti. |
| *F-04.3* | Volonter mora moći vidjeti rutu do mjesta okupljanja. | Nizak |Zahtjev dionika | Na stranici akcije mora biti prikazano mjesto okupljanja na karti i ruta dolaska od trenutne lokacije korisnika. |
| *F-05.1* | Komunalni redar mora moći verificirati prijave. | Visok |Zahtjev dionika | Redaru mora biti omogućeno potvrđivanje prijave ili njezino odbacivanje kao lažne (spam, slike bez otpada). Odbačena prijava ne prikazuje se na karti. |
| *F-05.2* | Komunalni redar mora moći izmijeniti kategoriju otpada. | Srednji |Zahtjev dionika | Redaru mora biti omogućena izmjena kategorije ako ju je građanin pogrešno odabrao. |
| *F-05.3* | Komunalni redar mora moći izdati službeni nalog. | Visok |Zahtjev dionika | Za opasan ili prevelik otpad redar jednim klikom generira službeni nalog i prosljeđuje ga ugovorenoj komunalnoj tvrtki. Status prijave mijenja se u „Službena sanacija”. |
| *F-05.4* | Komunalni redar mora moći zatvoriti saniranu prijavu. | Visok |Zahtjev dionika | Redar potvrđuje zahtjev udruge za zatvaranje ili sam učitava fotografiju saniranog stanja. Prijava prelazi u status Očišćeno, a podnositelj automatski dobiva obavijest. |
| *F-05.5* | Komunalni redar mora imati pristup analitici. | Srednji |Zahtjev dionika | Redar mora moći pregledati grafikone: broj prijava po mjesecima, najkritičniji kvartovi u gradu i postotak uspješno riješenih prijava. |
| *F-06.1* | Eko-udruga mora imati upravljačku ploču s kartom. | Visok |Zahtjev dionika | Udruga mora vidjeti sve aktivne (crvene) prijave u svojoj regiji te klikom na pribadaču detalje, slike i procjenu volumena otpada. |
| *F-06.2* | Eko-udruga mora moći kreirati akciju čišćenja. | Visok |Zahtjev dionika | Udruga odabire jednu ili više pribadača i definira naziv, opis, najveći broj volontera te datum, vrijeme i mjesto okupljanja. Uključene prijave prelaze u status Akcija u tijeku. |
| *F-06.3* | Eko-udruga mora imati kalendar akcija. | Srednji |Zahtjev dionika | Sustav mora automatski voditi kalendar svih planiranih akcija udruge s prikazom popunjenosti volonterskih mjesta. |
| *F-06.4* | Eko-udruga mora moći odgoditi akciju ili promijeniti termin. | Srednji |Zahtjev dionika | Nakon promjene termina prijavljeni volonteri moraju dobiti obavijest, a događaj u Google kalendaru mora se ažurirati. |
| *F-06.5* | Eko-udruga mora moći potvrditi sanaciju. | Visok |Zahtjev dionika | Nakon akcije voditelj udruge učitava fotografiju očišćenog prostora kao „Dokaz o sanaciji” i šalje zahtjev za zatvaranje prijave. Zahtjev se ne može poslati bez fotografije. |
| *F-07.1* | Građanin se mora moći prijaviti kao volonter. | Visok |Zahtjev dionika | Građanin mora moći pregledati akcije čišćenja u blizini i jednim klikom se prijaviti; prijava nije moguća kad je akcija popunjena. |
| *F-07.2* | Volonter mora moći otkazati sudjelovanje u akciji. | Nizak | Odluka tima | Mora biti omogućeno otkazivanje prijave na akciju; oslobođeno mjesto ponovno je dostupno drugima. |
| *F-08* | Akcije moraju biti sinkronizirane s Google kalendarom. | Visok |Zahtjev dionika | Pri kreiranju akcije i prijavi volontera mora se automatski stvoriti događaj u Google kalendaru korisnika s točnim terminom, poveznicom na lokaciju na karti, kratkim uputama i podsjetnikom 2 sata prije akcije. |
| *F-09.1* | Mora se prikazivati vremenska prognoza za akciju. | Visok |Zahtjev dionika | U sučelju udruge i volontera mora se prikazati prognoza s OpenWeatherMap API-ja za GPS koordinate odlagališta i dan akcije. |
| *F-09.2* | Organizator mora dobiti upozorenje o nevremenu. | Visok |Zahtjev dionika | Ako je za termin akcije najavljeno nevrijeme (jaka kiša, oluja, ekstremne temperature), sučelje mora istaknuti crveno upozorenje i ponuditi brzu odgodu ili promjenu termina. |


## 2.2 Nefunkcijski zahtjevi

### Zahtjevi za održavanje

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-1.1 | Sustav mora biti jednostavan za održavanje i nadogradnju, s jasnim uputama za postavljanje i konfiguraciju vanjskih servisa (Google Maps, Google Calendar, OpenWeatherMap). | Srednji | Prijedlog tima (ODL-04) | Pregled dokumentacije i probno postavljanje sustava prema uputama. |
| NF-1.2 | Konfiguracija sustava (API ključevi, adrese servisa, podaci za bazu) mora biti odvojena od izvornog koda putem varijabli okruženja. | Visok | Prijedlog tima (ODL-04) | Pregled izvornog koda i konfiguracije; provjera da se sustav pokreće s vrijednostima iz varijabli okruženja. |
| NF-1.3 | Sustav mora imati redovite sigurnosne kopije baze podataka i učitanih fotografija. | Srednji | Prijedlog tima (ODL-04) | Pregled rasporeda sigurnosnog kopiranja i probno vraćanje baze podataka i fotografija. |
| NF-1.4 | Nedostupnost vanjskog servisa (OpenWeatherMap, Google Calendar) ne smije onemogućiti podnošenje prijava ni kreiranje akcija i prijavu na njih; korisnik mora dobiti poruku o nedostupnosti. | Srednji | Prijedlog tima (ODL-04) | Simulacija nedostupnosti vanjskih servisa; provjera da se osnovna radnja dovršava i prikazuje jasna poruka. |

### Zahtjevi za sigurnost

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-2.1 | Sva komunikacija između klijenta i poslužitelja mora se odvijati preko HTTPS-a (TLS). | Visok | Prijedlog tima (ODL-05) | Pregled mrežnog prometa i TLS konfiguracije. |
| NF-2.2 | Autentikacija se mora provoditi isključivo putem vanjskog pružatelja identiteta (OpenID Connect / OAuth 2.0); Sustav ne smije pohranjivati korisničke lozinke. | Visok | Projektni zadatak | Pregled implementacije autentikacije, baze podataka i konfiguracije pružatelja identiteta. |
| NF-2.3 | Ovlasti uloga (Građanin, Eko-udruga, Komunalni redar) moraju se provjeravati na poslužitelju. Neprijavljeni korisnik dobiva odgovor 401, a korisnik bez potrebne uloge odgovor 403. | Visok | Prijedlog tima (ODL-05) | Slanje zaštićenih API zahtjeva bez tokena i s tokenom nedovoljne uloge. Provjera odgovora 401/403. |
| NF-2.4 | Tajni ključevi (OAuth client secret, OpenWeatherMap ključ) ne smiju se nalaziti u repozitoriju ni u klijentskom kodu. Google Maps ključ mora biti ograničen na domenu aplikacije. | Visok | Prijedlog tima (ODL-05) | Pregled repozitorija, klijentskog koda i postavki ograničenja ključa u Google Cloud konzoli. |
| NF-2.5 | Sustav mora biti u skladu s GDPR-om: javna karta ne prikazuje identitet podnositelja prijave, s fotografija se uklanjaju EXIF metapodaci, a korisnik može izbrisati svoj profil. | Visok | Prijedlog tima (ODL-05) | Pregled javnog prikaza, analiza metapodataka fotografije i provjera brisanja profila. |
| NF-2.6 | Sustav mora uključivati zaštitu od zlouporabe: učitane datoteke ograničene su na JPEG i PNG do 10 MB, a broj prijava po korisniku ograničen je (rate limiting) radi sprječavanja spama. | Srednji | Prijedlog tima (ODL-05) | Pokušaj učitavanja nedopuštenih datoteka i slanja većeg broja prijava u zadanom vremenu. |

### Zahtjevi za performanse

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-3.1 | Karta s prijavama mora se učitati unutar 3 sekunde; pri većem broju prijava pribadače se grupiraju (clustering). | Srednji | Prijedlog tima (ODL-06) | Mjerenje učitavanja karte s oglednim skupom prijava i provjera grupiranja pribadača. |
| NF-3.2 | Podnošenje prijave s fotografijom ne smije trajati duže od 5 sekundi; fotografija se prije slanja komprimira na klijentu. | Srednji | Prijedlog tima (ODL-06) | Mjerenje trajanja podnošenja uz mobilnu vezu i provjera komprimirane datoteke prije slanja. |
| NF-3.3 | Nova prijava ili promjena statusa mora biti vidljiva na panelu redara i karti udruge unutar 30 sekundi, bez ručnog osvježavanja stranice. | Srednji | Prijedlog tima (ODL-06) | Simulacija nove prijave i promjene statusa te mjerenje vremena prikaza na oba sučelja. |
| NF-3.4 | Odgovori OpenWeatherMap API-ja moraju se privremeno pohranjivati (npr. 1 sat) kako se ne bi prekoračilo ograničenje broja poziva. | Srednji | Uvjeti korištenja OpenWeatherMap API-ja | Pregled zapisa poziva i provjera da ponovljeni zahtjevi unutar razdoblja pohrane ne stvaraju nove pozive. |

### Zahtjevi za upotrebljivost

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-4.1 | Aplikacija mora biti responzivna. Mobilno sučelje mora biti upotrebljivo na zaslonu širine 360 px bez vodoravnog pomicanja sadržaja. | Visok | Projektni zadatak | Pregled sučelja u emulaciji uređaja širine 360 px i na stvarnom mobilnom uređaju. |
| NF-4.2 | Prijava otpada s terena mora biti jednostavna: najviše tri koraka (fotografija, kategorija, slanje) i dovoljno velike kontrole za rad na otvorenom. | Visok | Projektni zadatak | Testiranje tijeka podnošenja prijave s korisnicima i pregled veličine kontrola na mobilnom uređaju. |
| NF-4.3 | Sustav mora pružati jasne poruke o greškama i uputu za njihovo rješavanje (npr. odbijen pristup lokaciji, prevelika fotografija, nedostupna prognoza). | Srednji | Prijedlog tima (ODL-07) | Izazivanje navedenih pogrešaka i provjera sadržaja prikazanih poruka. |
| NF-4.4 | Korisničko sučelje mora biti na hrvatskom jeziku. | Srednji | Zahtjev korisnika (prema analizi tima) | Pregled svih javno dostupnih ekrana i poruka sustava. |
| NF-4.5 | Status prijave na karti ne smije se označavati samo bojom; uz boju pribadače mora postojati ikona ili tekstualna oznaka. | Srednji | Prijedlog tima (ODL-07) | Pregled pribadača i legende na karti te provjera prikaza bez oslanjanja samo na boju. |

## 2.3 Dionici i akteri

### Dionici sustava
| Dionik |	Uloga i odgovornost |	Očekivanja od sustava |
| --- | --- | --- |
| Građani |	Prijavljuju lokacije nepropisno odloženog otpada. |	Pouzdana prijava i transparentno praćenje statusa. |
| Ekološke udruge |	Organiziraju akcije čišćenja i koordiniraju volontere. |	Jednostavno planiranje akcija i upravljanje sudionicima. |
| Volonteri |	Sudjeluju u organiziranim akcijama čišćenja. |	Pravovremene informacije o akcijama i jednostavna prijava. |
| Komunalni redari |	Provjeravaju prijave i pokreću službene postupke sanacije. | Točni podaci o prijavama i učinkovito upravljanje njihovim statusima. |
| Lokalna samouprava i komunalne tvrtke	| Sudjeluju u rješavanju prijavljenih problema i službenoj sanaciji. |	Pregled relevantnih prijava i informacija potrebnih za postupanje. |

### Akteri sustava
| Akter |	Vrsta aktera |	Interakcija sa sustavom |
| --- | --- | --- |
| Građanin |	Primarni |	Prijavljuje otpad, pregledava kartu i status vlastitih prijava te se prijavljuje na akcije čišćenja. |
| Predstavnik eko-udruge	| Primarni	| Pregledava prijave, kreira akcije čišćenja, upravlja volonterima i dostavlja dokaz o sanaciji. |
| Komunalni redar |	Primarni |	Provjerava i moderira prijave, izdaje službene naloge, odobrava profile udruga i pregledava statistiku. |
| Vanjski pružatelj identiteta	| Sekundarni (vanjski sustav) |	Omogućuje autentifikaciju korisnika putem OpenID Connect / OAuth 2.0. |
| Google Maps API	| Sekundarni (vanjski sustav) |	Omogućuje prikaz lokacija na karti, geokodiranje i navigaciju. |
| Google Calendar API	| Sekundarni (vanjski sustav) |	Omogućuje stvaranje kalendarskih događaja i podsjetnika za akcije čišćenja. |
| OpenWeatherMap API |	Sekundarni (vanjski sustav)	| Dostavlja vremensku prognozu za lokaciju i termin planirane akcije. |


## 2.4 Ograničenja i pretpostavke

| ID | Tip | Opis ograničenja ili pretpostavke | Izvor | Inženjerska posljedica / Provjera |
| --- | --- | --- | --- | --- |
| C-01 | Ograničenje | Prijava novih i postojećih korisnika ostvaruje se preko vanjskog pružatelja identiteta (OpenID Connect / OAuth 2.0). Klasično upravljanje lozinkama nije potrebno. | Zahtjev dionika | Aplikacijske uloge (Građanin, Udruga, Redar) i status blokade čuvaju se u vlastitoj bazi i povezuju s identifikatorom korisnika od pružatelja (`sub`). Provjeriti autorizirane i neautorizirane zahtjeve (NF-2.2 i NF-2.3). |
| C-02 | Ograničenje | Obavezne su integracije Google Maps API (prilagođene oznake, geokodiranje, rute), Google Calendar API i OpenWeatherMap API. | Zahtjev dionika | Potrebni API ključevi i projekt u Google Cloudu; pozive vanjskim servisima izdvojiti u zasebne adaptere radi ispitivanja i otpornosti (NF-1.4 i NF-3.4). |
| C-03 | Ograničenje | Građanin koristi primarno mobilno sučelje. Komunalni redar koristi isključivo web sučelje. | Zahtjev dionika | Utječe na izbor klijentske tehnologije (responzivna web aplikacija / PWA ili mobilna aplikacija) i na raspored sučelja po ulogama. |
| C-04 | Ograničenje | Zapis u korisnikov Google kalendar zahtijeva korisnikov Google račun i njegov izričit pristanak za pristup kalendaru. | Zahtjev dionika | Pristanak za kalendar tražiti tek kada je potreban (pri prvom kreiranju akcije ili prijavi na akciju). Pohraniti token za osvježavanje na siguran način, ako je potreban za sinkronizaciju. Ako pružatelj identiteta iz C-01 nije Google, potrebna je zasebna autorizacija. |
| AS-01 | Pretpostavka | Uređaj građanina ima GPS i korisnik dopušta pristup lokaciji. Ako ne dopusti, lokaciju označava ručno na karti. | Procjena tima | Ispitati tijek s odbijenim dopuštenjem. |
| AS-02 | Pretpostavka | Prijava se prikazuje na karti odmah nakon podnošenja, prije verifikacije redara, kako opisuje životni vijek prijave u zadatku. Odbačene prijave se uklanjaju. | Zahtjev dionika | Potvrditi s nastavnikom vide li i ostali građani neverificirane prijave ili samo redar i udruge. |
| AS-03 | Pretpostavka | Zadatak ne određuje boju statusa *Službena sanacija*; tim predlaže zasebnu (plavu) oznaku. | Procjena tima | Potvrditi pri prvoj kontrolnoj točki. |
| AS-04 | Pretpostavka | Zahtjev udruge za zatvaranje potvrđuje komunalni redar prije nego što prijava dobije status *Očišćeno*. | Zahtjev dionika | Zadatak spominje i da „sustav automatski obavještava” nakon unosa fotografije; potvrditi je li potvrda redara obavezna. |
| AS-05 | Pretpostavka | „Regija” udruge je područje djelovanja (grad ili općina) koje udruga navodi u zahtjevu za profil (F-02.1). | Procjena tima | Ako se odluči za radijus oko točke, promijeniti F-02.1 i F-06.1. |
| AS-06 | Pretpostavka | Procjenu volumena otpada unosi građanin pri prijavi odabirom jedne od ponuđenih vrijednosti (malo/srednje/veliko). | Procjena tima | Zadatak navodi da udruga vidi procjenu, ali ne i tko je unosi. |
| AS-07 | Pretpostavka | Službeni nalog generira se kao PDF i šalje komunalnoj tvrtki e-poštom. Tvrtka nije korisnik sustava. | Procjena tima | Kanal dostave nije zadan; potvrditi. Popis ugovorenih tvrtki održava redar. |
| AS-08 | Pretpostavka | Volonter koji sudjeluje u akciji je Građanin (F-07.1); Eko-udruga je zaseban račun s odobrenim profilom. | Zahtjev dionika | Potvrditi tumačenje uloge „Eko-udruga / Volonter”. |
| AS-09 | Pretpostavka | Prognoza je dostupna samo ograničen broj dana unaprijed (ovisno o planu OpenWeatherMap). Pragove nevremena (oborine, vjetar, temperatura) određuje tim. | Procjena tima | Za udaljenije termine prikazati „prognoza još nije dostupna”; konkretne pragove dokumentirati uz F-09.2. |
| AS-10 | Pretpostavka | Push obavijesti ovise o podršci uređaja i dopuštenju korisnika; unutaraplikacijske obavijesti dovoljne su kao minimalna realizacija F-03.3. | Zahtjev dionika | Ispitati na barem jednom Android i jednom iOS uređaju. |

## 2.5 Značajne promjene zahtjeva

| Datum | ID zahtjeva | Promjena i razlog | Dogovoreno s / dokaz |
| --- | --- | --- | --- |
| — | — | Još nema dogovorenih promjena. | — |


### Evidencija prijedloga i odluka tima

Zahtjevi proizašli iz odluka i procjena projektnog tima evidentiraju se zasebno kako bi se mogao pratiti njihov izvor i povezanost s konkretnim zahtjevima. Odluke u nastavku predstavljaju prijedloge projektnog tima; njihovo uključivanje u dokumentaciju ne podrazumijeva da su ih vanjski dionici već formalno potvrdili. Izmjene nastale njihovim usuglašavanjem bilježe se u evidenciji značajnih promjena zahtjeva.

| ID odluke | Povezani zahtjevi | Odluka ili prijedlog | Obrazloženje | Status |
| --- | --- | --- | --- | --- |
| ODL-01 | F-01.2 | Omogućiti odjavu iz sustava. | Korisnik treba moći završiti prijavljenu sesiju i onemogućiti daljnji pristup zaštićenim radnjama bez ponovne prijave. | Prijedlog tima |
| ODL-02 | F-01.3 | Omogućiti brisanje korisničkog profila uz uklanjanje poveznice prijava s podnositeljem. | Korisniku se omogućuje upravljanje vlastitim profilom uz očuvanje podataka o prijavljenim odlagalištima. | Prijedlog tima |
| ODL-03 | F-07.2 | Omogućiti otkazivanje sudjelovanja u akciji čišćenja. | Oslobađanje mjesta drugim volonterima i točniji prikaz popunjenosti akcija. | Prijedlog tima |
| ODL-04 | NF-1.1–NF-1.4 | Definirati pravila održavanja, konfiguracije, izrade sigurnosnih kopija i rada pri nedostupnosti vanjskih servisa. | Smanjenje ovisnosti o konfiguraciji okruženja i vanjskim uslugama te olakšavanje održavanja. | Prijedlog tima |
| ODL-05 | NF-2.1, NF-2.3–NF-2.6 | Definirati dodatne mjere sigurnosti, autorizacije, zaštite podataka i zaštite od zlouporabe. | Zaštita korisnika, pristupa sustavu i učitanih podataka. | Prijedlog tima |
| ODL-06 | NF-3.1–NF-3.3 | Definirati mjerljive kriterije učitavanja karte, podnošenja prijava i vidljivosti promjena. | Omogućavanje provjere odziva sustava i kvalitete korisničkog iskustva. | Prijedlog tima |
| ODL-07 | NF-4.3, NF-4.5 | Definirati razumljive poruke o pogreškama i statusne oznake koje se ne oslanjaju samo na boju. | Poboljšanje razumljivosti i pristupačnosti sučelja. | Prijedlog tima |



## 2.6 Sljedivost zahtjeva

Obrasci uporabe definirani su u poglavlju 3. Pregledni UML dijagram prikazuje glavne korisničke ciljeve, dok su UC-02, UC-03, UC-06, UC-13 i UC-19 opisani tekstualnim specifikacijama. UC-20 predstavlja pomoćni obrazac uporabe sinkronizacije s Google kalendarom.

| ID zahtjeva | Obrazac uporabe / modul | Arhitekturna realizacija | Ispitivanje ili provjera |
| --- | --- | --- | --- |
| F-01.1 | UC-01 Prijava u sustav | `Login.jsx`, `AuthContext.jsx`, `SecurityConfig`, `KorisnikOidcUserService`, `Korisnik` i Google OpenID Connect. | Provjera preusmjeravanja na Google prijavu, povratka u aplikaciju, stvaranja korisnika pri prvom pristupu i dodjele uloge Građanin. |
| F-01.2 | UC-02 Odjava iz sustava | `AuthContext.jsx`, `Navbar.jsx` i odjava putem `/api/logout` u `SecurityConfig`. | Odjava prijavljenog korisnika i provjera da zaštićene API radnje nakon odjave zahtijevaju novu prijavu. |
| F-01.3 | UC-03 Brisanje vlastitog profila | Planirana funkcionalnost upravljanja profilom u modulu `korisnik`, uz uklanjanje osobnih podataka i očuvanje anonimiziranih prijava. | Brisanje korisničkog profila i provjera da korisnički podaci više nisu dostupni, a ranije prijave ostaju bez identiteta prijavitelja. |
| F-02.1 | UC-04 Podnošenje zahtjeva za profil eko-udruge | Model `Udruga`, modul `udruga` i planirani obrazac za predaju zahtjeva za profil udruge. | Slanje zahtjeva s valjanim podacima te provjera da korisnik prije odobrenja nema ovlasti eko-udruge. |
| F-02.2 | UC-05 Odlučivanje o profilu eko-udruge | Modul `udruga`, model `Udruga` i planirana redarska funkcionalnost odobravanja odnosno odbijanja zahtjeva. | Odobravanje i odbijanje zahtjeva; provjera promjene statusa udruge i pripadajućih ovlasti. |
| F-02.3 | UC-06 Blokiranje korisnika | Model `Korisnik`, modul `korisnik` i planirana redarska funkcionalnost blokiranja korisnika. | Blokiranje korisnika i pokušaj podnošenja prijave i prijave na akciju s blokiranim računom. |
| F-03.1 | UC-07 Podnošenje prijave odlagališta | `PrijavaOtpada.jsx`, `PrijavaController`, `PrijavaService`, `PohranaSlika`, `PrijavaRepository`, `Fotografija`. | `PrijavaControllerTest`: prijava s valjanim podacima, zahtjev bez autentikacije, bez CSRF tokena i s nevaljanim koordinatama; dodatno provjera obvezne fotografije i kategorije. |
| F-03.2–F-03.3 | UC-08 Praćenje vlastitih prijava; UC-12 Zatvaranje sanirane prijave | `PrijavaService.mojePrijave`, `PovijestStatusa`, `ObavijestService`, `Obavijest` i planirani prikaz povijesti prijava na profilu. | Dohvat prijava prijavljenog građanina; `PrijavaServiceTest`: provjera da promjena statusa zapisuje povijest i pokreće obavijest, a ponavljanje istog statusa ne stvara novu obavijest. |
| F-04.1–F-04.2 | UC-09 Pregled prijava na karti; UC-07 Podnošenje prijave odlagališta | `Karta.jsx`, komponenta `GoogleMap`, javni API prijava (`PrijavaController`, `PrijavaService.pinoviZaKartu`) i Google Maps; geokodiranje kao dio predviđene integracije. | `PrijavaControllerTest`: javni dohvat prijava; `PrijavaServiceTest`: odbačene prijave nisu na karti; ručna provjera oznaka statusa, detalja lokacije te pretvorbe adrese u koordinate i obrnuto. |
| F-04.3 | UC-15 Pregled akcija i kalendara | Planirani prikaz mjesta okupljanja i rute u sučelju akcije, uz Google Maps Directions. | Odabir akcije i provjera prikaza rute od lokacije korisnika do mjesta okupljanja. |
| F-05.1–F-05.2 | UC-10 Verifikacija i moderiranje prijave | `RedarPrijavaController`, `PrijavaService.odbaci`, `PrijavaService.promijeniKategoriju`, `RedarDashboard.jsx`. | `PrijavaServiceTest`: odbijanje odbacivanja očišćene prijave i isključivanje odbačenih prijava s karte; API provjera odbacivanja prijave i promjene kategorije. |
| F-05.3 | UC-11 Izdavanje službenog naloga | Model službenog naloga u bazi te planirana redarska komponenta za generiranje i slanje naloga i promjenu statusa u Službena sanacija. | Izdavanje naloga za odabranu prijavu; provjera podataka naloga, dostave i promjene statusa. |
| F-05.4 | UC-12 Zatvaranje sanirane prijave | `SanacijaService`, `PotvrdaSanacije` i `PrijavaService.promijeniStatus`; planirana redarska potvrda sanacije. | Provjera zatvaranja prijave nakon odobrenja dokaza o sanaciji, zapisivanja statusa Očišćeno i slanja obavijesti prijavitelju. |
| F-05.5 | UC-13 Pregled analitike i izvještaja | `RedarDashboard.jsx` prikazuje osnovne brojače prijava; detaljnija analitika planirana je u redarskom modulu. | Usporedba prikazanih brojača s podacima o prijavama; provjera planiranih grafikona po mjesecima, kvartovima i uspješnosti sanacije. |
| F-06.1 | UC-09 Pregled prijava na karti; UC-14 Planiranje akcije čišćenja | `UdrugaController`, `UdrugaService`, `PrijavaService.aktivneURegiji`; planirana nadogradnja `UdrugaDashboard.jsx` kartom i detaljima prijava. | Dohvat aktivnih prijava za regiju udruge i provjera da se ne prikazuju prijave iz drugih regija. |
| F-06.2 | UC-14 Planiranje akcije čišćenja | `UdrugaController`, `AkcijaService`, `AkcijaCiscenja`, `AkcijaCiscenjaRepository`; planirani korisnički obrazac za kreiranje akcije. | `AkcijaServiceTest`: uspješno kreiranje akcije, promjena statusa prijava, odbijanje prijava iz druge regije, nevaljanih statusa i duplikata. |
| F-06.3 | UC-15 Pregled akcija i kalendara | Model `AkcijaCiscenja` i planirani kalendarski prikaz akcija u korisničkom sučelju udruge. | Pregled planiranih akcija, njihovih termina i broja prijavljenih volontera. |
| F-06.4 | UC-16 Promjena termina akcije | Planirana funkcionalnost izmjene termina u modulu `akcija`, uz obavijesti sudionicima i ažuriranje Google Calendar događaja. | Promjena datuma akcije i provjera novog termina, obavijesti volonterima i ažuriranja kalendarskog događaja. |
| F-06.5 | UC-17 Podnošenje zahtjeva za zatvaranje prijave | `UdrugaController`, `SanacijaService`, `PotvrdaSanacije`, `Fotografija`, `PohranaSlika`. | `SanacijaServiceTest`: obvezna fotografija, pripadnost akcije udruzi, odbijanje dvostrukog zahtjeva te spremanje fotografije i potvrde koja čeka odluku redara. |
| F-07.1 | UC-18 Prijava za volontiranje | Modeli `AkcijaCiscenja` i `Sudjelovanje` u bazi; planirana funkcionalnost prijave volontera na akciju. | Prijava korisnika na akciju s dostupnim mjestima te odbijanje prijave na popunjenu akciju. |
| F-07.2 | UC-19 Otkazivanje sudjelovanja u akciji | Model `Sudjelovanje` i planirana funkcionalnost otkazivanja sudjelovanja u modulu akcija. | Otkazivanje prijave i provjera da se broj slobodnih mjesta povećava te da je mjesto ponovno dostupno. |
| F-08 | UC-14 Planiranje akcije čišćenja; UC-16 Promjena termina akcije; UC-18 Prijava za volontiranje; UC-20 Sinkronizacija s Google kalendarom | Google Calendar API, `GoogleToken`, `DbAuthorizedClientService` i planirani servis za stvaranje i ažuriranje događaja. | Provjera autorizacije Google kalendara te stvaranja događaja pri kreiranju akcije i prijavi volontera; kontrola termina, lokacije i podsjetnika. |
| F-09.1 | UC-14 Planiranje akcije čišćenja; UC-15 Pregled akcija i kalendara | Planirani adapter za OpenWeatherMap API te prikaz prognoze u sučelju akcije prema koordinatama i datumu. | Provjera dohvaćanja i prikaza prognoze za lokaciju akcije te ponašanja kada prognoza još nije dostupna. |
| F-09.2 | UC-14 Planiranje akcije čišćenja; UC-16 Promjena termina akcije | Planirana logika procjene vremenskih uvjeta iz OpenWeatherMap odgovora i upozorenje u sučelju organizatora. | Simulacija prognoze s nevremenom; provjera upozorenja i mogućnosti promjene termina akcije. |
| NF-1.4 | Otpornost na nedostupnost vanjskih servisa | Odvojeni pozivi vanjskih usluga u planiranim integracijskim adapterima; osnovni moduli prijava i akcija u Spring Boot aplikaciji. | Simulacija nedostupnosti OpenWeatherMap i Google Calendar servisa; provjera dostupnosti osnovnih radnji i razumljive poruke o pogrešci. |
| NF-2.2–NF-2.4 | Autentikacija, autorizacija i zaštita tajni | `SecurityConfig`, `KorisnikOidcUserService`, `ProtectedRoute.jsx`, `AuthContext.jsx`, konfiguracija putem varijabli okruženja i provjera uloga na backendu. | `PrijavaControllerTest`: provjere 401, 403, CSRF zaštite i redarske uloge; dodatno pregled konfiguracije i provjera da tajni ključevi nisu dostupni u javnom kodu. |
| NF-3.1–NF-3.4 | Performanse i predmemoriranje odgovora | `Karta.jsx`, `GoogleMap`, `PrijavaService`, API za prijave; planirani mehanizmi grupiranja oznaka, optimizacije fotografija, automatskog osvježavanja i predmemoriranja prognoze. | Mjerenje učitavanja karte i slanja prijave, provjera grupiranja oznaka i ažuriranja statusa unutar 30 sekundi te provjera predmemoriranja odgovora OpenWeatherMap API-ja. |
| NF-4.1–NF-4.5 | Responzivnost, upotrebljivost i lokalizacija | React stranice (`PrijavaOtpada.jsx`, `Karta.jsx`, `Login.jsx`, nadzorne ploče), CSS stilovi, validacija korisničkog unosa i poruke na hrvatskom jeziku. | Ručno ispitivanje na mobilnoj širini od 360 px, provjera toka prijave u tri koraka, poruka o pogreškama, hrvatskog jezika te razlikovanja statusa bez oslanjanja samo na boju. |

## 2.7 Pregled zahtjeva

| Kontrolna točka | Povratna informacija ili nalaz | Odluka o zahtjevu ili opsegu |
| --- | --- | --- |
| Pregled dokumentacije (20. 11. 2026.) | Komentari na dokumentaciju: jasno razdvajanje dionika i aktera, ravnomjerniji prioriteti i sljedivost timskih odluka. | Usklađena je struktura poglavlja 2.3, revidirani su prioriteti i uspostavljena evidencija odluka u 2.5. |
| Demo 2 / Završni pregled (14. tjedan) | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
