package br.ufpr.dac.ms_voo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AeroportoDTO {
  private String cod;
  private Date data;
  private String nome;
  private String cidade;
  private String estado;
}
