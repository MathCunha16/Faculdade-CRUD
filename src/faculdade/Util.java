package faculdade;

import java.util.Scanner;

import model.Curso;
import model.EnumCurso;

public class Util { // Metódos úteis para serem usados na main

	public final class Cores { // Strings com cores pra melhor visualização de texto
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
	
	public final static EnumCurso escolherCurso(Scanner scanner) {
	    System.out.println(Cores.AMARELO + "Escolha um curso:" + Cores.RESET);
	    EnumCurso[] cursos = EnumCurso.values();
	    for (int i = 0; i < cursos.length; i++) {
	        System.out.printf(Cores.AMARELO + "[%d]" + Cores.ROXO + "%s%n", i + 1, cursos[i].getNomeAmigavel());
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
	
	public static Curso converter(EnumCurso enumCurso) {
	    return new Curso(enumCurso.getId(), enumCurso.getNomeAmigavel());
	}
	
}
