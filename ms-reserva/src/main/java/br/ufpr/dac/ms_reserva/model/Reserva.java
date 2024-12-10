package br.ufpr.dac.ms_reserva.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reserva", schema = "Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva implements Serializable {
  @Id
  @Column(name = "cod")
  private String cod;

  @ManyToOne
	@JoinColumn(name="cod_estado", referencedColumnName="cod")
  private EstadoReserva estado;

  @Column(name = "cod_voo")
  private String cod_voo;

  @Column(name = "id_cliente")
  private String id_cliente;

  @Column(name = "data_hora")
  private LocalDateTime data_hora;

  @Column(name = "valor")
  private double valorGasto;

  @Column(name = "milhas")
  private double milhasGasto;
}
