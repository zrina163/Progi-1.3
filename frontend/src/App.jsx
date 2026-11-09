import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Login from './pages/Login'
import Karta from './pages/Karta'
import Akcije from './pages/Akcije'

function App() {
  return (
    <>
      <Navbar />

      <Routes>
        <Route path="/" element={<Karta />} />
        <Route path="/akcije" element={<Akcije />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </>
  )
}

export default App