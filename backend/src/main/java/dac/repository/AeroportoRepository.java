package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_voo.Aeroporto;

public interface AeroportoRepository extends JpaRepository<Aeroporto, String> 
{

}
