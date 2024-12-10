package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_orchestrator.dto.ClienteDTO;
import br.ufpr.dac.ms_orchestrator.dto.UsuarioDTO;
import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class UsuarioHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_USUARIO_CLIENTE_CRIADO)
  private void usuarioClienteCriado(String stringCliente) throws JsonProcessingException {
    

    rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RETORNAR_USUARIO_CLIENTE, 1);
  }
}
