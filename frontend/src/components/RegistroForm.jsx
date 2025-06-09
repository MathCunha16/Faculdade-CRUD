import { useState } from 'react';
import './LoginForm.css';
import eye from '/images/eye.png';
import hidden from '/images/hidden.png';

function RegistroForm({ onSwitchToLogin }) {
  const [formData, setFormData] = useState({
    matricula: '',
    email: '',
    password: ''
  });

  const [showPassword, setShowPassword] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({ ...prevState, [name]: value }));
  };
  
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMensagem({ texto: '', tipo: '' });

    const dadosParaEnviar = {
      matricula: parseInt(formData.matricula, 10),
      email: formData.email,
      senha: formData.password
    };

    try {
      const response = await fetch(`http://localhost:8080/api/registrar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dadosParaEnviar)
      });

      const responseText = await response.text();

      if (response.ok) {
        setMensagem({ texto: responseText, tipo: 'sucesso' });
        setTimeout(() => onSwitchToLogin(), 2000);
      } else {
        setMensagem({ texto: `Erro: ${responseText}`, tipo: 'erro' });
      }
    } catch (error) {
      setMensagem({ texto: 'Erro de conexão com o servidor.', tipo: 'erro' });
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Registrar Novo Usuário</h2>
        {mensagem.texto && <p className={`form-message ${mensagem.tipo}`}>{mensagem.texto}</p>}
        
        <div className="form-group">
          <label htmlFor="matricula">Matrícula:</label>
          <input id="matricula" type="text" name="matricula" value={formData.matricula} onChange={handleChange} required />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input id="email" type="email" name="email" value={formData.email} onChange={handleChange} required />
        </div>

        <div className="form-group">
          <label htmlFor="password">Senha:</label>
          <div className="password-wrapper">
            <input
              id="password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              value={formData.password}
              onChange={handleChange}
              required
            />
            <button type="button" className="toggle-password" onClick={togglePasswordVisibility}>
              {showPassword ? <img src={eye} alt="Esconder senha" className="eye-icon" /> : <img src={hidden} alt="Mostrar senha" className="hidden-icon" />}
            </button>
          </div>
        </div>

        <button type="submit" className="login-button">Registrar</button>

        <p style={{ textAlign: 'center', marginTop: '16px' }}>
          Já tem uma conta?{' '}
          <a href="#" className="switch-form-link" onClick={onSwitchToLogin}>
            Faça o login
          </a>
        </p>
      </form>
    </div>
  );
}

export default RegistroForm;