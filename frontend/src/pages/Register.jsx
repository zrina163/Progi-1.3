import { useState } from 'react';

export default function Register() {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        if(password !== confirmPassword){
            alert("Lozinke se ne podudaraju!");
            return;
        }

        console.log("Podatci za registraciju: ", {
            username,
            email
        });

        setUsername('');
        setEmail('');
        setPassword('');
        setConfirmPassword('');
    };

    return (
        <div>
          <h2>Registracija u EcoSentry</h2>
          <form onSubmit={handleSubmit}>
            <div>
              <label>Username:</label>
              <input
                type="text"
                placeholder="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)} 
                required      
              />
            </div>

            <div>
              <label>Email:</label>
              <input
                type="email"
                placeholder="korisnik.mail@gmail.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            
            <div>
              <label>Password:</label>
              <input
                type="password"
                placeholder="#####"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            
            <div>
              <label>Confirm password:</label>
              <input
                type="password"
                placeholder="#####"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>

        <button type="submit">Registriraj se</button>
      </form>
        </div>
    );
}