package dac.entity.ms_voo;

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
@Table(name="Voo", schema="Voo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voo 
{
	@Id
	@Column
	private String cod;
	
	@OneToOne
	@JoinColumn(name="aeroporto_origem", referencedColumnName="cod")
	private Aeroporto aeroportoOrigem;
	
	@OneToOne
	@JoinColumn(name="aeroporto_destino", referencedColumnName="cod")
	private Aeroporto aeroportoDestino;
	
	@Column(name="data")
	private String data;
	
	@Column(name="valor_passagem")
	private double valorPassagem;
	
	@Column(name="qtd_poltronas_total")
	private Long qtdPoltronasTotal;
	
	@Column(name="qtd_poltronas_ocupadas")
	private Long qtdPoltronasOcupadas;
}
