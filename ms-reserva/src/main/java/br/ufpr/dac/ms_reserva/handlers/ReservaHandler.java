package br.ufpr.dac.ms_reserva.handlers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_reserva.model.EstadoReserva;
import br.ufpr.dac.ms_reserva.model.Reserva;
import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaRepository;


@Component
public class ReservaHandler {
  @Autowired
  private ReservaReadRepository readRepository;
  @Autowired
  private ReservaRepository reservaRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final static String FILA_RESERVAS_REALIZADO = "RESERVAS_REALIZADO_VOO";
  private final static String FILA_RESERVAS_CANCELADO = "RESERVAS_CANCELADO_VOO";
  private final static String FILA_RESERVAS_READ_REALIZADO = "RESERVAS_READ_REALIZADO_VOO";
  private final static String FILA_RESERVAS_READ_CANCELADO = "RESERVAS_READ_CANCELADO_VOO";


  @RabbitListener(queues = FILA_RESERVAS_REALIZADO)
  private void realizarVooReservas(String codVoo) throws JsonProcessingException {
    List<ReservaRead> reservasRead = readRepository.findByCod_voo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();

    for (ReservaRead reservaRead : reservasRead) {
      if (reservaRead.getEstado() == EstadoReserva.EMBARCADO) {
        reservaRead.setEstado(EstadoReserva.REALIZADO);
      } else {
        reservaRead.setEstado(EstadoReserva.NÃO_REALIZADO);
      }
      reservasToUpdate.add(modelMapper.map(reservaRead, Reserva.class));
    }
    reservaRepository.saveAllAndFlush(reservasToUpdate);

    rabbitTemplate.convertAndSend(FILA_RESERVAS_READ_REALIZADO, objectMapper.writeValueAsString(reservasRead));
  }

  @RabbitListener(queues = FILA_RESERVAS_CANCELADO)
  private void cancelarVooReservas(String codVoo) throws JsonProcessingException {
    List<ReservaRead> reservasRead = readRepository.findByCod_voo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();

    for (ReservaRead reservaRead : reservasRead) {
      reservaRead.setEstado(EstadoReserva.CANCELADO_VOO);
      reservasToUpdate.add(modelMapper.map(reservaRead, Reserva.class));
    }
    reservaRepository.saveAllAndFlush(reservasToUpdate);

    rabbitTemplate.convertAndSend(FILA_RESERVAS_READ_CANCELADO, objectMapper.writeValueAsString(reservasRead));
  }
}
