package dao;

import db.DB;
import db.DbException;
import model.Turma;
import model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    public void criarTurma(Turma turma) {
        String sql = "INSERT INTO turma (nome_turma, turno, id_curso, alunos) VALUES (?, ?, ?, '')";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, turma.getNomeTurma());
            stmt.setString(2, turma.getTurno());
            stmt.setInt(3, turma.getIdCurso());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idTurma = rs.getInt(1);
                    System.out.println("Turma criada com ID: " + idTurma);
                }
            }
            
        } catch (SQLException e) {
            throw new DbException("Erro ao criar turma: " + e.getMessage());
        }
    }

    public void adicionarAluno(int idTurma, int matricula) {
        Turma turma = buscarPorId(idTurma);
        if (turma == null) {
            throw new DbException("Turma não encontrada!");
        }

        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
        if (aluno == null) {
            throw new DbException("Aluno não encontrado!");
        }

        String novoAluno = matricula + ":" + aluno.getNome();
        if (turma.getAlunos().contains(novoAluno)) {
            throw new DbException("Aluno já está na turma!");
        }

        turma.getAlunos().add(novoAluno);

        String sql = "UPDATE turma SET alunos = ? WHERE id_turma = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, turma.getAlunosComoString());
            stmt.setInt(2, idTurma);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar turma: " + e.getMessage());
        }

        
        turma = buscarPorId(idTurma);
    }

    public Turma buscarPorId(int idTurma) {
        String sql = "SELECT * FROM turma WHERE id_turma = ?";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTurma);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Turma(
                        rs.getInt("id_turma"),
                        rs.getString("nome_turma"),
                        rs.getString("turno"),
                        rs.getInt("id_curso"),
                        rs.getString("alunos")
                    );
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar turma: " + e.getMessage());
        }
    }

    public List<Turma> listarTurmas() {
        String sql = "SELECT * FROM turma";
        List<Turma> turmas = new ArrayList<>();
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Turma turma = new Turma(
                    rs.getInt("id_turma"),
                    rs.getString("nome_turma"),
                    rs.getString("turno"),
                    rs.getInt("id_curso"),
                    rs.getString("alunos")
                );
                turmas.add(turma);
            }
            
        } catch (SQLException e) {
            throw new DbException("Erro ao listar turmas: " + e.getMessage());
        }
        return turmas;
    }
    
    public void removerAluno(int idTurma, String matricula) {
        Turma turma = buscarPorId(idTurma);
        if (turma == null) {
            throw new DbException("Turma não encontrada!");
        }

        List<String> alunos = turma.getAlunos();
        boolean removido = alunos.removeIf(aluno -> aluno.startsWith(matricula + ":"));
        
        if (!removido) {
            throw new DbException("Aluno não encontrado na turma!");
        }

        String sql = "UPDATE turma SET alunos = ? WHERE id_turma = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, String.join(",", alunos));
            stmt.setInt(2, idTurma);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException("Erro ao remover aluno: " + e.getMessage());
        }
    }
}