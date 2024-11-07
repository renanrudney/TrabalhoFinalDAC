package br.ufpr.dac.ms_auth.model;

public class UsuarioDTO {
  private String id;
  private String login;
  private String senha;
  private int tipo;

  public UsuarioDTO() {
    super();
  }

  public UsuarioDTO(String id, String login, String senha, int tipo) {
    super();
    this.id = id;
    this.login = login;
    this.senha = senha;
    this.tipo = tipo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public int getTipo() {
    return tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }
}
