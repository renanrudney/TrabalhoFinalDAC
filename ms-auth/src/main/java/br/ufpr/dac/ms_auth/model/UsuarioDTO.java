package br.ufpr.dac.ms_auth.model;

public class UsuarioDTO {
  private String id;
  private String login;
  private int tipo;

  public UsuarioDTO() {
    super();
  }

  public UsuarioDTO(String id, String login, int tipo) {
    super();
    this.id = id;
    this.login = login;
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

  public int getTipo() {
    return tipo;
  }

  public void setTipo(int tipo) {
    this.tipo = tipo;
  }
}
