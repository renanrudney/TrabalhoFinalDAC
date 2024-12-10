package br.ufpr.dac.ms_auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufpr.dac.ms_auth.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
  Optional<Usuario> findByLogin(String login);
}
