package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Cliente;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    public Cliente findByCpf(String cpf);
    public Cliente findByEmail(String email);
}
