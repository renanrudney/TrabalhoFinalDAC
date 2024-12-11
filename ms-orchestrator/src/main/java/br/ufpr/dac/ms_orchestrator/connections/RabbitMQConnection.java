package br.ufpr.dac.ms_orchestrator.connections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import br.ufpr.dac.ms_orchestrator.rabbitmq.config.RabbitMQConstants;
import jakarta.annotation.PostConstruct;

@Configuration
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
