import { useState } from 'react';
import './LoginForm.css';
import eye from '/images/eye.png';
import hidden from '/images/hidden.png';

function LoginForm({ onSwitchToRegister, onLoginSuccess }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault(); // Impede que a página recarregue

    // --- DEBUG NO FRONTEND ---
    console.log('VALORES NO MOMENTO DO ENVIO:');
    console.log('Email do estado:', email);
    console.log('Senha do estado:', password);
    // -------------------------

    setError(''); 

    try {
      const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: email, senha: password }),
      });

      if (response.ok) { 
        const userData = await response.json();
        console.log('Login bem-sucedido:', userData);
        onLoginSuccess(userData);
      } else { 
        const errorData = await response.text();
        setError(errorData);
      }
    } catch (err) {
      console.error('Erro de conexão:', err);
      setError('Não foi possível conectar ao servidor.');
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Portal Acadêmico</h2>
        {/* Mostra a mensagem de erro, se houver */}
        {error && <p className="error-message">{error}</p>}

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha:</label>
          <div className="password-wrapper">
            <input id="password" type={showPassword ? 'text' : 'password'} value={password} onChange={(e) => setPassword(e.target.value)} />
            <button type="button" className="toggle-password" onClick={togglePasswordVisibility}>
              {showPassword ? <img src={eye} alt="Esconder senha" className="eye-icon" /> : <img src={hidden} alt="Esconder senha" className="hidden-icon" />}
            </button>
          </div>
        </div>
        <button type="submit" className="login-button">Entrar</button>
        <p style={{ textAlign: 'center', marginTop: '16px' }}>
          Não tem uma conta?{' '}
          <a href="#" className="switch-form-link" onClick={onSwitchToRegister}>
            Registre-se
          </a>
        </p>
      </form>
    </div>
  );
}

export default LoginForm;