package dac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import dac.dto.FuncionarioDTO;
import dac.entity.ms_funcionario.Funcionario;
import dac.repository.FuncionarioRepository;

@CrossOrigin
@RestController
public class FuncionarioController 
{
	@Autowired
	ModelMapper mapper;
	@Autowired
	FuncionarioRepository repoFuncionario;
	
	@GetMapping("/funcionario")
	public List<FuncionarioDTO> buscarTodosFuncionarios()
	{
		List<Funcionario> buscarFuncionarios = repoFuncionario.findAll();
		List<FuncionarioDTO> lista = new ArrayList<>();
		
		for(Funcionario funcionario : buscarFuncionarios)
			lista.add(mapper.map(funcionario, FuncionarioDTO.class));
		
		return lista;
	}
	
	@GetMapping("/funcionario/{id}")
	public FuncionarioDTO buscarFuncionario(@PathVariable Long id)
	{
		Optional<Funcionario> buscarFuncionario = repoFuncionario.findById(id);
		
		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");
		
		return mapper.map(buscarFuncionario.get(), FuncionarioDTO.class);
	}
	
	@SuppressWarnings("null")
	@PostMapping("/funcionario")
	public ResponseEntity<FuncionarioDTO> criarFuncionario(@RequestBody FuncionarioDTO funcionarioRecebido)
	{
		// Validar Funcionario
		
		// Realizar requisição para microsserviço que cria um usuário e retorna o id
		Long idRecebidoMicrosservico = 1L;
		
		Funcionario novoFuncionario = mapper.map(funcionarioRecebido, Funcionario.class);
		novoFuncionario.setIdUsuario(idRecebidoMicrosservico);
		
		novoFuncionario.setAtivo(true);
		novoFuncionario = repoFuncionario.save(novoFuncionario);
		
		return ResponseEntity.created(null).body(mapper.map(novoFuncionario, FuncionarioDTO.class));
	}
	
	// Se houver alteração de informações da entidade usuário, o orquestrador manda a alteração para o microsserviço correspondente
	@PutMapping("/funcionario/{id}")
	public FuncionarioDTO atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioRecebido)
	{
		// Validar Funcionário atualizado
		
		Optional<Funcionario> buscarFuncionario = repoFuncionario.findById(id);
		
		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");
		
		buscarFuncionario.get().setCpf(funcionarioRecebido.getCpf());
		buscarFuncionario.get().setEmail(funcionarioRecebido.getEmail());
		
		buscarFuncionario.get().setNome(funcionarioRecebido.getNome());
		buscarFuncionario.get().setTelefone(funcionarioRecebido.getTelefone());
		
		return mapper.map(repoFuncionario.save(buscarFuncionario.get()), FuncionarioDTO.class);
	}
	
	@DeleteMapping("/funcionario/{id}")
	public FuncionarioDTO removerFuncionario(@PathVariable Long id)
	{
		Optional<Funcionario> buscarFuncionario = repoFuncionario.findById(id);
		
		if(buscarFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe funcionário com esse id!");
		
		buscarFuncionario.get().setAtivo(false);
		return mapper.map(repoFuncionario.save(buscarFuncionario.get()), FuncionarioDTO.class);
	}
}
