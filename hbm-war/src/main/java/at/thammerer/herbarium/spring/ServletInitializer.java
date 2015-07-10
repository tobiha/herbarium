package at.thammerer.herbarium.spring;

import at.thammerer.herbarium.spring.configuration.SecurityConfiguration;
import at.thammerer.herbarium.spring.configuration.SpringConfig;
import org.springframework.boot.actuate.autoconfigure.ManagementSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;


@EnableAutoConfiguration(exclude = {
	LiquibaseAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class,
	ManagementSecurityAutoConfiguration.class})
@Import({SpringConfig.class, SecurityConfiguration.class})
public class ServletInitializer extends SpringBootServletInitializer {
	public ServletInitializer() {
	}

	public static void main(String[] args)  {
		SpringServiceApplication springServiceApplication = new SpringServiceApplication(ServletInitializer.class);
		springServiceApplication.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		SpringServiceApplication.configureWebApp(application);
		return application.sources(ServletInitializer.class);
	}
}
