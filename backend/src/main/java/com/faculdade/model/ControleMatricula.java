package com.faculdade.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "controle_matricula")
public class ControleMatricula {
	@Id // Usando a própria matricula como ID, já que só tem uma linha
	private int ultima_matricula;

	public int getUltima_matricula() {
		return ultima_matricula;
	}

	public void setUltima_matricula(int ultima_matricula) {
		this.ultima_matricula = ultima_matricula;
	}
}