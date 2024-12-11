package br.ufpr.dac.ms_voo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Voo", schema = "Voo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voo implements Serializable {
  @Id
  @Column(name = "cod")
  private String cod;

  @Column(name = "data")
  private Date data;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "cod_estado")
  private EstadoVoo estado;

  @Column(name = "aeroporto_origem")
  private String aeroporto_origem;

  @Column(name = "aeroporto_destino")
  private String aeroporto_destino;

  @Column(name = "valor_passagem")
  private Double valor_passagem;

  @Column(name = "qtd_poltronas_total")
  private Integer qtd_poltronas_total;

  @Column(name = "qtd_poltronas_ocupadas")
  private Integer qtd_poltronas_ocupadas;
}
