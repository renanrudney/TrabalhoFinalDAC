package dac.entity.ms_reserva;

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
@Table(name="Estado_reserva", schema="Reserva_cud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReservaCUD 
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="estado_reserva_cod_seq")
	@SequenceGenerator(name="estado_reserva_cod_seq", sequenceName = "Reserva_cud.estado_reserva_cod_seq", allocationSize=1)
	private Long cod;
	
	@Column(name="sigla")
	private String sigla;
	
	@Column(name="descricao")
	private String descricao;
}
