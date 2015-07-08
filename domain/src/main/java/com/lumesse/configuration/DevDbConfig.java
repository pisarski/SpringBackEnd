package com.lumesse.configuration;

import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile({ "dev", "test" })
public class DevDbConfig {

	@Autowired
	private Environment env;

	@Profile("test")
	@Bean
	public DataSource testDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.build();
	}

	@Profile("dev")
	@Bean
	public BasicDataSource devDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:spittr_db/spittr_db");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		dataSource.setInitialSize(1);
		dataSource.setMaxActive(1);
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(isTestProfile());
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		return adapter;
	}

	private boolean isTestProfile() {
		return Arrays.asList(env.getActiveProfiles()).contains("test");
	}

}
