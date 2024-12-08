package br.ufpr.dac.ms_cliente.rest;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_cliente.model.Cliente;
import br.ufpr.dac.ms_cliente.repository.ClienteRepository;
import br.ufpr.dac.ms_cliente.services.RabbitMQService;
import br.ufpr.dac.ms_orchestrator.dto.ClienteDTO;

@CrossOrigin
@RestController
public class ClienteREST {
  @Autowired
  private ClienteRepository repoCliente;
  @Autowired
  private ModelMapper mapper;

  @Autowired
  private RabbitMQService rabbitMQService;

  private final static String FILA_CRIAR_USUARIO_CLIENTE = "CRIAR_USUARIO_CLIENTE";

  @PostMapping("/Cliente")
	public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO ClienteRecebido) {

		// Validar se o Cliente já existe
		Cliente clienteJaExiste = repoCliente.findByCpf(ClienteRecebido.getCpf());
		if (clienteJaExiste != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Cliente com esse CPF!");
		}

		// Realizar requisição para microsserviço que cria um usuário e retorna o id

    // rabbitMQService.
		
		// Long idRecebidoMicrosservico = getRandomLong(1L, 100L);

		Cliente novoCliente = mapper.map(ClienteRecebido, Cliente.class);
		// novoCliente.setIdUsuario(idRecebidoMicrosservico);

		novoCliente.setAtivo(true);
		// novoCliente = repoCliente.save(novoCliente);
		// Map<String, Object> clienteMap = mapper.map(ClienteRecebido, Map.class);
		ClienteDTO testdto = mapper.map(novoCliente, ClienteDTO.class);
		// String test = new ObjectMapper().writeValueAsString(novoCliente);

		System.out.println(testdto.getEmail());
		try {
			String test = new ObjectMapper().writeValueAsString(testdto);
			rabbitMQService.enviaMensagem(FILA_CRIAR_USUARIO_CLIENTE, test);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// return ResponseEntity.created(null).body(mapper.map(novoCliente, ClienteDTO.class));
    return ResponseEntity.accepted().build();
	}
}
