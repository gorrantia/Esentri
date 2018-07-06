package ch.rhb.integra.mitarbeiter.trapeze;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "trapezeEntityManagerFactory",
transactionManagerRef = "trapezeTransactionManager",
    basePackages = {"ch.rhb.integra.mitarbeiter.trapeze.repo"})
public class TrapezeDBConfig {

 
  @Bean(name = "trapezeDataSource")
  @ConfigurationProperties(prefix = "trapeze.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  
  @Bean(name = "trapezeEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("trapezeDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("ch.rhb.integra.mitarbeiter.trapeze.domain")
        .persistenceUnit("trapeze").build();
  }

 
  @Bean(name = "trapezeTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("trapezeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
