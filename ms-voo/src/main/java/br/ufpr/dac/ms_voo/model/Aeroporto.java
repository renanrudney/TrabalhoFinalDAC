package br.ufpr.dac.ms_voo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Aeroporto", schema = "Voo")
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

  public Aeroporto() {
  }

  public Aeroporto(String cod, Date data, String nome, String cidade, String estado) {
    this.cod = cod;
    this.data = data;
    this.nome = nome;
    this.cidade = cidade;
    this.estado = estado;
  }

  public String getCod() {
    return cod;
  }

  public void setCod(String cod) {
    this.cod = cod;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }


}
