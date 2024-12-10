package br.ufpr.dac.ms_cliente.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
	private UUID id;
	private String rua;
	private int numero;
	private String complemento;
	private String cep;
	private String cidade;
	private String estado;
}
