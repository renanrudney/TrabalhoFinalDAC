package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_reserva.ReservaCUD;

public interface ReservaCUDRepository extends JpaRepository<ReservaCUD, String>
{
	
}
