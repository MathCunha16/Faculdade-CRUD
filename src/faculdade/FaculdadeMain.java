package faculdade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.UsuarioDAO;
import db.InicializadorBanco;
import model.Aluno;
import model.Curso;
import model.TipoUsuario;
import model.Usuario;
import model.Validador;

public class FaculdadeMain {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		AlunoDAO alunoDAO = new AlunoDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		InicializadorBanco.inicializarAluno(); // Criando tabela aluno Se não existir
		InicializadorBanco.inicializarControleMatricula();
		InicializadorBanco.inicializarUsuario(); // Criando tabela usuario SE não existir
		InicializadorBanco.iniciarAdminPadrao(); // Criando ADM padrão do sistema
		InicializadorBanco.iniciarAlunosPadrao(); // Criando 2 alunos padrões do sistema

		Usuario user = null; // settando user como null por padrão

		System.out.println(Cores.VERDE + "=".repeat(15) + "Faculdade UniEsquina" + "=".repeat(15) + Cores.RESET );
		System.out.println("-" + Cores.CIANO + " LOGAR: " + Cores.RESET +  "faça login como aluno ou ADM ");
		System.out.println("-" + Cores.CIANO + " REGISTRAR: " + Cores.RESET +  "insira sua matricula para se cadastrar ");
		System.out.println("-" + Cores.VERMELHO + " SAIR: " + Cores.RESET + "encerrar o programa");

		loopLogin: // label de controle para o painel de login
		while (true) {
			System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
			System.out.println("Digite o comando para sua respectiva ação:");
			System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
			String entrada = scanner.nextLine().trim().toUpperCase();

			switch (entrada) { // encerra o programa
			case "SAIR":
				System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
				System.out.println(Cores.VERMELHO + "Encerrando programa..." + Cores.RESET);
				scanner.close();
				System.exit(0); // Encerra o programa imediatamente

			case "LOGAR":
				user = null; // Settando como null pra ficar em loop enquanto não logar
				while (user == null) {
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
					System.out.println("Digite seu email: ");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					String email = scanner.nextLine();
					System.out.println("Digite sua senha: ");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					String senha = scanner.nextLine();

					user = usuarioDAO.autenticar(email, senha);

					if (user == null) { // Se continuar null é pq não deu certo o login
						System.out.println(Cores.ERRO +"Email ou senha incorretos" + Cores.RESET);
						System.out.print(Cores.AMARELO + "--> Deseja tentar novamente? (S/N): " + Cores.RESET);
						String escolha = scanner.nextLine().toUpperCase();
						if (escolha.equals("N")) {
							continue loopLogin;

						}
					}
				}

				break loopLogin;

			case "REGISTRAR":
				String entradaMatricula;
				while (true) {
					System.out.println("Digite sua matrícula: ");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					entradaMatricula = scanner.nextLine();

					if (!entradaMatricula.matches("\\d+")) { // verifica se tem so tem numero
						System.out.println(Cores.ERRO + "Matrícula inválida. Digite apenas números." + Cores.RESET);
						System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
						continue;
					}

					break;
				}

				int matricula = Integer.parseInt(entradaMatricula);
				Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
				if (aluno == null) {
					System.out.println(Cores.ERRO +"Essa matrícula não pode ser encontrada!!!" + Cores.RESET);
					System.out.println(Cores.ERRO + "Retornando ao menu..." + Cores.RESET);
					continue loopLogin;
				}

				Usuario usuarioExistente = usuarioDAO.buscarPorMatricula(matricula);
				if (usuarioExistente != null) {
					System.out.println(Cores.ERRO + "Já existe um usuário registrado com essa matrícula!!!" + Cores.RESET);
					System.out.println(Cores.ERRO + "Retornando ao menu..." + Cores.RESET);
					continue loopLogin;
				}

				System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
				System.out.println(Cores.AMARELO + "Seja bem-vindo " + Cores.CIANO + aluno.getNome() + Cores.AMARELO + "!" + Cores.RESET);
				System.out.println("Digite seu email");
				System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				String email = scanner.nextLine();

				while (!Validador.emailValido(email)) {
					System.out.println(Cores.ERRO +"E-mail inválido, insira um email valido, ex silvio@gmail.com" + Cores.RESET);
					System.out.println("Tente novamente");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					email = scanner.nextLine();
				}

				System.out.println("Digite sua senha: ");
				System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				String senha = scanner.nextLine();

				while (senha.length() <= 5) {
					System.out.println(Cores.ERRO +"Sua senha deve conter mais de 5 caracteres, insira uma valida: " + Cores.RESET);
					System.out.println("Tente novamente");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					senha = scanner.nextLine();
				}

				Usuario novoUsuario = new Usuario();
				novoUsuario.setEmail(email);
				novoUsuario.setSenha(senha);
				novoUsuario.setTipoUsuario(TipoUsuario.ALUNO);
				novoUsuario.setMatricula(matricula);

				usuarioDAO.inserir(novoUsuario);
				System.out.println(Cores.SUCESSO + "Usuário registrado com sucesso!" + Cores.RESET);
				System.out.println(Cores.SUCESSO + "Retornando ao menu..." + Cores.RESET);
				continue loopLogin;

			default:
				System.out.println(Cores.ERRO + "Comando desconhecido, tente novamente" + Cores.RESET);
				continue loopLogin;
			}

		}

