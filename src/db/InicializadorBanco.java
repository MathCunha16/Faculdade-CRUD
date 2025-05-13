package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Year;

import dao.AlunoDAO;
import dao.UsuarioDAO;
import faculdade.FaculdadeMain.Cores;
import model.Aluno;
import model.Curso;
import model.TipoUsuario;
import model.Usuario;

public class InicializadorBanco {
	 
	 public static void inicializarUsuario() {
		 try (Connection conn = DB.getConnection(); // try-with-resources fecha a conexão
	             Statement st = conn.createStatement()) {

	            // script de criação da tabela usuarios
			 st.execute("create table if not exists usuarios ("
			 		+ "id_usuario INT AUTO_INCREMENT PRIMARY KEY," // Não da pra usar Serial no mysql ;)
					+ "email VARCHAR(50) UNIQUE NOT NULL,"
			 		+ "senha VARCHAR(255) NOT NULL,"
					+ "tipo_usuario VARCHAR(25) CHECK (tipo_usuario IN ('aluno', 'adm')) NOT NULL,"
			 		+ "aluno_matricula INT," // pode ser null (por causa do ADM)
					+ "FOREIGN KEY (aluno_matricula) REFERENCES aluno(matricula) ON DELETE SET NULL);");

	        } catch (SQLException e) {
	            throw new DbException("Erro ao inicializar usuarios ao banco: " + e.getMessage());
	        }
	 }
	 
	 public static void inicializarAluno() {
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
			 throw new DbException("Erro ao inicializar alunos ao banco: " + e.getMessage());
		 } 
	 }
	 
	 public static void iniciarAdminPadrao() {
		    String email = "matheuscunhaprado@gmail.com";
		    String senha = "MarinaLinda"; // <3

		    try (Connection conn = DB.getConnection();
		         Statement st = conn.createStatement()) {

		        // Verifica se o ADM já existe
		        var rs = st.executeQuery("SELECT COUNT(*) FROM usuarios WHERE email = '" + email + "'");
		        rs.next();
		        if (rs.getInt(1) == 0) {
		            // Insere o ADM padrão
		            st.executeUpdate("INSERT INTO usuarios (email, senha, tipo_usuario, aluno_matricula) "
		                    + "VALUES ('" + email + "', '" + senha + "', 'adm', NULL)");
		            System.out.println(Cores.SUCESSO + "Administrador padrão criado com sucesso." + Cores.RESET);
		        } 
		        
		    } catch (SQLException e) {
		        throw new DbException("Erro ao iniciar administrador padrão: " + e.getMessage());
		    }
		}
	 
	 public static void inicializarControleMatricula() {
		    try (Connection conn = DB.getConnection();
		         Statement st = conn.createStatement()) {

		        st.execute("CREATE TABLE IF NOT EXISTS controle_matricula (ultima_matricula INT NOT NULL)");

		        var rs = st.executeQuery("SELECT COUNT(*) FROM controle_matricula");
		        rs.next();
		        if (rs.getInt(1) == 0) {
		            int anoAtual = Year.now().getValue();
		            int matriculaInicial = anoAtual * 1000000;
		            st.executeUpdate("INSERT INTO controle_matricula VALUES (" + matriculaInicial + ")");
		        }

		    } catch (SQLException e) {
		        throw new DbException("Erro ao inicializar controle de matrícula: " + e.getMessage());
		    }
		}
	 
	 public static void iniciarAlunosPadrao() {
		    AlunoDAO alunoDAO = new AlunoDAO();

		    String cpfSilvio = "634.611.160-41";
		    if (!alunoDAO.existeCpf(cpfSilvio)) {
		        Aluno silvio = new Aluno(
		            alunoDAO.gerarNovaMatricula(), 
		            "Silvio Vidal de Miranda Junior",
		            "62 997632875",
		            LocalDate.of(1999, 7, 30),
		            Curso.ENGENHARIA_DE_SOFTWARE,
		            cpfSilvio
		        );
		        
		        alunoDAO.inserirDados(silvio);
		        criarUsuarioAluno(silvio.getMatricula(), "silviovidal@gmail.com", "MatheusLindo");
		        System.out.println(Cores.SUCESSO + "Aluno Silvio Vidal cadastrado com sucesso! Matrícula: " + silvio.getMatricula() + Cores.RESET); // <-- AQUI
		    }

		    String cpfStenio = "555.094.420-00";
		    if (!alunoDAO.existeCpf(cpfStenio)) {
		        Aluno stenio = new Aluno(
		            alunoDAO.gerarNovaMatricula(), 
		            "Stenio Labor Hater da Silva",
		            "62 998734555",
		            LocalDate.of(1980, 8, 18),
		            Curso.RECURSOS_HUMANOS,
		            cpfStenio
		        );
		        
		        alunoDAO.inserirDados(stenio);
		        criarUsuarioAluno(stenio.getMatricula(), "stenio@outlook.com", "OdeioTrabalhar123");
		        System.out.println(Cores.SUCESSO + "Aluno Stenio Labor cadastrado com sucesso! Matrícula: " + stenio.getMatricula() + Cores.RESET); // <-- AQUI
		    }
		}

		private static void criarUsuarioAluno(int matricula, String email, String senha) {
		    try {
		        Usuario usuario = new Usuario();
		        usuario.setEmail(email);
		        usuario.setSenha(senha);
		        usuario.setTipoUsuario(TipoUsuario.ALUNO);
		        usuario.setMatricula(matricula);
		        
		        UsuarioDAO usuarioDAO = new UsuarioDAO();
		        usuarioDAO.inserir(usuario);
		        
		    } catch (DbException e) {
		        System.out.println(Cores.ERRO + "Erro ao criar usuário para matrícula " + matricula + ": " + e.getMessage() + Cores.RESET);
		    }
		}
	 
}	 