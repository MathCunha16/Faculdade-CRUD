import { useState, useEffect } from 'react';
import './AlunoDashboard.css';

function AlunoDashboard({ user, onLogout }) {
  const [aluno, setAluno] = useState(null);
  const [turmas, setTurmas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Função para buscar todos os dados do aluno e suas turmas
    const fetchData = async () => {
      if (!user || !user.matricula) return;

      try {
        setLoading(true);
        // 1 Busca os detalhes do aluno especifico
        const alunoResponse = await fetch(`http://localhost:8080/api/alunos/${user.matricula}`);
        if (!alunoResponse.ok) throw new Error('Falha ao buscar dados do aluno.');
        const alunoData = await alunoResponse.json();
        setAluno(alunoData);

        // 2 Busca TODAS as turmas
        const turmasResponse = await fetch('http://localhost:8080/api/turmas');
        if (!turmasResponse.ok) throw new Error('Falha ao buscar turmas.');
        const todasAsTurmas = await turmasResponse.json();

        // 3 Filtra as turmas para encontrar apenas as do aluno logado
        const turmasDoAluno = todasAsTurmas.filter(turma => 
          turma.alunosStr && turma.alunosStr.includes(`${user.matricula}:`)
        );
        setTurmas(turmasDoAluno);

      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [user]); // Roda o efeito sempre que o usuário logado mudar

  if (loading) return <p>Carregando suas informações...</p>;
  if (error) return <p>Erro: {error}</p>;
  if (!aluno) return <p>Não foi possível carregar os dados do aluno.</p>;

  return (
    <div className="aluno-dashboard">
      <header className="dashboard-header">
        <h1>Portal do Aluno</h1>
        <div className="user-info">
          <span>Bem-vindo(a), <strong>{aluno.nome.split(' ')[0]}</strong>!</span>
          <button onClick={onLogout} className="logout-btn">Sair</button>
        </div>
      </header>

      <main className="dashboard-main-content">
        <div className="info-card">
          <h2>Minhas Informações</h2>
          <div className="info-grid">
            <p><strong>Nome Completo:</strong> {aluno.nome}</p>
            <p><strong>Matrícula:</strong> {aluno.matricula}</p>
            <p><strong>CPF:</strong> {aluno.cpf}</p>
            <p><strong>Telefone:</strong> {aluno.telefone}</p>
            <p><strong>Data de Nascimento:</strong> {new Date(aluno.dataDeNascimento).toLocaleDateString('pt-BR', { timeZone: 'UTC' })}</p>
            <p><strong>Curso:</strong> {aluno.curso.nome}</p>
          </div>
        </div>

        <div className="info-card">
          <h2>Minhas Turmas</h2>
          {turmas.length > 0 ? (
            <ul className="turmas-list">
              {turmas.map(turma => (
                <li key={turma.idTurma}>
                  <span className="turma-nome">{turma.nomeTurma}</span>
                  <span className="turma-turno">{turma.turno}</span>
                </li>
              ))}
            </ul>
          ) : (
            <p>Você não está matriculado em nenhuma turma no momento.</p>
          )}
        </div>
      </main>
    </div>
  );
}

export default AlunoDashboard;