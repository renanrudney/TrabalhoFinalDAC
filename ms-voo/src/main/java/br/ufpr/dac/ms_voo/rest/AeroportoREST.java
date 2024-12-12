package br.ufpr.dac.ms_voo.rest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufpr.dac.ms_voo.dto.AeroportoDTO;
import br.ufpr.dac.ms_voo.model.Aeroporto;
import br.ufpr.dac.ms_voo.repository.AeroportoRepository;

@CrossOrigin
@RestController
public class AeroportoREST {
  @Autowired
  private AeroportoRepository aeroportoRepository;
  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/aeroporto")
  public ResponseEntity<List<AeroportoDTO>> listarAeroporto() {
    List<Aeroporto> aeroportos = aeroportoRepository.findAll();
    List<AeroportoDTO> list = new ArrayList<>();

    for (Aeroporto aeroporto : aeroportos)
      list.add(modelMapper.map(aeroporto, AeroportoDTO.class));

    return ResponseEntity.ok().body(list);
  }
}
