package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_reserva.EstadoReservaCUD;

public interface EstadoReservaCUDRepository extends JpaRepository<EstadoReservaCUD, Long>
{

}
