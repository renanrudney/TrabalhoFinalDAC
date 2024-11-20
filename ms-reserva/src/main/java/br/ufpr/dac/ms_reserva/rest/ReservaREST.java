package br.ufpr.dac.ms_reserva.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.dac.ms_reserva.repository.ReservaRepository;

@CrossOrigin
@RestController
public class ReservaREST {
  @Autowired
  private ReservaRepository reservaRepository;
}
