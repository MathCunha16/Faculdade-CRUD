package faculdade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import dao.AlunoDAO;
import db.InicializadorBanco;
import model.Aluno;
import model.Curso;

public class FaculdadeMain {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		AlunoDAO alunoDAO = new AlunoDAO();

		// Criando a tabela aluno SE não existir
		InicializadorBanco.inicializar();

		System.out.println("=".repeat(15) + "Faculdade UniEsquina" + "=".repeat(15));
		System.out.println("Comandos disponíveis:");
		System.out.println("- CADASTRAR: adicionar um novo aluno");
		System.out.println("- EDITAR: editar informações de um aluno");
		System.out.println("- LISTAR: listar todos os alunos");
		System.out.println("- BUSCAR MATRICULA: busque um aluno pelo número de matricula");
		System.out.println("- BUSCAR NOME: busque alunos pelo nome");
		System.out.println("- DELETAR: deletar um aluno do sistema por meio da matricula");
		System.out.println("- SAIR: encerrar programa");

		loopPrincipal: // label de controle pro loop
		while (true) {
			System.out.println("=".repeat(50));
			System.out.print("Digite o comando que deseja realizar:");
			String entrada = scanner.nextLine().trim().toUpperCase();

			switch (entrada) { // encerra o programa
			case "SAIR":
				System.out.println("Encerrando programa...");
				System.out.println("=".repeat(50));
				break loopPrincipal;

			case "CADASTRAR": // Adiciona novo aluno ao DB
				System.out.println("=".repeat(50));

				System.out.print("Digite o nome do aluno: ");
				String nome = scanner.nextLine();

				System.out.print("Digite o telefone do aluno: ");
				String telefone = scanner.nextLine();

				LocalDate dataDeNascimento = null;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				while (dataDeNascimento == null) {
					try {
						System.out.print("Digite a data de nascimento do aluno (DD/MM/yyyy): ");
						String dataInput = scanner.nextLine();
						dataDeNascimento = LocalDate.parse(dataInput, formatter);
					} catch (DateTimeParseException e) {
						System.err.println("Formato inválido! Use DD/MM/AAAA (ex: 07/11/2004)");
					}
				}

				Curso cursoSelecionado = escolherCurso(scanner); // Listagem de cursos e escolha 

				System.out.print("Digite o CPF do aluno: ");
				String cpf = scanner.nextLine();

				Integer matricula = alunoDAO.gerarNovaMatricula();
				System.out.println("A matricula gerada foi: " + matricula);

				Aluno aluno = new Aluno(matricula, nome, telefone, dataDeNascimento, cursoSelecionado, cpf);
				alunoDAO.inserirDados(aluno);

				continue loopPrincipal;

			case "BUSCAR MATRICULA":
				System.out.println("=".repeat(50));
				System.out.print("Digite a matricula do aluno: ");
				int matriculaBusca = Integer.parseInt(scanner.nextLine());
				Aluno alunoEncontrado = alunoDAO.buscarPorMatricula(matriculaBusca);
				if (alunoEncontrado != null) {
					System.out.println("Aluno encontrado:");
					System.out.println("-".repeat(50));
					System.out.println("Nome: " + alunoEncontrado.getNome());
					System.out.println("Telefone: " + alunoEncontrado.getTelefone());
					System.out.println("Data de Nascimento: " + alunoEncontrado.getDataDeNascimento());
					System.out.println("Curso: " + alunoEncontrado.getCurso());
					System.out.println("CPF: " + alunoEncontrado.getCpf());
					System.out.println("-".repeat(50));
				} else {
					System.out.println("Nenhum aluno encontrado com a matricula " + matriculaBusca);
				}
				continue loopPrincipal;
				
			case "BUSCAR NOME":
				System.out.println("=".repeat(50));
				System.out.print("Digite o nome do aluno: ");
				String nomeBusca = scanner.nextLine();
				List<Aluno> alunosEncontrados = alunoDAO.buscarPorNome(nomeBusca);
				exibirAlunos(alunosEncontrados);
				continue loopPrincipal;

			case "EDITAR":
			    System.out.println("=".repeat(50));

			    // 1) Loop pra verificar matricula
			    int matEdit = -1;
			    while (matEdit < 0) {
			        System.out.print("Digite a matrícula do aluno a editar: ");
			        String line = scanner.nextLine().trim();
			        try {
			            matEdit = Integer.parseInt(line);
			        } catch (NumberFormatException e) {
			            System.err.println("Matrícula inválida! Digite apenas números.");
			        }
			    }

			    // 2) Buscar o aluno
			    Aluno aEditar = alunoDAO.buscarPorMatricula(matEdit);
			    if (aEditar == null) {
			        System.err.println("Aluno não encontrado! Voltando ao menu principal...");
			        continue loopPrincipal;
			    }

			    // 3) Sub-menu
			    System.out.println("O que deseja editar?");
			    System.out.println("[1] Nome");
			    System.out.println("[2] Telefone");
			    System.out.println("[3] Data de Nascimento");
			    System.out.println("[4] Curso");
			    System.out.println("[5] CPF");
			    System.out.print("Escolha a opção: ");
			    
			    int opc = -1;
			    try {
			        opc = Integer.parseInt(scanner.nextLine().trim());
			    } catch (NumberFormatException e) {
			        System.err.println("Opção inválida! Voltando ao menu principal...");
			        continue loopPrincipal;
			    }

			    boolean sucesso = false;
			    switch (opc) {
			        case 1:
			            System.out.print("Novo nome: ");
			            String novoNome = scanner.nextLine();
			            sucesso = alunoDAO.atualizarNome(matEdit, novoNome);
			            break;

			        case 2:
			            System.out.print("Novo telefone: ");
			            String novoTel = scanner.nextLine();
			            sucesso = alunoDAO.atualizarTelefone(matEdit, novoTel);
			            break;

			        case 3:
			            LocalDate novaData = null;
			            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			            while (novaData == null) {
			                try {
			                    System.out.print("Digite a nova data de nascimento (DD/MM/yyyy): ");
			                    novaData = LocalDate.parse(scanner.nextLine(), fmt);
			                } catch (DateTimeParseException e) {
			                    System.err.println("Formato inválido! Use DD/MM/AAAA.");
			                }
			            }
			            sucesso = alunoDAO.atualizarDataNascimento(matEdit, novaData);
			            break;

			        case 4:
			            Curso novoCurso = escolherCurso(scanner);
			            sucesso = alunoDAO.atualizarCurso(matEdit, novoCurso);
			            break;

			        case 5:
			            System.out.print("Novo CPF: ");
			            String novoCpf = scanner.nextLine();
			            sucesso = alunoDAO.atualizarCpf(matEdit, novoCpf);
			            break;

			        default:
			            System.err.println("Opção inválida! Voltando ao menu principal...");
			    }

			    if (sucesso) {
			        System.out.println("Atualização realizada com sucesso!");
			    } else {
			        System.err.println("Falha ao atualizar (talvez matrícula inválida).");
			    }
			    continue loopPrincipal;

			case "LISTAR":
				List<Aluno> alunos = alunoDAO.listarAlunos();
				exibirAlunos(alunos);
				continue loopPrincipal;

			case "DELETAR":
			    System.out.println("=".repeat(50));
			    System.out.print("Digite a matrícula do aluno a ser removido:");
			    try {
			        int matriculaParaRemover = Integer.parseInt(scanner.nextLine());
			        
			        Aluno alunoRemovido = alunoDAO.removerAluno(matriculaParaRemover);
			        
			        if (alunoRemovido != null) {
			            System.out.println("\nAluno removido com sucesso:");
			            System.out.println("Nome: " + alunoRemovido.getNome());
			            System.out.println("Matrícula: " + alunoRemovido.getMatricula());
			        } else {
			            System.err.println("\nErro: Aluno não encontrado ou não pôde ser removido! Voltando para o menu principal...");
			        }
			        
			    } catch (NumberFormatException e) {
			        System.err.println("Matrícula inválida! Digite apenas números. Voltando para o menu principal...");
			    }
			    continue loopPrincipal;

			default:
				System.err.println("Comando desconhecido, tente novamente");
				continue loopPrincipal;
			}
		}

