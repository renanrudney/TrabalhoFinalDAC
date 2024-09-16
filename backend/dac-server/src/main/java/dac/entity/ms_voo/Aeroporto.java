package dac.entity.ms_voo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Aeroporto", schema="Voo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aeroporto 
{
	@Id
	@Column(name="cod")
	private String cod;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cidade")
	private String cidade;
	
	@Column(name="estado")
	private String estado;
}
