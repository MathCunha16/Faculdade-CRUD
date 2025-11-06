package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import com.faculdade.entity.enums.StatusMatricula;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "aluno_turma", uniqueConstraints = {
    @UniqueConstraint(name = "UK_aluno_turma", columnNames = {"id_aluno", "id_turma"})
})
public class AlunoTurma extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false, foreignKey = @ForeignKey(name = "FK_aluno_turma_aluno"))
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma", nullable = false, foreignKey = @ForeignKey(name = "FK_aluno_turma_turma"))
    private Turma turma;

    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusMatricula status = StatusMatricula.MATRICULADO;

    @Column(name = "nota_final", precision = 4, scale = 2)
    private BigDecimal notaFinal;

    @Column(precision = 5, scale = 2)
    private BigDecimal frequencia;

    public AlunoTurma() {
    }

    public AlunoTurma(Aluno aluno, Turma turma, LocalDate dataMatricula, StatusMatricula status) {
        this.aluno = aluno;
        this.turma = turma;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(BigDecimal notaFinal) {
        this.notaFinal = notaFinal;
    }

    public BigDecimal getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(BigDecimal frequencia) {
        this.frequencia = frequencia;
    }

}


