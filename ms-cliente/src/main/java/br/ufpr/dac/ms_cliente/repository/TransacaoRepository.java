package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Transacao;
import java.util.List;
import java.util.UUID;


public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
  List<Transacao> findByIdCliente(UUID idCliente);
}
