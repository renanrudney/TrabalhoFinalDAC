package br.ufpr.dac.ms_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_cliente.model.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    public Optional<Cliente> findByCpf(String cpf);
    public Optional<Cliente> findByEmail(String email);
}
