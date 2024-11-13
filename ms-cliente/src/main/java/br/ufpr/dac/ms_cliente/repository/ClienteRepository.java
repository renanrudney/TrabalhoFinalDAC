package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
    public Cliente findByCpf(String cpf);
}
