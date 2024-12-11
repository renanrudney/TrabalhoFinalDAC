package br.ufpr.dac.ms_voo.dto;

import java.util.Date;

import br.ufpr.dac.ms_voo.model.EstadoVoo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VooDTO {
  private String cod;
  private Date data;
  private EstadoVoo estado;
  private String aeroporto_origem;
  private String aeroporto_destino;
  private Double valor_passagem;
  private Integer qtd_poltronas_total;
  private Integer qtd_poltronas_ocupadas;
}
