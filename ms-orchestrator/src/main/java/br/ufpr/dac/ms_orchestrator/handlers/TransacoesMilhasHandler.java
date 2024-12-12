package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_orchestrator.dto.ReservaDTO;
import br.ufpr.dac.ms_orchestrator.dto.TransacaoDTO;
import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class TransacoesMilhasHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_RETIRAR_MILHAS_RESERVA)
  private void retirarMilhasReserva(String reservaString) throws JsonProcessingException {
    ReservaDTO reserva = objectMapper.readValue(reservaString, ReservaDTO.class);
    TransacaoDTO transacao = new TransacaoDTO();
    transacao.setIdCliente(reserva.getId_cliente());
    transacao.setQtdMilhas(reserva.getMilhasGasto());
    rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RETIRAR_TRANSACAO, objectMapper.writeValueAsString(transacao)); 
  }

  @RabbitListener(queues = RabbitMQConstants.FILA_REEMBOLSAR_MILHAS_RESERVA)
  private void reembolsarMilhasReserva(String reservaString) throws JsonProcessingException {
    ReservaDTO reserva = objectMapper.readValue(reservaString, ReservaDTO.class);
    TransacaoDTO transacao = new TransacaoDTO();
    transacao.setIdCliente(reserva.getId_cliente());
    transacao.setQtdMilhas(reserva.getMilhasGasto());
    rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_REEMBOLSAR_TRANSACAO, objectMapper.writeValueAsString(transacao));
  }
}
