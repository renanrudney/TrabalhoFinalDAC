package dac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_cliente.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>
{

}
