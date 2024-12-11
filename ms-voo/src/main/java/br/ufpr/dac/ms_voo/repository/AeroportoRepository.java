package br.ufpr.dac.ms_voo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_voo.model.Aeroporto;

public interface AeroportoRepository extends JpaRepository<Aeroporto, String> {
  public Optional<Aeroporto> findByCod(String cod);
}
