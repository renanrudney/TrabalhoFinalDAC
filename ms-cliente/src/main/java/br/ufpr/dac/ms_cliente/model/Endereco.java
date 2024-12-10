package br.ufpr.dac.ms_cliente.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Endereco", schema="Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {
	@Id
	@Column(name="id_endereco")
	private UUID id;
	
	@Column(name="rua")
	private String rua;
	
	@Column(name="numero")
	private Long numero;
	
	@Column(name="complemento")
	private String complemento;
	
	@Column(name="cep")
	private String cep;
	
	@Column(name="cidade")
	private String cidade;
	
	@Column(name="estado")
	private String estado;
}
