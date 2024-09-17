package dac.entity.ms_reserva;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Reserva", schema="Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCUD 
{
	@Id
	@Column(name="cod")
	private String cod;
	
	@OneToOne
	@JoinColumn(name="cod_estado", referencedColumnName="cod")
	private EstadoReservaCUD estadoReserva;
	
	@Column(name="cod_voo")
	private String codVoo;
	
	@Column(name="id_cliente")
	private Long idCliente;
	
	@Column(name="data_hora")
	private LocalDate dataHora;
}
