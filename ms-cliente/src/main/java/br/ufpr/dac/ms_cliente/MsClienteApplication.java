package br.ufpr.dac.ms_cliente;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

@SpringBootApplication
public class MsClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClienteApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

		@Bean
	public MessageConverter jsonToMapMessageConverter() {
			DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
			defaultClassMapper.setTrustedPackages("br.ufpr.dac.ms_cliente.dto"); // trusted packages
			Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
			jackson2JsonMessageConverter.setSupportedContentType(MediaType.ALL);
			jackson2JsonMessageConverter.setSupportedContentType(MediaType.APPLICATION_JSON);
			jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
			return jackson2JsonMessageConverter;
	}
}
