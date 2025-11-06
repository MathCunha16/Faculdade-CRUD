package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "disciplina", uniqueConstraints = {
    @UniqueConstraint(name = "UK_disciplina_codigo", columnNames = "codigo")
})
public class Disciplina extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "FK_disciplina_curso"))
    private Curso curso;

    @Column(name = "semestre_recomendado", nullable = false)
    private Integer semestreRecomendado;

    public Disciplina() {
    }

    public Disciplina(String nome, String codigo, Integer cargaHoraria, Curso curso, Integer semestreRecomendado) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
        this.semestreRecomendado = semestreRecomendado;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getSemestreRecomendado() {
        return semestreRecomendado;
    }

    public void setSemestreRecomendado(Integer semestreRecomendado) {
        this.semestreRecomendado = semestreRecomendado;
    }

}


