import React, { useState } from 'react';
import AlunoSearch from './AlunoSearch';
import './TurmaDetail.css';

function TurmaDetail({ turma: turmaInicial, onBack }) {
  const [turma, setTurma] = useState(turmaInicial);

  // A lógica para separar a string de alunos em um array de objetos
  const listaDeAlunos = turma.alunosStr ? turma.alunosStr.split(',').filter(Boolean).map(alunoStr => {
    const [matricula, ...nomeParts] = alunoStr.split(':');
    const nome = nomeParts.join(':');
    return { matricula, nome };
  }) : [];

  const handleAddAluno = async (alunoSelecionado) => {
    try {
      const response = await fetch(`http://localhost:8080/api/turmas/${turma.idTurma}/alunos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ matricula: alunoSelecionado.matricula })
      });
      if (response.ok) {
        const turmaAtualizada = await response.json();
        setTurma(turmaAtualizada);
        alert('Aluno adicionado com sucesso!');
      } else {
        alert('Falha ao adicionar aluno.');
      }
    } catch (error) {
      alert('Erro de conexão ao adicionar aluno.');
    }
  };

  const handleRemoveAluno = async (matriculaAluno) => {
    if (window.confirm(`Tem certeza que deseja remover o aluno de matrícula ${matriculaAluno} desta turma?`)) {
      try {
        const response = await fetch(`http://localhost:8080/api/turmas/<span class="math-inline">\{turma\.idTurma\}/alunos/</span>{matriculaAluno}`, {
          method: 'DELETE'
        });
        if (response.ok) {
          const turmaAtualizada = await response.json();
          setTurma(turmaAtualizada);
          alert('Aluno removido com sucesso!');
        } else {
          alert('Falha ao remover aluno.');
        }
      } catch (error) {
        alert('Erro de conexão ao remover aluno.');
      }
    }
  };

  return (
    <div className="turma-detail-container">
      <button className="back-button" onClick={onBack}>&larr; Voltar para a Lista</button>

      <div className="detail-header">
        <h1>{turma.nomeTurma}</h1>
        <div className="header-pills">
          <span className="pill">ID da Turma: {turma.idTurma}</span>
          <span className="pill">Curso: {turma.curso ? `${turma.curso.nome} (ID: ${turma.curso.id})` : 'Não especificado'}</span>

          <span className="pill">Turno: {turma.turno}</span>
        </div>
      </div>

      <div className="detail-grid">
        <div className="alunos-section card">
          <h2>Alunos na Turma ({listaDeAlunos.length})</h2>
          {listaDeAlunos.length > 0 ? (
            <ul className="aluno-na-turma-list">
              {listaDeAlunos.map((aluno, index) => (
                <li key={index}>
                  <span>{aluno.matricula} - {aluno.nome}</span>
                  <button className="remove-aluno-btn" onClick={() => handleRemoveAluno(aluno.matricula)}>Remover</button>
                </li>
              ))}
            </ul>
          ) : (
            <p>Nenhum aluno matriculado nesta turma.</p>
          )}
        </div>

        <div className="add-aluno-section card">
          <h3>Adicionar Aluno à Turma</h3>
          <p>Busque pelo nome do aluno que deseja matricular nesta turma.</p>
          <AlunoSearch onAlunoSelect={handleAddAluno} />
        </div>
      </div>
    </div>
  );
}

export default TurmaDetail;