package br.ufpr.dac.ms_reserva.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reserva", schema = "Reserva_cud")
public class Reserva implements Serializable {
  @Id
  @Column(name = "cod")
  private String cod;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "cod_estado")
  private EstadoReserva estado;

  @Column(name = "cod_voo")
  private String cod_voo;

  @Column(name = "id_cliente")
  private String id_cliente;

  @Column(name = "data_hora")
  private LocalDateTime data_hora;

  public Reserva() {
  }

  public Reserva(String cod, EstadoReserva estado, String cod_voo, String id_cliente, LocalDateTime data_hora) {
    this.cod = cod;
    this.estado = estado;
    this.cod_voo = cod_voo;
    this.id_cliente = id_cliente;
    this.data_hora = data_hora;
  }

  public String getCod() {
    return cod;
  }

  public void setCod(String cod) {
    this.cod = cod;
  }

  public EstadoReserva getEstado() {
    return estado;
  }

  public void setEstado(EstadoReserva estado) {
    this.estado = estado;
  }

  public String getCod_voo() {
    return cod_voo;
  }

  public void setCod_voo(String cod_voo) {
    this.cod_voo = cod_voo;
  }

  public String getId_cliente() {
    return id_cliente;
  }

  public void setId_cliente(String id_cliente) {
    this.id_cliente = id_cliente;
  }

  public LocalDateTime getData_hora() {
    return data_hora;
  }

  public void setData_hora(LocalDateTime data_hora) {
    this.data_hora = data_hora;
  }

  
}
