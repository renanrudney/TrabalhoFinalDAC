package br.ufpr.dac.ms_voo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Voo", schema = "Voo")
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

  public Voo() {
  }

  public Voo(String cod, Date data, String aeroporto_origem, String aeroporto_destino, Double valor_passagem,
      Integer qtd_poltronas) {
    this.cod = cod;
    this.data = data;
    this.aeroporto_origem = aeroporto_origem;
    this.aeroporto_destino = aeroporto_destino;
    this.valor_passagem = valor_passagem;
    this.qtd_poltronas_total = qtd_poltronas;
    this.qtd_poltronas_ocupadas = 0;
  }

  public Voo(String cod, Date data, String aeroporto_origem, String aeroporto_destino, Double valor_passagem,
      Integer qtd_poltronas_total, Integer qtd_poltronas_ocupadas) {
    this.cod = cod;
    this.data = data;
    this.aeroporto_origem = aeroporto_origem;
    this.aeroporto_destino = aeroporto_destino;
    this.valor_passagem = valor_passagem;
    this.qtd_poltronas_total = qtd_poltronas_total;
    this.qtd_poltronas_ocupadas = qtd_poltronas_ocupadas;
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

  public String getAeroporto_origem() {
    return aeroporto_origem;
  }

  public void setAeroporto_origem(String aeroporto_origem) {
    this.aeroporto_origem = aeroporto_origem;
  }

  public String getAeroporto_destino() {
    return aeroporto_destino;
  }

  public void setAeroporto_destino(String aeroporto_destino) {
    this.aeroporto_destino = aeroporto_destino;
  }

  public Double getValor_passagem() {
    return valor_passagem;
  }

  public void setValor_passagem(Double valor_passagem) {
    this.valor_passagem = valor_passagem;
  }

  public Integer getQtd_poltronas_total() {
    return qtd_poltronas_total;
  }

  public void setQtd_poltronas_total(Integer qtd_poltronas_total) {
    this.qtd_poltronas_total = qtd_poltronas_total;
  }

  public Integer getQtd_poltronas_ocupadas() {
    return qtd_poltronas_ocupadas;
  }

  public void setQtd_poltronas_ocupadas(Integer qtd_poltronas_ocupadas) {
    this.qtd_poltronas_ocupadas = qtd_poltronas_ocupadas;
  }

  public EstadoVoo getEstado() {
    return estado;
  }

  public void setEstado(EstadoVoo estado) {
    this.estado = estado;
  }

  
}