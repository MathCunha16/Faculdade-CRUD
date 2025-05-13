package model;

public class Usuario {
	private Integer id;
	private String email;
	private String senha;
	private TipoUsuario tipoUsuario;
	private Integer matricula;

	public Usuario() {

	}
	
	public Usuario(String email, String senha) { // Construtor pra login
		this.email = email;
		this.senha = senha;
	}

	public Usuario(Integer id, String email, String senha, model.TipoUsuario tipoUsuario, Integer matricula) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.tipoUsuario = tipoUsuario;
		this.matricula = matricula;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

}
