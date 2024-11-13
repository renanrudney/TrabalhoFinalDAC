package br.ufpr.dac.ms_cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Endereco", schema="Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="endereco_id_seq")
	@SequenceGenerator(name="endereco_id_seq", sequenceName = "Cliente.endereco_id_seq", allocationSize=1)
	private Long id;
	
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
