package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_orchestrator.dto.ClienteDTO;
import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class ClienteHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_CLIENTE_CRIADO)
  private void clienteCriado(String clienteString) throws JsonProcessingException {
    ClienteDTO clienteDTO = objectMapper.readValue(clienteString, ClienteDTO.class);
    String email = clienteDTO.getEmail();
    rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_CRIAR_USUARIO_CLIENTE, email);
  }
}
