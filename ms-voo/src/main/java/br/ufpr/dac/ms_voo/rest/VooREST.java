package br.ufpr.dac.ms_voo.rest;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_voo.dto.VooDTO;
import br.ufpr.dac.ms_voo.model.Aeroporto;
import br.ufpr.dac.ms_voo.model.EstadoVoo;
import br.ufpr.dac.ms_voo.model.Voo;
import br.ufpr.dac.ms_voo.repository.AeroportoRepository;
import br.ufpr.dac.ms_voo.repository.VooRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin
@RestController
public class VooREST {
  @Autowired
  private VooRepository vooRepository;
  @Autowired
  private AeroportoRepository aeroportoRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final static String FILA_VOO_REALIZADO = "VOO_REALIZADO";
  private final static String FILA_VOO_CANCELADO = "VOO_CANCELADO";

  @PostMapping("/voos")
  public ResponseEntity<VooDTO> cadastrarVoo(@RequestBody VooDTO vooRecebido) {
    Voo vooJaExiste = vooRepository.findByCod(vooRecebido.getCod());
		if (vooJaExiste != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um Voo com esse código!");
		}

    Voo novoVoo = modelMapper.map(vooRecebido, Voo.class);

    String origem = novoVoo.getAeroporto_origem();
    Aeroporto aeroportoOrigem = aeroportoRepository.findByCod(origem);
    if (aeroportoOrigem == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aeroporto origem não existe!");
    }
    String destino = novoVoo.getAeroporto_destino();
    Aeroporto aeroportoDestino = aeroportoRepository.findByCod(destino);
    if (aeroportoDestino == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aeroporto destino não existe!");
    }

    novoVoo.setEstado(EstadoVoo.CONFIRMADO);
    vooRepository.save(novoVoo);

    VooDTO vooDTO = modelMapper.map(novoVoo, VooDTO.class);
    return ResponseEntity.ok().body(vooDTO);
  }
  

  @PostMapping("/voos/{cod}/realizar")
  public ResponseEntity<VooDTO> realizarVoo(@PathVariable String cod) throws JsonProcessingException {
    Voo voo = vooRepository.findByCod(cod);
      
    if (voo == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voo não existe!");
    }

    if (voo.getEstado() != EstadoVoo.CONFIRMADO) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voo não confirmado!");
    }

    voo.setEstado(EstadoVoo.REALIZADO);
    vooRepository.save(voo);

    VooDTO vooDTO = modelMapper.map(voo, VooDTO.class);
    String vooMsg = objectMapper.writeValueAsString(vooDTO);
    rabbitTemplate.convertAndSend(FILA_VOO_REALIZADO, vooMsg);

    return ResponseEntity.ok().body(vooDTO);
  }
  

  @PostMapping("/voos/{cod}/cancelar")
  public ResponseEntity<VooDTO> cancelarVoo(@PathVariable String cod) throws JsonProcessingException {
    Voo voo = vooRepository.findByCod(cod);
    if (voo == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voo não existe!");
    }

    if (voo.getEstado() != EstadoVoo.CONFIRMADO) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voo não confirmado!");
    }

    voo.setEstado(EstadoVoo.CANCELADO);
    vooRepository.save(voo);

    VooDTO vooDTO = modelMapper.map(voo, VooDTO.class);
    String vooMsg = objectMapper.writeValueAsString(vooDTO);
    rabbitTemplate.convertAndSend(FILA_VOO_CANCELADO, vooMsg);

    return ResponseEntity.ok().body(vooDTO);
  }
}
