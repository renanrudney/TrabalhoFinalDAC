package br.ufpr.dac.ms_cliente.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Transacao", schema="Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transacao_id_seq")
	@SequenceGenerator(name="transacao_id_seq", sequenceName = "Cliente.transacao_id_seq", allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", referencedColumnName="id")
	private Cliente cliente;
	
	@Column(name="data_hora")
	@Temporal(TemporalType.TIMESTAMP)
  private Date dataHora;
	
	@Column(name="qtd_milhas")
	private double qtdMilhas;
	
	@Column(name="entrada")
	private boolean entrada;
	
	@Column(name="descricao")
	private String descricao;
}
