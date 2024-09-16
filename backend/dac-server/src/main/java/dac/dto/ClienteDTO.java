package dac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO 
{
	private int idUsuario;
	private EnderecoDTO endereco;
	private String cpf;
	private String nome;
	private String email;
	private double milhas;
}
