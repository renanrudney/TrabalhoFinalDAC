package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
    public Cliente findByCpf(String cpf);
}