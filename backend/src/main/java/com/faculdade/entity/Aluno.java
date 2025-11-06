package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import com.faculdade.entity.enums.StatusAluno;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "aluno", uniqueConstraints = {
    @UniqueConstraint(name = "UK_aluno_matricula", columnNames = "matricula"),
    @UniqueConstraint(name = "UK_aluno_cpf", columnNames = "cpf"),
    @UniqueConstraint(name = "UK_aluno_email", columnNames = "email")
})
public class Aluno extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer matricula;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 15, unique = true)
    private String cpf;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 25)
    private String telefone;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false, foreignKey = @ForeignKey(name = "FK_aluno_curso"))
    private Curso curso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAluno status = StatusAluno.ATIVO;

    public Aluno() {
    }

    public Aluno(Integer matricula, String nome, String cpf, String email, String telefone, 
                 LocalDate dataNascimento, Curso curso, StatusAluno status) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
        this.status = status;
    }

    // Getters and Setters
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        this.status = status;
    }

}



