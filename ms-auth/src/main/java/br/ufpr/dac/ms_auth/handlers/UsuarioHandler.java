package br.ufpr.dac.ms_auth.handlers;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.common.constants.RabbitMQConstants;
import br.ufpr.dac.ms_auth.dto.ClienteDTO;
import br.ufpr.dac.ms_auth.model.Tipo;
import br.ufpr.dac.ms_auth.model.Usuario;
import br.ufpr.dac.ms_auth.model.UsuarioDTO;
import br.ufpr.dac.ms_auth.repository.UsuarioRepository;
import br.ufpr.dac.ms_auth.services.EnviarEmailService;
import br.ufpr.dac.ms_auth.services.RabbitMQService;

@Component
public class UsuarioHandler {
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private RabbitMQService rabbitMQService;
  @Autowired
  private EnviarEmailService enviarEmailService;

  private final static String FILA_USUARIO_CLIENTE_CRIADO = "USUARIO_CLIENTE_CRIADO";

  @RabbitListener(queues = RabbitMQConstants.FILA_CRIAR_USUARIO_CLIENTE)
  private void criarUsuarioCliente(String stringCliente) {
    ClienteDTO cliente;
    try {  
      cliente = new ObjectMapper().readValue(stringCliente, ClienteDTO.class);
      Usuario usuarioJaExiste = usuarioRepository.findUsuarioByLogin(cliente.getEmail());

      if (usuarioJaExiste == null) {
        System.out.println(cliente.getEmail());
        // String senha = gerarNovaSenha();
        String senha = "1234";
        Usuario novoUsuario = new Usuario(cliente.getEmail(),senha,"CLIENTE");
        usuarioRepository.save(novoUsuario);

        cliente.setIdUsuario(novoUsuario.getId());
    
        // enviarEmailService.enviarEmail(cliente.getEmail(), "Você foi adicionado como cliente, aqui está sua senha.", senha);

        // UsuarioDTO usuario = mapper.map(novoUsuario, UsuarioDTO.class);
        String test = new ObjectMapper().writeValueAsString(cliente);
        rabbitMQService.enviaMensagem(FILA_USUARIO_CLIENTE_CRIADO, test);
      } else {
        String senha = "1234";
        Usuario novoUsuario = new Usuario(cliente.getEmail(), senha,"CLIENTE");
        UsuarioDTO usuario = mapper.map(novoUsuario, UsuarioDTO.class);
        rabbitMQService.enviaMensagem(FILA_USUARIO_CLIENTE_CRIADO, usuario);
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }
	}

  private String gerarNovaSenha() {
    String characters = "0123456789";
    return RandomStringUtils.random(4, characters);
  }
}
