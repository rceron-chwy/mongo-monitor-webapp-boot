package com.mongodash.spring;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
		basePackages = { "com.mongodash" }, 
		excludeFilters = { @ComponentScan.Filter(Controller.class),
		@ComponentScan.Filter(Configuration.class) })
@PropertySource(value = { "classpath:app.properties" })
@EnableMBeanExport
@EnableAspectJAutoProxy
public class AppConfig {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("validation", "messages");
		return messageSource;
	}

	@Bean
	public PropertiesFactoryBean properties() {
		PropertiesFactoryBean ppc = new PropertiesFactoryBean();
		ppc.setLocations(new Resource[] { new ClassPathResource("app.properties") });
		ppc.setIgnoreResourceNotFound(false);
		return ppc;
	}
}
