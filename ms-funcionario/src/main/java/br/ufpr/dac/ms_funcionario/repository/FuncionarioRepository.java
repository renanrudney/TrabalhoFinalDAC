package br.ufpr.dac.ms_funcionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dac.entity.ms_funcionario.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>
{
	
}
