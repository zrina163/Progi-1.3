import { Navigate } from 'react-router-dom';
import { useAuth } from '../services/AuthContext';

export default function ProtectedRoute({ uloge, children }) {
  const { user, loading } = useAuth();

  if (loading) return null;
  if (!user) return <Navigate to="/login" replace />;
  if (uloge && !uloge.includes(user.uloga)) return <Navigate to="/" replace />;

  return children;
}