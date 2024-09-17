package dac.entity.ms_reserva;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Log_reserva", schema="Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogReservaCUD 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="log_reserva_id_seq")
	@SequenceGenerator(name="log_reserva_id_seq", sequenceName = "Reserva_cud.log_reserva_id_seq", allocationSize=1)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="cod_reserva", referencedColumnName="cod")
	private ReservaCUD reserva;
	
	@OneToOne
	@JoinColumn(name="estado_origem", referencedColumnName="cod")
	private EstadoReservaCUD estadoOrigem;
	
	@OneToOne
	@JoinColumn(name="estado_destino", referencedColumnName="cod")
	private EstadoReservaCUD estadoDestino;
	
	@Column(name="data_hora")
	private LocalDate dataHora;
}
