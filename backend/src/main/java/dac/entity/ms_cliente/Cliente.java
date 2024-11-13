package dac.entity.ms_cliente;

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
public class Cliente 
{
	@Id
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_endereco", referencedColumnName="id")
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
