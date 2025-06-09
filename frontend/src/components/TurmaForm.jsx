import { useState, useEffect } from 'react';
import './AlunoForm.css';

function TurmaForm({ onTurmaCriada }) {
  const [cursos, setCursos] = useState([]);
  const [turma, setTurma] = useState({
    nomeTurma: '',
    turno: '',
    idCurso: ''
  });
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });

  useEffect(() => {
    const fetchCursos = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/cursos');
        const data = await response.json();
        setCursos(data);
      } catch (error) {
        console.error("Erro ao buscar cursos:", error);
      }
    };
    fetchCursos();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTurma(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensagem({ texto: '', tipo: '' });

    const turmaParaEnviar = {
        nomeTurma: turma.nomeTurma,
        turno: turma.turno,
        curso: { id: parseInt(turma.idCurso, 10) },
        alunosStr: ''
      };

    try {
      const response = await fetch('http://localhost:8080/api/turmas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(turmaParaEnviar)
      });

      if (response.ok) {
        setMensagem({ texto: 'Turma criada com sucesso!', tipo: 'sucesso' });
        if (onTurmaCriada) {
          setTimeout(() => onTurmaCriada(), 1500); // Espera 1.5s e volta para a lista
        }
      } else {
        setMensagem({ texto: 'Falha ao criar turma.', tipo: 'erro' });
      }
    } catch (error) {
      setMensagem({ texto: 'Erro de conexão.', tipo: 'erro' });
    }
  };

  return (
    <div className="aluno-form-container">
      <h1>Criar Nova Turma</h1>
      <form onSubmit={handleSubmit} className="aluno-form">
        <div className="form-group">
          <label>Nome da Turma</label>
          <input type="text" name="nomeTurma" value={turma.nomeTurma} onChange={handleChange} required />
        </div>
        <div className="form-row">
          <div className="form-group">
            <label>Turno</label>
            <select name="turno" value={turma.turno} onChange={handleChange} required>
              <option value="">Selecione o turno</option>
              <option value="MANHA">Manhã</option>
              <option value="TARDE">Tarde</option>
              <option value="NOITE">Noite</option>
            </select>
          </div>
          <div className="form-group">
            <label>Curso</label>
            <select name="idCurso" value={turma.idCurso} onChange={handleChange} required>
              <option value="">Selecione o curso</option>
              {cursos.map(curso => (
                <option key={curso.id} value={curso.id}>{curso.nome}</option>
              ))}
            </select>
          </div>
        </div>
        <button type="submit" className="submit-btn">Salvar Turma</button>
      </form>
      {mensagem.texto && <p className={`form-message ${mensagem.tipo}`}>{mensagem.texto}</p>}
    </div>
  );
}

export default TurmaForm;