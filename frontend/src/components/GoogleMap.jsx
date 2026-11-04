import { APIProvider, Map, AdvancedMarker } from "@vis.gl/react-google-maps";

const FERLocation = {
  lat: 45.8003,
  lng: 15.9712,
};

export default function GoogleMap() {
  return (
    <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}>
      <div style={{ 
          height: "600px"
      }}>
        <Map
          defaultCenter={FERLocation}
          defaultZoom={12}
          mapId="DEMO_MAP_ID"
        >
          <AdvancedMarker position={FERLocation} />
        </Map>
      </div>
    </APIProvider>
  );
}
