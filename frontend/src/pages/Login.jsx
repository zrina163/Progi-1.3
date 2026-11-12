import { Navigate, useSearchParams } from 'react-router-dom';
import { useAuth } from '../services/AuthContext';

export default function Login() {
  const { user, login } = useAuth();
  const [params] = useSearchParams();

  if (user) return <Navigate to="/" replace />;

  return (
    <div>
      <h2>Prijava u EcoSentry</h2>

      {params.has('error') && (
        <p style={{ color: 'red' }}>Prijava nije uspjela ili je račun blokiran.</p>
      )}

      <button onClick={login}>Prijavi se putem Googlea</button>
    </div>
  );
}