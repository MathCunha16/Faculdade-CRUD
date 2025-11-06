package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import com.faculdade.entity.enums.Turno;
import jakarta.persistence.*;

@Entity
@Table(name = "turma", uniqueConstraints = {
    @UniqueConstraint(name = "UK_turma_codigo", columnNames = "codigo_turma")
})
public class Turma extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_turma", nullable = false, length = 50, unique = true)
    private String codigoTurma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina", nullable = false, foreignKey = @ForeignKey(name = "FK_turma_disciplina"))
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", nullable = false, foreignKey = @ForeignKey(name = "FK_turma_professor"))
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "FK_turma_curso"))
    private Curso curso;

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo;

    @Column(nullable = false)
    private Integer semestre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Turno turno;

    @Column(name = "vagas_totais", nullable = false)
    private Integer vagasTotais;

    public Turma() {
    }

    public Turma(String codigoTurma, Disciplina disciplina, Professor professor, Curso curso,
                 Integer anoLetivo, Integer semestre, Turno turno, Integer vagasTotais) {
        this.codigoTurma = codigoTurma;
        this.disciplina = disciplina;
        this.professor = professor;
        this.curso = curso;
        this.anoLetivo = anoLetivo;
        this.semestre = semestre;
        this.turno = turno;
        this.vagasTotais = vagasTotais;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(Integer anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(Integer vagasTotais) {
        this.vagasTotais = vagasTotais;
    }

}


