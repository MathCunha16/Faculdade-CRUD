package com.faculdade.entity;

import com.faculdade.entity.base.BaseEntity;
import com.faculdade.entity.enums.StatusProfessor;
import jakarta.persistence.*;

@Entity
@Table(name = "professor", uniqueConstraints = {
    @UniqueConstraint(name = "UK_professor_cpf", columnNames = "cpf"),
    @UniqueConstraint(name = "UK_professor_email", columnNames = "email")
})
public class Professor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 15, unique = true)
    private String cpf;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 25)
    private String telefone;

    @Column(length = 100)
    private String especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProfessor status = StatusProfessor.ATIVO;

    public Professor() {
    }

    public Professor(String nome, String cpf, String email, String telefone, 
                     String especialidade, StatusProfessor status) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.status = status;
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public StatusProfessor getStatus() {
        return status;
    }

    public void setStatus(StatusProfessor status) {
        this.status = status;
    }
}


