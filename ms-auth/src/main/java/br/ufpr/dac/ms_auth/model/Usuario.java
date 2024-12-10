package br.ufpr.dac.ms_auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
  @Id
  private String id;
  private String login;
  private String senha;
  private String tipo;

  public Usuario(String email, String encodeSenha, String tipo) {
    this.login = email;
    this.senha = encodeSenha;
    this.tipo = tipo;
  }
}
