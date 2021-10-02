package br.com.curso.api.rest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Classe para pegar o contexto do spring , todo que esta em memoria pode ser acessado chamando esta classe
 * @author andre
 *
 */
@Component
public class ApplicationContextLoad implements ApplicationContextAware{

	@Autowired
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	
		this.applicationContext = applicationContext;
		
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
