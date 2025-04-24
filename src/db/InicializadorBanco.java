package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorBanco {
	 public static void inicializar() {
		 try (Connection conn = DB.getConnection(); // try-with-resources fecha a conexão
	             Statement st = conn.createStatement()) {

	            // script de criação da tabela aluno
	            st.execute("create table if not exists aluno ("
	            		+ "id INT AUTO_INCREMENT PRIMARY KEY," // Não da pra usar Serial no mysql ;)
	            		+ "matricula INT UNIQUE NOT NULL,"
	            		+ "nome VARCHAR(255) NOT NULL,"
	            		+ "telefone VARCHAR(25),"
	            		+ "data_de_nascimento DATE NOT NULL,"
	            		+ "curso VARCHAR(100) NOT NULL,"
	            		+ "CPF VARCHAR(15) NOT NULL);");

	        } catch (SQLException e) {
	            throw new DbException("Erro ao inicializar banco: " + e.getMessage());
	        } 
	 }
}	 