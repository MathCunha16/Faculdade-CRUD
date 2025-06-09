import { useState, useEffect, useRef } from 'react';
import './CustomSelect.css';

function CustomSelect({ options, value, onChange, placeholder }) {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const wrapperRef = useRef(null);

  const selectedOption = options.find(option => option.id == value);

  // Efeito para fechar o dropdown se clicar fora dele
  useEffect(() => {
    function handleClickOutside(event) {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [wrapperRef]);

  const filteredOptions = options.filter(option =>
    option.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleSelect = (optionValue) => {
    onChange({ target: { name: 'cursoId', value: optionValue } });
    setIsOpen(false);
    setSearchTerm('');
  };

  return (
    <div className="custom-select-wrapper" ref={wrapperRef}>
      <div className="custom-select-header" onClick={() => setIsOpen(!isOpen)}>
        {selectedOption ? selectedOption.nome : <span className="placeholder">{placeholder}</span>}
        <span className={`arrow ${isOpen ? 'up' : 'down'}`}></span>
      </div>
      {isOpen && (
        <div className="custom-select-list-container">
          <input
            type="text"
            className="search-box"
            placeholder="Buscar curso..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            autoFocus
          />
          <ul className="custom-select-list">
            {filteredOptions.length > 0 ? (
              filteredOptions.map(option => (
                <li key={option.id} onClick={() => handleSelect(option.id)}>
                  {option.nome}
                </li>
              ))
            ) : (
              <li className="no-options">Nenhum curso encontrado</li>
            )}
          </ul>
        </div>
      )}
    </div>
  );
}

export default CustomSelect;