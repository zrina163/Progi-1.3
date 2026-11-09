CREATE TABLE KORISNIK
(
  idKorisnik SERIAL NOT NULL,
  oidcSub VARCHAR(255) NOT NULL,        -- ID korisnika (OpenID Connect)
  ime VARCHAR(100),
  email VARCHAR(255),
  uloga VARCHAR(20) NOT NULL DEFAULT 'GRADANIN',
  blokiran BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (idKorisnik),
  UNIQUE (oidcSub),
  CHECK (uloga IN ('GRADANIN', 'EKO_UDRUGA', 'KOMUNALNI_REDAR'))
);

CREATE TABLE UDRUGA
(
  idUdruga SERIAL NOT NULL,
  idKorisnik INT NOT NULL,          -- (voditelj)
  naziv VARCHAR(200) NOT NULL,
  registarskiBroj VARCHAR(80),
  opis TEXT,
  regija VARCHAR(120),
  statusOdobrenja VARCHAR(20) NOT NULL DEFAULT 'NA_CEKANJU',
  PRIMARY KEY (idUdruga),
  UNIQUE (idKorisnik),
  FOREIGN KEY (idKorisnik) REFERENCES KORISNIK(idKorisnik) ON DELETE CASCADE,
  CHECK (statusOdobrenja IN ('NA_CEKANJU', 'ODOBRENO', 'ODBIJENO'))
);

CREATE TABLE KATEGORIJA_OTPADA
(
  idKategorija SERIAL NOT NULL,
  nazivKategorije VARCHAR(100) NOT NULL,
  PRIMARY KEY (idKategorija),
  UNIQUE (nazivKategorije)
);

CREATE TABLE AKCIJA_CISCENJA
(
  idAkcija SERIAL NOT NULL,
  idUdruga INT NOT NULL,
  naziv VARCHAR(200) NOT NULL,
  opis TEXT,
  maxVolontera INT NOT NULL,
  pocetak TIMESTAMPTZ NOT NULL,
  sastanakSirina DECIMAL(9,6) NOT NULL,   -- mjesto okupljanja
  sastanakDuzina DECIMAL(9,6) NOT NULL,
  sastanakAdresa TEXT,
  status VARCHAR(20) NOT NULL DEFAULT 'PLANIRANA',
  googleEventId VARCHAR(255),             -- događaj u Google kalendaru
  PRIMARY KEY (idAkcija),
  FOREIGN KEY (idUdruga) REFERENCES UDRUGA(idUdruga),
  CHECK (maxVolontera > 0),
  CHECK (status IN ('PLANIRANA', 'ODGODENA', 'ZAVRSENA', 'OTKAZANA'))
);

CREATE TABLE PRIJAVA
(
  idPrijava SERIAL NOT NULL,
  idPrijavitelj INT NOT NULL,
  idKategorija INT NOT NULL,
  idAkcija INT,                           -- akcija čišćenja -> prijava uvrštena
  opis TEXT,
  sirina DECIMAL(9,6) NOT NULL,
  duzina DECIMAL(9,6) NOT NULL,
  adresa TEXT,                            -- (Google Maps)
  grad VARCHAR(120),
  kvart VARCHAR(120),                     -- za analitiku "najkritičniji kvartovi"
  procijenjeniVolumenM3 DECIMAL(10,2),
  status VARCHAR(30) NOT NULL DEFAULT 'PRIJAVLJENO',
  datumPrijave TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idPrijava),
  FOREIGN KEY (idPrijavitelj) REFERENCES KORISNIK(idKorisnik),
  FOREIGN KEY (idKategorija) REFERENCES KATEGORIJA_OTPADA(idKategorija),
  FOREIGN KEY (idAkcija) REFERENCES AKCIJA_CISCENJA(idAkcija) ON DELETE SET NULL,
  CHECK (sirina BETWEEN -90 AND 90),
  CHECK (duzina BETWEEN -180 AND 180),
  CHECK (status IN ('PRIJAVLJENO', 'AKCIJA_U_TIJEKU', 'OCISCENO', 'SLUZBENA_SANACIJA', 'ODBACENO'))
);

CREATE TABLE FOTOGRAFIJA
(
  idFotografija SERIAL NOT NULL,
  idPrijava INT NOT NULL,
  idKorisnik INT NOT NULL,         -- tko je učitao
  url TEXT NOT NULL,
  tip VARCHAR(10) NOT NULL,              -- PRIJE = fotografija otpada, POSLIJE = dokaz o sanaciji
  datumUcitavanja TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idFotografija),
  FOREIGN KEY (idPrijava) REFERENCES PRIJAVA(idPrijava) ON DELETE CASCADE,
  FOREIGN KEY (idKorisnik) REFERENCES KORISNIK(idKorisnik),
  CHECK (tip IN ('PRIJE', 'POSLIJE'))
);

CREATE TABLE POVIJEST_STATUSA
(
  idPovijest SERIAL NOT NULL,
  idPrijava INT NOT NULL,
  idKorisnik INT,                   -- tko je promijenio status
  status VARCHAR(30) NOT NULL,
  napomena TEXT,                          -- npr. razlog odbacivanja prijave
  datumPromjene TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idPovijest),
  FOREIGN KEY (idPrijava) REFERENCES PRIJAVA(idPrijava) ON DELETE CASCADE,
  FOREIGN KEY (idKorisnik) REFERENCES KORISNIK(idKorisnik) ON DELETE SET NULL,
  CHECK (status IN ('PRIJAVLJENO', 'AKCIJA_U_TIJEKU', 'OCISCENO', 'SLUZBENA_SANACIJA', 'ODBACENO'))
);

