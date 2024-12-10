package br.ufpr.dac.ms_reserva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.EstadoReserva;

public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, String> {
Optional<EstadoReserva> findBySigla(String sigla);
}
