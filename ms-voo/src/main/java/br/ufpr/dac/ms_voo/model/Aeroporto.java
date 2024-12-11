package br.ufpr.dac.ms_voo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Aeroporto", schema = "Voo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aeroporto implements Serializable {
  @Id
  @Column(name = "cod")
  private String cod;

  @Column(name = "data")
  private Date data;

  @Column(name = "nome")
  private String nome;

  @Column(name = "cidade")
  private String cidade;

  @Column(name = "estado")
  private String estado;
}
