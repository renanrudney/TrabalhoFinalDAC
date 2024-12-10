package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_orchestrator.dto.VooDTO;
import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class VooHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_VOO_REALIZADO)
  private void vooRealizadoHandler(String vooRealizado) throws JsonProcessingException {
    VooDTO voo = objectMapper.readValue(vooRealizado, VooDTO.class);
    String cod = voo.getCod();
    this.rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RESERVAS_REALIZADO, cod);
  }

  @RabbitListener(queues = RabbitMQConstants.FILA_VOO_CANCELADO)
  private void vooCanceladoHandler(String vooCancelado) throws JsonProcessingException {
    VooDTO voo = objectMapper.readValue(vooCancelado, VooDTO.class);
    String cod = voo.getCod();
    this.rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RESERVAS_CANCELADO, cod);
  }
}
