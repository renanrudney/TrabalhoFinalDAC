package br.ufpr.dac.ms_cliente.handlers;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufpr.dac.ms_cliente.dto.TransacaoDTO;
import br.ufpr.dac.ms_cliente.model.Transacao;
import br.ufpr.dac.ms_cliente.repository.TransacaoRepository;


@Component
public class TransacaoHandler {
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private TransacaoRepository transacaoRepository;

  private final static String FILA_RETIRAR_TRANSACAO = "RETIRAR_TRANSACAO";
  private final static String FILA_REEMBOLSAR_TRANSACAO = "REEMBOLSAR_TRANSACAO";

  @RabbitListener(queues = FILA_RETIRAR_TRANSACAO)
  private void retirarTransacao(String transacaoString) throws JsonProcessingException {
    TransacaoDTO transacaoDTO = objectMapper.readValue(transacaoString, TransacaoDTO.class);
    Transacao transacao = modelMapper.map(transacaoDTO, Transacao.class);
    transacao.setEntrada(true);
    transacao.getCliente().setMilhas(transacao.getCliente().getMilhas() - transacao.getQtdMilhas());
    transacaoRepository.saveAndFlush(transacao);
  }

  @RabbitListener(queues = FILA_REEMBOLSAR_TRANSACAO)
  private void reembolsarTransacao(String transacaoString) throws JsonProcessingException {
    TransacaoDTO transacaoDTO = objectMapper.readValue(transacaoString, TransacaoDTO.class);
    Transacao transacao = modelMapper.map(transacaoDTO, Transacao.class);
    transacao.setEntrada(true);
    transacao.getCliente().setMilhas(transacao.getCliente().getMilhas() + transacao.getQtdMilhas());
    transacaoRepository.saveAndFlush(transacao);
  }
}
