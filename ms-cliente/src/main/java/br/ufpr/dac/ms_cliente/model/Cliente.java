package br.ufpr.dac.ms_cliente.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Cliente", schema="Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente implements Serializable {
	@Id
	@Column(name="id")
	private UUID id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_endereco", referencedColumnName="id_endereco")
	private Endereco endereco;
	
	@Column(name="cpf")
	private String cpf;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="email")
	private String email;
	
	@Column(name="milhas")
	private double milhas;

	@Column(name="ativo")
	private boolean ativo;
}
