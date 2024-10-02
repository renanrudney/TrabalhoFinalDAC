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

import dac.dto.VooDTO;
import dac.entity.ms_voo.Aeroporto;
import dac.entity.ms_voo.Voo;
import dac.repository.VooRepository;

@CrossOrigin
@RestController
public class VooController {
	@Autowired
	ModelMapper mapper;
	@Autowired
	VooRepository repoVoo;

	@GetMapping("/Voo")
	public List<VooDTO> buscarTodosVoos() {
		List<Voo> buscarVoos = repoVoo.findAll();
		List<VooDTO> lista = new ArrayList<>();

		for (Voo Voo : buscarVoos)
			lista.add(mapper.map(Voo, VooDTO.class));

		return lista;
	}

	@GetMapping("/Voo/{id}")
	public VooDTO buscarVoo(@PathVariable String id) {
		Optional<Voo> buscarVoo = repoVoo.findById(id);

		if (buscarVoo.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Voo com esse Código!");

		return mapper.map(buscarVoo.get(), VooDTO.class);
	}

	@PostMapping("/Voo")
	public ResponseEntity<VooDTO> criarVoo(@RequestBody VooDTO VooRecebido) {

		// Validar se o Voo já existe
		// Voo vooJaExiste = repoVoo.findByCpf(VooRecebido.getCpf());
		// if (vooJaExiste != null) {
		// 	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Voo com esse CPF!");
		// }

		// Realizar requisição para microsserviço que cria um usuário e retorna o id

		Voo novoVoo = mapper.map(VooRecebido, Voo.class);

		novoVoo = repoVoo.save(novoVoo);

		return ResponseEntity.created(null).body(mapper.map(novoVoo, VooDTO.class));
	}

	// Se houver alteração de informações da entidade usuário, o orquestrador manda
	// a alteração para o microsserviço correspondente
	@PutMapping("/Voo/{id}")
	public VooDTO atualizarVoo(@PathVariable String id, @RequestBody VooDTO VooRecebido) {
		// Validar Voo atualizado

		Optional<Voo> buscarVoo = repoVoo.findById(id);

		if (buscarVoo.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Voo com esse id!");

		Aeroporto novoAeroportoDestino = mapper.map(VooRecebido.getAeroportoDestino(), Aeroporto.class);
		Aeroporto novoAeroportoOrigem = mapper.map(VooRecebido.getAeroportoOrigem(), Aeroporto.class);

		buscarVoo.get().setAeroportoDestino(novoAeroportoDestino);
		buscarVoo.get().setAeroportoOrigem(novoAeroportoOrigem);
		buscarVoo.get().setCod(VooRecebido.getCod());
		buscarVoo.get().setData(VooRecebido.getData());
		buscarVoo.get().setQtdPoltronasOcupadas(VooRecebido.getQtdPoltronasOcupadas());
		buscarVoo.get().setQtdPoltronasTotal(VooRecebido.getQtdPoltronasTotal());
		buscarVoo.get().setValorPassagem(VooRecebido.getValorPassagem());

		return mapper.map(repoVoo.save(buscarVoo.get()), VooDTO.class);
	}

	@DeleteMapping("/Voo/{id}")
	public VooDTO removerVoo(@PathVariable String id) {
		Optional<Voo> buscarVoo = repoVoo.findById(id);

		if (buscarVoo.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe Voo com esse id!");

		return mapper.map(repoVoo.save(buscarVoo.get()), VooDTO.class);
	}
}
