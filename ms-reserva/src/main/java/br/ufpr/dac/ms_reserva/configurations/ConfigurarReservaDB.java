package br.ufpr.dac.ms_reserva.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import br.ufpr.dac.ms_reserva.model.Reserva;

@Configuration
@EnableJpaRepositories(
  basePackages = "br.ufpr.dac.ms_reserva.repository",
  entityManagerFactoryRef = "reservaEntityManagerFactory",
  transactionManagerRef = "reservaTransactionManager"
)
public class ConfigurarReservaDB {
  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.reserva")
  public DataSourceProperties reservaDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.reserva.configuration")
  public DataSource reservaDataSource() {
    return reservaDataSourceProperties().initializeDataSourceBuilder()
      .type(HikariDataSource.class).build();
  }

  @Primary
  @Bean(name="reservaEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean reservaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(reservaDataSource()).packages(Reserva.class).build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager reservaTransactionManager(
    final @Qualifier("reservaTransactionManager") LocalContainerEntityManagerFactoryBean reservaEntityManagerFactory
  ) {
    return new JpaTransactionManager(reservaEntityManagerFactory.getObject());
  }
}
