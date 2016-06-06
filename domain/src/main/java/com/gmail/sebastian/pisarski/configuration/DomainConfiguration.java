package com.gmail.sebastian.pisarski.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.gmail.sebastian.pisarski.repository",
		"com.gmail.sebastian.pisarski.validator" })
@EnableTransactionManagement
public class DomainConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter);
		emfb.setPackagesToScan("com.gmail.sebastian.pisarski.entity");
		setJpaProperties(emfb);
		return emfb;
	}

	private void setJpaProperties(LocalContainerEntityManagerFactoryBean emfb) {
		Boolean dropDbOnStar = env.getProperty("db.dropDbOnStar",
				Boolean.class, false);

		if (Boolean.TRUE.equals(dropDbOnStar)) {
			Properties properties = new Properties();
			properties.setProperty("hibernate.hbm2ddl.auto", "create");
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf, DataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

	@Bean
	public LocalValidatorFactoryBean validatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

}
