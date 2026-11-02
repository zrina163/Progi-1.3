# 2. Analiza zahtjeva

## 2.1 Funkcijski zahtjevi

| ID zahtjeva | Opis | Prioritet | Izvor | Kriteriji prihvaćanja |
| --- | --- | --- | --- | --- |
| *F-01.1* | Korisnik se mora moći prijaviti putem vanjskog pružatelja identiteta. | Visok |Zahtjev dionika | Korisniku mora biti omogućena prijava i registracija preko OpenID Connect/OAuth 2.0 servisa. Pri prvoj prijavi korisnik dobiva ulogu Građanin. |
| *F-01.2* | Korisnik se mora moći odjaviti iz sustava. | Visok | Odluka tima | Mora biti omogućena odjava korisničkog računa. Nakon odjave zaštićene radnje nisu dostupne bez ponovne prijave. |
| *F-01.3* | Korisnik mora moći izbrisati svoj profil. | Srednji | Odluka tima | Korisnik ima mogućnost u opstavkama profila obrisati profil. Podnesene prijave ostaju u sustavu bez podataka o podnositelju. |
| *F-02.1* | Korisnik mora moći zatražiti profil eko-udruge. | Visok |Zahtjev dionika | Korisnik mora moći poslati zahtjev s nazivom udruge, brojem iz registra udruga i regijom djelovanja. Nema ovlasti organizatora do odorbrenja komunalnog redara |
| *F-02.2* | Komunalni redar mora moći odobriti profil eko-udruge. | Visok |Zahtjev dionika | Redaru mora biti omogućeno odobravanje ili odbijanje zahtjeva za profil udruge. |
| *F-02.3* | Komunalni redar mora moći blokirati korisnika. | Visok |Zahtjev dionika | Redaru mora biti omogućeno blokiranje korisnika koji šalju lažne prijave. Blokirani korisnik ne može podnositi prijave ni prijavljivati se na akcije. |
| *F-03.1* | Građanin mora moći prijaviti divlje odlagalište otpada. | Visok |Zahtjev dionika | Prijava mora sadržavati GPS lokaciju, fotografiju (kamera ili galerija) i kategoriju otpada (plastika, građevinski, glomazni otpad i sl.). Prijava bez fotografije ili kategorije ne smije se pohraniti. |
| *F-03.2* | Građanin mora moći pregledati povijest svojih prijava. | Visok |Zahtjev dionika | Na profilu moraju biti vidljive sve prijave korisnika s datumom, kategorijom i trenutnim statusom. |
| *F-03.3* | Građanin mora dobiti obavijest o promjeni statusa prijave. | Visok |Zahtjev dionika | Obavijest se mora poslati kao push ili unutaraplikacijska obavijest i sadržavati broj prijave i novi status. |
| *F-04.1* | Prijave moraju biti prikazane na interaktivnoj karti. | Visok |Zahtjev dionika | Prijave se prikazuju kao pribadače na Google Maps karti, obojene prema statusu: crveno – Prijavljeno, žuto – Akcija u tijeku, zeleno – Očišćeno. Klikom na pribadaču otvaraju se detalji prijave s fotografijom. |
| *F-04.2* | Sustav mora podržavati geokodiranje lokacija. | Srednji |Zahtjev dionika | Koordinate prijave moraju se pretvarati u tekstualnu adresu, a upisana adresa u koordinate na karti. |
| *F-04.3* | Volonter mora moći vidjeti rutu do mjesta okupljanja. | Srednji |Zahtjev dionika | Na stranici akcije mora biti prikazano mjesto okupljanja na karti i ruta dolaska od trenutne lokacije korisnika. |
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
| NF-1.1 | Sustav mora biti jednostavan za održavanje i nadogradnju, s jasnim uputama za postavljanje i konfiguraciju vanjskih servisa (Google Maps, Google Calendar, OpenWeatherMap). | Visok | Procjena tima | Pregled dokumentacije i probno postavljanje sustava prema uputama. |
| NF-1.2 | Konfiguracija sustava (API ključevi, adrese servisa, podaci za bazu) mora biti odvojena od izvornog koda putem varijabli okruženja. | Visok | Procjena tima | Pregled izvornog koda i konfiguracije; provjera da se sustav pokreće s vrijednostima iz varijabli okruženja. |
| NF-1.3 | Sustav mora imati redovite sigurnosne kopije baze podataka i učitanih fotografija. | Srednji | Procjena tima | Pregled rasporeda sigurnosnog kopiranja i probno vraćanje baze podataka i fotografija. |
| NF-1.4 | Nedostupnost vanjskog servisa (OpenWeatherMap, Google Calendar) ne smije onemogućiti podnošenje prijava ni kreiranje akcija i prijavu na njih; korisnik mora dobiti poruku o nedostupnosti. | Srednji | Procjena tima | Simulacija nedostupnosti vanjskih servisa; provjera da se osnovna radnja dovršava i prikazuje jasna poruka. |

