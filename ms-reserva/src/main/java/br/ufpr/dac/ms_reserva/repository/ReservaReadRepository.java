package br.ufpr.dac.ms_reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_reserva.model.ReservaRead;
import java.util.List;
import java.util.Optional;


public interface ReservaReadRepository  extends JpaRepository<ReservaRead, String>  {
  public List<ReservaRead> findByCod_voo(String cod_voo);
  public Optional<ReservaRead> findByCod(String cod);
}
