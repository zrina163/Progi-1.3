import { Navigate, useSearchParams } from 'react-router-dom';
import { useAuth } from '../services/AuthContext';
import logo from '../assets/logo.png';
import googleLogo from '../assets/googleLogo.png';
import './Login.css';

export default function Login() {
  const { user, loading, login } = useAuth();
  const [params] = useSearchParams();

  if (loading) return null;
  if (user) return <Navigate to="/" replace />;

  return (
    <main className="login-page">
      <div className="login-card">
        <img src={logo} alt="EcoSentry" className="login-logo" />

        <h1>Dobrodošli u EcoSentry</h1>
        <p className="login-description">
          Prijavite se kako biste sudjelovali u očuvanju
          okoliša i čišćenju našeg grada.
        </p>

        {params.has('error') && (
          <p className="login-error">
            Prijava nije uspjela ili je račun blokiran.
          </p>
        )}

        <button className="google-login-button" onClick={login}>
          <img src={googleLogo} alt="Google" className="google-icon" />
          Nastavi s Google računom
        </button>

        <p className="login-footer">
          
        </p>
      </div>
    </main>
  );
}