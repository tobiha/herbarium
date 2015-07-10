package at.thammerer.herbarium.spring.configuration;

import at.thammerer.herbarium.util.DateAsISO8601StringObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

@ComponentScan({"at.thammerer.herbarium"})
@EnableConfigurationProperties(JpaProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
public class SpringConfig {

	// emf for bsm_data
	@Bean(name = "entityManagerFactory")
	@DependsOn("mainLiquibase")
	public org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean entityManagerFactory(
		JpaProperties jpa, DataSource dataSource){
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpa, null)
			.dataSource(dataSource)
			.packages("at.thammerer.herbarium.model")
			.persistenceUnit("hbm-unit")
			.build();
	}

	@Bean
	public TransactionTemplate transactionTemplate(JpaTransactionManager transactionManager) {
		return new TransactionTemplate(transactionManager);
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper dozerBean() {
		List<String> mappingFiles = Arrays.asList(
			"dozer-global-configuration.xml",
			"dozer-bean-mappings.xml"
		);

		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}


	@Bean
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	// liquibase for bsm_data
	@Bean(name = "mainLiquibase")
	public SpringLiquibase liquibase(DataSource dataSource, Environment env) {
		SpringLiquibase springLiquibase = new SpringLiquibase();

		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:db-changelog-master.xml");
		springLiquibase.setDefaultSchema(env.getRequiredProperty("spring.jpa.properties.hibernate.default_schema"));

		return springLiquibase;
	}

		// data source for ResolverMock
	@Bean
	public DataSource dataSource(Environment env) {
		DataSource ds = new DataSource();
		ds.setDriverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
		ds.setUsername(env.getRequiredProperty("spring.datasource.username"));
		ds.setPassword(env.getRequiredProperty("spring.datasource.password"));

		ds.setMaxIdle(env.getRequiredProperty("spring.datasource.maxIdle", Integer.class));
		ds.setMaxWait(env.getRequiredProperty("spring.datasource.maxWait", Integer.class));
		ds.setValidationQuery(env.getProperty("spring.datasource.validation-query"));
		ds.setTestWhileIdle(env.getRequiredProperty("spring.datasource.test-while-idle", Boolean.class));

		ds.setUrl(env.getRequiredProperty("spring.datasource.url"));

		return ds;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new DateAsISO8601StringObjectMapper();
	}




}