### Zahtjevi za sigurnost

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-2.1 | Sva komunikacija između klijenta i poslužitelja mora se odvijati preko HTTPS-a (TLS). | Visok | Procjena tima | Pregled mrežnog prometa i TLS konfiguracije. |
| NF-2.2 | Autentikacija se mora provoditi isključivo putem vanjskog pružatelja identiteta (OpenID Connect / OAuth 2.0);. Sustav ne smije pohranjivati korisničke lozinke. | Visok | Odobreni zadatak | Pregled implementacije autentikacije, baze podataka i konfiguracije pružatelja identiteta. |
| NF-2.3 | Ovlasti uloga (Građanin, Eko-udruga, Komunalni redar) moraju se provjeravati na poslužitelju. Neprijavljeni korisnik dobiva odgovor 401, a korisnik bez potrebne uloge odgovor 403. | Visok | Procjena tima | Slanje zaštićenih API zahtjeva bez tokena i s tokenom nedovoljne uloge. Provjera odgovora 401/403. |
| NF-2.4 | Tajni ključevi (OAuth client secret, OpenWeatherMap ključ) ne smiju se nalaziti u repozitoriju ni u klijentskom kodu. Google Maps ključ mora biti ograničen na domenu aplikacije. | Visok | Procjena tima | Pregled repozitorija, klijentskog koda i postavki ograničenja ključa u Google Cloud konzoli. |
| NF-2.5 | Sustav mora biti u skladu s GDPR-om: javna karta ne prikazuje identitet podnositelja prijave, s fotografija se uklanjaju EXIF metapodaci, a korisnik može izbrisati svoj profil. | Visok | Procjena tima | Pregled javnog prikaza, analiza metapodataka fotografije i provjera brisanja profila. |
| NF-2.6 | Sustav mora uključivati zaštitu od zlouporabe: učitane datoteke ograničene su na JPEG i PNG do 10 MB, a broj prijava po korisniku ograničen je (rate limiting) radi sprječavanja spama. | Srednji | Procjena tima | Pokušaj učitavanja nedopuštenih datoteka i slanja većeg broja prijava u zadanom vremenu. |

### Zahtjevi za performanse

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-3.1 | Karta s prijavama mora se učitati unutar 3 sekunde; pri većem broju prijava pribadače se grupiraju (clustering). | Visok | Procjena tima | Mjerenje učitavanja karte s oglednim skupom prijava i provjera grupiranja pribadača. |
| NF-3.2 | Podnošenje prijave s fotografijom ne smije trajati duže od 5 sekundi; fotografija se prije slanja komprimira na klijentu. | Srednji | Procjena tima | Mjerenje trajanja podnošenja uz mobilnu vezu i provjera komprimirane datoteke prije slanja. |
| NF-3.3 | Nova prijava ili promjena statusa mora biti vidljiva na panelu redara i karti udruge unutar 30 sekundi, bez ručnog osvježavanja stranice. | Srednji | Procjena tima | Simulacija nove prijave i promjene statusa te mjerenje vremena prikaza na oba sučelja. |
| NF-3.4 | Odgovori OpenWeatherMap API-ja moraju se privremeno pohranjivati (npr. 1 sat) kako se ne bi prekoračilo ograničenje broja poziva. | Srednji | Uvjeti korištenja OpenWeatherMap API-ja | Pregled zapisa poziva i provjera da ponovljeni zahtjevi unutar razdoblja pohrane ne stvaraju nove pozive. |

