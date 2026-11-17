import { useEffect, useState } from 'react';
import { useAuth } from '../services/AuthContext';
import api from '../services/api';
import './Dashboard.css';

function RedarDashboard() {
    const { user } = useAuth();
    const [prijave, setPrijave] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        api.get('/api/redar/prijave')
            .then(res => setPrijave(res.data))
            .catch(() => setError(true))
            .finally(() => setLoading(false));
    }, []);

    const aktivne = prijave.filter(
        p => p.status === 'PRIJAVLJENO'
    ).length;

    const ociscene = prijave.filter(
        p => p.status === 'OCISCENO'
    ).length;

    if (loading) return <p>Učitavanje...</p>;
    if (error) return <p>Greška pri dohvaćanju prijava.</p>;

    return (
        <main className="dashboard-background">
            <div className="dashboard-page">

                <div className="dashboard-header">
                    <h1>Upravljačka ploča redara</h1>
                    <p>Dobrodošli, {user.ime}!</p>
                </div>

                <div className="dashboard-stats">
                    <div className="dashboard-card">
                        <h3>Ukupno prijava</h3>
                        <span>{prijave.length}</span>
                    </div>

                    <div className="dashboard-card">
                        <h3>Nove prijave</h3>
                        <span>{aktivne}</span>
                    </div>

                    <div className="dashboard-card">
                        <h3>Očišćene lokacije</h3>
                        <span>{ociscene}</span>
                    </div>
                </div>

                <section className="dashboard-section">
                    <h2>Pregled prijava</h2>

                    <div className="dashboard-table-container">
                        <table className="dashboard-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Kategorija</th>
                                    <th>Lokacija</th>
                                    <th>Prijavitelj</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>
                                {prijave.map(prijava => (
                                    <tr key={prijava.id}>
                                        <td>{prijava.id}</td>
                                        <td>{prijava.kategorija.naziv}</td>
                                        <td>{prijava.adresa || prijava.kvart || '-'}</td>
                                        <td>{prijava.prijavitelj?.ime}</td>
                                        <td>{prijava.status}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    {prijave.length === 0 && <p>Nema prijava.</p>}
                </section>

            </div>
        </main>
    );
}

export default RedarDashboard;