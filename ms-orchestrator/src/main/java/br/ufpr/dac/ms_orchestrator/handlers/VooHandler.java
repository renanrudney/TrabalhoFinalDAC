package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;

@Component
public class VooHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConstants.FILA_VOO_REALIZADO)
  private void vooRealizadoHandler(String vooRealizado) {
    this.rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RESERVAS_REALIZADO, vooRealizado);
  }

  @RabbitListener(queues = RabbitMQConstants.FILA_VOO_CANCELADO)
  private void vooCanceladoHandler(String vooCancelado) {
    this.rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_RESERVAS_CANCELADO, vooCancelado);
  }
}
