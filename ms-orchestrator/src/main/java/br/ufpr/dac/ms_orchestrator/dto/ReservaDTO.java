package br.ufpr.dac.ms_orchestrator.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
  private String cod;
  private EstadoReservaDTO estado;
  private String cod_voo;
  private String id_cliente;
  private LocalDateTime data_hora;
  private double valorGasto;
  private double milhasGasto;
}
