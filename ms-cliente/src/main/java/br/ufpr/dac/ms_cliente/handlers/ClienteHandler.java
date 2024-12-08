package br.ufpr.dac.ms_cliente.handlers;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.common.constants.RabbitMQConstants;
import br.ufpr.dac.ms_cliente.dto.ClienteDTO;
import br.ufpr.dac.ms_cliente.model.Cliente;
import br.ufpr.dac.ms_cliente.repository.ClienteRepository;

@Component
public class ClienteHandler {
  @Autowired
  private ClienteRepository clienteRepository;
  @Autowired
	private ModelMapper mapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_CRIAR_CLIENTE)
	private void criarClienteHandler(String stringCliente) {
    System.out.println("Entrou no cliente");
    // String test = string;

 
    try {
      // UsuarioDTO cl = new ObjectMapper().readValue(string, UsuarioDTO.class);
      ClienteDTO cliente =  new ObjectMapper().readValue(stringCliente, ClienteDTO.class);
      Cliente novoCliente = mapper.map(cliente, Cliente.class);
      clienteRepository.saveAndFlush(novoCliente);
      System.out.println(novoCliente.getEmail());
      // this.rabbitMQService.enviaMensagem(FILA_TESTE1, test);
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }
	}
}


