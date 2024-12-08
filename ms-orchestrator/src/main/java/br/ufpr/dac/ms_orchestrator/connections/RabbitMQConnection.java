package br.ufpr.dac.ms_orchestrator.connections;

import br.ufpr.dac.common.constants.RabbitMQConstants;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
  private static final String NOME_EXCHANGE = "amq.direct";
  private AmqpAdmin amqpAdmin;

  public RabbitMQConnection(AmqpAdmin amqpAdmin) {
    this.amqpAdmin = amqpAdmin;
  }

  private Queue novaFila(String nomeFila) {
		return new Queue(nomeFila, true, false, false);
	}
	
	private DirectExchange novaExchange() {
		return new DirectExchange(NOME_EXCHANGE);
	}
	
	private Binding relacionarFilaExchange(Queue fila, DirectExchange exchange) {
		return new Binding(fila.getName(), DestinationType.QUEUE, exchange.getName(),
				fila.getName(), null);
	}

  @PostConstruct
	private void criarFilas() {
		DirectExchange exchange = novaExchange();
		this.amqpAdmin.declareExchange(exchange);

		String[] nomeFilas = RabbitMQConstants.TODAS_FILAS;

		for (String nomeFila : nomeFilas) {
			Queue fila = this.novaFila(nomeFila);
			Binding binding = this.relacionarFilaExchange(fila, exchange);
			this.amqpAdmin.declareQueue(fila);
			this.amqpAdmin.declareBinding(binding);
		}
	}
}
