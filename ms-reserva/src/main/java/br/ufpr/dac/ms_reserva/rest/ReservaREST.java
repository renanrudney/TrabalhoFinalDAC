package br.ufpr.dac.ms_reserva.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_reserva.dto.EmbarqueDTO;
import br.ufpr.dac.ms_reserva.dto.ReservaDTO;
import br.ufpr.dac.ms_reserva.model.EstadoReservaRead;
import br.ufpr.dac.ms_reserva.model.Reserva;
import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.EstadoReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;
import br.ufpr.dac.ms_reserva.repository.ReservaRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
public class ReservaREST {
  @Autowired
  private ReservaReadRepository reservaReadRepository;
  @Autowired
  private ReservaRepository reservaRepository;
  @Autowired
  private EstadoReservaReadRepository estadoRepository;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final static String FILA_REALIZAR_RESERVA_READ = "REALIZAR_RESERVA_READ";
  private final static String FILA_CHECKIN_RESERVA_READ = "CHECKIN_RESERVA_READ";
  private final static String FILA_CANCELAR_RESERVA_READ = "CANCELAR_RESERVA_READ";
  private final static String FILA_RETIRAR_MILHAS_RESERVA = "RETIRAR_MILHAS_RESERVA";
  private final static String FILA_REEMBOLSAR_MILHAS_RESERVA = "REEMBOLSAR_MILHAS_RESERVA";
  private final static String FILA_EMBARQUE_RESERVA_READ = "EMBARQUE_RESERVA_READ";

  @GetMapping("/reservas")
  public ResponseEntity<List<ReservaDTO>> listarReservas() {
    List<ReservaRead> reservasRead = reservaReadRepository.findAll();
    List<ReservaDTO> list = new ArrayList<>();

    for (ReservaRead reservaRead : reservasRead)
      list.add(modelMapper.map(reservaRead, ReservaDTO.class));

    return ResponseEntity.ok().body(list);
  }

  @PostMapping("/reservas")
  public ResponseEntity<ReservaDTO> efetuarReserva(@RequestBody ReservaDTO reservaRecebida) throws JsonProcessingException {
    Optional<EstadoReservaRead> estado = estadoRepository.findBySigla("CONF");

    if (estado.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não existe!");

    ReservaRead reservaRead = modelMapper.map(reservaRecebida, ReservaRead.class);
    reservaRead.setEstado(estado.get());
    reservaRead.setCod(gerarNovoCod());

    reservaRepository.saveAndFlush(modelMapper.map(reservaRead, Reserva.class));

    rabbitTemplate.convertAndSend(FILA_REALIZAR_RESERVA_READ, objectMapper.writeValueAsString(reservaRead));

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    rabbitTemplate.convertAndSend(FILA_RETIRAR_MILHAS_RESERVA, objectMapper.writeValueAsString(reservaDTO));

    return ResponseEntity.ok().body(reservaDTO);
  }

  @GetMapping("/reservas/{cod}")
  public ResponseEntity<ReservaDTO> verReserva(@PathVariable String cod) {
    Optional<ReservaRead> reservaRead = reservaReadRepository.findByCod(cod);

    if (reservaRead.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");
    }

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class); 
    return ResponseEntity.ok().body(reservaDTO);
  }

  @PostMapping("/reservas/{cod}/embarque")
  public ResponseEntity<ReservaDTO> embarqueReserva(@PathVariable String cod, @RequestBody EmbarqueDTO embarque) throws JsonProcessingException {
    Optional<ReservaRead> reservaExiste = reservaReadRepository.findByCod(cod);

    if (reservaExiste.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");

    if(embarque.getCod_voo() != reservaExiste.get().getCodVoo())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva não é do voo fornecido!");

    Optional<EstadoReservaRead> estado = estadoRepository.findBySigla("EMB");

    if (estado.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não existe!");

    ReservaRead reservaRead = reservaExiste.get();
    reservaRead.setEstado(estado.get());
    reservaRepository.saveAndFlush(modelMapper.map(reservaRead, Reserva.class));

    rabbitTemplate.convertAndSend(FILA_EMBARQUE_RESERVA_READ, objectMapper.writeValueAsString(reservaRead));

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    return ResponseEntity.ok().body(reservaDTO);
  }

  @PostMapping("/reservas/{cod}/checkin")
  public ResponseEntity<ReservaDTO> checkinReserva(@PathVariable String cod) throws JsonProcessingException {
    Optional<ReservaRead> reservaExiste = reservaReadRepository.findByCod(cod);

    if (reservaExiste.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");

    Optional<EstadoReservaRead> estado = estadoRepository.findBySigla("CHECK");

    if (estado.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não existe!");

    ReservaRead reservaRead = reservaExiste.get();
    reservaRead.setEstado(estado.get());
    reservaRepository.saveAndFlush(modelMapper.map(reservaRead, Reserva.class));

    rabbitTemplate.convertAndSend(FILA_CHECKIN_RESERVA_READ, objectMapper.writeValueAsString(reservaRead));

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    return ResponseEntity.ok().body(reservaDTO);
  }

  @PostMapping("/reservas/{cod}/cancelar")
  public ResponseEntity<ReservaDTO> cancelarReserva(@PathVariable String cod) throws JsonProcessingException {
    Optional<ReservaRead> reservaExiste = reservaReadRepository.findByCod(cod);

    if (reservaExiste.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");
    }

    Optional<EstadoReservaRead> estado = estadoRepository.findBySigla("CANC");

    if (estado.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não existe!");

    ReservaRead reservaRead = reservaExiste.get();
    reservaRead.setEstado(estado.get());
    reservaRepository.saveAndFlush(modelMapper.map(reservaRead, Reserva.class));

    rabbitTemplate.convertAndSend(FILA_CANCELAR_RESERVA_READ, objectMapper.writeValueAsString(reservaRead));

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    rabbitTemplate.convertAndSend(FILA_REEMBOLSAR_MILHAS_RESERVA, objectMapper.writeValueAsString(reservaDTO));

    return ResponseEntity.ok().body(reservaDTO);
  }

  private String gerarCod() {
    final String characters = "ABCDEFGJIJKLMNOPQRSTUVWXYZ";
    final String numbers = "0123456789";

    String codChar = RandomStringUtils.random(3, characters);
    String numChar = RandomStringUtils.random(3, numbers);
    String cod = codChar + numChar;
    return cod;
  }

  private String gerarNovoCod() {
    String cod;
    do {
      cod = gerarCod();
    } while (reservaReadRepository.findByCod(cod).isPresent());

    return cod;
  }
}
