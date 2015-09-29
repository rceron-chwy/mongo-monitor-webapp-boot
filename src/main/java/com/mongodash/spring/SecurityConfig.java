package com.mongodash.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import com.mongodash.auth.MongoDashAuthenticationProvider;
import com.mongodash.auth.MongoDashPersistentTokenRepository;
import com.mongodash.license.LicenseFilter;
import com.mongodash.service.SettingsService;
import com.mongodash.service.UserService;

@Configuration
@EnableWebMvcSecurity
@PropertySource("classpath:app.properties")
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.app.key}")
	String appKey;
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	SettingsService settings;	
	
	@Autowired
	MongoDashPersistentTokenRepository mongoDashPersistentTokenRepository;
	
	@Autowired
	MongoDashAuthenticationProvider mongoDashAuthenticationProvider;
	
	@Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.eraseCredentials(true)
        	.authenticationProvider(mongoDashAuthenticationProvider)
        	.authenticationProvider(rememberMeAuthenticationProvider());
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/assets/**")
				.antMatchers("/css/**")
				.antMatchers("/img/**")
				.antMatchers("/js/**")
				.antMatchers("/fonts/**")
				.antMatchers("/notifications/**");
				
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http		
			.authorizeRequests()
				.antMatchers("/login", "/loginCheck").permitAll()
				.antMatchers(HttpMethod.POST, "/api/**").permitAll()
				.antMatchers("/settings/license/**").permitAll()
				.antMatchers("/**").access("isRememberMe() or isFullyAuthenticated()")
				.and()
			.formLogin()
				.loginProcessingUrl("/loginCheck")
				.loginPage("/login")
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/login")
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login")
				.sessionFixation().newSession()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.csrf()
				.disable()
			.rememberMe()
				.rememberMeServices(rememberMeServices())
				.and()
			.addFilterBefore(new LicenseFilter(settings), ChannelProcessingFilter.class);
	}
	
	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(appKey);
	}
	
	@Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
		PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(appKey, userService,
				mongoDashPersistentTokenRepository);
		rememberMeServices.setCookieName("REMEMBER_ME");
		rememberMeServices.setParameter("remember");
		rememberMeServices.setTokenValiditySeconds(172800);
		return rememberMeServices;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(15);
	}
}
