import { Routes, Route, Link } from 'react-router-dom'
import Navbar from './components/Navbar'
import Login from './pages/Login'
import Register from './pages/Register'
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
        <Route path="/register" element={<Register />} />
      </Routes>
    </>
  )
}

export default App