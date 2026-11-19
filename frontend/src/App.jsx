import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Karta from "./pages/Karta";
import Akcije from "./pages/Akcije";
import PrijavaOtpada from "./pages/PrijavaOtpada";
import ProtectedRoute from "./components/ProtectedRoute";
import RedarDashboard from "./pages/RedarDashboard";
import UdrugaDashboard from "./pages/UdrugaDashboard";

function App() {
  return (
    <>
      <Navbar />

      <Routes>
        <Route path="/" element={<Karta />} />
        <Route path="/akcije" element={<Akcije />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/prijava-otpada"
          element={
            <ProtectedRoute uloge={["GRADANIN"]}>
              <PrijavaOtpada />
            </ProtectedRoute>
          }
        />
        <Route
          path="/redar"
          element={
            <ProtectedRoute uloge={["KOMUNALNI_REDAR"]}>
              <RedarDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/udruga"
          element={
            <ProtectedRoute uloge={["EKO_UDRUGA"]}>
              <UdrugaDashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;
