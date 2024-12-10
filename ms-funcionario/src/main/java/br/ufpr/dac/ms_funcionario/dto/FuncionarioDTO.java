package br.ufpr.dac.ms_funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO 
{
	private int idUsuario;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private boolean ativo;
}
