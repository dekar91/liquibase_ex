package ru.team42.Analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class AnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerApplication.class, args);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

//		dataSource.setDriverClassName("com.postgres.cj.jdbc.Driver");
		dataSource.setUsername("postgres");
		dataSource.setPassword("qwerty");
		dataSource.setUrl(
				"jdbc:postgresql://localhost:5432/test?createDatabaseIfNotExist=true");

		return dataSource;
	}

}
