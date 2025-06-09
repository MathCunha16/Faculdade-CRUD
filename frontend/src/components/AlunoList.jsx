import { useState, useEffect } from 'react';
import './AlunoList.css';

function AlunoList({ key, onEdit }) {
  const [alunos, setAlunos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchAlunos = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/alunos');
      if (!response.ok) {
        throw new Error('Falha ao buscar dados');
      }
      const data = await response.json();
      setAlunos(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAlunos();
  }, [key]);

  const handleDelete = async (matricula) => {
    if (window.confirm(`Tem certeza que deseja excluir o aluno de matrícula ${matricula}?`)) {
      try {
        const response = await fetch(`http://localhost:8080/api/alunos/${matricula}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          // Se a exclusão deu certo, atualiza a lista de alunos na tela
          alert('Aluno excluído com sucesso!');
          fetchAlunos(); // Re-busca os alunos para atualizar a lista
        } else {
          alert('Falha ao excluir o aluno.');
        }
      } catch (err) {
        alert('Erro de conexão ao excluir o aluno.');
      }
    }
  };

  if (loading) return <p>Carregando alunos...</p>;
  if (error) return <p>Erro: {error}</p>;

  return (
    <div className="aluno-list-container">
      <h1>Lista de Alunos</h1>
      <table className="aluno-table">
        <thead>
          <tr>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>Curso</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {alunos.map((aluno) => (
            <tr key={aluno.id}>
              <td>{aluno.matricula}</td>
              <td>{aluno.nome}</td>
              <td>{aluno.curso ? aluno.curso.nome : 'N/A'}</td>
              <td>{aluno.telefone}</td>
              <td className="actions-cell">
                <button className="edit-btn" onClick={() => onEdit(aluno)}>Editar</button>
                <button className="delete-btn" onClick={() => handleDelete(aluno.matricula)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AlunoList;