package br.ufpr.dac.ms_reserva.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufpr.dac.ms_reserva.dto.ReservaDTO;
import br.ufpr.dac.ms_reserva.model.EstadoReserva;
import br.ufpr.dac.ms_reserva.model.ReservaRead;
import br.ufpr.dac.ms_reserva.repository.ReservaReadRepository;

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
  private ModelMapper modelMapper;

  @PostMapping("/reserva")
  public String efetuarReserva(@RequestBody ReservaDTO reservaRecebida) {
    // enfileira milhas
    return "";
  }
  

  @GetMapping("/reserva/{cod}")
  public ResponseEntity<ReservaDTO> verReserva(@PathVariable String cod) {
    ReservaRead reservaRead = reservaReadRepository.findByCod(cod);
    if (reservaRead == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");
    }

    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    return ResponseEntity.ok().body(reservaDTO);
  }
  
  @PostMapping("/reserva/{cod}/cancelar")
  public ResponseEntity<ReservaDTO> cancelarReserva(@PathVariable String cod) {
    ReservaRead reservaRead = reservaReadRepository.findByCod(cod);
    if (reservaRead == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não existe!");
    }

    reservaRead.setEstado(EstadoReserva.CANCELADO);
    // Enfileira reembolsar milhas
    // Enfileira historico milhas
    // Enfileira atualizar reserva para cancelado
    ReservaDTO reservaDTO = modelMapper.map(reservaRead, ReservaDTO.class);
    return ResponseEntity.ok().body(reservaDTO);
  }
  
}
