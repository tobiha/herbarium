package at.thammerer.herbarium.spring.configuration;

import at.thammerer.herbarium.spring.security.CsrfHeaderFilter;
import at.thammerer.herbarium.spring.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * @author thammerer
 */
@Configuration
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private SecurityUserDetailsService userDetailsService;

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().and()
			.authorizeRequests()
			.antMatchers("/**", "/index.html", "/views/public/**", "/css/**", "/font-awesome/**","/fonts/**", "/js/**", "/").permitAll().anyRequest()
			.authenticated()
			.and()
//			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
//			.csrf().csrfTokenRepository(csrfTokenRepository());
		.csrf().disable();
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
