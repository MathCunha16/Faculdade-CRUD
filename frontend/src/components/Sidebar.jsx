import './Sidebar.css';

function Sidebar({ setActiveView, onShowCadastro, onLogout }) {
  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h3>UniEsquina</h3>
        <span>Painel ADM</span>
      </div>
      <nav className="sidebar-nav">
        <button onClick={() => setActiveView('listarAlunos')}>
          Listar Alunos
        </button>
        <button onClick={onShowCadastro}>
          Cadastrar Aluno
        </button>
        <button onClick={() => setActiveView('gerenciarTurmas')}>
          Gerenciar Turmas
        </button>
      </nav>
      <div className="sidebar-footer">
        <button className="logout-button" onClick={onLogout}>
          Sair (Logout)
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;