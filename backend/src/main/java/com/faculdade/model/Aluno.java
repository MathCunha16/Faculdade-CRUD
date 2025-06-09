package com.faculdade.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "aluno")
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer matricula;
	
	@NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres (nome e sobrenome).")
	private String nome;
	
	@NotBlank(message = "O telefone é obrigatório.")
	private String telefone;
	
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data no passado.")
	private LocalDate dataDeNascimento;
	
    @ManyToOne
    @JoinColumn(name = "id_curso")
	private Curso curso;
	
    @NotBlank(message = "O CPF é obrigatório.")
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
