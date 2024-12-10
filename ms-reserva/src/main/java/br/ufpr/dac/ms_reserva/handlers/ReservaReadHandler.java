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
}
