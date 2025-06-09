import { useState } from 'react';
import LoginForm from './components/LoginForm';
import RegistroForm from './components/RegistroForm';
import AdminDashboard from './components/AdminDashboard';
import AlunoDashboard from './components/AlunoDashboard';
import './index.css';

function App() {
  const [view, setView] = useState('login'); // 'login' ou 'registro'
  const [loggedInUser, setLoggedInUser] = useState(null); // Guarda os dados do usuário logado

  // funçao chamada pelo LoginForm quando o login é da sucesso
  const handleLoginSuccess = (userData) => {
    console.log("Usuário logado:", userData);
    setLoggedInUser(userData);
  };

  const handleLogout = () => {
    setLoggedInUser(null);
    setView('login'); 
  };

  // Se ninguem estiver logado, mostre a tela de Login ou Registro
  if (!loggedInUser) {
    return view === 'login' ? (
      <LoginForm onSwitchToRegister={() => setView('registro')} onLoginSuccess={handleLoginSuccess} />
    ) : (
      <RegistroForm onSwitchToLogin={() => setView('login')} />
    );
  }

  // Se alguem estiver logado, verifica o tipo de usuario e mostra o painel correto
  return loggedInUser.tipoUsuario === 'ADM' ? (
    <AdminDashboard user={loggedInUser} onLogout={handleLogout} />
) : (
    <AlunoDashboard user={loggedInUser} onLogout={handleLogout} />
);
}

export default App;