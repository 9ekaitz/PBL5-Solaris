package eus.solaris.solaris;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import eus.solaris.solaris.controller.filters.MultiPanelDataFilter;
import eus.solaris.solaris.controller.filters.SinglePanelDataFilter;

@SpringBootApplication
public class SolarisApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SolarisApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("es", "es"));
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public FilterRegistrationBean<MultiPanelDataFilter> multiPanelDataFilter() {
		FilterRegistrationBean<MultiPanelDataFilter> registrationBean = new FilterRegistrationBean<>();
		MultiPanelDataFilter multiPanelDataFilter = new MultiPanelDataFilter();
		registrationBean.setFilter(multiPanelDataFilter);
		registrationBean.addUrlPatterns("/api/user-panel/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<SinglePanelDataFilter> singlePanelDataFilter() {
		FilterRegistrationBean<SinglePanelDataFilter> registrationBean = new FilterRegistrationBean<>();
		SinglePanelDataFilter singlePanelDataFilter = new SinglePanelDataFilter();
		registrationBean.setFilter(singlePanelDataFilter);
		registrationBean.addUrlPatterns("/api/panel/*");
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

}
