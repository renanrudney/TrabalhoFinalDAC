package br.ufpr.dac.ms_cliente.rest;

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
	public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) throws JsonProcessingException {

		Cliente clienteJaExiste = clienteRepository.findByCpf(clienteDTO.getCpf());
		if (clienteJaExiste != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Cliente com esse CPF!");
		}

		clienteDTO.setId(UUID.randomUUID());
		clienteDTO.getEndereco().setId(UUID.randomUUID());
		clienteDTO.setAtivo(true);
		clienteRepository.saveAndFlush(modelMapper.map(clienteDTO, Cliente.class));
		
		rabbitTemplate.convertAndSend(FILA_CLIENTE_CRIADO, objectMapper.writeValueAsString(clienteDTO));
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping("/clientes")
	public ResponseEntity<ClienteDTO> buscarClientesPorEmail(@RequestParam String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
		}
		return ResponseEntity.ok(modelMapper.map(cliente, ClienteDTO.class));
	}
}
