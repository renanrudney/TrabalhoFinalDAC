package br.ufpr.dac.ms_orchestrator;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

@SpringBootApplication
public class MsOrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrchestratorApplication.class, args);
	}

	@Bean
	public MessageConverter jsonToMapMessageConverter() {
			DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
			defaultClassMapper.setTrustedPackages("br.ufpr.dac.ms_orchestrator.dto"); // trusted packages
			Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
			jackson2JsonMessageConverter.setSupportedContentType(MediaType.ALL);
			jackson2JsonMessageConverter.setSupportedContentType(MediaType.APPLICATION_JSON);
			jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
			return jackson2JsonMessageConverter;
	}
}
