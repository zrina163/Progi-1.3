
import { useEffect, useState } from 'react'
import './PrijavaOtpada.css'

function PrijavaOtpada() {
  const [fotografija, setFotografija] = useState(null)
  const [pregled, setPregled] = useState(null)
  const [kategorija, setKategorija] = useState('')
  const [lokacija, setLokacija] = useState(null)
  const [greskaLokacije, setGreskaLokacije] = useState('')
  const [dohvacanjeLokacije, setDohvacanjeLokacije] = useState(false)
  const [pokusanaProvjera, setPokusanaProvjera] = useState(false)

  function odaberiFotografiju(event) {
    const datoteka = event.target.files?.[0]

    if (!datoteka) return

    if (!datoteka.type.startsWith('image/')) {
      alert('Odaberi slikovnu datoteku.')
      event.target.value = ''
      return
    }

    setFotografija(datoteka)
    setPregled(URL.createObjectURL(datoteka))

    event.target.value = ''
  }

  function ukloniFotografiju() {
    setFotografija(null)
    setPregled(null)
    }
    

function dohvatiLokaciju() {
  if (!navigator.geolocation) {
    setGreskaLokacije('Tvoj preglednik ne podržava geolokaciju.')
    return
  }

  setDohvacanjeLokacije(true)
  setGreskaLokacije('')
  setLokacija(null)

  navigator.geolocation.getCurrentPosition(
    (position) => {
      setLokacija({
        latitude: position.coords.latitude,
        longitude: position.coords.longitude
      })

      setDohvacanjeLokacije(false)
    },
    (error) => {
      if (error.code === 1) {
        setGreskaLokacije('Nisi dopustila pristup lokaciji.')
      } else if (error.code === 2) {
        setGreskaLokacije('Trenutna lokacija nije dostupna.')
      } else if (error.code === 3) {
        setGreskaLokacije('Isteklo je vrijeme za dohvaćanje lokacije.')
      } else {
        setGreskaLokacije('Došlo je do greške pri dohvaćanju lokacije.')
      }

      setDohvacanjeLokacije(false)
    },
    {
      enableHighAccuracy: true,
      timeout: 10000,
      maximumAge: 0
    }
  )
}


  useEffect(() => {
    return () => {
      if (pregled) {
        URL.revokeObjectURL(pregled)
      }
    }
  }, [pregled])

  
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
            accept="image/*"
            onChange={odaberiFotografiju}
            className="skriveni-input"
          />

          <label htmlFor="fotografija" className="gumb-galerija">
            Odaberi iz galerije
          </label>

          <input
            id="kamera"
            type="file"
            accept="image/*"
            capture="environment"
            onChange={odaberiFotografiju}
            className="skriveni-input"
          />

          <label htmlFor="kamera" className="gumb-kamera">
            Snimi fotografiju
          </label>
        </div>

        {pregled ? (
          <div className="prijava-pregled">
            <img
              src={pregled}
              alt="Odabrana fotografija otpada"
            />

            <p>{fotografija.name}</p>

            <button
              type="button"
              onClick={ukloniFotografiju}
            >
              Ukloni fotografiju
            </button>
          </div>
        ) : (
          <p className="prijava-pomoc">
            Fotografija još nije odabrana.
          </p>
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
            onChange={(event) =>
              setKategorija(event.target.value)
            }
            required
          >
            <option value="">Odaberi kategoriju</option>
            <option value="plastika">Plastika</option>
            <option value="gradevinski">Građevinski otpad</option>
            <option value="glomazni">Glomazni otpad</option>
          </select>
        </section>

        {/* LOKACIJA */}
        <section className="prijava-kartica">
          <h2>Lokacija otpada *</h2>

          <button
            type="button"
            onClick={dohvatiLokaciju}
            disabled={dohvacanjeLokacije}
          >
            {dohvacanjeLokacije
              ? 'Dohvaćanje lokacije...'
              : 'Dohvati moju lokaciju'}
          </button>

          {lokacija && (
            <div className="prijava-koordinate">
              <p>Lokacija uspješno dohvaćena!</p>
              <p>Širina: {lokacija.latitude.toFixed(5)}</p>
              <p>Dužina: {lokacija.longitude.toFixed(5)}</p>
            </div>
          )}

          {greskaLokacije && (
            <p role="alert">{greskaLokacije}</p>
          )}
        </section>
      </div>
    </div>

    {/* DONJI DIO - PROVJERA */}
    <div className="prijava-donja-traka">
      <button
        type="button"
        onClick={() => setPokusanaProvjera(true)}
      >
        Provjeri prijavu
      </button>

      {pokusanaProvjera && (
        !fotografija || !kategorija || !lokacija ? (
          <div role="alert">
            <p>Potrebno je ispuniti sva obavezna polja:</p>
            <ul>
              {!fotografija && <li>Dodaj fotografiju.</li>}
              {!kategorija && <li>Odaberi kategoriju.</li>}
              {!lokacija && <li>Dohvati lokaciju.</li>}
            </ul>
          </div>
        ) : (
          <p role="status">
            Svi podaci su uneseni! Prijava je spremna za slanje.
          </p>
        )
      )}
    </div>
  </main>
)

}

export default PrijavaOtpada
