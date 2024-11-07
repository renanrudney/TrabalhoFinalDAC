package br.ufpr.dac.ms_auth.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

  @PostMapping("/login")
  ResponseEntity<UsuarioDTO> login(@RequestBody Login login) {
    Usuario usuario = repository.findUsuarioByLogin(login.getLogin());

    if (usuario != null) {
      if (usuario.getSenha().equals(login.getSenha())) {
        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok().body(usuarioDTO);
      }
    }

    return ResponseEntity.status(401).build();
  }
}
