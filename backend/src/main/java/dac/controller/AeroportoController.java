package dac.controller;

import java.util.ArrayList;
import java.util.Date;
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

import dac.dto.AeroportoDTO;
import dac.entity.ms_voo.Aeroporto;
import dac.repository.AeroportoRepository;

@CrossOrigin
@RestController
public class AeroportoController {
	@Autowired
	ModelMapper mapper;
	@Autowired
	AeroportoRepository repoAeroporto;

	@GetMapping("/Aeroporto")
	public List<AeroportoDTO> buscarTodosAeroportos() {
		List<Aeroporto> buscarAeroportos = repoAeroporto.findAll();
		List<AeroportoDTO> lista = new ArrayList<>();

		for (Aeroporto Aeroporto : buscarAeroportos)
			lista.add(mapper.map(Aeroporto, AeroportoDTO.class));

		return lista;
	}

	@GetMapping("/Aeroporto/{id}")
	public AeroportoDTO buscarAeroporto(@PathVariable String id) {
		Optional<Aeroporto> buscarAeroporto = repoAeroporto.findById(id);

		if (buscarAeroporto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Aeroporto com esse Código!");

		return mapper.map(buscarAeroporto.get(), AeroportoDTO.class);
	}

	@PostMapping("/Aeroporto")
	public ResponseEntity<AeroportoDTO> criarAeroporto(@RequestBody AeroportoDTO AeroportoRecebido) {

		// Validar se o Aeroporto já existe
		// Aeroporto aeroportoJaExiste = repoAeroporto.findByCpf(AeroportoRecebido.getCpf());
		// if (aeroportoJaExiste != null) {
		// 	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Aeroporto com esse CPF!");
		// }

		// Realizar requisição para microsserviço que cria um usuário e retorna o id

		Aeroporto novoAeroporto = mapper.map(AeroportoRecebido, Aeroporto.class);

		novoAeroporto = repoAeroporto.save(novoAeroporto);

		return ResponseEntity.created(null).body(mapper.map(novoAeroporto, AeroportoDTO.class));
	}

	// Se houver alteração de informações da entidade usuário, o orquestrador manda
	// a alteração para o microsserviço correspondente
	@PutMapping("/Aeroporto/{id}")
	public AeroportoDTO atualizarAeroporto(@PathVariable String id, @RequestBody AeroportoDTO AeroportoRecebido) {
		// Validar Aeroporto atualizado

		Optional<Aeroporto> buscarAeroporto = repoAeroporto.findById(id);

		if (buscarAeroporto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Aeroporto com esse Código!");

		buscarAeroporto.get().setCidade(AeroportoRecebido.getCidade());
		buscarAeroporto.get().setCod(AeroportoRecebido.getCod());
		buscarAeroporto.get().setEstado(AeroportoRecebido.getEstado());
		buscarAeroporto.get().setNome(AeroportoRecebido.getNome());

		return mapper.map(repoAeroporto.save(buscarAeroporto.get()), AeroportoDTO.class);
	}

	@DeleteMapping("/Aeroporto/{id}")
	public AeroportoDTO removerAeroporto(@PathVariable String id) {
		Optional<Aeroporto> buscarAeroporto = repoAeroporto.findById(id);

		if (buscarAeroporto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Aeroporto com esse id!");

		return mapper.map(repoAeroporto.save(buscarAeroporto.get()), AeroportoDTO.class);
	}
}
