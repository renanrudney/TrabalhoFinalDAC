package br.ufpr.dac.ms_funcionario.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_funcionario.dto.FuncionarioDTO;
import br.ufpr.dac.ms_funcionario.model.Funcionario;
import br.ufpr.dac.ms_funcionario.repository.FuncionarioRepository;

@CrossOrigin
@RestController
public class FuncionarioREST {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final static String FILA_FUNCIONARIO_CRIADO = "FUNCIONARIO_CRIADO";
	
	@GetMapping("/funcionarios")
	public ResponseEntity<List<FuncionarioDTO>> buscarTodosFuncionarios() {
		List<Funcionario> buscarFuncionarios = funcionarioRepository.findAllByOrderByNomeAsc();
		List<FuncionarioDTO> lista = new ArrayList<>();
		
		for(Funcionario funcionario : buscarFuncionarios)
			lista.add(modelMapper.map(funcionario, FuncionarioDTO.class));
		
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/funcionarios/{id}")
	public ResponseEntity<FuncionarioDTO> buscarFuncionario(@PathVariable String id) {
		UUID uuid = UUID.fromString(id);
		Optional<Funcionario> buscarFuncionario = funcionarioRepository.findById(uuid);

		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");
		
		return ResponseEntity.ok(modelMapper.map(buscarFuncionario.get(), FuncionarioDTO.class));
	}
	
	@PostMapping("/funcionarios")
	public ResponseEntity<FuncionarioDTO> criarFuncionario(@RequestBody FuncionarioDTO funcionarioRecebido) throws JsonProcessingException {
		Optional<Funcionario> cpfExiste = funcionarioRepository.findByCpf(funcionarioRecebido.getCpf());

		if (cpfExiste.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionário com esse cpf já existe!");
		
		Optional<Funcionario> emailExiste = funcionarioRepository.findByEmail(funcionarioRecebido.getEmail());
		if (emailExiste.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Funcionário com esse email já existe!");

		Funcionario novoFuncionario = modelMapper.map(funcionarioRecebido, Funcionario.class);
		novoFuncionario.setAtivo(true);
		novoFuncionario.setId(UUID.randomUUID());
		funcionarioRepository.saveAndFlush(novoFuncionario);

		FuncionarioDTO funcionarioDTO = modelMapper.map(novoFuncionario, FuncionarioDTO.class);
		rabbitTemplate.convertAndSend(FILA_FUNCIONARIO_CRIADO, objectMapper.writeValueAsString(funcionarioDTO));
		
		return ResponseEntity.created(null).body(funcionarioDTO);
	}
	
	@PutMapping("/funcionarios/{id}")
	public ResponseEntity<FuncionarioDTO> atualizarFuncionario(@PathVariable UUID id, @RequestBody FuncionarioDTO funcionarioRecebido) {
		Optional<Funcionario> buscarFuncionario = funcionarioRepository.findById(id);
		
		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");
		
		Funcionario atualizarFuncionario = buscarFuncionario.get();
		atualizarFuncionario.setEmail(funcionarioRecebido.getEmail());
		atualizarFuncionario.setNome(funcionarioRecebido.getNome());
		atualizarFuncionario.setTelefone(funcionarioRecebido.getTelefone());
		funcionarioRepository.saveAndFlush(atualizarFuncionario);
		
		return ResponseEntity.ok(modelMapper.map(atualizarFuncionario, FuncionarioDTO.class));
	}
	
	@DeleteMapping("/funcionarios/{id}")
	public ResponseEntity<FuncionarioDTO> removerFuncionario(@PathVariable String id) {
		UUID uuid = UUID.fromString(id);
		Optional<Funcionario> buscarFuncionario = funcionarioRepository.findById(uuid);
		
		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");

		Funcionario atualizarFuncionario = buscarFuncionario.get();
		atualizarFuncionario.setAtivo(false);
		funcionarioRepository.saveAndFlush(atualizarFuncionario);

		return ResponseEntity.ok(modelMapper.map(atualizarFuncionario, FuncionarioDTO.class));
	}
}
