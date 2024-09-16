package dac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReservaCUDDTO 
{
	private int cod;
	private String sigla;
	private String descricao;
}
