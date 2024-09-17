package dac.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO 
{
	private int id;
	private ClienteDTO cliente;
	private LocalDate dataHora;
	private double qtdMilhas;
	private boolean entrada;
	private String descricao;
}
