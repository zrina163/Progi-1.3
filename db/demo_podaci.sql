BEGIN;

INSERT INTO korisnik (oidcsub, ime, uloga)
VALUES ('demo-gradanin-1', 'Demo građanin', 'GRADANIN')
ON CONFLICT (oidcsub) DO NOTHING;

INSERT INTO prijava (
    idprijavitelj,
    idkategorija,
    opis,
    sirina,
    duzina,
    grad,
    kvart,
    status
)
SELECT
    k.idkorisnik,
    kat.idkategorija,
    'DEMO: Odložene plastične vrećice',
    45.800300,
    15.971200,
    'Zagreb',
    'Trnje',
    'PRIJAVLJENO'
FROM korisnik k
JOIN kategorija_otpada kat
    ON kat.nazivkategorije = 'Plastika'
WHERE k.oidcsub = 'demo-gradanin-1'
  AND NOT EXISTS (
    SELECT 1 FROM prijava p
    WHERE p.idprijavitelj = k.idkorisnik
      AND p.opis = 'DEMO: Odložene plastične vrećice'
  );


INSERT INTO prijava (
    idprijavitelj,
    idkategorija,
    opis,
    sirina,
    duzina,
    grad,
    kvart,
    status
)
SELECT
    k.idkorisnik,
    kat.idkategorija,
    'DEMO: Odloženi stari namještaj',
    45.807500,
    15.945000,
    'Zagreb',
    'Trešnjevka',
    'PRIJAVLJENO'
FROM korisnik k
JOIN kategorija_otpada kat
    ON kat.nazivkategorije = 'Glomazni otpad'
WHERE k.oidcsub = 'demo-gradanin-1'
  AND NOT EXISTS (
    SELECT 1 FROM prijava p
    WHERE p.idprijavitelj = k.idkorisnik
      AND p.opis = 'DEMO: Odloženi stari namještaj'
  );


INSERT INTO fotografija (
    idprijava,
    idkorisnik,
    url,
    tip
)
SELECT
    p.idprijava,
    k.idkorisnik,
    'https://example.com/demo-plastika.jpg',
    'PRIJE'
FROM prijava p
JOIN korisnik k
    ON p.idprijavitelj = k.idkorisnik
WHERE k.oidcsub = 'demo-gradanin-1'
  AND p.opis = 'DEMO: Odložene plastične vrećice'
  AND NOT EXISTS (
      SELECT 1
      FROM fotografija f
      WHERE f.idprijava = p.idprijava
        AND f.tip = 'PRIJE'
  );



INSERT INTO fotografija (
    idprijava,
    idkorisnik,
    url,
    tip
)
SELECT
    p.idprijava,
    k.idkorisnik,
    'https://example.com/demo-namjestaj.jpg',
    'PRIJE'
FROM prijava p
JOIN korisnik k
    ON p.idprijavitelj = k.idkorisnik
WHERE k.oidcsub = 'demo-gradanin-1'
  AND p.opis = 'DEMO: Odloženi stari namještaj'
  AND NOT EXISTS (
      SELECT 1
      FROM fotografija f
      WHERE f.idprijava = p.idprijava
        AND f.tip = 'PRIJE'
  );


INSERT INTO korisnik (oidcsub, ime, uloga)
VALUES ('demo-udruga-1', 'Demo organizator', 'EKO_UDRUGA')
ON CONFLICT (oidcsub) DO NOTHING;

INSERT INTO udruga (
    idkorisnik,
    naziv,
    registarskibroj,
    regija,
    statusodobrenja
)
SELECT
    k.idkorisnik,
    'DEMO Eko Zagreb',
    'DEMO-001',
    'Zagreb',
    'ODOBRENO'
FROM korisnik k
WHERE k.oidcsub = 'demo-udruga-1'
ON CONFLICT (idkorisnik) DO NOTHING;


INSERT INTO akcija_ciscenja (
    idudruga,
    naziv,
    opis,
    maxvolontera,
    pocetak,
    sastanaksirina,
    sastanakduzina,
    sastanakadresa,
    status
)
SELECT
    u.idudruga,
    'DEMO: Čišćenje otpada na Trešnjevci',
    'Volonterska akcija uklanjanja otpada.',
    15,
    '2026-12-12 10:00:00+01',
    45.807500,
    15.945000,
    'Trešnjevka, Zagreb',
    'PLANIRANA'
FROM udruga u
JOIN korisnik k
    ON u.idkorisnik = k.idkorisnik
WHERE k.oidcsub = 'demo-udruga-1'
  AND u.statusodobrenja = 'ODOBRENO'
  AND NOT EXISTS (
      SELECT 1
      FROM akcija_ciscenja a
      WHERE a.idudruga = u.idudruga
        AND a.naziv =
            'DEMO: Čišćenje otpada na Trešnjevci'
  );


UPDATE prijava p
SET
    idakcija = a.idakcija,
    status = 'AKCIJA_U_TIJEKU'
FROM akcija_ciscenja a
JOIN udruga u
    ON a.idudruga = u.idudruga
JOIN korisnik k
    ON u.idkorisnik = k.idkorisnik
WHERE p.opis = 'DEMO: Odloženi stari namještaj'
  AND p.idprijavitelj = (
      SELECT idkorisnik
      FROM korisnik
      WHERE oidcsub = 'demo-gradanin-1'
  )
  AND k.oidcsub = 'demo-udruga-1'
  AND a.naziv = 'DEMO: Čišćenje otpada na Trešnjevci'
  AND p.idakcija IS DISTINCT FROM a.idakcija;

COMMIT;