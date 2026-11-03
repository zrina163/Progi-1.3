import { useState } from "react";

export default function Login() {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log('E-mail iz bilježnice: ', email);

    setEmail('');
    setPassword('');
  }

  return (
    <div>
      <h2>Prijava u EcoSentry</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            placeholder="korinik.mail@gmail.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div>
          <label>Lozinka:</label>
          <input
            type="password"
            placeholder="#####"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit">Prijavi se</button>
      </form>
    </div>
  );
}