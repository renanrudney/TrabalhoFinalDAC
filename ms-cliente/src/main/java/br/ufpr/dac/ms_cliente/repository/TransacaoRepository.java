package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Cliente;
import br.ufpr.dac.ms_cliente.model.Transacao;
import java.util.List;


public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
  List<Transacao> findByCliente(Cliente cliente);
}
