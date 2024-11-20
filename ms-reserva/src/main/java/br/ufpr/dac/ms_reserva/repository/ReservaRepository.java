package br.ufpr.dac.ms_reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, String> {

}
