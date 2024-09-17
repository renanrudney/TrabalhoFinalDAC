package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_reserva.LogReservaCUD;

public interface LogReservaCUDRepository extends JpaRepository<LogReservaCUD, Long>
{
	
}
