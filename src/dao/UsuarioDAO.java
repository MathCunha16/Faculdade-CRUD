package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import db.DB;
import db.DbException;
import model.TipoUsuario;
import model.Usuario;

public class UsuarioDAO {

	public Usuario autenticar(String email, String senha) { // login dos usuarios
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        
        try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id_usuario"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario").toUpperCase()));
                    u.setMatricula(rs.getInt("aluno_matricula")); // pode ser null (por causa do ADM)
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao autenticar usuário: " + e.getMessage());
        }
        
        return null; // vou retornar null pra tratar na main
    }
	
	public void inserir(Usuario usuario) { // cadastro de usuarios
	    String sql = "INSERT INTO usuarios (email, senha, tipo_usuario, aluno_matricula) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, usuario.getEmail());
	        ps.setString(2, usuario.getSenha());
	        ps.setString(3, usuario.getTipoUsuario().toString().toLowerCase());

	        if (usuario.getMatricula() != null) {
	            ps.setInt(4, usuario.getMatricula());
	        } else {
	            ps.setNull(4, Types.INTEGER);
	        }

	        int linhasAfetadas = ps.executeUpdate();

	        if (linhasAfetadas > 0) {
	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) {
	                    usuario.setId(rs.getInt(1));
	                }
	            }
	        } else {
	            throw new DbException("Erro ao inserir: nenhuma linha afetada");
	        }

	    } catch (SQLException e) {
	        throw new DbException("Erro ao inserir usuário: " + e.getMessage());
	    }
	}
	
	public Usuario buscarPorMatricula(int matricula) {
	    String sql = "SELECT * FROM usuarios WHERE aluno_matricula = ?";

	    try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, matricula);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Usuario u = new Usuario();
	                u.setId(rs.getInt("id_usuario"));
	                u.setEmail(rs.getString("email"));
	                u.setSenha(rs.getString("senha"));
	                u.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario").toUpperCase()));
	                u.setMatricula(rs.getInt("aluno_matricula"));
	                return u;
	            }
	        }
	    } catch (SQLException e) {
	        throw new DbException("Erro ao buscar usuário pela matrícula: " + e.getMessage());
	    }

	    return null;
	}
	
	public boolean atualizarEmail(int idUsuario, String novoEmail) {
	    String sql = "UPDATE usuarios SET email = ? WHERE id_usuario = ?";
	    try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, novoEmail);
	        ps.setInt(2, idUsuario);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new DbException("Erro ao atualizar e-mail: " + e.getMessage());
	    }
	}

	public boolean atualizarSenha(int idUsuario, String novaSenha) {
	    String sql = "UPDATE usuarios SET senha = ? WHERE id_usuario = ?";
	    try (Connection conn = DB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, novaSenha);
	        ps.setInt(2, idUsuario);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        throw new DbException("Erro ao atualizar senha: " + e.getMessage());
	    }
	}
	
}
