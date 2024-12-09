package br.ufpr.dac.ms_auth.handlers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.ufpr.dac.ms_auth.model.Usuario;
import br.ufpr.dac.ms_auth.repository.UsuarioRepository;
import br.ufpr.dac.ms_auth.services.EnviarEmailService;

@Component
public class UsuarioHandler {
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private EnviarEmailService enviarEmailService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private final static String FILA_CRIAR_USUARIO_CLIENTE = "CRIAR_USUARIO_CLIENTE";

  @RabbitListener(queues = FILA_CRIAR_USUARIO_CLIENTE)
  private void criarUsuarioCliente(String email) {
    Usuario usuarioJaExiste = usuarioRepository.findByLogin(email);
    if (usuarioJaExiste != null) {
      throw new IllegalArgumentException("Usuário já existe!");
    }

    String senha = gerarNovaSenha();
    String encodeSenha = passwordEncoder.encode(senha);
    Usuario novoUsuario = new Usuario(email, encodeSenha, "CLIENTE");

    usuarioRepository.save(novoUsuario);
    enviarEmailService.enviarEmail(email, "Você foi adicionado como cliente, aqui está sua senha.", senha);
	}

  private String gerarNovaSenha() {
    String characters = "0123456789";
    return RandomStringUtils.random(4, characters);
  }
}
