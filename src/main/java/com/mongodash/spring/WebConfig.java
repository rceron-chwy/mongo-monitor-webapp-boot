package com.mongodash.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mongodash.interceptor.ApiInterceptor;
import com.mongodash.interceptor.MDCInterceptor;
import com.mongodash.interceptor.ModelInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(
	useDefaultFilters = false, 
	basePackages = { "com.mongodash.controller" }, 
	includeFilters = { @ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class) })
@PropertySource("classpath:app.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	ApiInterceptor apiInterceptor;
	
	@Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(31556926);
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor);
		registry.addInterceptor(new MDCInterceptor());
		registry.addInterceptor(new ModelInterceptor());
	}
	
}
