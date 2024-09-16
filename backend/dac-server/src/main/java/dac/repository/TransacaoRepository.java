package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_cliente.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>
{
	
}
