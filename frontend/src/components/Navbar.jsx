import { Link, NavLink } from 'react-router-dom'
import { useAuth } from '../services/AuthContext'
import './Navbar.css'
import logo from '../assets/logo.png'

function Navbar() {
  const { user, logout } = useAuth()

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

      <div className="navbar-auth">
        {user ? (
          <>
            <span>{user.ime}</span>
            <button className="login-button" onClick={logout}>Odjava</button>
          </>
        ) : (
          <NavLink to="/login" className="login-button">
            Prijava
          </NavLink>
        )}
      </div>
    </header>
  )
}

export default Navbar