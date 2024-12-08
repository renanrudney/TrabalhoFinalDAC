package br.ufpr.dac.ms_orchestrator.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.common.constants.RabbitMQConstants;
import br.ufpr.dac.ms_orchestrator.dto.ClienteDTO;
import br.ufpr.dac.ms_orchestrator.dto.UsuarioDTO;
import br.ufpr.dac.ms_orchestrator.services.RabbitMQService;

@Component
public class ClienteHandler {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConstants.FILA_USUARIO_CLIENTE_CRIADO)
	private void usuarioClienteCriadoHandler(String usuarioCliente) {
    System.out.println("Entrou no orchestrator");
    this.rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_CRIAR_CLIENTE, usuarioCliente);

    // try {
    //   ClienteDTO cliente =  new ObjectMapper().readValue(usuarioCliente, ClienteDTO.class);
    //   System.out.println(cliente.getEmail());
    //   String test = new ObjectMapper().writeValueAsString(cliente);

    //   this.rabbitMQService.enviaMensagem(RabbitMQConstants.FILA_CRIAR_CLIENTE, test);
    // } catch (Exception e) {
    //   // TODO: handle exception
    //   System.out.println(e.getMessage());
    // }
    // this.rabbitMQService.enviaMensagem(RabbitMQConstants.FILA_CRIAR_CLIENTE, string);
	}
}