		if (user.getTipoUsuario() == TipoUsuario.ADM) {
			// <<<<<------------------ MENU PARA ADMs !!!!!!!

			System.out.println(Cores.VERDE + "=".repeat(15) + "Faculdade UniEsquina" + "=".repeat(15) + Cores.RESET );

			System.out.println(Cores.AMARELO +"Você foi logado como " + Cores.VERMELHO + "ADM"
			+ Cores.AMARELO + ", seja bem vindo " + Cores.VERMELHO + user.getEmail() + Cores.RESET);

			System.out.println(Cores.AMARELO + "Comandos disponíveis:" + Cores.RESET);
			System.out.println("-" + Cores.AZUL + " CADASTRAR 📋: " + Cores.RESET + "adicionar um novo aluno");
			System.out.println("-" + Cores.CIANO + " EDITAR ✏️: " + Cores.RESET + "editar informações de um aluno");
			System.out.println("-" + Cores.ROXO + " LISTAR 📄: " + Cores.RESET + "listar todos os alunos");
			System.out.println("-" + Cores.VERDE + " BUSCAR MATRICULA 🔍: " + Cores.RESET +  "busque um aluno pelo número de matricula");
			System.out.println("-" + Cores.VERDE + " BUSCAR NOME 🔎: " + Cores.RESET + "busque alunos pelo nome");
			System.out.println("-" + Cores.LARANJA + " DELETAR 🗑️: " + Cores.RESET + "deletar um aluno do sistema por meio da matricula");
			System.out.println("-" + Cores.VERMELHO + " SAIR ⛔: " + Cores.RESET + "encerrar programa");

			loopPrincipal: // label principal de controle pro loop
			while (true) {
				System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
				System.out.println("Digite o comando que deseja realizar:");
				System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				String entrada = scanner.nextLine().trim().toUpperCase();

				switch (entrada) { // encerra o programa
				case "SAIR":
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
					System.out.println(Cores.VERMELHO + "Encerrando programa..." + Cores.RESET);
					break loopPrincipal;

				case "CADASTRAR": // Adiciona novo aluno ao DB
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);

					System.out.println("Digite o nome do aluno:");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					String nome = scanner.nextLine();

					while (Validador.validarNome(nome) == false) { // Adição pra validar nome
						System.out.println(Cores.ERRO + "Nome inválido. Digite nome e sobrenome, sem números: " + Cores.RESET);
						System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
						nome = scanner.nextLine();
					}

					System.out.println("Digite o telefone do aluno: ");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					String telefone = scanner.nextLine();

					while (Validador.validarTelefone(telefone) == false) { // Adição pra validar numero
						System.out.println(Cores.ERRO + "Telefone inválido. Informe no formato (DDD) 9XXXX-XXXX: " + Cores.RESET);
						System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
						telefone = scanner.nextLine();
					}

					LocalDate dataDeNascimento = null;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					while (dataDeNascimento == null) {
						try {
							System.out.println("Digite a data de nascimento do aluno (DD/MM/yyyy): ");
							System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
							String dataInput = scanner.nextLine();
							dataDeNascimento = LocalDate.parse(dataInput, formatter);
						} catch (DateTimeParseException e) {
							System.out.println(Cores.ERRO + "Formato inválido! Use DD/MM/AAAA (ex: 07/11/2004)" + Cores.RESET);
						}
					}

					Curso cursoSelecionado = escolherCurso(scanner); // Listagem de cursos e escolha

					System.out.println("Digite o CPF do aluno: ");
				    System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				    String cpf = scanner.nextLine();

				    while (true) { 
				        if (!Validador.validarCPF(cpf)) { 
				            System.out.println(Cores.ERRO + "CPF inválido" + Cores.RESET);
				            System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
				            cpf = scanner.nextLine();
				            continue;
				        }

				        // formatando e verificando duplicata
				        String cpfFormatado = Validador.formatarCPF(cpf); 
				        if (alunoDAO.existeCpf(cpfFormatado)) {
				            System.out.println(Cores.ERRO + "CPF já cadastrado no sistema!" + Cores.RESET);
				            System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
				            cpf = scanner.nextLine();
				        } else {
				            cpf = cpfFormatado; // Atribui o CPF formatado
				            break; // Sai do loop
				        }
				    }

					Integer matricula = alunoDAO.gerarNovaMatricula();
					System.out.println(Cores.SUCESSO + "Aluno cadastrado com Sucesso!!!" + Cores.RESET);
					System.out.println(Cores.SUCESSO + "A matricula gerada foi: " + matricula + Cores.RESET);

					Aluno aluno = new Aluno(matricula, nome, telefone, dataDeNascimento, cursoSelecionado, cpf);
					alunoDAO.inserirDados(aluno);

					continue loopPrincipal;

				case "BUSCAR MATRICULA":
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
					System.out.println("Digite a matricula do aluno: ");

					int matriculaBusca;
					while (true) {
					    System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					    try {
					        matriculaBusca = Integer.parseInt(scanner.nextLine());
					        break;
					    } catch (NumberFormatException e) {
					        System.out.println(Cores.ERRO + "Digite apenas números." + Cores.RESET);
					    }
					}
					
					Aluno alunoEncontrado = alunoDAO.buscarPorMatricula(matriculaBusca);
					if (alunoEncontrado != null) {
						System.out.println(Cores.SUCESSO + "Aluno encontrado:" + Cores.RESET);
						System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
						System.out.println(Cores.AZUL + "Nome: "+ Cores.RESET + alunoEncontrado.getNome());
						System.out.println(Cores.AZUL + "Telefone: "+ Cores.RESET + alunoEncontrado.getTelefone());
						System.out.println(Cores.AZUL + "Data de Nascimento: " + Cores.RESET + alunoEncontrado.getDataDeNascimento());
						System.out.println(Cores.AZUL + "Curso: " + Cores.RESET + alunoEncontrado.getCurso());
						System.out.println(Cores.AZUL + "CPF: "+ Cores.RESET + alunoEncontrado.getCpf());
					} else {
						System.out.println(Cores.ERRO + "Nenhum aluno encontrado com a matricula " + matriculaBusca + Cores.RESET);
						System.out.println(Cores.ERRO + "Retornando ao menu principal...." + Cores.RESET);
					}
					continue loopPrincipal;

				case "BUSCAR NOME":
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
					System.out.println("Digite o nome do aluno: ");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					String nomeBusca = scanner.nextLine();
					List<Aluno> alunosEncontrados = alunoDAO.buscarPorNome(nomeBusca);
					
					if (!alunosEncontrados.isEmpty()) {
					    exibirAlunos(alunosEncontrados); // mostra lista de alunos
					} else {
					    System.out.println(Cores.ERRO + "Nenhum aluno encontrado com esse nome...Voltando ao menu" + Cores.RESET);
					}
					
					continue loopPrincipal;

				case "EDITAR":
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);

					// 1) Loop pra verificar matricula
					int matEdit = -1;
					while (matEdit < 0) {
						System.out.println("Digite a matrícula do aluno que deseja editar: ");
						System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
						String line = scanner.nextLine().trim();
						try {
							matEdit = Integer.parseInt(line);
						} catch (NumberFormatException e) {
							System.out.println(Cores.ERRO + "Matrícula inválida! Digite apenas números." + Cores.RESET);
						}
					}

					// 2) Buscar o aluno
					Aluno aEditar = alunoDAO.buscarPorMatricula(matEdit);
					if (aEditar == null) {
						System.out.println(Cores.ERRO + "Aluno não encontrado! Voltando ao menu principal..." + Cores.RESET);
						continue loopPrincipal;
					} else {
						System.out.println(Cores.SUCESSO + "Aluno encontrado com sucesso!" + Cores.RESET);
						System.out.println(Cores.LARANJA + Cores.TEXTO_NEGRITO +"ATENÇÂO --> " + Cores.RESET +
											Cores.LARANJA + "a partir de agora você estará editando as informações de "
											+ Cores.VERDE+ aEditar.getNome() + Cores.RESET);
					}

					// 3) Sub-menu
					System.out.println("O que deseja editar?");
					System.out.println(Cores.AMARELO + "[1]" + Cores.CIANO + "👤 Nome" + Cores.RESET);
					System.out.println(Cores.AMARELO + "[2]" + Cores.CIANO +  "📞 Telefone" + Cores.RESET);
					System.out.println(Cores.AMARELO + "[3]" + Cores.CIANO+  "📅 Data de Nascimento" + Cores.RESET);
					System.out.println(Cores.AMARELO + "[4]" + Cores.CIANO +  "🎓 Curso" + Cores.RESET);
					System.out.println(Cores.AMARELO + "[5]" + Cores.CIANO +  "🆔 CPF" + Cores.RESET);
					System.out.print(Cores.AMARELO + "Escolha o número correspondente a opção --> " + Cores.RESET);

					int opc = -1;
					try {
						opc = Integer.parseInt(scanner.nextLine().trim());
					} catch (NumberFormatException e) {
						System.out.println(Cores.ERRO + "Opção inválida! Voltando ao menu principal..." + Cores.RESET);
						continue loopPrincipal;
					}

					boolean sucesso = false;
					switch (opc) {
					case 1:
						System.out.println("Insira o novo nome: ");
						System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
						String novoNome = scanner.nextLine();

						while (Validador.validarNome(novoNome) == false) { // Adição pra validar nome
							System.out.println(Cores.ERRO + "Nome inválido. Digite nome e sobrenome, sem números: " + Cores.RESET);
							System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
							novoNome = scanner.nextLine();
						}

						sucesso = alunoDAO.atualizarNome(matEdit, novoNome);
						break;

					case 2:
						System.out.println("Insira o novo telefone: ");
						System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
						String novoTel = scanner.nextLine();

						while (Validador.validarTelefone(novoTel) == false) { // Adição pra validar numero
							System.out.println(Cores.ERRO + "Telefone inválido. Informe no formato (DDD) 9XXXX-XXXX: "+ Cores.RESET);
							System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
							novoTel = scanner.nextLine();
						}

						sucesso = alunoDAO.atualizarTelefone(matEdit, novoTel);
						break;

					case 3:
						LocalDate novaData = null;
						DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						while (novaData == null) {
							try {
								System.out.println("Digite a nova data de nascimento (DD/MM/yyyy): ");
								System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
								novaData = LocalDate.parse(scanner.nextLine(), fmt);
							} catch (DateTimeParseException e) {
								System.out.println(Cores.ERRO + "Formato inválido! Use DD/MM/AAAA." + Cores.RESET);
							}
						}
						sucesso = alunoDAO.atualizarDataNascimento(matEdit, novaData);
						break;

					case 4:
						Curso novoCurso = escolherCurso(scanner);
						sucesso = alunoDAO.atualizarCurso(matEdit, novoCurso);
						break;

					case 5:
					    System.out.println("Insira o novo CPF: ");
					    System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					    String novoCpf = scanner.nextLine();
					    
					    while (true) { 
					        novoCpf = Validador.formatarCPF(novoCpf); // formata antes de validar
					        
					        if (!Validador.validarCPF(novoCpf)) { 
					            System.out.println(Cores.ERRO + "CPF inválido!" + Cores.RESET);
					            System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
					            novoCpf = scanner.nextLine();
					            continue;
					        }
					        
					        // verifica se ja existe em OUTRO aluno (ignora ele msm)
					        if (alunoDAO.existeCpfParaOutroAluno(novoCpf, matEdit)) { 
					            System.out.println(Cores.ERRO + "Este CPF já pertence a outro aluno!" + Cores.RESET);
					            System.out.print(Cores.AMARELO + "Tente novamente --> " + Cores.RESET);
					            novoCpf = scanner.nextLine();
					            continue;
					        }
					        
					        break;
					    }
					    
					    sucesso = alunoDAO.atualizarCpf(matEdit, novoCpf);
					    break;

					default:
						System.out.println(Cores.ERRO + "Opção inválida! Voltando ao menu principal..." + Cores.RESET);
						continue;
					}

					if (sucesso) {
						System.out.println(Cores.SUCESSO + "Atualização realizada com sucesso!" + Cores.RESET);
					} else {
						System.out.println(Cores.ERRO + "Falha ao atualizar informações do aluno!" + Cores.RESET);
					}
					continue loopPrincipal;

				case "LISTAR":
					List<Aluno> alunos = alunoDAO.listarAlunos();
					exibirAlunos(alunos);
					continue loopPrincipal;

				case "DELETAR":
					System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
					System.out.println("Digite a matrícula do aluno a ser removido:");
					System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
					try {
						int matriculaParaRemover = Integer.parseInt(scanner.nextLine());

						Aluno alunoRemovido = alunoDAO.removerAluno(matriculaParaRemover);

						if (alunoRemovido != null) {
							System.out.println(Cores.SUCESSO + "\nAluno removido com sucesso:" + Cores.RESET);
							System.out.println(Cores.SUCESSO + "Nome: " + alunoRemovido.getNome() + Cores.RESET );
							System.out.println(Cores.SUCESSO + "Matrícula: " + alunoRemovido.getMatricula() + Cores.RESET);
						} else {
							System.out.println(Cores.ERRO + "Aluno não encontrado ou não pôde ser removido!"
									+ " Voltando para o menu principal..." + Cores.RESET);
						}

					} catch (NumberFormatException e) {
						System.err.println(
								"Matrícula inválida! Digite apenas números. Voltando para o menu principal...");
					}
					continue loopPrincipal;

				default:
					System.out.println(Cores.ERRO + "Comando desconhecido, tente novamente" + Cores.RESET);
					continue loopPrincipal;
				}
			}

			scanner.close();

		}else { // <<<<<<<<<<<<<<<<----------------------------------- MENU PARA ALUNOS
			// !!!!!!!!!!

	Aluno alunoLogado = alunoDAO.buscarPorMatricula(user.getMatricula());

	System.out.println(Cores.VERDE + "=".repeat(15) + "Faculdade UniEsquina" + "=".repeat(15) + Cores.RESET);

	System.out.println(Cores.AMARELO + "Você foi logado como " + Cores.CIANO + "ALUNO"
	        + Cores.AMARELO + ", seja bem vindo " + Cores.CIANO + alunoLogado.getNome() + Cores.RESET);

	System.out.println(Cores.AMARELO + "Comandos disponíveis:" + Cores.RESET);
	System.out.println("-" + Cores.AZUL + " CONSULTAR 🔍: " + Cores.RESET + "veja suas informações de aluno");
	System.out.println("-" + Cores.ROXO + " EDITAR ✏️: " + Cores.RESET + "alterar suas credenciais de login");
	System.out.println("-" + Cores.VERMELHO + " SAIR ⛔: " + Cores.RESET + "encerrar programa");

	loopMenuAlunos: // Label menu aluno
	while (true) {
		System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
		System.out.println("Digite o comando que deseja realizar:");
		System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
		String entrada = scanner.nextLine().trim().toUpperCase();

		switch (entrada) {
		case "SAIR":
			System.out.println(Cores.VERDE + "=".repeat(50) + Cores.RESET);
			System.out.println(Cores.VERMELHO + "Encerrando programa..." + Cores.RESET);
			break loopMenuAlunos; 

		case "CONSULTAR":
			if (alunoLogado != null) {
				System.out.println(Cores.VERDE + "=".repeat(20) + Cores.VERMELHO + " SEUS DADOS " + Cores.VERDE + "=".repeat(20) + Cores.RESET);
				System.out.println(Cores.CIANO + "Nome: " + Cores.RESET + alunoLogado.getNome());
				System.out.println(Cores.VERDE + "Telefone: " + Cores.RESET + alunoLogado.getTelefone());
				System.out.println(Cores.AZUL + "Data de Nascimento: " + Cores.RESET + alunoLogado.getDataDeNascimento());
				System.out.println(Cores.ROXO + "Curso: " + Cores.RESET + alunoLogado.getCurso());
				System.out.println(Cores.LARANJA + "CPF: " + Cores.RESET + alunoLogado.getCpf());
				System.out.println(Cores.AZUL + "E-mail: " + Cores.RESET + user.getEmail());
				System.out.println(Cores.AMARELO + "Senha de login: " + Cores.RESET + user.getSenha());
			} else {
				System.out.println(Cores.ERRO + "Não foi possível realizar esta ação. Contate o administrador." + Cores.RESET);
			}
			continue loopMenuAlunos;
			
		case "EDITAR":
			if (alunoLogado != null) {
				System.out.println(Cores.VERDE + "=".repeat(20) + Cores.LARANJA + " EDITAR DADOS " + Cores.VERDE + "=".repeat(20) + Cores.RESET);
				System.out.println(Cores.AMARELO + "O que deseja editar?" + Cores.RESET);
				System.out.println("- " + Cores.CIANO + "EMAIL 📧" + Cores.RESET + ": alterar seu e-mail de login");
				System.out.println("- " + Cores.CIANO + "SENHA 🔒" + Cores.RESET + ": alterar sua senha de acesso");
				System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				String opcao = scanner.nextLine().trim().toUpperCase();

				switch (opcao) {
				case "EMAIL":
				    while (true) {
				        System.out.println("Digite o novo e-mail: ");
				        System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				        String novoEmail = scanner.nextLine().trim();

				        if (!Validador.emailValido(novoEmail)) {
				            System.out.println(Cores.ERRO + "E-mail inválido, insira um e-mail válido (ex: silvio@gmail.com)" + Cores.RESET);
				            continue;
				        }

				        System.out.println("Confirme o e-mail:");
				        System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				        String confirmacao = scanner.nextLine().trim();

				        if (!confirmacao.equals(novoEmail)) {
				            System.out.println(Cores.ERRO + "Os e-mails não coincidem! Deseja tentar novamente? (S/N)" + Cores.RESET);
				            System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				            String continuar = scanner.nextLine().trim().toUpperCase();

				            if (continuar.equals("N")) {
				            	System.out.println("Retornando ao menu...");
				                continue loopMenuAlunos;
				            } else if (!continuar.equals("N") && !continuar.equals("S")) {
				            	System.out.println(Cores.ERRO + "Comando desconhecido, retornando ao menu principal..." + Cores.RESET);
				            	continue loopMenuAlunos;
				            }
				            
				            else {
				                continue; // volta pro início do while
				            }
				        }

				        // se chegou aqui é pq ta tudo certo!
				        boolean sucesso = usuarioDAO.atualizarEmail(user.getId(), novoEmail);
				        if (sucesso) {
				            System.out.println(Cores.SUCESSO + "E-mail atualizado com sucesso!" + Cores.RESET);
				        } else {
				            System.out.println(Cores.ERRO + "Erro ao atualizar e-mail!" + Cores.RESET);
				        }

				        break; // sai do while
				    }
				    break; 
				    
				case "SENHA":
				    while (true) {
				        System.out.println("Digite a nova senha (mínimo 6 caracteres):");
				        System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				        String novaSenha = scanner.nextLine().trim();

				        if (novaSenha.length() < 6) {
				            System.out.println(Cores.ERRO + "Senha muito curta! A senha deve ter pelo menos 6 caracteres." + Cores.RESET);
				            continue;
				        }

				        System.out.println("Confirme a nova senha:");
				        System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				        String confirmacaoSenha = scanner.nextLine().trim();

				        if (!confirmacaoSenha.equals(novaSenha)) {
				            System.out.println(Cores.ERRO + "As senhas não coincidem! Deseja tentar novamente? (S/N)" + Cores.RESET);
				            System.out.print(Cores.AMARELO + "--> " + Cores.RESET);
				            String continuar = scanner.nextLine().trim().toUpperCase();

				            if (continuar.equals("N")) {
				                continue loopMenuAlunos;
				            } else if (!continuar.equals("N") && !continuar.equals("S")) {
				                System.out.println(Cores.ERRO + "Comando desconhecido, retornando ao menu principal..." + Cores.RESET);
				                continue loopMenuAlunos;
				            }
				            
				            else {
				                continue; // volta pro inicio do while
				            }
				        }

				        // se chegou aqui é porque a senha foi validada
				        boolean sucesso = usuarioDAO.atualizarSenha(user.getId(), novaSenha);
				        if (sucesso) {
				            System.out.println(Cores.SUCESSO + "Senha atualizada com sucesso!" + Cores.RESET);
				        } else {
				            System.out.println(Cores.ERRO + "Erro ao atualizar senha!" + Cores.RESET);
				        }

				        break; // sai do while
				    }
				    break; 

				default:
					System.out.println(Cores.ERRO + "Comando desconhecido, tente novamente" + Cores.RESET);
					continue loopMenuAlunos; 
					
				} // fim do switch
			} // fim do if (alunoLogado != null)
			break;

		default:
			System.out.println(Cores.ERRO + "Comando desconhecido, tente novamente" + Cores.RESET);
			continue loopMenuAlunos; 
			
		} // fim do switch
	} // fim do loop while do menu
} // fim do else (que entra no menu de aluno)
} // FIM DO PROGRAMA !!!!

	private static void exibirAlunos(List<Aluno> alunos) {
	    System.out.println(Cores.VERDE + "\nLista de Alunos:" + Cores.RESET);
	    System.out.println(Cores.TEXTO_BRANCO + "-".repeat(160) + Cores.RESET);

	    // Cabeçalho com negrito e texto branco
	    System.out.printf(Cores.TEXTO_NEGRITO + Cores.TEXTO_BRANCO + "| %-4s | %-10s | %-30s | %-15s | %-12s | %-50s | %-15s |%n" + Cores.RESET,
	            "ID", "Matrícula", "Nome", "Telefone", "Nascimento", "Curso", "CPF");

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	    for (Aluno aluno : alunos) {
	        System.out.printf("| " + Cores.VERMELHO + "%-4d" + Cores.RESET + " | "
	                        + Cores.LARANJA + "%-10d" + Cores.RESET + " | "
	                        + Cores.CIANO + "%-30s" + Cores.RESET + " | "
	                        + Cores.VERDE + "%-15s" + Cores.RESET + " | "
	                        + Cores.AZUL + "%-12s" + Cores.RESET + " | "
	                        + Cores.ROXO + "%-50s" + Cores.RESET + " | "
	                        + Cores.LARANJA + "%-15s" + Cores.RESET + " |%n",
	                aluno.getId(),
	                aluno.getMatricula(),
	                aluno.getNome(),
	                aluno.getTelefone(),
	                aluno.getDataDeNascimento().format(formatter),
	                aluno.getCurso(),
	                aluno.getCpf());
	    }

	    System.out.println(Cores.TEXTO_BRANCO + "-".repeat(160) + Cores.RESET);
	}


	public static Curso escolherCurso(Scanner scanner) {
		System.out.println(Cores.AMARELO + "Escolha um curso:" + Cores.RESET);
		Curso[] cursos = Curso.values();
		for (int i = 0; i < cursos.length; i++) {
			System.out.printf(Cores.AMARELO + "[%d]" + Cores.ROXO+ "%s%n", i + 1, cursos[i].getNomeAmigavel());
		}

		int opcaoCurso = -1;
		do {
			System.out.print(Cores.AMARELO + "Digite o número do curso --> " + Cores.RESET);
			try {
				opcaoCurso = Integer.parseInt(scanner.nextLine());
				if (opcaoCurso < 1 || opcaoCurso > cursos.length) {
					System.out.println(Cores.ERRO + "Opção inválida." + Cores.RESET);
					opcaoCurso = -1;
				}
			} catch (NumberFormatException e) {
				System.out.println(Cores.ERRO + "Entrada inválida." + Cores.RESET);
			}
		} while (opcaoCurso == -1);

		return cursos[opcaoCurso - 1];
	}
	
	public class Cores { // Strings com cores pra melhor visualização de texto
	    public static final String RESET = "\u001B[0m"; // retorna a cor padrão
	    public static final String VERMELHO = "\u001B[31m";
	    public static final String VERDE = "\u001B[32m";
	    public static final String AMARELO = "\u001B[33m";
	    public static final String LARANJA = "\u001B[38;5;208m";
	    public static final String AZUL = "\u001B[34m";
	    public static final String ROXO = "\u001B[35m";
	    public static final String CIANO = "\u001B[36m";
	    
	    public static final String TEXTO_NEGRITO = "\u001B[1m";  // Negrito
	    public static final String TEXTO_BRANCO = "\u001B[37m";
	    public static final String TEXTO_PRETO = "\u001B[30m";
	    public static final String FUNDO_VERMELHO = "\u001B[41m";
	    public static final String FUNDO_VERDE = "\u001B[42m";
	    public static final String ERRO = FUNDO_VERMELHO + TEXTO_BRANCO + TEXTO_NEGRITO; // Usado para mostrar ERROS!!!
	    public static final String SUCESSO = FUNDO_VERDE + TEXTO_NEGRITO + TEXTO_PRETO; // Usado pra mostrar msgm de Sucesso!

	}
	
}
