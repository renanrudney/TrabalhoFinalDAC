package dac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
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

import dac.dto.ClienteDTO;
import dac.entity.ms_cliente.Cliente;
import dac.repository.ClienteRepository;

@CrossOrigin
@RestController
public class ClienteController {
	@Autowired
	ModelMapper mapper;
	@Autowired
	ClienteRepository repoCliente;

	@GetMapping("/Cliente")
	public List<ClienteDTO> buscarTodosClientes() {
		List<Cliente> buscarClientes = repoCliente.findAll();
		List<ClienteDTO> lista = new ArrayList<>();

		for (Cliente Cliente : buscarClientes)
			lista.add(mapper.map(Cliente, ClienteDTO.class));

		return lista;
	}

	@GetMapping("/Cliente/{id}")
	public ClienteDTO buscarCliente(@PathVariable Long id) {
		Optional<Cliente> buscarCliente = repoCliente.findById(id);

		if (buscarCliente.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Cliente com esse id!");

		return mapper.map(buscarCliente.get(), ClienteDTO.class);
	}

	@PostMapping("/Cliente")
	public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO ClienteRecebido) {

		// Validar se o Cliente já existe
		Cliente clienteJaExiste = repoCliente.findByCpf(ClienteRecebido.getCpf());
		if (clienteJaExiste != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Cliente com esse CPF!");
		}

		// Realizar requisição para microsserviço que cria um usuário e retorna o id
		
		Long idRecebidoMicrosservico = getRandomLong(1L, 100L);

		Cliente novoCliente = mapper.map(ClienteRecebido, Cliente.class);
		novoCliente.setIdUsuario(idRecebidoMicrosservico);

		novoCliente.setAtivo(true);
		novoCliente = repoCliente.save(novoCliente);

		return ResponseEntity.created(null).body(mapper.map(novoCliente, ClienteDTO.class));
	}

	// Se houver alteração de informações da entidade usuário, o orquestrador manda
	// a alteração para o microsserviço correspondente
	@PutMapping("/Cliente/{id}")
	public ClienteDTO atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO ClienteRecebido) {
		// Validar Cliente atualizado

		Optional<Cliente> buscarCliente = repoCliente.findById(id);

		if (buscarCliente.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Cliente com esse id!");

		buscarCliente.get().setCpf(ClienteRecebido.getCpf());
		buscarCliente.get().setEmail(ClienteRecebido.getEmail());
		buscarCliente.get().setNome(ClienteRecebido.getNome());

		return mapper.map(repoCliente.save(buscarCliente.get()), ClienteDTO.class);
	}

	@DeleteMapping("/Cliente/{id}")
	public ClienteDTO removerCliente(@PathVariable Long id) {
		Optional<Cliente> buscarCliente = repoCliente.findById(id);

		if (buscarCliente.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Cliente com esse id!");

		buscarCliente.get().setAtivo(false);
		return mapper.map(repoCliente.save(buscarCliente.get()), ClienteDTO.class);
	}


	// Função temporária para teste de inserção de clientes no banco
	private static final Random random = new Random();

    public static long getRandomLong(long min, long max) {
        // Garante que o valor mínimo seja menor que o máximo
        if (min >= max) {
            throw new IllegalArgumentException("O valor mínimo deve ser menor que o valor máximo.");
        }
        
        // Gera um número aleatório no intervalo especificado
        return min + (long) (random.nextDouble() * (max - min));
    }
}
