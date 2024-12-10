package br.ufpr.dac.ms_orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReservaDTO {
  private String sigla;
  private String descricao;
}