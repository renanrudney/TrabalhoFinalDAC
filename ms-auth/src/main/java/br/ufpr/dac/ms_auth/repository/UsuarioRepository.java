package br.ufpr.dac.ms_auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufpr.dac.ms_auth.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
  Usuario findByLogin(String login);
}
