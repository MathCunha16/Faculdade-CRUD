import { useState } from 'react';
import Sidebar from './Sidebar';
import AlunoList from './AlunoList';
import AlunoForm from './AlunoForm';
import TurmaList from './TurmaList';
import TurmaDetail from './TurmaDetail';
import TurmaForm from './TurmaForm'; // Importe o novo formulário de turma
import './AdminDashboard.css';

function AdminDashboard({ user, onLogout }) {
  const [activeView, setActiveView] = useState('listarAlunos');
  const [alunoParaEditar, setAlunoParaEditar] = useState(null);
  const [turmaSelecionada, setTurmaSelecionada] = useState(null);
  const [listKey, setListKey] = useState(0); // Chave para forçar refresh das listas

  const refreshList = () => {
    setListKey(prevKey => prevKey + 1);
  };

  const handleShowCadastroAluno = () => {
    setAlunoParaEditar(null);
    setActiveView('formAluno');
  };

  const handleEditAluno = (aluno) => {
    setAlunoParaEditar(aluno);
    setActiveView('formAluno');
  };

  const handleGerenciarTurma = (turma) => {
    setTurmaSelecionada(turma);
    setActiveView('detalheTurma');
  };

  const handleShowCreateTurma = () => {
    setActiveView('formTurma');
  };

  const handleFormSubmit = () => {
    setActiveView(activeView.includes('Aluno') ? 'listarAlunos' : 'gerenciarTurmas');
    refreshList();
  };

  const renderView = () => {
    switch (activeView) {
      case 'listarAlunos':
        return <AlunoList key={listKey} onEdit={handleEditAluno} />;
      case 'formAluno':
        return <AlunoForm alunoInicial={alunoParaEditar} onFormSubmit={handleFormSubmit} />;
      case 'gerenciarTurmas':
        return <TurmaList onGerenciar={handleGerenciarTurma} onShowCreateForm={handleShowCreateTurma} />;
      case 'detalheTurma':
        return <TurmaDetail turma={turmaSelecionada} onBack={() => setActiveView('gerenciarTurmas')} />;
      case 'formTurma': // <-- NOVA OPÇÃO AQUI
        return <TurmaForm onTurmaCriada={handleFormSubmit} />;
      default:
        return <AlunoList key={listKey} onEdit={handleEditAluno} />;
    }
  };

  return (
    <div className="admin-dashboard">
      <Sidebar
        setActiveView={setActiveView}
        onShowCadastro={handleShowCadastroAluno}
        onLogout={onLogout}
      />
      <main className="dashboard-content">
        {renderView()}
      </main>
    </div>
  );
}

export default AdminDashboard;