package br.ufpr.dac.ms_voo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_voo.model.Aeroporto;

public interface AeroportoRepository extends JpaRepository<Aeroporto, String> {
  public Aeroporto findByCod(String cod);
}
