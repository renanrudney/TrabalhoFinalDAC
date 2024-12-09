package br.ufpr.dac.ms_auth.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufpr.dac.ms_auth.model.Login;
import br.ufpr.dac.ms_auth.model.Usuario;
import br.ufpr.dac.ms_auth.model.UsuarioDTO;
import br.ufpr.dac.ms_auth.repository.UsuarioRepository;


@CrossOrigin
@RestController
public class AuthREST {
  @Autowired
  private UsuarioRepository repository;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  ResponseEntity<UsuarioDTO> login(@RequestBody Login login) {
    Usuario usuario = repository.findByLogin(login.getLogin());

    if (usuario != null) {
      if (passwordEncoder.matches(login.getSenha(), usuario.getSenha())) {
        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok().body(usuarioDTO);
      }
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas!");
  }
}
