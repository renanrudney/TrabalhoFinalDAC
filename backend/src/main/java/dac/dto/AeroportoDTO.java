package dac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AeroportoDTO 
{
	private String cod;
	private String nome;
	private String cidade;
	private String estado;
}
