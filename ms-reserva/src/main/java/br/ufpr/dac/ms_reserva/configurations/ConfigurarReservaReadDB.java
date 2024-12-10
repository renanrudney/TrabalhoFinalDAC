package br.ufpr.dac.ms_reserva.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import br.ufpr.dac.ms_reserva.model.ReservaRead;


@Configuration
@EnableJpaRepositories(
  basePackages = "br.ufpr.dac.conta.read.repository",
  entityManagerFactoryRef = "reservaReadEntityManagerFactory",
  transactionManagerRef = "reservaReadTransactionManager"
)
public class ConfigurarReservaReadDB {
  @Bean(name="reservaReadProperties")
  @ConfigurationProperties("spring.datasource.reserva-read")
  public DataSourceProperties reservaReadDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name="reservaReadDataSource")
  @ConfigurationProperties("spring.datasource.reserva-read.configuration")
  public DataSource reservaReadDataSource() {
    return reservaReadDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean(name="reservaReadEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean reservaReadEntityManagerFactoryBean(
    EntityManagerFactoryBuilder builder
  ) {
    return builder.dataSource(reservaReadDataSource()).packages(ReservaRead.class).build();
  }

  @Bean(name="reservaReadTransactionManager")
  public PlatformTransactionManager reservaReadTransactionManager(
    final @Qualifier("reservaReadEntityManagerFactory") LocalContainerEntityManagerFactoryBean reservaReadEntityManagerFactoryBean
  ) {
    return new JpaTransactionManager(reservaReadEntityManagerFactoryBean.getObject());
  }
}
