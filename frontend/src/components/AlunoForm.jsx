import { useState, useEffect } from 'react';
import CustomSelect from './CustomSelect';
import './AlunoForm.css';

function AlunoForm({ alunoInicial, onFormSubmit }) {
  const [cursos, setCursos] = useState([]);
  const [aluno, setAluno] = useState({
    nome: '',
    telefone: '',
    dataDeNascimento: '',
    cpf: '',
    curso: { id: '' }
  });
  const [mensagem, setMensagem] = useState({ texto: '', tipo: '' });
  const [isEditing, setIsEditing] = useState(false);

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

  useEffect(() => {
    if (alunoInicial) {
      const dataFormatada = alunoInicial.dataDeNascimento ? alunoInicial.dataDeNascimento.split('T')[0] : '';
      setAluno({ ...alunoInicial, dataDeNascimento: dataFormatada });
      setIsEditing(true);
    } else {
      setAluno({ nome: '', telefone: '', dataDeNascimento: '', cpf: '', curso: { id: '' } });
      setIsEditing(false);
    }
  }, [alunoInicial]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'cursoId') {
      setAluno(prevState => ({ ...prevState, curso: { id: value } }));
    } else {
      setAluno(prevState => ({ ...prevState, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // Impede o recarregamento da página
    setMensagem({ texto: '', tipo: '' }); // Limpa mensagens antigas

    // Determina a URL e o método (POST para criar, PUT para editar)
    const url = isEditing
      ? `http://localhost:8080/api/alunos/${aluno.matricula}`
      : 'http://localhost:8080/api/alunos';
    
    const method = isEditing ? 'PUT' : 'POST';

    try {
      // ---- ESTA PARTE É A QUE FAZ A CHAMADA PARA O BACKEND ----
      const response = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(aluno)
      });
      // ----------------------------------------------------------------------

      if (response.ok) {
        const successMessage = isEditing ? 'Aluno atualizado com sucesso!' : 'Aluno cadastrado com sucesso!';
        setMensagem({ texto: successMessage, tipo: 'sucesso' });
        if (onFormSubmit) {
          // Avisa o componente pai para trocar de tela depois de 1.5s
          setTimeout(() => onFormSubmit(), 1500); 
        }
      } else {
        // Tenta ler a mensagem de erro do backend
        const errorData = await response.json();
        const errorMessage = errorData.message || 'Falha ao salvar aluno.';
        setMensagem({ texto: `Erro: ${errorMessage}`, tipo: 'erro' });
      }
    } catch (error) {
      setMensagem({ texto: 'Erro de conexão ao salvar aluno.', tipo: 'erro' });
      console.error("Erro de conexão:", error);
    }
  };

  return (
    <div className="aluno-form-container">
      <h1>{isEditing ? 'Editar Aluno' : 'Cadastrar Novo Aluno'}</h1>
      <form onSubmit={handleSubmit} className="aluno-form">
        <div className="form-row">
          <div className="form-group">
            <label>Nome Completo</label>
            <input type="text" name="nome" value={aluno.nome} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label>CPF</label>
            <input type="text" name="cpf" value={aluno.cpf} onChange={handleChange} required />
          </div>
        </div>
        <div className="form-row">
          <div className="form-group">
            <label>Telefone</label>
            <input type="tel" name="telefone" value={aluno.telefone} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label>Data de Nascimento</label>
            <input type="date" name="dataDeNascimento" value={aluno.dataDeNascimento} onChange={handleChange} required />
          </div>
        </div>
        <div className="form-group">
          <label>Curso</label>
          
          <CustomSelect
            options={cursos}
            value={aluno.curso ? aluno.curso.id : ''}
            onChange={handleChange}
            placeholder="Selecione ou busque um curso"
          />
          
        </div>
        <button type="submit" className="submit-btn">{isEditing ? 'Salvar Alterações' : 'Salvar Aluno'}</button>
      </form>
      {mensagem.texto && <p className={`form-message ${mensagem.tipo}`}>{mensagem.texto}</p>}
    </div>
  );
}

export default AlunoForm;