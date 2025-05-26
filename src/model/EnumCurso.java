package model;

public enum EnumCurso {
    ADS(1, "Tecnológia em Analíse e Desenvolvimento de Sistemas"),
    SISTEMAS_INFORMACAO(2, "Sistemas de Informação"),
    CIENCIA_COMPUTACAO(3, "Ciência da Computação"),
    CINEMA(4, "Cinema"),
    ENGENHARIA_DE_SOFTWARE(5, "Engenharia de Software"),
    ENGENHARIA_CIVIL(6, "Engenharia Civil"),
    ENGENHARIA_ELETRICA(7, "Engenharia Elétrica"),
    ENGENHARIA_MECANICA(8, "Engenharia Mecânica"),
    DIREITO(9, "Direito"),
    ADMINISTRACAO(10, "Administração"),
    CIENCIAS_CONTABEIS(11, "Ciências Contábeis"),
    ECONOMIA(12, "Economia"),
    GESTAO_COMERCIAL(13, "Gestão Comercial"),
    MARKETING(14, "Marketing"),
    RECURSOS_HUMANOS(15, "Recursos Humanos"),
    PEDAGOGIA(16, "Pedagogia"),
    ENFERMAGEM(17, "Enfermagem"),
    MEDICINA(18, "Medicina"),
    PSICOLOGIA(19, "Psicologia"),
    EDUCACAO_FISICA(20, "Educação Física"),
    LETRAS(21, "Letras"),
    DESIGN_GRAFICO(22, "Design Gráfico"),
    DESIGN_INTERIORES(23, "Design de Interiores"),
    ARQUITETURA_URBANISMO(24, "Arquitetura e Urbanismo"),
    BIOLOGIA(25, "Biologia"),
    FISICA(26, "Física"),
    QUIMICA(27, "Química"),
    MATEMATICA(28, "Matemática");

    private final int id;
    private final String nomeAmigavel;

    EnumCurso(int id, String nomeAmigavel) {
        this.id = id;
        this.nomeAmigavel = nomeAmigavel;
    }

    public int getId() {
        return id;
    }

    public String getNomeAmigavel() {
        return nomeAmigavel;
    }

    public static EnumCurso porId(int id) {
        for (EnumCurso curso : values()) {
            if (curso.getId() == id) {
                return curso;
            }
        }
        return null;
    }
}
