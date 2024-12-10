package br.ufpr.dac.ms_reserva.rabbitmq.consumer;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpr.dac.ms_reserva.model.EstadoReserva;
import br.ufpr.dac.ms_reserva.model.Reserva;
import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaRepository;


@Component
public class RabbitMQConsumer {
  @Autowired
  ReservaReadRepository readRepository;
  @Autowired
  ReservaRepository repository;
  @Autowired
  ModelMapper modelMapper;

  private final static String FILA_RESERVAS_REALIZADO = "RESERVAS_REALIZADO_VOO";
  private final static String FILA_RESERVAS_CANCELADO = "RESERVAS_CANCELADO_VOO";

  @RabbitListener(queues = FILA_RESERVAS_REALIZADO)
  private void realizarVooReservas(String codVoo) {
    List<ReservaRead> reservas = readRepository.findByCod_voo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();
    for (ReservaRead reserva : reservas) {
      if (reserva.getEstado() == EstadoReserva.EMBARCADO) {
        reserva.setEstado(EstadoReserva.REALIZADO);
      } else {
        reserva.setEstado(EstadoReserva.NÃO_REALIZADO);
      }
      reservasToUpdate.add(modelMapper.map(reserva, Reserva.class));
    }
    repository.saveAllAndFlush(reservasToUpdate);
    readRepository.saveAllAndFlush(reservas); // Chamar Outra fila para atualizar isso
  }

  @RabbitListener(queues = FILA_RESERVAS_CANCELADO)
  private void cancelarVooReservas(String codVoo) {
    List<ReservaRead> reservas = readRepository.findByCod_voo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();
    for (ReservaRead reserva : reservas) {
      reserva.setEstado(EstadoReserva.CANCELADO_VOO);
      reservasToUpdate.add(modelMapper.map(reserva, Reserva.class));
    }
    repository.saveAllAndFlush(reservasToUpdate);
    readRepository.saveAllAndFlush(reservas); // Chamar Outra fila para atualizar isso
  }
}