### Zahtjevi za upotrebljivost

| ID zahtjeva | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-4.1 | Aplikacija mora biti responzivna. Mobilno sučelje mora biti upotrebljivo na zaslonu širine 360 px bez vodoravnog pomicanja sadržaja. | Visok | Zahtjev dionika | Pregled sučelja u emulaciji uređaja širine 360 px i na stvarnom mobilnom uređaju. |
| NF-4.2 | Prijava otpada s terena mora biti jednostavna: najviše tri koraka (fotografija, kategorija, slanje) i dovoljno velike kontrole za rad na otvorenom. | Visok | Zahtjev dionika | Testiranje tijeka podnošenja prijave s korisnicima i pregled veličine kontrola na mobilnom uređaju. |
| NF-4.3 | Sustav mora pružati jasne poruke o greškama i uputu za njihovo rješavanje (npr. odbijen pristup lokaciji, prevelika fotografija, nedostupna prognoza). | Srednji | Procjena tima | Izazivanje navedenih pogrešaka i provjera sadržaja prikazanih poruka. |
| NF-4.4 | Korisničko sučelje mora biti na hrvatskom jeziku. | Srednji | Zahtjev dionika | Pregled svih javno dostupnih ekrana i poruka sustava. |
| NF-4.5 | Status prijave na karti ne smije se označavati samo bojom; uz boju pribadače mora postojati ikona ili tekstualna oznaka. | Nizak | Procjena tima | Pregled pribadača i legende na karti te provjera prikaza bez oslanjanja samo na boju. |

## 2.3 Dionici i akteri

| ID uloge ili aktera | Odnos prema sustavu | Relevantni zahtjevi |
| --- | --- | --- |
| A-01 Građanin (prijavitelj) | Primarni akter koji pokreće životni ciklus prijave. Koristi klijentski, primarno mobilni dio aplikacije na terenu. Podnosi prijave divljih odlagališta s fotografijom, GPS lokacijom i kategorijom otpada, pregledava interaktivnu kartu prijava, prati povijest i status vlastitih prijava te prima obavijesti o promjenama. Može pregledavati akcije čišćenja u blizini i jednim klikom se prijaviti kao volonter. | F-01.1–F-01.3, F-03.1–F-04.3, F-07.1–F-07.2, F-08, F-09.1 |
| A-02 Eko-udruga (organizator) | Akter zadužen za upravljanje ljudstvom i logistiku na terenu. Koristi naprednije web i mobilno sučelje. Pregledava aktivne (crvene) prijave u svojoj regiji, kreira akcije čišćenja za jednu ili više prijava, prati kalendar akcija i popunjenost volonterskih mjesta te nakon akcije učitava dokaz o sanaciji i šalje zahtjev za zatvaranje prijave. Ovlasti organizatora dobiva nakon što komunalni redar odobri profil udruge. | F-01.1–F-01.3, F-02.1–F-02.2, F-04.1–F-04.3, F-06.1–F-06.5, F-07.1–F-07.2, F-08, F-09.1–F-09.2 |
| A-03 Komunalni redar (službenik / administrator) | Akter s potpunim pravima kontrole nad bazom podataka i korisnicima. Koristi isključivo web sučelje (upravljačku ploču). Verificira i moderira prijave, izdaje službene naloge za otpad koji nije prikladan za volontere, zatvara prijave nakon sanacije, odobrava profile eko-udruga, blokira korisnike koji šalju lažne prijave i pregledava analitiku. | F-01.1–F-01.3, F-02.2–F-02.3, F-03.1, F-04.1–F-04.2, F-05.1–F-05.5 |

## 2.4 Ograničenja i pretpostavke

