package br.ufpr.dac.ms_auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("usuario")
public class Usuario {

  @Id
  private String id;

  private String login;
  private String senha;
  // private Tipo tipo;
  private String tipo;

  // public Usuario(String id, String login, String senha, Tipo tipo) {
  //   super();
  //   this.id = id;
  //   this.login = login;
  //   this.senha = senha;
  //   this.tipo = tipo;
  // }

  public Usuario() {
  }
  

  public Usuario(String id, String login, String senha, String tipo) {
    super();
    this.id = id;
    this.login = login;
    this.senha = senha;
    this.tipo = tipo;
  }



  public Usuario(String login, String senha, String tipo) {
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

  // public Tipo getTipo() {
  //   return tipo;
  // }

  // public void setTipo(Tipo tipo) {
  //   this.tipo = tipo;
  // }

    public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
