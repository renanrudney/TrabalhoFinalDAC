package dac.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogReservaCUDDTO 
{
	private int id;
	private ReservaCUDDTO reserva;
	private EstadoReservaCUDDTO estadoOrigem;
	private EstadoReservaCUDDTO estadoDestino;
	private LocalDate dataHora;
}
