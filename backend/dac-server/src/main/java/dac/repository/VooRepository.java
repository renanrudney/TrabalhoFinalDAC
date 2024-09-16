package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_voo.Voo;

public interface VooRepository extends JpaRepository<Voo, String>
{
	
}
