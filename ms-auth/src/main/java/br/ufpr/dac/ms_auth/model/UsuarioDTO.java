package br.ufpr.dac.ms_auth.model;

public class UsuarioDTO {
  private String id;
  private String login;
  private Tipo tipo;

  public UsuarioDTO() {
    super();
  }

  public UsuarioDTO(String id, String login, Tipo tipo) {
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

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }
}
