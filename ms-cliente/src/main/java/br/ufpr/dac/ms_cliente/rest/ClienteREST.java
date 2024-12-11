package br.ufpr.dac.ms_cliente.rest;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_cliente.dto.ClienteDTO;
import br.ufpr.dac.ms_cliente.model.Cliente;
import br.ufpr.dac.ms_cliente.repository.ClienteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
public class ClienteREST {
  @Autowired
  private ClienteRepository clienteRepository;
  @Autowired
  private ModelMapper modelMapper;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RabbitTemplate rabbitTemplate;

  private final static String FILA_CLIENTE_CRIADO = "CLIENTE_CRIADO";

  @PostMapping("/clientes")
	public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteRecebido) throws JsonProcessingException {
		Optional<Cliente> clienteJaExiste = clienteRepository.findByCpf(clienteRecebido.getCpf());

		if (clienteJaExiste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Cliente com esse CPF!");
		}

		Cliente novoCliente = modelMapper.map(clienteRecebido, Cliente.class);
		novoCliente.setId(UUID.randomUUID());
		novoCliente.getEndereco().setId(UUID.randomUUID());
		novoCliente.setAtivo(true);
		clienteRepository.saveAndFlush(novoCliente);
		
		ClienteDTO clienteDTO = modelMapper.map(novoCliente, ClienteDTO.class);
		rabbitTemplate.convertAndSend(FILA_CLIENTE_CRIADO, objectMapper.writeValueAsString(clienteDTO));

		return ResponseEntity.created(null).body(clienteDTO);
	}

	@GetMapping("/clientes")
	public ResponseEntity<ClienteDTO> buscarClientesPorEmail(@RequestParam String email) {
		Optional<Cliente> cliente = clienteRepository.findByEmail(email);

		if (cliente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
		}

		return ResponseEntity.ok(modelMapper.map(cliente, ClienteDTO.class));
	}
 
	@GetMapping("/clientes/{id}")
	public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable String id) {
		UUID clienteId = UUID.fromString(id);
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		if (cliente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
		}

		return ResponseEntity.ok(modelMapper.map(cliente, ClienteDTO.class));
	}
}
