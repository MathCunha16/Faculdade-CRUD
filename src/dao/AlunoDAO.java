package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.Aluno;
import model.Curso;

public class AlunoDAO {

	public void inserirDados(Aluno aluno) { // INSERT
		String sql = "INSERT INTO aluno (matricula, nome, telefone, data_de_nascimento, curso, cpf)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, aluno.getMatricula());
			stmt.setString(2, aluno.getNome());
			stmt.setString(3, aluno.getTelefone());
			stmt.setDate(4, java.sql.Date.valueOf(aluno.getDataDeNascimento()));
			stmt.setString(5, aluno.getCurso().name());
			stmt.setString(6, aluno.getCpf());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("Erro ao salvar histórico: " + e.getMessage());
		}
	}

	public List<Aluno> listarAlunos() { // SELECT
		String sql = "SELECT * FROM faculdade.aluno";
		List<Aluno> alunos = new ArrayList<>();

		try (Connection conn = DB.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				alunos.add(new Aluno(rs.getInt("id"), rs.getInt("matricula"), rs.getString("nome"),
						rs.getString("telefone"), rs.getDate("data_de_nascimento").toLocalDate(),
						Curso.fromString(rs.getString("curso")), rs.getString("CPF")));
			}
		} catch (SQLException e) {
			throw new DbException("Erro ao consultar histórico: " + e.getMessage());
		}
		return alunos;
	}

	public Aluno buscarPorMatricula(int matricula) {
		String sql = "SELECT * FROM aluno WHERE matricula = ?";
		Aluno aluno = null;

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, matricula);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					aluno = new Aluno(rs.getInt("id"), rs.getInt("matricula"), rs.getString("nome"),
							rs.getString("telefone"), rs.getDate("data_de_nascimento").toLocalDate(),
							Curso.fromString(rs.getString("curso")), rs.getString("CPF"));
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar aluno por matrícula: " + e.getMessage());
		}

		return aluno;
	}

	public List<Aluno> buscarPorNome(String nome) {
		String sql = "SELECT * FROM aluno WHERE nome LIKE ?";
		List<Aluno> alunos = new ArrayList<>();

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, "%" + nome + "%"); // busca parcial com wildcard %

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Aluno aluno = new Aluno(rs.getInt("id"), rs.getInt("matricula"), rs.getString("nome"),
							rs.getString("telefone"), rs.getDate("data_de_nascimento").toLocalDate(),
							Curso.fromString(rs.getString("curso")), rs.getString("CPF"));
					alunos.add(aluno);
				}
			}

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar aluno por nome: " + e.getMessage());
		}

		return alunos; // Retorna a lista (vazia se não encontrar)
	}

	public Aluno removerAluno(int matricula) {
		String sql = "DELETE FROM aluno WHERE matricula = ?";
		Aluno alunoRemovido = null;

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			alunoRemovido = buscarPorMatricula(matricula);

			if (alunoRemovido == null) {
				return null;
			}

			stmt.setInt(1, matricula);
			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas == 0) {
				return null;
			}

			return alunoRemovido;

		} catch (SQLException e) {
			throw new DbException("Erro ao remover aluno: " + e.getMessage());
		}
	}
	
	 public boolean atualizarNome(int matricula, String novoNome) {
	        String sql = "UPDATE aluno SET nome = ? WHERE matricula = ?";
	        try (Connection conn = DB.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, novoNome);
	            ps.setInt(2, matricula);
	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            throw new DbException("Erro ao atualizar nome: " + e.getMessage());
	        }
	    }

	    public boolean atualizarTelefone(int matricula, String novoTelefone) {
	        String sql = "UPDATE aluno SET telefone = ? WHERE matricula = ?";
	        try (Connection conn = DB.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, novoTelefone);
	            ps.setInt(2, matricula);
	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            throw new DbException("Erro ao atualizar telefone: " + e.getMessage());
	        }
	    }

	    public boolean atualizarDataNascimento(int matricula, LocalDate novaData) {
	        String sql = "UPDATE aluno SET data_de_nascimento = ? WHERE matricula = ?";
	        try (Connection conn = DB.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setDate(1, java.sql.Date.valueOf(novaData));
	            ps.setInt(2, matricula);
	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            throw new DbException("Erro ao atualizar data de nascimento: " + e.getMessage());
	        }
	    }

	    public boolean atualizarCurso(int matricula, Curso novoCurso) {
	        String sql = "UPDATE aluno SET curso = ? WHERE matricula = ?";
	        try (Connection conn = DB.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, novoCurso.name());
	            ps.setInt(2, matricula);
	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            throw new DbException("Erro ao atualizar curso: " + e.getMessage());
	        }
	    }

	    public boolean atualizarCpf(int matricula, String novoCpf) {
	        String sql = "UPDATE aluno SET CPF = ? WHERE matricula = ?";
	        try (Connection conn = DB.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, novoCpf);
	            ps.setInt(2, matricula);
	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	            throw new DbException("Erro ao atualizar CPF: " + e.getMessage());
	        }
	    }

	public int gerarNovaMatricula() {
		try (Connection conn = DB.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT MAX(matricula) FROM aluno");
				ResultSet rs = ps.executeQuery()) {

			// consulta SQL para pegar a maior matricula cadastrada
			int anoAtual = java.time.Year.now().getValue();

			// começa o sequencial em 1 por padrão (caso ñ tenha nenhuma matricula ainda)
			int sequencial = 1;

			if (rs.next()) {
				int ultimaMatricula = rs.getInt(1);

				// extrai os 4 primeiros digitos da matricula (ex: 2025)
				int anoMatricula = ultimaMatricula / 1000000;

				if (anoMatricula == anoAtual) {
					// se a matricula for do mesmo ano atual
					sequencial = (ultimaMatricula % 1000000) + 1;
				}
			}

			// retorna a nova matricula no formato: ano + sequencial com 4 dígitos
			return Integer.parseInt(anoAtual + String.format("%06d", sequencial));

		} catch (SQLException e) {
			throw new DbException("Erro ao gerar matrícula: " + e.getMessage());
		}
	}
}