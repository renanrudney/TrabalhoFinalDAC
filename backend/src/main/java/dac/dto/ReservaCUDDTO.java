package dac.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCUDDTO 
{
	private String cod;
	private EstadoReservaCUDDTO estadoReserva;
	private String codVoo;
	private int idCliente;
	private LocalDate data_hora;
}
