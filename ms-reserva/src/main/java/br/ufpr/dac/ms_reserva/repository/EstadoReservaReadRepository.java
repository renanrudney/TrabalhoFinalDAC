package br.ufpr.dac.ms_reserva.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.EstadoReservaRead;


public interface EstadoReservaReadRepository extends JpaRepository<EstadoReservaRead, String> {
  Optional<EstadoReservaRead> findBySigla(String sigla);
}