CREATE TABLE POTVRDA_SANACIJE
(
  idPotvrda SERIAL NOT NULL,
  idPrijava INT NOT NULL,
  idFotografija INT NOT NULL,             -- "Dokaz o sanaciji" (fotografija tipa POSLIJE)
  idPodnositelj INT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'NA_CEKANJU',
  datumZahtjeva TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idPotvrda),
  FOREIGN KEY (idPrijava) REFERENCES PRIJAVA(idPrijava) ON DELETE CASCADE,
  FOREIGN KEY (idFotografija) REFERENCES FOTOGRAFIJA(idFotografija),
  FOREIGN KEY (idPodnositelj) REFERENCES KORISNIK(idKorisnik),
  CHECK (status IN ('NA_CEKANJU', 'ODOBRENO', 'ODBIJENO'))
);

CREATE TABLE KOMUNALNA_TVRTKA
(
  idTvrtka SERIAL NOT NULL,
  nazivTvrtke VARCHAR(200) NOT NULL,
  email VARCHAR(255),
  PRIMARY KEY (idTvrtka),
  UNIQUE (nazivTvrtke)
);

CREATE TABLE SLUZBENI_NALOG
(
  idNalog SERIAL NOT NULL,
  idPrijava INT NOT NULL,
  idRedar INT NOT NULL,
  idTvrtka INT NOT NULL,
  opis TEXT,
  status VARCHAR(20) NOT NULL DEFAULT 'IZDAN',
  datumIzdavanja TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idNalog),
  FOREIGN KEY (idPrijava) REFERENCES PRIJAVA(idPrijava),
  FOREIGN KEY (idRedar) REFERENCES KORISNIK(idKorisnik),
  FOREIGN KEY (idTvrtka) REFERENCES KOMUNALNA_TVRTKA(idTvrtka),
  CHECK (status IN ('IZDAN', 'U_TIJEKU', 'ZAVRSEN', 'OTKAZAN'))
);

CREATE TABLE SUDJELOVANJE
(
  idSudjelovanje SERIAL NOT NULL,
  idAkcija INT NOT NULL,
  idKorisnik INT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PRIJAVLJEN',
  googleEventId VARCHAR(255),             -- događaj u Google kalendaru volontera
  datumPrijave TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idSudjelovanje),
  UNIQUE (idAkcija, idKorisnik),
  FOREIGN KEY (idAkcija) REFERENCES AKCIJA_CISCENJA(idAkcija) ON DELETE CASCADE,
  FOREIGN KEY (idKorisnik) REFERENCES KORISNIK(idKorisnik) ON DELETE CASCADE,
  CHECK (status IN ('PRIJAVLJEN', 'OTKAZAN'))
);

CREATE TABLE OBAVIJEST
(
  idObavijest SERIAL NOT NULL,
  idKorisnik INT NOT NULL,           -- primatelj
  idPrijava INT,
  idAkcija INT,
  poruka TEXT NOT NULL,
  procitana BOOLEAN NOT NULL DEFAULT FALSE,
  datumSlanja TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  PRIMARY KEY (idObavijest),
  FOREIGN KEY (idKorisnik) REFERENCES KORISNIK(idKorisnik) ON DELETE CASCADE,
  FOREIGN KEY (idPrijava) REFERENCES PRIJAVA(idPrijava) ON DELETE CASCADE,
  FOREIGN KEY (idAkcija) REFERENCES AKCIJA_CISCENJA(idAkcija) ON DELETE CASCADE
);

CREATE TABLE GOOGLE_TOKEN
(
  principalName VARCHAR(255) NOT NULL,    -- = KORISNIK.oidcSub
  accessToken TEXT NOT NULL,
  accessTokenIssuedAt TIMESTAMPTZ,
  accessTokenExpiresAt TIMESTAMPTZ,
  scopes TEXT,
  refreshToken TEXT,                      -- za Google Calendar dok korisnik nije prijavljen
  PRIMARY KEY (principalName),
  FOREIGN KEY (principalName) REFERENCES KORISNIK(oidcSub) ON DELETE CASCADE
);

-- idx za najčešće upite
CREATE INDEX idxPrijavaStatus ON PRIJAVA(status);
CREATE INDEX idxPrijavaLokacija ON PRIJAVA(sirina, duzina);
CREATE INDEX idxPrijavaPrijavitelj ON PRIJAVA(idPrijavitelj);
CREATE INDEX idxObavijestKorisnik ON OBAVIJEST(idKorisnik);
CREATE INDEX idxGoogleTokenPrincipalName ON GOOGLE_TOKEN(principalName);
--bazni podatci
INSERT INTO KATEGORIJA_OTPADA (nazivKategorije) VALUES
  ('Plastika'),
  ('Građevinski otpad'),
  ('Glomazni otpad'),
  ('Opasni otpad (npr. azbest)'),
  ('Ostalo');