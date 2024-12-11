package br.ufpr.dac.ms_reserva.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Log_reserva", schema = "Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoReserva implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "serial")
  private Long id;

  @OneToOne
	@JoinColumn(name="cod_reserva", referencedColumnName="cod")
  private Reserva reserva;

  @ManyToOne
	@JoinColumn(name="estado_origem", referencedColumnName="sigla")
  private EstadoReserva estadoOrigem;

  @ManyToOne
	@JoinColumn(name="estado_destino", referencedColumnName="sigla")
  private EstadoReserva estadoDestino;

  @Column(name = "data_hora")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dataHora;
}
