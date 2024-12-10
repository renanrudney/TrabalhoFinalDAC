package br.ufpr.dac.ms_cliente.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
  private Long id;
  private String idCliente;
  private Date dataHora;
  private Double qtdMilhas;
  private boolean entrada;
  private String descricao;
}
