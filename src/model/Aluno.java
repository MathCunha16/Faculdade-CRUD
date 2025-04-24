package model;

import java.time.LocalDate;

public class Aluno {
	private Integer id;
	private Integer matricula;
	private String nome;
	private String telefone;
	private LocalDate dataDeNascimento;
	private Curso curso;
	private String cpf;
	
	public Aluno () {
		
	}
	
	public Aluno(Integer matricula, String nome, String telefone, LocalDate dataDeNascimento, Curso curso,
			String cpf) {  // Construtor sem o ID
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.telefone = telefone;
		this.dataDeNascimento = dataDeNascimento;
		this.curso = curso;
		this.cpf = cpf;
	}



	public Aluno(Integer id, Integer matricula, String nome, String telefone, LocalDate dataDeNascimento, Curso curso,
			String cpf) {
		super();
		this.id = id;
		this.matricula = matricula;
		this.nome = nome;
		this.telefone = telefone;
		this.dataDeNascimento = dataDeNascimento;
		this.curso = curso;
		this.cpf = cpf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
