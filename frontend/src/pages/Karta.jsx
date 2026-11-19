import GoogleMap from "../components/GoogleMap";
import './Karta.css'
import api from '../services/api';
import { useEffect, useState } from "react";

function Karta() {
  const [prijave, setPrijave] = useState([]);

  useEffect(() => {
    api.get('/api/public/prijave')
      .then(res => setPrijave(res.data))
      .catch(err => console.error(err));
  }, []);

  return (

    <div className="karta-background">

      <main className="karta-page">
        <div className="karta-header">
          <h1>Istraži lokacije</h1>
          <p>Pretražite dostupne lokacije na karti.</p>
        </div>

        <div className="karta-container">
          <GoogleMap prijave={prijave} />
        </div>
      </main>
      
    </div>
    
  );
}

export default Karta;