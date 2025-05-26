package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Year;

import dao.AlunoDAO;
import dao.TurmaDAO;
import dao.UsuarioDAO;
import faculdade.Util.Cores;
import model.Aluno;
import model.Curso;
import model.EnumCurso;
import model.TipoUsuario;
import model.Turma;
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
			 st.execute("CREATE TABLE IF NOT EXISTS aluno ("
		                + "id INT AUTO_INCREMENT PRIMARY KEY,"
		                + "matricula INT UNIQUE NOT NULL,"
		                + "nome VARCHAR(255) NOT NULL,"
		                + "telefone VARCHAR(25),"
		                + "data_de_nascimento DATE NOT NULL,"
		                + "id_curso INT NOT NULL,"
		                + "CPF VARCHAR(15) NOT NULL,"
		                + "FOREIGN KEY (id_curso) REFERENCES curso(id)"
		                + ");");
			 
		 } catch (SQLException e) {
			 throw new DbException("Erro ao inicializar alunos ao banco: " + e.getMessage());
		 } 
	 }
	 
	 public static void inicializarCurso() {
		    try (Connection conn = DB.getConnection(); Statement st = conn.createStatement()) {

		        // Criação da tabela curso
		        st.execute("CREATE TABLE IF NOT EXISTS curso ("
		            + "id INT AUTO_INCREMENT PRIMARY KEY,"
		            + "nome VARCHAR(100) NOT NULL UNIQUE"
		            + ");");
 
		        st.execute("INSERT IGNORE INTO curso (nome) VALUES "
		                + "('Tecnológia em Analíse e Desenvolvimento de Sistemas'),"
		                + "('Sistemas de Informação'),"
		                + "('Ciência da Computação'),"
		                + "('Cinema'),"
		                + "('Engenharia de Software'),"
		                + "('Engenharia Civil'),"
		                + "('Engenharia Elétrica'),"
		                + "('Engenharia Mecânica'),"
		                + "('Direito'),"
		                + "('Administração'),"
		                + "('Ciências Contábeis'),"
		                + "('Economia'),"
		                + "('Gestão Comercial'),"
		                + "('Marketing'),"
		                + "('Recursos Humanos'),"
		                + "('Pedagogia'),"
		                + "('Enfermagem'),"
		                + "('Medicina'),"
		                + "('Psicologia'),"
		                + "('Educação Física'),"
		                + "('Letras'),"
		                + "('Design Gráfico'),"
		                + "('Design de Interiores'),"
		                + "('Arquitetura e Urbanismo'),"
		                + "('Biologia'),"
		                + "('Física'),"
		                + "('Química'),"
		                + "('Matemática');");

		    } catch (SQLException e) {
		        throw new DbException("Erro ao inicializar tabela curso: " + e.getMessage());
		    }
		}
	 
	 public static void inicializarTurma() {
		    try (Connection conn = DB.getConnection(); Statement st = conn.createStatement()) {

		        st.execute("CREATE TABLE IF NOT EXISTS turma ("
		            + "id_turma INT AUTO_INCREMENT PRIMARY KEY,"
		            + "nome_turma VARCHAR(100) NOT NULL,"
		            + "turno VARCHAR(20) NOT NULL,"
		            + "id_curso INT NOT NULL,"
		            + "alunos TEXT,"
		            + "FOREIGN KEY (id_curso) REFERENCES curso(id)"
		            + ");");

		    } catch (SQLException e) {
		        throw new DbException("Erro ao inicializar tabela turma: " + e.getMessage());
		    }
		}
	 
	 private static Turma criarTurmaPadrao(String nomeTurma, String turno, int idCurso, int matriculaAluno) {
		    TurmaDAO turmaDAO = new TurmaDAO();

		    // cria a turma
		    Turma turma = new Turma(0, nomeTurma, turno, idCurso, "");
		    turmaDAO.criarTurma(turma);

		    // recupera a turma que acabou de ser criada
		    Turma criada = turmaDAO.listarTurmas().stream()
		        .filter(t -> t.getNomeTurma().equals(nomeTurma)
		                  && t.getTurno().equals(turno)
		                  && t.getIdCurso() == idCurso)
		        .findFirst()
		        .orElseThrow(() -> new DbException("Turma " + nomeTurma + " não encontrada"));

		    // matricula o aluno
		    turmaDAO.adicionarAluno(criada.getIdTurma(), matriculaAluno);

		    return criada;
		}

	 
	 public static void iniciarAdminPadrao() {
		    String email = "matheuscunhaprado@gmail.com";
		    String senha = "Cunha123";

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

		    // Silvio
		    String cpfSilvio = "634.611.160-41";
		    if (!alunoDAO.existeCpf(cpfSilvio)) {
		        Aluno silvio = new Aluno(
		            alunoDAO.gerarNovaMatricula(),
		            "Silvio Vidal de Miranda Junior",
		            "62 997632875",
		            LocalDate.of(1999, 7, 30),
		            new Curso(EnumCurso.ENGENHARIA_DE_SOFTWARE.getId(),
		                      EnumCurso.ENGENHARIA_DE_SOFTWARE.getNomeAmigavel()),
		            cpfSilvio
		        );
		        alunoDAO.inserirDados(silvio);
		        criarUsuarioAluno(silvio.getMatricula(),
		                          "silviovidal@gmail.com",
		                          "MatheusLindo");

		        criarTurmaPadrao(
		            "sala01",
		            "NOITE",
		            EnumCurso.ENGENHARIA_DE_SOFTWARE.getId(),
		            silvio.getMatricula()
		        );
		        System.out.println(Cores.SUCESSO +
		            "Aluno Silvio Vidal cadastrado e matriculado em sala01." +
		            Cores.RESET);
		    }

		    // Stenio
		    String cpfStenio = "555.094.420-00";
		    if (!alunoDAO.existeCpf(cpfStenio)) {
		        Aluno stenio = new Aluno(
		            alunoDAO.gerarNovaMatricula(),
		            "Stenio Labor Hater da Silva",
		            "62 998734555",
		            LocalDate.of(1980, 8, 18),
		            new Curso(EnumCurso.RECURSOS_HUMANOS.getId(),
		                      EnumCurso.RECURSOS_HUMANOS.getNomeAmigavel()),
		            cpfStenio
		        );
		        alunoDAO.inserirDados(stenio);
		        criarUsuarioAluno(stenio.getMatricula(),
		                          "stenio@outlook.com",
		                          "OdeioTrabalhar123");

		        criarTurmaPadrao(
		            "sala02",
		            "TARDE",
		            EnumCurso.RECURSOS_HUMANOS.getId(),
		            stenio.getMatricula()
		        );
		        System.out.println(Cores.SUCESSO +
		            "Aluno Stenio Labor cadastrado e matriculado em sala02." +
		            Cores.RESET);
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