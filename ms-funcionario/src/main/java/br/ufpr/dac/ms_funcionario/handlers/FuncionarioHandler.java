package br.ufpr.dac.ms_funcionario.handlers;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.common.constants.RabbitMQConstants;
import dac.dto.FuncionarioDTO;
import dac.entity.ms_funcionario.Funcionario;
import dac.repository.FuncionarioRepository;

@Component
public class FuncionarioHandler {
  @Autowired
  private FuncionarioRepository funcionarioRepository;
  @Autowired
	private ModelMapper mapper;

  @RabbitListener(queues = RabbitMQConstants.FILA_CRIAR_CLIENTE)
	private void criarFuncionarioHandler(String stringFuncionario) {
    System.out.println("Entrou no funcionario");
    // String test = string;

 
    try {
      // UsuarioDTO cl = new ObjectMapper().readValue(string, UsuarioDTO.class);
      FuncionarioDTO funcionario =  new ObjectMapper().readValue(stringFuncionario, FuncionarioDTO.class);
      Funcionario novoFuncionario = mapper.map(funcionario, Funcionario.class);
      funcionarioRepository.saveAndFlush(novoFuncionario);
      System.out.println(novoFuncionario.getEmail());
      // this.rabbitMQService.enviaMensagem(FILA_TESTE1, test);
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }
	}
}


