package br.ufpr.dac.ms_orchestrator.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
  private UUID id;
  private EnderecoDTO endereco;
  private String cpf;
  private String nome;
  private String email;
  private double milhas;
  private boolean ativo;
}
