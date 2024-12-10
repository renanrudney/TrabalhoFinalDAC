package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_orchestrator.dto.FuncionarioDTO;
import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class FuncionarioHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_FUNCIONARIO_CRIADO)
  private void funcionarioCriado(String funcionarioString) throws JsonProcessingException {
    FuncionarioDTO funcionario = objectMapper.readValue(funcionarioString, FuncionarioDTO.class);
    String email = funcionario.getEmail();
    rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_CRIAR_USUARIO_FUNCIONARIO, email);
  }
}
