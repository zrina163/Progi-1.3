import { Link, NavLink } from 'react-router-dom'
import { useAuth } from '../services/AuthContext'
import './Navbar.css'
import logo from '../assets/logo.png'
import { UserRound } from 'lucide-react';

function Navbar() {
  const { user, logout } = useAuth()

  const uloge = {
    GRADANIN: 'Građanin',
    EKO_UDRUGA: 'Eko-udruga',
    KOMUNALNI_REDAR: 'Komunalni redar'
  };

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
            <details className="profile-menu">
              <summary className="profile-trigger">
                <span className="profile-avatar">
                  {user.ime?.charAt(0).toUpperCase() || 'K'}
                </span>

                <span className="profile-name">
                  {user.ime || 'Moj profil'}
                </span>

                <span className="profile-arrow">⌄</span>
              </summary>

              <div className="profile-dropdown">
                <h3 className="profile-title">
                  <UserRound size={20} />
                  Moj profil
                </h3>
                <p className="profile-fullname">{user.ime}</p>
                <p className="profile-email">{user.email}</p>
                <span className="profile-role">
                  {uloge[user.uloga] || user.uloga}
                </span>
              </div>
            </details>

            <button
              type="button"
              className="login-button"
              onClick={logout}
            >
              Odjava
            </button>
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