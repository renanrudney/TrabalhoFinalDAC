package br.ufpr.dac.ms_reserva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
  public Optional<Reserva> findByCod(String cod);
}
