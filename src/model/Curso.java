package model;

public enum Curso {
    ADS("Tecnológia em Analíse e Desenvolvimento de Sistemas"),
    SISTEMAS_DE_INFORMACAO("Sistemas de Informação"),
    CIENCIA_DA_COMPUTACAO("Ciência da Computação"),
    CINEMA("Cinema"),
    ENGENHARIA_DE_SOFTWARE("Engenharia de Software"),
    ENGENHARIA_CIVIL("Engenharia Civil"),
    ENGENHARIA_ELETRICA("Engenharia Elétrica"),
    ENGENHARIA_MECANICA("Engenharia Mecânica"),
    DIREITO("Direito"),
    ADMINISTRACAO("Administração"),
    CONTABILIDADE("Ciências Contábeis"),
    ECONOMIA("Economia"),
    GESTAO_COMERCIAL("Gestão Comercial"),
    MARKETING("Marketing"),
    RECURSOS_HUMANOS("Recursos Humanos"),
    PEDAGOGIA("Pedagogia"),
    ENFERMAGEM("Enfermagem"),
    MEDICINA("Medicina"),
    PSICOLOGIA("Psicologia"),
    EDUCACAO_FISICA("Educação Física"),
    LETRAS("Letras"),
    DESIGN_GRAFICO("Design Gráfico"),
    DESIGN_DE_INTERIORES("Design de Interiores"),
    ARQUITETURA("Arquitetura e Urbanismo"),
    BIOLOGIA("Biologia"),
    FISICA("Física"),
    QUIMICA("Química"),
    MATEMATICA("Matemática");

    private final String nomeAmigavel;

    Curso(String nomeAmigavel) {
        this.nomeAmigavel = nomeAmigavel;
    }

    public String getNomeAmigavel() {
        return nomeAmigavel;
    }

    @Override
    public String toString() {
        return nomeAmigavel;
    }

    public static Curso fromString(String valor) {
        for (Curso c : Curso.values()) {
            if (c.name().equalsIgnoreCase(valor.trim()) || c.getNomeAmigavel().equalsIgnoreCase(valor.trim())) {
                return c;
            }
        }
        throw new IllegalArgumentException("Curso não encontrado: " + valor);
    }
}
