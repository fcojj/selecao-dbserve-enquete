package com.github.enquete.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.enquete.service.UserFormatterService;



@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class WebConfig extends WebMvcConfigurerAdapter {

	// spring will automatically bind value of property
	@Value("${spring.datasource.url}")
	private String connectionUrl;

	@Value("${spring.datasource.username}")
	private String connectionUsername;
	

	/**
	 * Tratamento de erros de páginas, dependendo do error faz um request para o path especificado,
	 * a qual é tratada pelo {@link com.github.enquete.controller.ErrorPagesController}.
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
				new ErrorPage(HttpStatus.FORBIDDEN, "/403"), new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500")));
	}

	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter(
			FormattingConversionService conversionService) {
		return new DomainClassConverter<FormattingConversionService>(conversionService);
	}

	//override path "/" for "/vote/new"
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		 registry.addRedirectViewController("/", "/votes/new");
	}
	
	
	@Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        
        return registrationBean;
    }

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

		driverManagerDataSource.setDriverClassName("org.h2.Driver");
		driverManagerDataSource.setUrl(connectionUrl);
		driverManagerDataSource.setUsername(connectionUsername);
		driverManagerDataSource.setPassword("");

		return driverManagerDataSource;
	}
	
	@Override
	@Description("Conversion Service, from username in String to User and date formatter")
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new UserFormatterService());
		registry.addFormatter(new DateFormatter("dd/MM/yyyy"));
	}
}
