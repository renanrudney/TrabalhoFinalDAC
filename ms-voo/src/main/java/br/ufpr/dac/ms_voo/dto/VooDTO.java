package br.ufpr.dac.ms_voo.dto;

import java.util.Date;

import br.ufpr.dac.ms_voo.model.EstadoVoo;

public class VooDTO {
  private String cod;
  private Date data;
  private EstadoVoo estado;
  private String aeroporto_origem;
  private String aeroporto_destino;
  private Double valor_passagem;
  private Integer qtd_poltronas_total;
  private Integer qtd_poltronas_ocupadas;
  
  public VooDTO() {
  }

  

  public VooDTO(String cod, Date data, String aeroporto_origem, String aeroporto_destino, Double valor_passagem,
      Integer qtd_poltronas_total) {
    this.cod = cod;
    this.data = data;
    this.aeroporto_origem = aeroporto_origem;
    this.aeroporto_destino = aeroporto_destino;
    this.valor_passagem = valor_passagem;
    this.qtd_poltronas_total = qtd_poltronas_total;
    this.qtd_poltronas_ocupadas = 0;
  }



  public VooDTO(String cod, Date data, EstadoVoo estado, String aeroporto_origem, String aeroporto_destino,
      Double valor_passagem, Integer qtd_poltronas_total, Integer qtd_poltronas_ocupadas) {
    this.cod = cod;
    this.data = data;
    this.estado = estado;
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

  public EstadoVoo getEstado() {
    return estado;
  }

  public void setEstado(EstadoVoo estado) {
    this.estado = estado;
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


  
}
