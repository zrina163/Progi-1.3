import { useEffect, useState } from "react";
import "./PrijavaOtpada.css";
import api from "../services/api";

function PrijavaOtpada() {
  const [fotografija, setFotografija] = useState(null);
  const [pregled, setPregled] = useState(null);
  const [kategorija, setKategorija] = useState("");
  const [lokacija, setLokacija] = useState(null);
  const [greskaLokacije, setGreskaLokacije] = useState("");
  const [dohvacanjeLokacije, setDohvacanjeLokacije] = useState(false);
  const [pokusanaProvjera, setPokusanaProvjera] = useState(false);
  const [kategorije, setKategorije] = useState([]);
  const [ucitavanjeKategorija, setUcitavanjeKategorija] = useState(true);
  const [greskaKategorija, setGreskaKategorija] = useState("");
  const [slanje, setSlanje] = useState(false);
  const [greskaSlanja, setGreskaSlanja] = useState("");
  const [uspjehSlanja, setUspjehSlanja] = useState("");

  function odaberiFotografiju(event) {
    const datoteka = event.target.files?.[0];

    if (!datoteka) return;

    if (!["image/jpeg", "image/png"].includes(datoteka.type)) {
      alert("Odaberi fotografiju u JPG ili PNG formatu.");
      event.target.value = "";
      return;
    }

    if (datoteka.size > 10 * 1024 * 1024) {
      alert("Fotografija ne smije biti veća od 10 MB.");
      event.target.value = "";
      return;
    }

    setFotografija(datoteka);
    setPregled(URL.createObjectURL(datoteka));

    event.target.value = "";
  }

  function ukloniFotografiju() {
    setFotografija(null);
    setPregled(null);
  }

  function dohvatiLokaciju() {
    if (!navigator.geolocation) {
      setGreskaLokacije("Tvoj preglednik ne podržava geolokaciju.");
      return;
    }

    setDohvacanjeLokacije(true);
    setGreskaLokacije("");
    setLokacija(null);

    navigator.geolocation.getCurrentPosition(
      (position) => {
        setLokacija({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });

        setDohvacanjeLokacije(false);
      },
      (error) => {
        if (error.code === 1) {
          setGreskaLokacije("Nisi dopustila pristup lokaciji.");
        } else if (error.code === 2) {
          setGreskaLokacije("Trenutna lokacija nije dostupna.");
        } else if (error.code === 3) {
          setGreskaLokacije("Isteklo je vrijeme za dohvaćanje lokacije.");
        } else {
          setGreskaLokacije("Došlo je do greške pri dohvaćanju lokacije.");
        }

        setDohvacanjeLokacije(false);
      },
      {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 0,
      },
    );
  }

  useEffect(() => {
    return () => {
      if (pregled) {
        URL.revokeObjectURL(pregled);
      }
    };
  }, [pregled]);

  useEffect(() => {
    let aktivno = true;

    async function dohvatiKategorije() {
      try {
        const odgovor = await api.get("/api/public/kategorije");

        if (aktivno) {
          setKategorije(odgovor.data);
        }
      } catch {
        if (aktivno) {
          setGreskaKategorija("Dohvaćanje kategorija nije uspjelo.");
        }
      } finally {
        if (aktivno) {
          setUcitavanjeKategorija(false);
        }
      }
    }

    dohvatiKategorije();

    return () => {
      aktivno = false;
    };
  }, []);

  async function posaljiPrijavu() {
    if (slanje) return;

    setPokusanaProvjera(true);
    setGreskaSlanja("");
    setUspjehSlanja("");

    if (!fotografija || !kategorija || !lokacija) {
      return;
    }

    const podaci = new FormData();

    podaci.append("fotografija", fotografija);
    podaci.append("idKategorija", kategorija);
    podaci.append("sirina", lokacija.latitude);
    podaci.append("duzina", lokacija.longitude);

    setSlanje(true);

    try {
      const odgovor = await api.post("/api/prijave", podaci);

      setUspjehSlanja(`Prijava #${odgovor.data.id} uspješno je poslana!`);

      ukloniFotografiju();
      setKategorija("");
      setLokacija(null);
      setGreskaLokacije("");
      setPokusanaProvjera(false);
    } catch (error) {
      console.error(error);

      if (error.response?.status === 401) {
        setGreskaSlanja("Potrebno je prijaviti se.");
      } else if (error.response?.status === 403) {
        setGreskaSlanja("Nemate dopuštenje za slanje prijave.");
      } else {
        setGreskaSlanja("Slanje prijave nije uspjelo.");
      }
    } finally {
      setSlanje(false);
    }
  }

  return (
    <main className="prijava-otpada">
      <header className="prijava-zaglavlje">
        <h1>Prijava otpada</h1>
        <p>Prijavite nepropisno odloženi otpad.</p>
      </header>

      <div className="prijava-mreza">
        {/* LIJEVI STUPAC - FOTOGRAFIJA */}
        <section className="prijava-kartica">
          <h2>Fotografija otpada *</h2>

          <div className="prijava-foto-gumbi">
            <input
              id="fotografija"
              type="file"
              accept="image/jpeg,image/png"
              onChange={odaberiFotografiju}
              className="skriveni-input"
              disabled={slanje}
            />

            <label htmlFor="fotografija" className="gumb-galerija">
              Odaberi iz galerije
            </label>

            <input
              id="kamera"
              type="file"
              accept="image/jpeg,image/png"
              capture="environment"
              onChange={odaberiFotografiju}
              className="skriveni-input"
              disabled={slanje}
            />

            <label htmlFor="kamera" className="gumb-kamera">
              Snimi fotografiju
            </label>
          </div>

          {pregled ? (
            <div className="prijava-pregled">
              <img src={pregled} alt="Odabrana fotografija otpada" />

              <p>{fotografija.name}</p>

              <button
                type="button"
                onClick={ukloniFotografiju}
                disabled={slanje}
              >
                Ukloni fotografiju
              </button>
            </div>
          ) : (
            <p className="prijava-pomoc">Fotografija još nije odabrana.</p>
          )}
        </section>

        {/* DESNI STUPAC */}
        <div className="prijava-desna-kolona">
          {/* KATEGORIJA */}
          <section className="prijava-kartica">
            <h2>Kategorija otpada *</h2>

            <select
              id="kategorija"
              aria-label="Kategorija otpada"
              value={kategorija}
              onChange={(event) => setKategorija(event.target.value)}
              disabled={slanje || ucitavanjeKategorija || !!greskaKategorija}
              required
            >
              <option value="">
                {ucitavanjeKategorija
                  ? "Učitavanje kategorija..."
                  : "Odaberi kategoriju"}
              </option>

              {kategorije.map((kat) => (
                <option key={kat.id} value={kat.id}>
                  {kat.naziv}
                </option>
              ))}
            </select>

            {greskaKategorija && <p role="alert">{greskaKategorija}</p>}

            {!ucitavanjeKategorija &&
              !greskaKategorija &&
              kategorije.length === 0 && (
                <p>Nema dostupnih kategorija otpada.</p>
              )}
          </section>

          {/* LOKACIJA */}
          <section className="prijava-kartica">
            <h2>Lokacija otpada *</h2>

            <button
              type="button"
              onClick={dohvatiLokaciju}
              disabled={slanje || dohvacanjeLokacije}
            >
              {dohvacanjeLokacije
                ? "Dohvaćanje lokacije..."
                : "Dohvati moju lokaciju"}
            </button>

            {lokacija && (
              <div className="prijava-koordinate">
                <p>Lokacija uspješno dohvaćena!</p>
                <p>Širina: {lokacija.latitude.toFixed(5)}</p>
                <p>Dužina: {lokacija.longitude.toFixed(5)}</p>
              </div>
            )}

            {greskaLokacije && <p role="alert">{greskaLokacije}</p>}
          </section>
        </div>
      </div>

      {/* PROVJERA */}
      <div className="prijava-donja-traka">
        <button type="button" onClick={posaljiPrijavu} disabled={slanje}>
          {slanje ? "Slanje prijave..." : "Pošalji prijavu"}
        </button>

        {pokusanaProvjera && (!fotografija || !kategorija || !lokacija) && (
          <div role="alert">
            <p>Potrebno je ispuniti sva obavezna polja:</p>

            <ul>
              {!fotografija && <li>Dodaj fotografiju.</li>}
              {!kategorija && <li>Odaberi kategoriju.</li>}
              {!lokacija && <li>Dohvati lokaciju.</li>}
            </ul>
          </div>
        )}

        {greskaSlanja && <p role="alert">{greskaSlanja}</p>}

        {uspjehSlanja && <p role="status">{uspjehSlanja}</p>}
      </div>
    </main>
  );
}

export default PrijavaOtpada;
