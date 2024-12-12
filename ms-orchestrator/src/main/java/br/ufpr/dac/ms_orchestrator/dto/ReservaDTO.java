package br.ufpr.dac.ms_orchestrator.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
  private String cod;
  private EstadoReservaDTO estado;
  private String codVoo;
  private String id_cliente;
  private Date data_hora;
  private double valorGasto;
  private double milhasGasto;
}
