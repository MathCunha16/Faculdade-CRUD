import { useState, useEffect } from 'react';
import './AlunoList.css';

function TurmaList({ onGerenciar, onShowCreateForm }) {
  const [turmas, setTurmas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTurmas = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/api/turmas');
        if (!response.ok) throw new Error('Falha ao buscar dados das turmas');
        const data = await response.json();
        setTurmas(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchTurmas();
  }, []);

  const handleDelete = async (idTurma) => {
    if (window.confirm(`Tem certeza que deseja excluir a turma de ID ${idTurma}?`)) {
      try {
        const response = await fetch(`http://localhost:8080/api/turmas/${idTurma}`, {
          method: 'DELETE',
        });
        if (response.ok) {
          alert('Turma excluída com sucesso!');
          setTurmas(turmas.filter(turma => turma.idTurma !== idTurma));
        } else {
          alert('Falha ao excluir a turma.');
        }
      } catch (err) {
        alert('Erro de conexão ao excluir a turma.');
      }
    }
  };

  if (loading) return <p>Carregando turmas...</p>;
  if (error) return <p>Erro: {error}</p>;

  return (
    <div className="aluno-list-container">
      <div className="list-header">
        <h1>Lista de Turmas</h1>
        <button className="submit-btn" onClick={onShowCreateForm}>+ Criar Nova Turma</button>
      </div>
      {turmas.length === 0 ? (
        <p>Nenhuma turma cadastrada no momento.</p>
      ) : (
        <table className="aluno-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome da Turma</th>
              <th>Curso</th> {/* <-- NOVA COLUNA */}
              <th>Turno</th>
              <th>Alunos Matriculados</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {turmas.map((turma) => (
              <tr key={turma.idTurma}>
                <td>{turma.idTurma}</td>
                <td>{turma.nomeTurma}</td>
                <td>{turma.curso ? turma.curso.nome : 'N/A'}</td> {/* <-- DADO DO CURSO AQUI */}
                <td>{turma.turno}</td>
                <td>{turma.alunosStr ? turma.alunosStr.split(',').filter(Boolean).length : 0}</td>
                <td className="actions-cell">
                  <button className="edit-btn" onClick={() => onGerenciar(turma)}>Gerenciar</button>
                  <button className="delete-btn" onClick={() => handleDelete(turma.idTurma)}>Excluir</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default TurmaList;