		scanner.close();

	}

	private static void exibirAlunos(List<Aluno> alunos) {
	    System.out.println("\nLista de Alunos:");
	    System.out.println("-".repeat(160));
	    System.out.printf("| %-4s | %-10s | %-30s | %-15s | %-12s | %-50s | %-15s |%n",
	        "ID", "Matrícula", "Nome", "Telefone", "Nascimento", "Curso", "CPF");

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    for (Aluno aluno : alunos) {
	        System.out.printf("| %-4d | %-10d | %-30s | %-15s | %-12s | %-50s | %-15s |%n",
	            aluno.getId(),
	            aluno.getMatricula(),
	            aluno.getNome(),
	            aluno.getTelefone(),
	            aluno.getDataDeNascimento().format(formatter),
	            aluno.getCurso(),
	            aluno.getCpf()
	        );
	    }

	    System.out.println("-".repeat(160) + "\n");
	}
	
	public static Curso escolherCurso(Scanner scanner) {
        System.out.println("Escolha um curso:");
        Curso[] cursos = Curso.values();
        for (int i = 0; i < cursos.length; i++) {
            System.out.printf("[%d] %s%n", i + 1, cursos[i].getNomeAmigavel());
        }

        int opcaoCurso = -1;
        do {
            System.out.print("Digite o número do curso: ");
            try {
                opcaoCurso = Integer.parseInt(scanner.nextLine());
                if (opcaoCurso < 1 || opcaoCurso > cursos.length) {
                    System.out.println("Opção inválida.");
                    opcaoCurso = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        } while (opcaoCurso == -1);

        return cursos[opcaoCurso - 1];
    }
}