| ID | Tip | Opis ograničenja ili pretpostavke | Izvor | Inženjerska posljedica / Provjera |
| --- | --- | --- | --- | --- |
| C-01 | Ograničenje | Prijava novih i postojećih korisnika ostvaruje se preko vanjskog pružatelja identiteta (OpenID Connect / OAuth 2.0). Klasično upravljanje lozinkama nije potrebno. | Zahtjev dionika | Aplikacijske uloge (Građanin, Udruga, Redar) i status blokade čuvaju se u vlastitoj bazi i povezuju s identifikatorom korisnika od pružatelja (`sub`). Provjeriti autorizirane i neautorizirane zahtjeve (NF-2.2 i NF-2.3). |
| C-02 | Ograničenje | Obavezne su integracije Google Maps API (prilagođene oznake, geokodiranje, rute), Google Calendar API i OpenWeatherMap API. | Zahtjev dionika | Potrebni API ključevi i projekt u Google Cloudu; pozive vanjskim servisima izdvojiti u zasebne adaptere radi ispitivanja i otpornosti (NF-1.4 i NF-3.4). |
| C-03 | Ograničenje | Građanin koristi primarno mobilno sučelje. Komunalni redar koristi isključivo web sučelje. | Zahtjev dionika | Utječe na izbor klijentske tehnologije (responzivna web aplikacija / PWA ili mobilna aplikacija) i na raspored sučelja po ulogama. |
| C-04 | Ograničenje | Zapis u korisnikov Google kalendar zahtijeva korisnikov Google račun i njegov izričit pristanak za pristup kalendaru. | Zahtjev dionika | Pristanak za kalendar tražiti tek kada je potreban (pri prvom kreiranju akcije ili prijavi na akciju). Premiti token za osvježavanje. Ako pružatelj identiteta iz C-01 nije Google, potrebna je zasebna autorizacija. |
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

## 2.6 Sljedivost zahtjeva

Obrasci uporabe navedeni su kao prijedlog i uskladit će se s poglavljem 3. Arhitekturna realizacija i ispitivanja popunjavaju se kako nastaju poglavlja 4–6.

| ID zahtjeva | Obrazac uporabe / modul | Arhitekturna realizacija | Ispitivanje ili provjera |
| --- | --- | --- | --- |
| F-01.1–F-01.3 | UC-01 Prijava, odjava i brisanje profila | *na čekanju* | *na čekanju* |
| F-02.1–F-02.3 | UC-02 Upravljanje profilom eko-udruge i korisnicima | *na čekanju* | *na čekanju* |
| F-03.1 | UC-03 Podnošenje prijave odlagališta | *na čekanju* | *na čekanju* |
| F-03.2–F-03.3 | UC-04 Pregled povijesti i obavijesti o prijavama | *na čekanju* | *na čekanju* |
| F-04.1–F-04.3 | UC-05 Karta, geokodiranje i ruta | *na čekanju* | *na čekanju* |
| F-05.1–F-05.5 | UC-06 Verifikacija, sanacija i analitika prijava | *na čekanju* | *na čekanju* |
| F-06.1–F-06.5 | UC-07 Upravljanje akcijama čišćenja | *na čekanju* | *na čekanju* |
| F-07.1–F-07.2 | UC-08 Prijava i odjava volontera | *na čekanju* | *na čekanju* |
| F-08 | UC-09 Sinkronizacija s Google kalendarom | *na čekanju* | *na čekanju* |
| F-09.1–F-09.2 | UC-10 Prognoza i upozorenje o nevremenu | *na čekanju* | *na čekanju* |
| NF-1.4 | Otpornost na nedostupnost vanjskih servisa | *na čekanju* | *na čekanju* |
| NF-2.2–NF-2.4 | Autentikacija, autorizacija i zaštita tajni | *na čekanju* | *na čekanju* |
| NF-3.1–NF-3.4 | Performanse i predmemoriranje odgovora | *na čekanju* | *na čekanju* |
| NF-4.1–NF-4.5 | Responzivnost, upotrebljivost i lokalizacija | *na čekanju* | *na čekanju* |

## 2.7 Pregled zahtjeva

| Kontrolna točka | Povratna informacija ili nalaz | Odluka o zahtjevu ili opsegu |
| --- | --- | --- |
| Iteracija 1 (7. tjedan) | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
| Demo 2 / Završni pregled (14. tjedan) | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |