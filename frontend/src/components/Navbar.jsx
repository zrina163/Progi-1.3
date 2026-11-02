
import { Link, NavLink } from 'react-router-dom'
import './Navbar.css'
import logo from '../assets/logo.png'

function Navbar() {
  return (
    <header className="navbar">
      <Link to="/" className="navbar-logo">
        <img src={logo} alt="EcoSentry Logo" />
        <span>EcoSentry</span>
      </Link>

      <nav className="navbar-links">
        <NavLink to="/" end className="nav-link">
          Karta
        </NavLink>

        <NavLink to="/akcije" className="nav-link">
          Akcije
        </NavLink>
      </nav>

      <NavLink to="/login" className="login-button">
        Prijava
      </NavLink>
    </header>
  )
}

export default Navbar
