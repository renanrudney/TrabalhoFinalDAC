package br.ufpr.dac.ms_reserva.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_reserva.model.EstadoReservaRead;
import br.ufpr.dac.ms_reserva.model.Reserva;
import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.EstadoReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaRepository;


@Component
public class ReservaHandler {
  @Autowired
  private ReservaReadRepository readRepository;
  @Autowired
  private ReservaRepository reservaRepository;
  @Autowired
  private EstadoReservaReadRepository estadoRepository;
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
    List<ReservaRead> reservasRead = readRepository.findByCodVoo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();

    Optional<EstadoReservaRead> embarcado = estadoRepository.findBySigla("EMB");
    Optional<EstadoReservaRead> realizado = estadoRepository.findBySigla("RD");
    Optional<EstadoReservaRead> naoRealizado = estadoRepository.findBySigla("NRD");

    if (embarcado.isEmpty() || realizado.isEmpty() || naoRealizado.isPresent())
      throw new IllegalArgumentException("Estados de reserva indisponíveis");

    for (ReservaRead reservaRead : reservasRead) {
      if (reservaRead.getEstado().getSigla() == embarcado.get().getSigla()) {
        reservaRead.setEstado(realizado.get());
      } else {
        reservaRead.setEstado(naoRealizado.get());
      }
      reservasToUpdate.add(modelMapper.map(reservaRead, Reserva.class));
    }
    reservaRepository.saveAllAndFlush(reservasToUpdate);

    rabbitTemplate.convertAndSend(FILA_RESERVAS_READ_REALIZADO, objectMapper.writeValueAsString(reservasRead));
  }

  @RabbitListener(queues = FILA_RESERVAS_CANCELADO)
  private void cancelarVooReservas(String codVoo) throws JsonProcessingException {
    List<ReservaRead> reservasRead = readRepository.findByCodVoo(codVoo);
    List<Reserva> reservasToUpdate = new ArrayList<Reserva>();

    Optional<EstadoReservaRead> canceladoVoo = estadoRepository.findBySigla("CAVOO");

    if (canceladoVoo.isPresent())
      throw new IllegalArgumentException("Estados de reserva indisponíveis");

    for (ReservaRead reservaRead : reservasRead) {
      reservaRead.setEstado(canceladoVoo.get());
      reservasToUpdate.add(modelMapper.map(reservaRead, Reserva.class));
    }
    reservaRepository.saveAllAndFlush(reservasToUpdate);

    rabbitTemplate.convertAndSend(FILA_RESERVAS_READ_CANCELADO, objectMapper.writeValueAsString(reservasRead));
  }
}
