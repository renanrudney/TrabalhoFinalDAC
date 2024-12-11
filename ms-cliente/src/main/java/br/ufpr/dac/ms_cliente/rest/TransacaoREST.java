package br.ufpr.dac.ms_cliente.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.ufpr.dac.ms_cliente.dto.TransacaoDTO;
import br.ufpr.dac.ms_cliente.model.Cliente;
import br.ufpr.dac.ms_cliente.model.Transacao;
import br.ufpr.dac.ms_cliente.repository.ClienteRepository;
import br.ufpr.dac.ms_cliente.repository.TransacaoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
public class TransacaoREST {
  @Autowired
  private TransacaoRepository transacaoRepository;
	@Autowired
  private ClienteRepository clienteRepository;
  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/milhas")
	public ResponseEntity<TransacaoDTO> comprarMilhas(@RequestBody TransacaoDTO transacaoDTO) throws JsonProcessingException {
		Optional<Cliente> clienteJaExiste = clienteRepository.findById(transacaoDTO.getIdCliente());

		if (clienteJaExiste.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!");

		Cliente cliente = clienteJaExiste.get();
		cliente.setMilhas(cliente.getMilhas() + transacaoDTO.getQtdMilhas());
		clienteRepository.saveAndFlush(cliente);

		Transacao transacao = modelMapper.map(transacaoDTO, Transacao.class);
		transacao.setEntrada(true);
		transacao.setDescricao("COMPRA DE MILHAS");
		transacaoRepository.saveAndFlush(transacao);

		return ResponseEntity.created(null).body(modelMapper.map(transacao, TransacaoDTO.class));
	}
 
	@GetMapping("/milhas")
	public ResponseEntity<List<TransacaoDTO>> listarTransacoes(@RequestParam UUID idCliente) {
		Optional<Cliente> clienteJaExiste = clienteRepository.findById(idCliente);

		if (clienteJaExiste.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!");

		List<Transacao> transacoes = transacaoRepository.findByIdCliente(idCliente);
		List<TransacaoDTO> list = new ArrayList<>();

    for (Transacao transacao : transacoes)
			list.add(modelMapper.map(transacao, TransacaoDTO.class));

		return ResponseEntity.ok(list);
	}
}
