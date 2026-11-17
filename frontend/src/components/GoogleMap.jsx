import { APIProvider, Map, AdvancedMarker } from "@vis.gl/react-google-maps";

const zagrebCenter = {
    lat: 45.8150,
    lng: 15.9819,
};

export default function GoogleMap({prijave = [] }) {
  return (
    <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}>
      <div style={{ 
          height: "600px"
      }}>
        <Map
          defaultCenter={zagrebCenter}
          defaultZoom={12}
          mapId="DEMO_MAP_ID"
        >
          {prijave.map((prijava) => (
            <AdvancedMarker
              key={prijava.id}
              position={{
                lat: Number(prijava.sirina),
                lng: Number(prijava.duzina),
              }}
            />
          ))}
        </Map>
      </div>
    </APIProvider>
  );
}
