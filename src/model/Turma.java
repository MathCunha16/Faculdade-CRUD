package model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private int idTurma;
    private String nomeTurma;
    private String turno;
    private int idCurso;
    private List<String> alunos; // formato: "matricula:nome"

    public Turma(int idTurma, String nomeTurma, String turno, int idCurso, String alunosStr) {
        this.idTurma = idTurma;
        this.nomeTurma = nomeTurma;
        this.turno = turno;
        this.idCurso = idCurso;
        this.alunos = converterStringParaLista(alunosStr);
    }

    private List<String> converterStringParaLista(String str) {
        List<String> lista = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            String[] partes = str.split(",");
            for (String parte : partes) {
                lista.add(parte.trim());
            }
        }
        return lista;
    }

    public String getAlunosComoString() {
        return String.join(",", alunos);
    }

    // Getters e Setters
    public int getIdTurma() { return idTurma; }
    public String getNomeTurma() { return nomeTurma; }
    public String getTurno() { return turno; }
    public int getIdCurso() { return idCurso; }
    public List<String> getAlunos() { return alunos; }
    public void setAlunos(List<String> alunos) { this.alunos = alunos; }
}