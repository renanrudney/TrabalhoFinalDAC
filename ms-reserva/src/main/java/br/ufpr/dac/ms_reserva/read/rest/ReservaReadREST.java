package br.ufpr.dac.ms_reserva.read.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.dac.ms_reserva.read.repository.ReservaReadRepository;

@CrossOrigin
@RestController
public class ReservaReadREST {
  @Autowired
  private ReservaReadRepository reservaReadRepository;
}
