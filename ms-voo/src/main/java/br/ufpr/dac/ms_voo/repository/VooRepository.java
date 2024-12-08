package br.ufpr.dac.ms_voo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_voo.model.Voo;


public interface VooRepository extends JpaRepository<Voo, String> {
  public Voo findByCod(String cod);
}
