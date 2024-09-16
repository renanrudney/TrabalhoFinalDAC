package dac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VooDTO 
{
	private String cod;
	private AeroportoDTO aeroportoOrigem;
	private AeroportoDTO aeroportoDestino;
	private String data;
	private double valorPassagem;
	private int qtdPoltronasTotal;
	private int qtdPoltronasOcupadas;
}
