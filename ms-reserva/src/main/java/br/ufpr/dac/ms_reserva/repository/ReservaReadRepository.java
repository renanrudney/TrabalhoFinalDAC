package br.ufpr.dac.ms_reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.ReservaRead;
import java.util.List;


public interface ReservaReadRepository  extends JpaRepository<ReservaRead, String>  {
  public List<ReservaRead> findByCod_voo(String cod_voo);
  public ReservaRead findByCod(String cod);
}
