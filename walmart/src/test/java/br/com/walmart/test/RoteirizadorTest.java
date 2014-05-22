package br.com.walmart.test;

import java.util.Calendar;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import br.com.walmart.Rota;
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
    public void testeAB() {
	long timeInMillisBefore = Calendar.getInstance().getTimeInMillis();
	Rota rota = service.calculaRota("A", "B");
	System.out.println(rota + ". Tempo processamento: " + (Calendar.getInstance().getTimeInMillis() - timeInMillisBefore));
	
	Assert.assertNotNull(rota);
    }
    
    @Test
    public void testeAE() {
	long timeInMillisBefore = Calendar.getInstance().getTimeInMillis();
	Rota rota = service.calculaRota("A", "E");
	System.out.println(rota + ". Tempo processamento: " + (Calendar.getInstance().getTimeInMillis() - timeInMillisBefore));

	Assert.assertNotNull(rota);
    }
    
    @Test
    public void testeAC() {
	long timeInMillisBefore = Calendar.getInstance().getTimeInMillis();
	Rota rota = service.calculaRota("A", "C");
	System.out.println(rota + ". Tempo processamento: " + (Calendar.getInstance().getTimeInMillis() - timeInMillisBefore));
	
	Assert.assertNotNull(rota);
    }

}
