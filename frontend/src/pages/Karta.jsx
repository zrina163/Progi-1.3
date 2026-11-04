import GoogleMap from "../components/Googlemap";
import './Karta.css'

function Karta() {
  return (

    <div className="karta-background">

      <main className="karta-page">
        <div className="karta-header">
          <h1>Istraži lokacije</h1>
          <p>Pretražite dostupne lokacije na karti.</p>
        </div>

        <div className="karta-container">
          <GoogleMap />
        </div>
      </main>
      
    </div>
    
  );
}

export default Karta;