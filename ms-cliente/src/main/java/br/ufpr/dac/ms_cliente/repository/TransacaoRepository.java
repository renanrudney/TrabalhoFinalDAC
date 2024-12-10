package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
