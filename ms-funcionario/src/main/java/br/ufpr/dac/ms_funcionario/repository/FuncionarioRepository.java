package br.ufpr.dac.ms_funcionario.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpr.dac.ms_funcionario.model.Funcionario;

public  interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
  public List<Funcionario> findAllByOrderByNomeAsc();
  public Optional<Funcionario> findByCpf(String cpf);
  public Optional<Funcionario> findByEmail(String email);
}
