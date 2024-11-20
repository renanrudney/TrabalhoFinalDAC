package br.ufpr.dac.ms_reserva.read.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.read.model.ReservaRead;

public interface ReservaReadRepository extends JpaRepository<ReservaRead, String> {
  public ReservaRead findById(Long id);
}
