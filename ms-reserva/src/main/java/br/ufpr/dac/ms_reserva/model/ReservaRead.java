package br.ufpr.dac.ms_reserva.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reserva", schema = "Reserva_read")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRead implements Serializable {
  @Id
  @Column(name = "cod")
  private String cod;

  @ManyToOne
	@JoinColumn(name="cod_estado", referencedColumnName="sigla")
  private EstadoReservaRead estado;

  @Column(name = "cod_voo")
  private String codVoo;

  @Column(name = "id_cliente")
  private UUID id_cliente;

  @Column(name = "data_hora")
  @Temporal(TemporalType.TIMESTAMP)
  private Date data_hora;

  @Column(name = "valor")
  private double valorGasto;

  @Column(name = "milhas")
  private double milhasGasto;
}
