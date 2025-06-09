import { useState, useEffect } from 'react';
import './AlunoSearch.css';

function AlunoSearch({ onAlunoSelect }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Não busca se o termo for muito curto
    if (searchTerm.length < 2) {
      setResults([]);
      return;
    }

    const fetchAlunos = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:8080/api/alunos/buscar?nome=${searchTerm}`);
        const data = await response.json();
        setResults(data);
      } catch (error) {
        console.error("Erro ao buscar alunos:", error);
      }
      setLoading(false);
    };

    // Adiciona um "delay" para não fazer uma busca a cada letra digitada
    const delayDebounceFn = setTimeout(() => {
      fetchAlunos();
    }, 500); // Espera 500ms depois da ultima letra digitada

    return () => clearTimeout(delayDebounceFn);
  }, [searchTerm]);

  const handleSelect = (aluno) => {
    onAlunoSelect(aluno);
    setSearchTerm('');
    setResults([]);
  };

  return (
    <div className="aluno-search">
      <input
        type="text"
        placeholder="Digite o nome do aluno para adicionar..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {loading && <p>Buscando...</p>}
      {results.length > 0 && (
        <ul className="search-results">
          {results.map(aluno => (
            <li key={aluno.id} onClick={() => handleSelect(aluno)}>
              {aluno.nome} ({aluno.matricula})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default AlunoSearch;