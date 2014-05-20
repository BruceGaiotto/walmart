package br.com.walmart.test;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import br.com.walmart.service.RoteirizadorService;

@ContextConfiguration(locations = { "classpath:/spring/spring-root.xml" })
public class RoteirizadorTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RoteirizadorService service;
    
    @PostConstruct
    public final void afterPropertiesSet() throws Exception {
	LocaleContextHolder.setLocale(new Locale("pt", "BR"));
    }

    @Test
    public void teste() {
	service.calculaRota("A", "D");
    }

}
