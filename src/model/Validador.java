package model;

public class Validador {
	
	public static String formatarCPF(String cpf) {
	    // remove todos os caracteres não numericos
	    cpf = cpf.replaceAll("[^\\d]", "");
	    
	    // aplica a formatação padrão
	    return cpf.substring(0, 3) + "." + 
	           cpf.substring(3, 6) + "." + 
	           cpf.substring(6, 9) + "-" + 
	           cpf.substring(9);
	}

	public static boolean validarCPF(String cpf) {
		// remove pontos e traço
		cpf = cpf.replaceAll("[^\\d]", "");

		// verifica se tem 11 digitos
		if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		try {
			// calculo do primeiro digito verificador
			int soma = 0;
			for (int i = 0; i < 9; i++) {
				soma += (cpf.charAt(i) - '0') * (10 - i);
			}
			int digito1 = 11 - (soma % 11);
			if (digito1 >= 10)
				digito1 = 0;

			// calculo do segundo digito verificador
			soma = 0;
			for (int i = 0; i < 10; i++) {
				soma += (cpf.charAt(i) - '0') * (11 - i);
			}
			int digito2 = 11 - (soma % 11);
			if (digito2 >= 10)
				digito2 = 0;

			// verifica os digitos
			return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validarNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			return false;
		}

		// o nome deve conter só letras e espaços
		if (!nome.matches("[A-Za-zÀ-ÿ ]+")) {
			return false;
		}

		// verifica se tem ao menos duas palavras
		String[] partes = nome.trim().split("\\s+");
		return partes.length >= 2;
	}

	public static boolean validarTelefone(String telefone) {
		if (telefone == null || telefone.trim().isEmpty()) {
			return false;
		}

		// remove tudo que ñ é numero
		String apenasNumeros = telefone.replaceAll("\\D", "");

		// telefone deve ter 10 (fixo com DDD) ou 11 (celular com DDD) digitos
		return apenasNumeros.matches("\\d{10,11}");
	}

	 public static boolean emailValido(String email) {
	        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
	        return email != null && email.matches(regex);
	    }
	
}
