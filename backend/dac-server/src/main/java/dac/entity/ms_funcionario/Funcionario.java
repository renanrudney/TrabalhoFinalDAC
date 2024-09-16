package dac.entity.ms_funcionario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Funcionario", schema="Funcionario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario 
{
	@Id
	@Column(name="id_usuario")
	private Long idUsuario;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private String cpf;
	
	@Column(name="email")
	private String email;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="ativo")
	private boolean ativo;
}
