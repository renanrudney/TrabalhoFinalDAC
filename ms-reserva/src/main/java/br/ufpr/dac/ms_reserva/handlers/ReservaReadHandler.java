package br.ufpr.dac.ms_reserva.handlers;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;

public class ReservaReadHandler {
  @Autowired
  private ReservaReadRepository readRepository;
  @Autowired
  private ObjectMapper objectMapper;

  private final static String FILA_RESERVAS_READ_REALIZADO = "RESERVAS_READ_REALIZADO_VOO";
  private final static String FILA_RESERVAS_READ_CANCELADO = "RESERVAS_READ_CANCELADO_VOO";
  private final static String FILA_CANCELAR_RESERVA_READ = "CANCELAR_RESERVA_READ";
  private final static String FILA_CHECKIN_RESERVA_READ = "CHECKIN_RESERVA_READ";
  private final static String FILA_REALIZAR_RESERVA_READ = "REALIZAR_RESERVA_READ";

  @RabbitListener(queues = FILA_RESERVAS_READ_REALIZADO)
  private void reservaReadRealizado(String reservasString) throws JsonProcessingException {
    List<ReservaRead> reservasRead = objectMapper.readValue(reservasString, new TypeReference<List<ReservaRead>>(){});
    readRepository.saveAllAndFlush(reservasRead);
  }

  @RabbitListener(queues = FILA_RESERVAS_READ_CANCELADO)
  private void reservaReadCancelado(String reservasString) throws JsonProcessingException {
    List<ReservaRead> reservasRead = objectMapper.readValue(reservasString, new TypeReference<List<ReservaRead>>(){});
    readRepository.saveAllAndFlush(reservasRead);
  }

  @RabbitListener(queues = FILA_REALIZAR_RESERVA_READ)
  private void realizarReservaRead(String reservaString) throws JsonProcessingException {
    ReservaRead reservaRead = objectMapper.readValue(reservaString, ReservaRead.class);
    readRepository.saveAndFlush(reservaRead);
  }

  @RabbitListener(queues = FILA_CHECKIN_RESERVA_READ)
  private void checkinReservaRead(String reservaString) throws JsonProcessingException {
    ReservaRead reservaRead = objectMapper.readValue(reservaString, ReservaRead.class);
    readRepository.saveAndFlush(reservaRead);
  }

  @RabbitListener(queues = FILA_CANCELAR_RESERVA_READ)
  private void cancelarReservaRead(String reservaString) throws JsonProcessingException {
    ReservaRead reservaRead = objectMapper.readValue(reservaString, ReservaRead.class);
    readRepository.saveAndFlush(reservaRead);
  }
}
