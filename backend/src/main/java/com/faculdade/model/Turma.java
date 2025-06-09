package com.faculdade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Entity
@Table(name = "turma")
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_turma")
	private int idTurma;

	@Column(name = "nome_turma") 
	private String nomeTurma;

	private String turno;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@Column(name = "alunos")
	private String alunosStr;

	@Transient
	private List<String> alunos;

	public Turma() {
		this.alunos = new ArrayList<>();
	}

	public Turma(int idTurma, String nomeTurma, String turno, Curso curso, String alunosStr) {
		this.idTurma = idTurma;
		this.nomeTurma = nomeTurma;
		this.turno = turno;
		this.curso = curso;
		setAlunosStr(alunosStr);
	}

	private void converterStringParaLista(String str) {
		this.alunos = new ArrayList<>();
		if (str != null && !str.isEmpty() && !str.isBlank()) {
			this.alunos.addAll(Arrays.asList(str.split(",")));
		}
	}

	// Getters e Setters
	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getAlunosStr() {
		return alunosStr;
	}

	public void setAlunosStr(String alunosStr) {
		this.alunosStr = alunosStr;
		converterStringParaLista(alunosStr);
	}

	public List<String> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<String> alunos) {
		this.alunos = alunos;
		this.alunosStr = String.join(",", alunos);
	}
}