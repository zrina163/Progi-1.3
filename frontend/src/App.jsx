import { Routes, Route, Link } from 'react-router-dom'
import Login from './pages/Login'
import Karta from './pages/Karta'

function App() {
  return (
    <>
      <nav>
        <Link to="/">Karta</Link> | <Link to="/login">Prijava</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Karta />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </>
  )
}

export default App