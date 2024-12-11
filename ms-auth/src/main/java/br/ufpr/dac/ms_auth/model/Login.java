package br.ufpr.dac.ms_auth.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login implements Serializable {
  private String login;
  private String senha;
}
