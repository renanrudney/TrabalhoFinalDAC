package br.ufpr.dac.ms_reserva.model;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estado_reserva", schema = "Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReserva implements Serializable {
  @Id
  @Column(name = "sigla")
  private String sigla;

  @Column(name = "descricao")
  private String descricao;
}
