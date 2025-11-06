package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "curso", uniqueConstraints = {
    @UniqueConstraint(name = "UK_curso_nome", columnNames = "nome"),
    @UniqueConstraint(name = "UK_curso_codigo", columnNames = "codigo")
})
public class Curso extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(name = "duracao_semestres", nullable = false)
    private Integer duracaoSemestres;

    public Curso() {
    }

    public Curso(String nome, String codigo, Integer duracaoSemestres) {
        this.nome = nome;
        this.codigo = codigo;
        this.duracaoSemestres = duracaoSemestres;
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

    public Integer getDuracaoSemestres() {
        return duracaoSemestres;
    }

    public void setDuracaoSemestres(Integer duracaoSemestres) {
        this.duracaoSemestres = duracaoSemestres;
    }

}


